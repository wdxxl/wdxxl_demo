package com.wdxxl.lucene.highlighter;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
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
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.NullFragmenter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class HighlighterCountDemo {
	static Directory dir = new RAMDirectory();

	static Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
	static String index_folder = "./lib/Highlighter_count/";

	public static void main(String[] args) throws IOException, ParseException, InvalidTokenOffsetsException {
		//dir = FSDirectory.open(new File(System.getProperty("user.dir")+ "/lib/lucene35/highlightcount"));
		Date start = new Date();
		index();
		Date end = new Date();
		System.out.println("It tooks : " + ((end.getTime() - start.getTime())) +" for Index.");
		System.out.println("--------------------Query Top Docs---------------------------");
		start = new Date();
		TopDocs topDocs = searcher("bookName", "from");
		end = new Date();
		System.out.println("It tooks : " + ((end.getTime() - start.getTime())) +" for Query Top Docs.");
		System.out.println("Matched TopDocs: "+topDocs.totalHits);

		System.out.println("--------------------TermQuery---------------------------");
		TermQuery query = new TermQuery(new Term("bookName", "from"));
		start = new Date();
		highLightDisplay(topDocs, query, false);
		end = new Date();
		System.out.println("It tooks : " + ((end.getTime() - start.getTime())) +" for TermQuery.");

		System.out.println("--------------------PhraseQuery---------------------------");
		PhraseQuery phraseQuery = new PhraseQuery();
		phraseQuery.setSlop(3);
		phraseQuery.add(new Term("bookName", "partially"));
		phraseQuery.add(new Term("bookName", "boxed"));
		start = new Date();
		highLightDisplay(topDocs, phraseQuery, false);
		end = new Date();
		System.out.println("It tooks : " + ((end.getTime() - start.getTime())) +" for PhraseQuery.");

		System.out.println("--------------------PhraseQuery---------------------------");
		phraseQuery = new PhraseQuery();
		phraseQuery.setSlop(3);
		phraseQuery.add(new Term("bookName", "sec"));
		phraseQuery.add(new Term("bookName", "from"));
		phraseQuery.add(new Term("bookName", "standard"));
		start = new Date();
		highLightDisplay(topDocs, phraseQuery, false);
		end = new Date();
		System.out.println("It tooks : " + ((end.getTime() - start.getTime())) +" for PhraseQuery.");

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

	public static void highLightDisplay(TopDocs topDocs, Query query, boolean display)
			throws CorruptIndexException, IOException, ParseException, InvalidTokenOffsetsException {
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		ScoreDoc[] scoreDoc = topDocs.scoreDocs;

		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
		Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
		highlighter.setMaxDocCharsToAnalyze(2 * 1024 * 1024);
		highlighter.setTextFragmenter(new NullFragmenter());
		//highlighter.setTextFragmenter(new SimpleFragmenter(200));
		for (int i = 0; i < scoreDoc.length; i++) {
			Document doc = searcher.doc(scoreDoc[i].doc);
			String fileName = doc.get("fileName");
			String text = doc.get("bookName");
			TokenStream tokenStream = analyzer.tokenStream("bookName", new StringReader(text));
			String highLightText = highlighter.getBestFragment(tokenStream, text);
			if (highLightText != "" && highLightText != null) {
				System.out.print("fileName: " + fileName + ", Count:" + countHighlighter(highLightText) +", ");
				if(display){
					System.out.println();
					System.out.println(highLightText);
				}
			}
		}
		System.out.println();
		searcher.close();
	}

	public static void index() throws CorruptIndexException, LockObtainFailedException, IOException {
		IndexWriter index = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_35, analyzer));
		File folder = new File(index_folder);
		for (final File fileEntry : folder.listFiles()) {
			if (!fileEntry.isDirectory()) {
				Document doc = new Document();
				doc.add(new Field("fileName", fileEntry.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
				doc.add(new Field("bookName", getContentFromJson(fileEntry.getAbsolutePath()), Field.Store.YES,
						Field.Index.ANALYZED));
				index.addDocument(doc);
			}
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
		return count;
	}
}
