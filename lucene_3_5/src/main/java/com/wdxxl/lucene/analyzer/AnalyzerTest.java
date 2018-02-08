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
		String text1 = "\nRef Product Cross Reference Table A350TRENTXWBA0000000000000PAD\n"; //Ref和Product Table和A350，有ASCII乱码
		text1 ="This is HTML TAG Yours your's , ad-123 abc_asd http://192.168.1.1:8080 wdxxlanswer@gmail.com A-350 those ";
		/** An unmodifiable set containing some common English words that are not usually useful for searching.*/
		text1 = text1 + "a an and are as at be but by for if in into is it no not of on or such that the their then there these they this to was will with ";
		// StopAnalyzer.ENGLISH_STOP_WORDS_SET
		// Punctuation Characters
		text1 = text1 + PunctuationCharacters.getPunct();
		System.out.println("-------StatndardAnalyzer-----------------");
		statndardAnalyzer(text1);
		System.out.println("------------WhitespaceAnalyzer-------------");
		whitespaceAnalyzer(text1);
		System.out.println("-----------WdxxlWhitespaceAnalyzer--------------");
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
