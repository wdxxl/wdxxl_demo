package com.wdxxl.lucene.grouping;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.CachingCollector;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiCollector;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.grouping.GroupDocs;
import org.apache.lucene.search.grouping.SearchGroup;
import org.apache.lucene.search.grouping.TermAllGroupsCollector;
import org.apache.lucene.search.grouping.TermFirstPassGroupingCollector;
import org.apache.lucene.search.grouping.TermSecondPassGroupingCollector;
import org.apache.lucene.search.grouping.TopGroups;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.wdxxl.lucene.util.LuceneUtil;

/* http://iamyida.iteye.com/blog/2202651 */
public class LuceneGroupingDemo {
	private static Directory directory;
	private IndexReader reader;

	static {
		try {
			directory = FSDirectory.open(new File(System.getProperty("user.dir") + "/lib/lucene35/grouping"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		LuceneGroupingDemo luceneGrouping = new LuceneGroupingDemo();
		luceneGrouping.index();
		System.out.println("-----------------------------------------");
		try {
			luceneGrouping.searchGrouping();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void searchGrouping() throws IOException {
		TermQuery query = new TermQuery(new Term("content", "random"));
		// 搜索相似度最高的3条记录并且分组
		int topNGroups = 3; // 前N条中分组 去几个分组
		int groupOffset = 0; // 分组起始偏移量
		boolean fillFields = true; // 是否填充SearchGroup的sortValues
		Sort docSort = Sort.RELEVANCE; // 每个分组内部的排序规则
		Sort groupSort = docSort;
		int docOffset = 0; // 用于组内分页，起始的偏移量
		int docsPerGroup = 2;// 每组返回多少条结果
		boolean requiredTotalGroupCount = true; // 是否需要计算总的组的数量
		boolean cacheScores = true; // 是否需要缓存评分
		double maxCacheRAMMB = 16.0; // 第一次查询缓存容量的大小：设置为16M

		TermFirstPassGroupingCollector c1 = new TermFirstPassGroupingCollector("author", groupSort,
				groupOffset + topNGroups);
		/**
		 * 将TermFirstPassGroupingCollector包装成CachingCollector, 为第一次查询加缓存，避免重复评分
		 **/
		CachingCollector cachedCollector = CachingCollector.create(c1, cacheScores, maxCacheRAMMB);

		// 开始第一次分组统计
		getSeacher().search(query, cachedCollector);
		/** 第一次查询返回的结果集TopGroups中只有分组域以及每组的评分，至于每个分组里面有几条，分别哪些索引文档，则需要进行第二次查询获取 **/
		Collection<SearchGroup<String>> topGroups = c1.getTopGroups(groupOffset, fillFields);

		if (topGroups == null) {
			System.out.println("No groups matched.");
			return;
		}

		Collector secondPassCollector = null;
		boolean getScores = true; // 是否获取每个分组内部每个索引的评分
		boolean getMaxScores = true; // 是否计算最大评分

		TermSecondPassGroupingCollector c2 = new TermSecondPassGroupingCollector("author", topGroups, groupSort,
				docSort, docOffset + docsPerGroup, getScores, getMaxScores, fillFields);

		// 如果需要计算总的分组数量，则需要把TermSecondPassGroupingCollector包装成TermAllGroupsCollector
		TermAllGroupsCollector allGroupsCollector = null;
		// 若需要统计总的分组数量
		if (requiredTotalGroupCount) {
			allGroupsCollector = new TermAllGroupsCollector("author");
			secondPassCollector = MultiCollector.wrap(c2, allGroupsCollector);
		} else {
			secondPassCollector = c2;
		}

		/** 如果第一次查询已经加了缓存，则直接从缓存中取 **/
		if (cachedCollector.isCached()) {
			cachedCollector.replay(secondPassCollector); // 第二次查询直接从缓存中取
		} else {
			getSeacher().search(query, secondPassCollector); // 开始第二次分组查询
		}

		int totalGroupCount = 0; // 所有组的数量
		int totalHitCount = 0; // 所有满足条件的记录数
		int totalGroupedHitCount = -1; // 所有组内满足条件的记录数(通常该值与TotalHitCount是一致的)
		if (requiredTotalGroupCount) {
			totalGroupCount = allGroupsCollector.getGroupCount();
		}

		// 打印总的分组数量
		System.out.println("GroupCount:" + totalGroupCount);

		TopGroups<String> groupsResult = c2.getTopGroups(docOffset);
		totalHitCount = groupsResult.totalHitCount;
		totalGroupedHitCount = groupsResult.totalGroupedHitCount;
		System.out.println("groupsResult.totalHitCount: " + totalHitCount);
		System.out.println("groupsResult.totalGroupedHitCount: " + totalGroupedHitCount);
		System.out.println("////////////////////////////////////");

		// 下面打印的是第二次查询的统计结果，如果你仅仅只需要第一次查询的统计结果信息，不需要每个分组内部的详细信息，则不需要进行第二次查询，请知晓！
		int groupIdx = 0;
		// 迭代组
		for (GroupDocs<String> groupDocs : groupsResult.groups) {
			groupIdx++;
			String groupVL = groupDocs.groupValue == null ? "分组域的域值为空" : new String(groupDocs.groupValue.getBytes());
			// 分组域的域值，groupIdx表示组的索引即第几组
			System.out.println("group[" + groupIdx + "].groupFieldValue: " + groupVL);
			System.out.println("group[" + groupIdx + "].totalHits: " + groupDocs.totalHits);

			int docIdx = 0;
			// 迭代组内的记录
			for (ScoreDoc scoreDoc : groupDocs.scoreDocs) {
				docIdx++;
				// 打印分组内部每条记录的索引文档ID及其评分
				System.out.println(
						"group[" + groupIdx + "][" + docIdx + "]{docId:Score}: " + scoreDoc.doc + "/" + scoreDoc.score);
				// 根据docID可以获取到整个Document对象，通过doc.get(fieldName)可以获取某个存储域的域值
				// 注意searcher.doc根据docID返回的document对象中不包含docValuesField域的域值，只包含非docValuesField域的域值，请知晓！
				Document doc = getSeacher().doc(scoreDoc.doc);
				System.out.println("group[" + groupIdx + "][" + docIdx + "]{docId:author}: " + doc.get("author") + ":"
						+ doc.get("id") + ":" + doc.get("content"));
			}
			System.out.println("***********************************************");
		}
	}

	public void index() {
		IndexWriter writer = null;
		try {
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35));
			writer = new IndexWriter(directory, iwc);

			Document doc = new Document();
			doc.add(new Field("author", "author1", Field.Store.YES, Field.Index.NOT_ANALYZED));
			doc.add(new Field("content", "random text", Field.Store.YES, Field.Index.ANALYZED));
			doc.add(new Field("id", "1", Field.Store.YES, Field.Index.NOT_ANALYZED));
			writer.addDocument(doc);

			doc = new Document();
			doc.add(new Field("author", "author1", Field.Store.YES, Field.Index.NOT_ANALYZED));
			doc.add(new Field("content", "some more random text", Field.Store.YES, Field.Index.ANALYZED));
			doc.add(new Field("id", "2", Field.Store.YES, Field.Index.NOT_ANALYZED));
			writer.addDocument(doc);

			doc = new Document();
			doc.add(new Field("author", "author1", Field.Store.YES, Field.Index.NOT_ANALYZED));
			doc.add(new Field("content", "some more random textual data", Field.Store.YES, Field.Index.ANALYZED));
			doc.add(new Field("id", "3", Field.Store.YES, Field.Index.NOT_ANALYZED));
			writer.addDocument(doc);

			doc = new Document();
			doc.add(new Field("author", "author2", Field.Store.YES, Field.Index.NOT_ANALYZED));
			doc.add(new Field("content", "some random text", Field.Store.YES, Field.Index.ANALYZED));
			doc.add(new Field("id", "4", Field.Store.YES, Field.Index.NOT_ANALYZED));
			writer.addDocument(doc);

			doc = new Document();
			doc.add(new Field("author", "author3", Field.Store.YES, Field.Index.NOT_ANALYZED));
			doc.add(new Field("content", "some more random text", Field.Store.YES, Field.Index.ANALYZED));
			doc.add(new Field("id", "5", Field.Store.YES, Field.Index.NOT_ANALYZED));
			writer.addDocument(doc);

			doc = new Document();
			doc.add(new Field("author", "author3", Field.Store.YES, Field.Index.NOT_ANALYZED));
			doc.add(new Field("content", "random", Field.Store.YES, Field.Index.ANALYZED));
			doc.add(new Field("id", "6", Field.Store.YES, Field.Index.NOT_ANALYZED));
			writer.addDocument(doc);

			doc = new Document();
			doc.add(new Field("content", "random word stuck in alot of other text", Field.Store.YES,
					Field.Index.ANALYZED));
			doc.add(new Field("id", "7", Field.Store.YES, Field.Index.NOT_ANALYZED));
			writer.addDocument(doc);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			LuceneUtil.closeWriter(writer);
		}
	}

	private IndexSearcher getSeacher() {
		try {
			if (reader == null) {
				reader = IndexReader.open(directory, false);// 非只读
			} else {
				// Index是单例的话需要设置-- 因为是同一个Reader 所以部分删除的数据并不能知道消失掉
				IndexReader tr = IndexReader.openIfChanged(reader);// 为了打开一个新的Reader.
				if (tr != null) {
					reader.close();
					reader = tr;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new IndexSearcher(reader);
	}

}
