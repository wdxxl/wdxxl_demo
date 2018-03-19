package com.wdxxl.lucene.analyzer;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

// https://github.com/apache/lucene-solr/blob/master/lucene/demo/src/java/org/apache/lucene/demo/SearchFiles.java
public class WdxxlAnalyzerDemo {
	static Directory dir = new RAMDirectory();
	static Analyzer analyzer = new WdxxlAnalyzer();
	static String[] bookNames = { "java enter maual core", "java swift core developer", "java core basic",
			"program developer java core dev", "java basic demo", "hadoop tutorial", "java core 1 java" };

	public static void main(String[] args) throws IOException, ParseException {
		index();
		TopDocs topDocs = search("bookName", "java");
		System.out.println("totalHits: " + topDocs.totalHits);
		display(topDocs);
	}

	private static void index() throws IOException {
		IndexWriter index = new IndexWriter(dir, new IndexWriterConfig(analyzer));
		for (int i = 0; i < bookNames.length; i++) {
			Document doc = new Document();
			doc.add(new TextField("bookName", bookNames[i], Field.Store.YES));
			index.addDocument(doc);
		}
		index.close();
	}

	private static TopDocs search(String fieldName, String keywords) throws IOException, ParseException {
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		QueryParser queryParser = new QueryParser(fieldName, analyzer);
		Query query = queryParser.parse(keywords);
		TopDocs topDocs = searcher.search(query, Integer.MAX_VALUE);
		// System.out.println("Explain: " + searcher.explain(query,
		// topDocs.scoreDocs[0].doc).toString());
		return topDocs;
	}

	private static void display(TopDocs topDocs) throws CorruptIndexException, IOException {
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		ScoreDoc[] scoreDoc = topDocs.scoreDocs;
		for (int i = 0; i < scoreDoc.length; i++) {
			Document doc = searcher.doc(scoreDoc[i].doc);
			System.out.println(doc.get("bookName"));
		}
	}
}
