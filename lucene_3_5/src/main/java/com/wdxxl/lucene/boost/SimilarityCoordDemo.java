package com.wdxxl.lucene.boost;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.DefaultSimilarity;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Similarity;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

// 继承并实现自己的Similarity
/**
 * 当coord返回1，不起作用的时候，文档一虽然包含了两个搜索词common和world，但由于world的所在的文档数太多，而文档二包含common的次数比较多，因而文档二分数较高：
 *
 * docid: 1, score:1.9059997, contents:common common common
 * docid: 0, score:1.2936771, contents:common hello world
 * 而当coord起作用的时候，文档一由于包含了两个搜索词而分数较高：
 * docid: 0, score:1.2936771, contents:common hello world
 * docid: 1, score:0.95299983, contents:common common common
 * @author wdxxl
 *
 */
public class SimilarityCoordDemo {
	public static void main(String[] args) throws Exception {
		System.out.println("当coord返回1，不起作用的时候，文档一虽然包含了两个搜索词common和world，但由于world的所在的文档数太多，而文档二包含common的次数比较多，因而文档二分数较高：");
		new SimilarityCoordDemo().testCoord(new MySimilarity());
		System.out.println("而当coord起作用的时候，文档一由于包含了两个搜索词而分数较高：");
		new SimilarityCoordDemo().testCoord(new DefaultSimilarity());
	}

	// 一次搜索可能包含多个搜索词，而一篇文档中也可能包含多个搜索词，此项表示，当一篇文档中包含的搜索词越多，则此文档则打分越高。
	private void testCoord(Similarity similarity) throws Exception {
		Directory dir = new RAMDirectory();
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
		IndexWriter writer = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_35, analyzer));
		Document doc1 = new Document();
		Field f1 = new Field("contents", "common hello world", Field.Store.YES, Field.Index.ANALYZED);
		doc1.add(f1);
		writer.addDocument(doc1);
		Document doc2 = new Document();
		Field f2 = new Field("contents", "common common common", Field.Store.YES, Field.Index.ANALYZED);
		doc2.add(f2);
		writer.addDocument(doc2);
		for (int i = 0; i < 10; i++) {
			Document doc3 = new Document();
			Field f3 = new Field("contents", "world", Field.Store.YES, Field.Index.ANALYZED);
			doc3.add(f3);
			writer.addDocument(doc3);
		}
		writer.close();

		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		searcher.setSimilarity(similarity);
		QueryParser parser = new QueryParser(Version.LUCENE_35, "contents", new StandardAnalyzer(Version.LUCENE_35));
		Query query = parser.parse("common world");
		TopDocs docs = searcher.search(query, 2);
		for (ScoreDoc doc : docs.scoreDocs) {
			System.out.println("docid: " + doc.doc + ", score:" + doc.score + ", contents:"
					+ searcher.doc(doc.doc).get("contents"));
		}
	}
}

class MySimilarity extends DefaultSimilarity {
	private static final long serialVersionUID = 1L;

	@Override
	public float coord(int overlap, int maxOverlap) {
		return 1;
		// return overlap / (float)maxOverlap;
	}
}
