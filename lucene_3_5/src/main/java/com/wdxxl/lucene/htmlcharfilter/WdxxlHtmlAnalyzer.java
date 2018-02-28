package com.wdxxl.lucene.htmlcharfilter;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharReader;
import org.apache.lucene.analysis.MappingCharFilter;
import org.apache.lucene.analysis.NormalizeCharMap;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;

public class WdxxlHtmlAnalyzer extends Analyzer {
	final static NormalizeCharMap preMap = new NormalizeCharMap();
	final static NormalizeCharMap postMap = new NormalizeCharMap();
	static {
		preMap.add("\\/", "/");
		postMap.add("/", " ");
	}

	public final TokenStream tokenStream(String fieldName, Reader reader) {
		reader = new MappingCharFilter(preMap, CharReader.get(reader));
		reader = new HTMLStripCharFilter(CharReader.get(reader));
		reader = new MappingCharFilter(postMap, CharReader.get(reader));
		TokenStream result = new StandardTokenizer(Version.LUCENE_35, reader);
		return result;
	}
}
