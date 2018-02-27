package com.wdxxl.lucene.queryparser;

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

// http://www.cnblogs.com/bysshijiajia/archive/2008/01/24/1051317.html
public class QueryParserDemo {
	static Directory SOURCES_DIR = new RAMDirectory();
	static Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
	static String[] BookNames = { "engine/pylon java core 1", "pylon java swift core</z> 2", "engine\\pylon core java basic swift 3",
			"java engine@pylon core 4", "j java tutorial javise this 11-12-13 5" };
	static String[] DeleteFlags = { "false", "false", "true", "false", "true" };

	public static void main(String[] args) throws CorruptIndexException, IOException, ParseException {
		index(SOURCES_DIR);
		System.out.println("-------------------SOURCES_DIR-------Case_1-----------------");
		QueryParser queryParser = new QueryParser(Version.LUCENE_35, "BookNames", analyzer);
		Query query = queryParser.parse("pylon");
		display(SOURCES_DIR, searcher(SOURCES_DIR, query));

		System.out.println("-------------------SOURCES_DIR------Case_2------------------");
		QueryParser queryParser2 = new QueryParser(Version.LUCENE_35, null, analyzer);
		Query query2 = queryParser2.parse("BookNames: pylon");
		display(SOURCES_DIR, searcher(SOURCES_DIR, query2));

		System.out.println("-------------------SOURCES_DIR------Case_3------------------");
		QueryParser queryParser3 = new QueryParser(Version.LUCENE_35, "BookNames", analyzer);
		Query query3 = queryParser3.parse("java pylon");
		// Query query3 = queryParser3.parse("java OR pylon");
		display(SOURCES_DIR, searcher(SOURCES_DIR, query3));

		System.out.println("-------------------SOURCES_DIR------Case_4------------------");
		QueryParser queryParser4 = new QueryParser(Version.LUCENE_35, "BookNames", analyzer);
		// Query query4 = queryParser4.parse("+java +pylon");
		Query query4 = queryParser4.parse("java AND pylon");
		display(SOURCES_DIR, searcher(SOURCES_DIR, query4));

		System.out.println("-------------------SOURCES_DIR------Case_5------------------");
		QueryParser queryParser5 = new QueryParser(Version.LUCENE_35, null, analyzer);
		// Query query5 = queryParser5.parse("BookNames: pylon -DeleteFlags: false");
		Query query5 = queryParser5.parse("BookNames:pylon AND NOT DeleteFlags:false");
		display(SOURCES_DIR, searcher(SOURCES_DIR, query5));

		System.out.println("-------------------SOURCES_DIR------Case_6------------------");
		QueryParser queryParser6 = new QueryParser(Version.LUCENE_35, "BookNames", analyzer);
		Query query6 = queryParser6.parse("(java OR core) AND swift");
		display(SOURCES_DIR, searcher(SOURCES_DIR, query6));

		System.out.println("-------------------SOURCES_DIR------Case_7------------------");
		QueryParser queryParser7 = new QueryParser(Version.LUCENE_35, "BookNames", analyzer);
		Query query7 = queryParser7.parse("jav*");
		// Query query7 = queryParser7.parse("*y*");
		// Cannot parse '*y*': '*' or '?' not allowed as first character in WildcardQuery
		display(SOURCES_DIR, searcher(SOURCES_DIR, query7));

		System.out.println("-------------------SOURCES_DIR------Case_8------------------");
		QueryParser queryParser8 = new QueryParser(Version.LUCENE_35, null, analyzer);
		Query query8 = queryParser8.parse("BookNames:\"java swift\"~1");
		display(SOURCES_DIR, searcher(SOURCES_DIR, query8));

		System.out.println("-------------------SOURCES_DIR------Case_10----fuzzyquery--------------");
		QueryParser queryParser10 = new QueryParser(Version.LUCENE_35, null, analyzer);
		Query query10 = queryParser10.parse("BookNames:jave~0.2 +BookNames:javise1~0.3");
		display(SOURCES_DIR, searcher(SOURCES_DIR, query10));

		System.out.println("-------------------SOURCES_DIR------Case_9------------------");
		QueryParser queryParser9 = new QueryParser(Version.LUCENE_35, null, analyzer);
		Query query9 = queryParser9.parse("BookNames:this");
		display(SOURCES_DIR, searcher(SOURCES_DIR, query9));
	}

	// 按照关键字查询图书
	public static TopDocs searcher(Directory dir, Query query)
			throws CorruptIndexException, IOException, ParseException {
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopDocs topDocs = searcher.search(query, 25);
		searcher.close();
		return topDocs;
	}

	// 把查询到的图书进行输出
	public static void display(Directory dir, TopDocs topDocs) throws CorruptIndexException, IOException {
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		ScoreDoc[] scoreDoc = topDocs.scoreDocs;
		for (int i = 0; i < scoreDoc.length; i++) {
			Document doc = searcher.doc(scoreDoc[i].doc);
			System.out.println("BookNames: " + doc.get("BookNames") + ", DeleteFlags: " + doc.get("DeleteFlags"));
		}
		searcher.close();
	}

	// 对图书名称进行索引
	public static void index(Directory dir) throws CorruptIndexException, LockObtainFailedException, IOException {
		IndexWriter writer = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_35, analyzer));
		for (int i = 0; i < BookNames.length; i++) {
			Document doc = new Document();
			doc.add(new Field("BookNames", BookNames[i], Field.Store.YES, Field.Index.ANALYZED));
			doc.add(new Field("DeleteFlags", DeleteFlags[i], Field.Store.YES, Field.Index.ANALYZED));
			writer.addDocument(doc);
		}
		writer.close();
	}
}
