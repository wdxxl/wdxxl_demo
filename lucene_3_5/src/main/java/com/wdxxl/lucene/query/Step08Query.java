package com.wdxxl.lucene.query;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.wdxxl.lucene.util.LuceneIndexReaderUtil;

public class Step08Query {
	private static final String PATH = "/lib/lucene35/query";
	private static Directory directory;

	static {
		try {
			directory = FSDirectory.open(new File(System.getProperty("user.dir") + PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// LuceneCreateIndexUtil.createIndexbyPath(PATH);
		Step08Query step08Query = new Step08Query();
		// step08Query.searchByPrefixQyery();
		step08Query.searchByPrefixQyerySortByAttach();
		// step08Query.searchByWildcardQuery();
		// step08Query.searchByBooleanQuery();
		// step08Query.searchByPhraseQuery();
		// step08Query.searchByFuzzyQuery();
	}

	// 1. 前缀搜索 searchByPrefix Sorting asc
	public void searchByPrefixQyerySortByAttach() {
		try {
			// 单例获取IndexReader
			IndexReader reader = LuceneIndexReaderUtil.getIndexReader(directory);
			Query query = new PrefixQuery(new Term("name", "j"));
			outputSort(new IndexSearcher(reader), query);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 * (5-1.0)email:ff@sina.org,name:jake,attach:6,date:1368342000000
		 * (3-1.0)email:dd@sina.org,name:jetty,attach:4,date:1463036400000
		 * (2-1.0)email:cc@wdxxl.com,name:john,attach:1,date:1431414000000
		 */
	}

	// 1. 前缀搜索 searchByPrefix
	public void searchByPrefixQyery() {
		try {
			// 单例获取IndexReader
			IndexReader reader = LuceneIndexReaderUtil.getIndexReader(directory);
			Query query = new PrefixQuery(new Term("name", "j"));
			output(new IndexSearcher(reader), query);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 * (2-1.0)email:cc@wdxxl.com,name:john,attach:1,date:1431414000000
		 * (3-1.0)email:dd@sina.org,name:jetty,attach:4,date:1463036400000
		 * (5-1.0)email:ff@sina.org,name:jake,attach:6,date:1368342000000
		 */
	}

	// 2. 通配符搜索 WildcardQuery
	public void searchByWildcardQuery() {
		try {
			// 单例获取IndexReader
			IndexReader reader = LuceneIndexReaderUtil.getIndexReader(directory);
			Query query = new WildcardQuery(new Term("name", "j???"));
			output(new IndexSearcher(reader), query);
			System.out.println("-------------");
			query = new WildcardQuery(new Term("email", "*@wdxxl.com"));
			output(new IndexSearcher(reader), query);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 3. 可以连接多个条件BooleanQuery
	public void searchByBooleanQuery() {
		try {
			// 单例获取IndexReader
			IndexReader reader = LuceneIndexReaderUtil.getIndexReader(directory);
			BooleanQuery query = new BooleanQuery();
			query.add(new TermQuery(new Term("name", "zhangsan")), Occur.SHOULD);
			query.add(new TermQuery(new Term("content", "football")), Occur.SHOULD);
			query.add(new TermQuery(new Term("content", "basketball")), Occur.MUST_NOT);
			output(new IndexSearcher(reader), query);
			/*
			 * (0-0.81665707)email:aa@wdxxl.com,name:zhangsan,attach:2,date:
			 * 1368342000000
			 * (3-0.1328938)email:dd@sina.org,name:jetty,attach:4,date:
			 * 1463036400000
			 */
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 4. 短语查询 PhraseQuery (I like football)
	// slop 是间隔 比如该例子是间隔一个单词 （分词主要用空格，在英文中）
	public void searchByPhraseQuery() {
		try {
			// 单例获取IndexReader
			IndexReader reader = LuceneIndexReaderUtil.getIndexReader(directory);
			PhraseQuery query = new PhraseQuery();
			query.setSlop(1);
			query.add(new Term("content", "i"));
			query.add(new Term("content", "football"));
			output(new IndexSearcher(reader), query);
			/*
			 * (4-1.0166318)email:ee@sinobot.org,name:mike,attach:5,date:
			 * 1494572400000
			 * (3-0.5083159)email:dd@sina.org,name:jetty,attach:4,date:
			 * 1463036400000
			 */
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 5. 模糊查询 FuzzyQuery
	public void searchByFuzzyQuery() {
		try {
			// 单例获取IndexReader
			IndexReader reader = LuceneIndexReaderUtil.getIndexReader(directory);
			FuzzyQuery query = new FuzzyQuery(new Term("name", "mik4"), 0.4f, 0);
			output(new IndexSearcher(reader), query);
			/*
			 * (4-2.0986123)email:ee@sinobot.org,name:mike,attach:5,date:
			 * 1494572400000
			 */
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void output(IndexSearcher searcher, Query query) throws CorruptIndexException, IOException {
		TopDocs tds = searcher.search(query, 10);
		print(searcher, tds.scoreDocs);
	}

	private void outputSort(IndexSearcher searcher, Query query) throws CorruptIndexException, IOException {
		Sort sort = new Sort(new SortField("date", SortField.STRING_VAL, true)); // reverse is false = asc / true = desc
		TopDocs tds = searcher.search(query, 10, sort);
		print(searcher, tds.scoreDocs);
	}

	private void print(IndexSearcher searcher, ScoreDoc[] docs) throws CorruptIndexException, IOException {
		for (ScoreDoc sd : docs) {
			Document doc = searcher.doc(sd.doc);
			// sd.score 评分有对应的公式：加权值和文档所出现的次数有关
			System.out.println("(" + sd.doc + "-" + sd.score + ")" + "email:" + doc.get("email") + ",name:"
					+ doc.get("name") + ",attach:" + doc.get("attach") + ",date:" + doc.get("date"));
		}
	}
}