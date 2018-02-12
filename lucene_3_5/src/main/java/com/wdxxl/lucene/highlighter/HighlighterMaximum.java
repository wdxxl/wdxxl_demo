package com.wdxxl.lucene.highlighter;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class HighlighterMaximum {
	static Directory dir = new RAMDirectory();
	static Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
	static String[] bookNames = { "./lib/highlighter/FALTCHAP-27.json" };

	public static void main(String[] args) throws IOException, ParseException, InvalidTokenOffsetsException {
		index();
		System.out.println("--------------------PhraseQuery-------Exact Match 1--------------------");
		TopDocs topDocs = searcher("bookName", "partially");// 27: partially
															// 28:fuel
		PhraseQuery query = new PhraseQuery();
		query.setSlop(3); // 允许空一个词
		query.add(new Term("bookName", "partially")); // 27: partially 28:fuel
		query.add(new Term("bookName", "boxed")); // 27: boxed 28:leak
		highLightDisplay(topDocs, query);
	}

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

	public static void highLightDisplay(TopDocs topDocs, Query query)
			throws CorruptIndexException, IOException, ParseException, InvalidTokenOffsetsException {
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		ScoreDoc[] scoreDoc = topDocs.scoreDocs;

		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
		Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
		highlighter.setMaxDocCharsToAnalyze(1024 * 1024 * 1024); // 2,097,152
		highlighter.setTextFragmenter(new NullFragmenter());
		for (int i = 0; i < scoreDoc.length; i++) {
			Document doc = searcher.doc(scoreDoc[i].doc);
			String text = doc.get("bookName");
			TokenStream tokenStream = analyzer.tokenStream("bookName", new StringReader(text));
			String highLightText = highlighter.getBestFragment(tokenStream, text);
			System.out.println("highLightText Length:" + highLightText.length());
			if (highLightText != "" && highLightText != null) {
				countHighlighter(highLightText);
			}
		}
		searcher.close();
	}

	public static void index() throws CorruptIndexException, LockObtainFailedException, IOException {
		IndexWriter index = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_35, analyzer));
		for (int i = 0; i < bookNames.length; i++) {
			Document doc = new Document();
			StringBuilder sb = new StringBuilder();
			sb.append(getContentFromJson(bookNames[i]))
			.append(getContentFromJson(bookNames[i]))
			.append(getContentFromJson(bookNames[i]))
			.append(getContentFromJson(bookNames[i]))
			.append(getContentFromJson(bookNames[i]))
			.append(getContentFromJson(bookNames[i]))
			.append(getContentFromJson(bookNames[i]))
			.append(getContentFromJson(bookNames[i]))
			.append(getContentFromJson(bookNames[i]));
			System.out.println("Index Length:" + sb.toString().length());
			doc.add(new Field("bookName", sb.toString(), Field.Store.YES, Field.Index.ANALYZED));
			index.addDocument(doc);
		}
		index.close();
	}

	public static String getContentFromJson(String fileName) throws IOException {
		String result = "";
		result = FileUtils.readFileToString(new File(fileName), "UTF-8");
		JsonObject jsonObject = new Gson().fromJson(result, JsonObject.class);
		JsonElement content = jsonObject.get("content");
		return content.toString();
	}

	public static int countHighlighter(String content) {
		String regex = "(<font color='red'>.*?</font>)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(content);
		int count = 0;
		while (matcher.find()) {
			count++;
		}
		System.out.println(count);
		return count;
	}

	public static void writeToHTMLFiles(String fileName, String data) throws IOException {
		File file = new File(fileName);
		FileUtils.writeStringToFile(file, data, "UTF-8", false);
	}

}
