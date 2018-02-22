package com.wdxxl.lucene.boost;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 * http://forfuture1978.iteye.com/blog/591804 在索引阶段设置Document Boost和Field
 * Boost，存储在(.nrm)文件中。
 *
 * @author wdxxl
 * testNormsDocBoost----------------------------------------------------
 * 文档的域f1也为Field.Index.ANALYZED_NO_NORMS的时候
 * docid: 2, score:1.2337708, contents:common common common
 * docid: 1, score:1.0073696, contents:common common hello
 * docid: 0, score:0.71231794, contents:common hello hello
 * 文档的域f1设为Field.Index.ANALYZED
 * docid: 0, score:39.889805, contents:common hello hello
 * docid: 2, score:0.6168854, contents:common common common
 * docid: 1, score:0.5036848, contents:common common hello
 * testNormsFieldBoost----------------------------------------------------
 * 文档的域f1也为Field.Index.ANALYZED_NO_NORMS的时候
 * docid: 1, score:0.8407992, contents:common common hello
 * docid: 0, score:0.5945348, contents:common hello hello
 * 文档的域f1设为Field.Index.ANALYZED
 * docid: 0, score:33.29395, contents:common hello hello
 * docid: 1, score:0.4203996, contents:common common hello
 * testNormLength----------------------------------------------------
 * 当norms被禁用的时候，包含两个common的第二篇文档打分较高：
 * docid: 1, score:0.13928263, contents:common common hello hello hello hello
 * docid: 0, score:0.09848769, contents:common hello
 * 当norms起作用的时候，虽然包含两个common的第二篇文档，由于长度较长，因而打分较低：
 * docid: 0, score:0.061554804, contents:common hello
 * docid: 1, score:0.052230984, contents:common common hello hello hello hello
 * testQueryBoost----------------------------------------------------
 * 根据tf/idf，包含两个common2的第二篇文档打分较高：
 * docid: 1, score:0.24999999, contents:common2 common2 hello
 * docid: 0, score:0.22097087, contents:common1 hello
 * 如果我们输入的查询语句为："common1^100 common2"，则第一篇文档打分较高：
 * docid: 0, score:0.31248438, contents:common1 hello
 * docid: 1, score:0.0035353568, contents:common2 common2 hello
 */
public class NormsDemo {

	public static void main(String[] args) throws Exception {
		NormsDemo normsDemo = new NormsDemo();
		System.out.println("testNormsDocBoost----------------------------------------------------");
		System.out.println("文档的域f1也为Field.Index.ANALYZED_NO_NORMS的时候");
		normsDemo.testNormsDocBoost(Field.Index.ANALYZED_NO_NORMS);
		System.out.println("文档的域f1设为Field.Index.ANALYZED");
		normsDemo.testNormsDocBoost(Field.Index.ANALYZED);
		System.out.println("testNormsFieldBoost----------------------------------------------------");
		System.out.println("文档的域f1也为Field.Index.ANALYZED_NO_NORMS的时候");
		normsDemo.testNormsFieldBoost(Field.Index.ANALYZED_NO_NORMS);
		System.out.println("文档的域f1设为Field.Index.ANALYZED");
		normsDemo.testNormsFieldBoost(Field.Index.ANALYZED);
		System.out.println("testNormLength----------------------------------------------------");
		System.out.println("当norms被禁用的时候，包含两个common的第二篇文档打分较高：");
		normsDemo.testNormLength(Field.Index.ANALYZED_NO_NORMS, Field.Index.ANALYZED_NO_NORMS);
		System.out.println("当norms起作用的时候，虽然包含两个common的第二篇文档，由于长度较长，因而打分较低：");
		normsDemo.testNormLength(Field.Index.ANALYZED, Field.Index.ANALYZED_NO_NORMS);
		System.out.println("testQueryBoost----------------------------------------------------");
		System.out.println("根据tf/idf，包含两个common2的第二篇文档打分较高：");
		normsDemo.testQueryBoost("common1 common2");
		System.out.println("如果我们输入的查询语句为：\"common1^100 common2\"，则第一篇文档打分较高：");
		normsDemo.testQueryBoost("common1^100 common2");
	}

	private void testNormsDocBoost(Field.Index fieldIndex) throws Exception{
		Directory dir = new RAMDirectory();
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
		IndexWriter writer = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_35, analyzer));
		Document doc1 = new Document();
		Field f1 = new Field("contents", "common hello hello", Field.Store.YES, fieldIndex);
		doc1.add(f1);
		doc1.setBoost(100); // doc setBoost
		writer.addDocument(doc1);
		Document doc2 = new Document();
		Field f2 = new Field("contents", "common common hello", Field.Store.YES, Field.Index.ANALYZED_NO_NORMS);
		doc2.add(f2);
		writer.addDocument(doc2);
		Document doc3 = new Document();
		Field f3 = new Field("contents", "common common common", Field.Store.YES, Field.Index.ANALYZED_NO_NORMS);
		doc3.add(f3);
		writer.addDocument(doc3);
		writer.close();
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		QueryParser parser = new QueryParser(Version.LUCENE_35, "contents", new StandardAnalyzer(Version.LUCENE_35));
		Query query = parser.parse("common");
		TopDocs docs = searcher.search(query, 10);
		for (ScoreDoc doc : docs.scoreDocs) {
			System.out.println("docid: " + doc.doc + ", score:" + doc.score + ", contents:"
					+ searcher.doc(doc.doc).get("contents"));
		}
	}

	private void testNormsFieldBoost(Field.Index fieldIndex) throws Exception{
		Directory dir = new RAMDirectory();
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
		IndexWriter writer = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_35, analyzer));
		Document doc1 = new Document();
		Field f1 = new Field("contents", "common hello hello", Field.Store.YES, fieldIndex);
		f1.setBoost(100); // field setBoost
		doc1.add(f1);
		writer.addDocument(doc1);
		Document doc2 = new Document();
		Field f2 = new Field("contents", "common common hello", Field.Store.YES, Field.Index.ANALYZED_NO_NORMS);
		doc2.add(f2);
		writer.addDocument(doc2);
		writer.close();
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		QueryParser parser = new QueryParser(Version.LUCENE_35, "contents", new StandardAnalyzer(Version.LUCENE_35));
		Query query = parser.parse("common");
		TopDocs docs = searcher.search(query, 10);
		for (ScoreDoc doc : docs.scoreDocs) {
			System.out.println("docid: " + doc.doc + ", score:" + doc.score + ", contents:"
					+ searcher.doc(doc.doc).get("contents"));
		}
	}

	private void testNormLength(Field.Index fieldIndex1, Field.Index fieldIndex2)
			throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
		Directory dir = new RAMDirectory();
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
		IndexWriter writer = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_35, analyzer));
		Document doc1 = new Document();
		Field f1 = new Field("contents", "common hello", Field.Store.YES, fieldIndex1);
		doc1.add(f1);
		writer.addDocument(doc1);
		Document doc2 = new Document();
		Field f2 = new Field("contents", "common common hello hello hello hello", Field.Store.YES, fieldIndex2);
		doc2.add(f2);
		writer.addDocument(doc2);
		writer.close();
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		QueryParser parser = new QueryParser(Version.LUCENE_35, "contents", new StandardAnalyzer(Version.LUCENE_35));
		Query query = parser.parse("title:common contents:common");
		TopDocs docs = searcher.search(query, 10);

		for (ScoreDoc doc : docs.scoreDocs) {
			System.out.println("docid: " + doc.doc + ", score:" + doc.score + ", contents:"
					+ searcher.doc(doc.doc).get("contents"));
		}
	}

	private void testQueryBoost(String queryStr)
			throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
		Directory dir = new RAMDirectory();
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
		IndexWriter writer = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_35, analyzer));
		Document doc1 = new Document();
		Field f1 = new Field("contents", "common1 hello", Field.Store.YES, Field.Index.ANALYZED);
		doc1.add(f1);
		writer.addDocument(doc1);
		Document doc2 = new Document();
		Field f2 = new Field("contents", "common2 common2 hello", Field.Store.YES, Field.Index.ANALYZED);
		doc2.add(f2);
		writer.addDocument(doc2);
		writer.close();
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		QueryParser parser = new QueryParser(Version.LUCENE_35, "contents", new StandardAnalyzer(Version.LUCENE_35));
		Query query = parser.parse(queryStr);
		TopDocs docs = searcher.search(query, 10);

		for (ScoreDoc doc : docs.scoreDocs) {
			System.out.println("docid: " + doc.doc + ", score:" + doc.score + ", contents:"
					+ searcher.doc(doc.doc).get("contents"));
		}
	}

}
