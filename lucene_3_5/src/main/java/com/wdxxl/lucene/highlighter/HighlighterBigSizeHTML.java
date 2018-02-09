package com.wdxxl.lucene.highlighter;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.NullFragmenter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class HighlighterBigSizeHTML {
	static Directory dir = new RAMDirectory();
	static Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
	static String[] bookNames = { "./lib/highlighter/FALTCHAP-28.json" };

	public static void main(String[] args) throws IOException, ParseException, InvalidTokenOffsetsException {
		index();
		// partially boxed~0 - 218
		System.out.println("--------------------PhraseQuery-------Exact Match 1--------------------");
		TopDocs topDocs = searcher("bookName", "fuel");// partially
		PhraseQuery query = new PhraseQuery();
		query.setSlop(3); // 允许空一个词
		query.add(new Term("bookName", "fuel")); // partially
		query.add(new Term("bookName", "leak")); // boxed
		highLightDisplay(topDocs, query);
	}

	// 按照关键字查询图书
	public static TopDocs searcher(String fieldName, String keyWords)
			throws CorruptIndexException, IOException, ParseException {
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		QueryParser queryParser = new QueryParser(Version.LUCENE_35, fieldName, analyzer);
		Query query = queryParser.parse(keyWords);
		TopDocs topDocs = searcher.search(query, Integer.MAX_VALUE);
		searcher.close();
		return topDocs;
	}

	// 把查询到的图书进行显示，并把关键字进行高亮显示
	public static void highLightDisplay(TopDocs topDocs, Query query)
			throws CorruptIndexException, IOException, ParseException, InvalidTokenOffsetsException {
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		ScoreDoc[] scoreDoc = topDocs.scoreDocs;

		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
		Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
		// highlighter.setTextFragmenter(new SimpleFragmenter(1024)); //52KB
		highlighter.setTextFragmenter(new NullFragmenter()); //52KB
		// highlighter.setTextFragmenter(new WdxxlFragmenter(Long.MAX_VALUE));
		for (int i = 0; i < scoreDoc.length; i++) {
			Document doc = searcher.doc(scoreDoc[i].doc);
			String text = doc.get("bookName");
			// System.out.print(text + "----");
			TokenStream tokenStream = analyzer.tokenStream("bookName", new StringReader(text));
			String highLightText = highlighter.getBestFragment(tokenStream, text);
			// System.out.println(highLightText);
			writeToHTMLFiles(bookNames[0] + ".after.html", highLightText);
		}
		searcher.close();
	}

	public static void display(TopDocs topDocs) throws CorruptIndexException, IOException {
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		ScoreDoc[] scoreDoc = topDocs.scoreDocs;
		for (int i = 0; i < scoreDoc.length; i++) {
			Document doc = searcher.doc(scoreDoc[i].doc);
			System.out.println(doc.get("bookName"));
		}
		searcher.close();
	}

	public static void index() throws CorruptIndexException, LockObtainFailedException, IOException {
		IndexWriter index = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_35, analyzer));
		for (int i = 0; i < bookNames.length; i++) {
			Document doc = new Document();
			doc.add(new Field("bookName", getContentFromJson(bookNames[i]), Field.Store.YES, Field.Index.ANALYZED));
			index.addDocument(doc);
		}
		index.close();
	}

	public static String getContentFromJson(String fileName) throws IOException {
		String result = "";
		result = FileUtils.readFileToString(new File(fileName), "UTF-8");
		JsonObject jsonObject = new Gson().fromJson(result, JsonObject.class);
		JsonElement content = jsonObject.get("content");
		writeToHTMLFiles(bookNames[0] + ".before.html", content.toString());
		return content.toString();
	}

	public static void writeToHTMLFiles(String fileName, String data) throws IOException {
		File file = new File(fileName);
		FileUtils.writeStringToFile(file, data, "UTF-8", false);
	}

}
