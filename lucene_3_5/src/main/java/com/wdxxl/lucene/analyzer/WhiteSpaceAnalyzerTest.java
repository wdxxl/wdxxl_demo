package com.wdxxl.lucene.analyzer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
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
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.IOUtils;
import org.apache.lucene.util.Version;

public class WhiteSpaceAnalyzerTest {
	static Analyzer analyzer = new WdxxlWhiteSpaceAnalyzer();
	static String[] bookNames = { "Your's 1", "\nTable A350T", "Table 1",
			"\nRef Product Cross Reference Table A350TRENTXWBA0000000000000PAD\n" };

	public static void main(String[] args) throws Exception {
		index();
		TopDocs topDocs = searcher("bookName", "table*");
		System.out.println("Table 共有记录" + topDocs.totalHits + "条");
		System.out.println("-----------------------------------------------");
		display(topDocs);
		System.out.println("-----------------------------------------------");
		highLightDisplay(topDocs, "table*");
	}

	// 把查询到的图书进行输出
	public static void display(TopDocs topDocs) throws CorruptIndexException, IOException {
		Directory dir = FSDirectory.open(new File(System.getProperty("user.dir") + "/lib/lucene35/white"));
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		ScoreDoc[] scoreDoc = topDocs.scoreDocs;
		for (int i = 0; i < scoreDoc.length; i++) {
			Document doc = searcher.doc(scoreDoc[i].doc);
			System.out.println(doc.get("bookName"));
		}
		searcher.close();
	}

	// 按照关键字查询图书
	public static TopDocs searcher(String fieldName, String keyWords)
			throws CorruptIndexException, IOException, ParseException {
		Directory dir = FSDirectory.open(new File(System.getProperty("user.dir") + "/lib/lucene35/white"));
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		QueryParser queryParser = new QueryParser(Version.LUCENE_35, fieldName, analyzer);
		Query query = queryParser.parse(keyWords);
		TopDocs topDocs = searcher.search(query, Integer.MAX_VALUE);
		searcher.close();
		return topDocs;
	}

	// 对图书名称进行索引
	public static void index() throws CorruptIndexException, LockObtainFailedException, IOException {
		Directory dir = FSDirectory.open(new File(System.getProperty("user.dir") + "/lib/lucene35/white"));
		IndexWriter index = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_35, analyzer));
		for (int i = 0; i < bookNames.length; i++) {
			Document doc = new Document();
			doc.add(new Field("bookName", bookNames[i], Field.Store.YES, Field.Index.ANALYZED));
			index.addDocument(doc);
		}
		// index.optimize();
		index.close();
	}

	// 把查询到的图书进行显示，并把关键字进行高亮显示
	public static void highLightDisplay(TopDocs topDocs, String keyWords)
			throws CorruptIndexException, IOException, ParseException, InvalidTokenOffsetsException {
		Directory dir = FSDirectory.open(new File(System.getProperty("user.dir") + "/lib/lucene35/white"));
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		QueryParser queryParser = new QueryParser(Version.LUCENE_35, "bookName", analyzer);
		Query query = queryParser.parse(keyWords);
		ScoreDoc[] scoreDoc = topDocs.scoreDocs;

		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
		Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
		highlighter.setTextFragmenter(new SimpleFragmenter(1024));
		for (int i = 0; i < scoreDoc.length; i++) {
			Document doc = searcher.doc(scoreDoc[i].doc);
			String text = doc.get("bookName");
			TokenStream tokenStream = new StandardAnalyzer(Version.LUCENE_35).tokenStream("bookName",
					getUTF8Reader(text));// updated
			String highLightText = highlighter.getBestFragment(tokenStream, text);
			System.out.println(highLightText);
		}
		searcher.close();
	}

	private static Reader getUTF8Reader(String text) {
		InputStream stream = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
		Reader reader = IOUtils.getDecodingReader(stream, IOUtils.CHARSET_UTF_8);
		return reader;
	}
}
