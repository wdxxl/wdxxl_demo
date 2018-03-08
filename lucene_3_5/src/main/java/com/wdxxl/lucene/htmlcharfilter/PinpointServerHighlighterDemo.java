package com.wdxxl.lucene.htmlcharfilter;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
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
import org.apache.lucene.util.Version;

public class PinpointServerHighlighterDemo {
	static Directory dir;
	static Analyzer analyzer = new WdxxlHtmlAnalyzer();

	public static void main(String[] args) throws Exception {
		dir = FSDirectory.open(new File(System.getProperty("user.dir") + "/lib/highlighter/pinpoint_server"));
		TopDocs topDocs = searcher("pps_document_html", "air* +bicycle*");
		System.out.println("Total Records:" + topDocs.totalHits + ".");
		System.out.println("--------------------TermQuery---------------------------");
		QueryParser queryParser = new QueryParser(Version.LUCENE_35, "pps_document_html", analyzer);
		Query query = queryParser.parse("air bicycle");
		highLightDisplay(topDocs, query);
	}

	public static void highLightDisplay(TopDocs topDocs, Query query)
			throws CorruptIndexException, IOException, ParseException, InvalidTokenOffsetsException {
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		ScoreDoc[] scoreDoc = topDocs.scoreDocs;

		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
		Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
		highlighter.setTextFragmenter(new SimpleFragmenter(30));
		for (int i = 0; i < scoreDoc.length; i++) {
			Document doc = searcher.doc(scoreDoc[i].doc);
			String text = doc.get("pps_document_html");
			TokenStream tokenStream = analyzer.tokenStream("pps_document_html", new StringReader(text));
			String highLightText = highlighter.getBestFragment(tokenStream, text);
			System.out.println(scoreDoc[i].score +"--"+highLightText);
		}
		searcher.close();
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
}
