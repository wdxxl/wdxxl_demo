package com.wdxxl.lucene.analyzer;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

// http://www.ascii-code.com
public class AnalyzerTest {
	public static void main(String[] args) throws IOException {
		String text1 = "\nRef Product Cross Reference Table A350TRENTXWBA0000000000000PAD\n";
		text1 ="This is HTML TAG";
		System.out.println("-------------------------");
		statndardAnalyzer(text1);
		System.out.println("-------------------------");
		whitespaceAnalyzer(text1);
		System.out.println("-------------------------");
		wdxxlWhitespaceAnalyzer(text1);
		System.out.println("-------------------------");
	}
	
	public static void statndardAnalyzer(String text) throws IOException{
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
		TokenStream tokenStream = analyzer.tokenStream("myfiled", new StringReader(text));
		tokenStream.reset();
		while (tokenStream.incrementToken()) {
			// 取得下一个分词
			System.out.println("token:" + tokenStream);
		}
		analyzer.close();
	}
	
	public static void whitespaceAnalyzer(String text) throws IOException{
		Analyzer analyzer = new WhitespaceAnalyzer (Version.LUCENE_35);
		TokenStream tokenStream = analyzer.tokenStream("myfiled", new StringReader(text));
		tokenStream.reset();
		while (tokenStream.incrementToken()) {
			// 取得下一个分词
			System.out.println("token:" + tokenStream);
		}
		analyzer.close();
	}
	
	public static void wdxxlWhitespaceAnalyzer(String text) throws IOException{
		Analyzer analyzer = new WdxxlWhiteSpaceAnalyzer();
		TokenStream tokenStream = analyzer.tokenStream("myfiled", new StringReader(text));
		tokenStream.reset();
		while (tokenStream.incrementToken()) {
			// 取得下一个分词
			System.out.println("token:" + tokenStream);
		}
		analyzer.close();
	}
}
