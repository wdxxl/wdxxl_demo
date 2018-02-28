package com.wdxxl.lucene.htmlcharfilter;

import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharReader;
import org.apache.lucene.analysis.MappingCharFilter;
import org.apache.lucene.analysis.NormalizeCharMap;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

// https://github.com/mwsoft/sample/blob/master/solr-filter-sample/src/jp/mwsoft/sample/lucene/filter/HTMLStripCharFilterSample.java
public class HTMLStripCharFilterSample {

	public static void main(String[] args) throws Exception {
		MyAnalyzer analyzer = new MyAnalyzer();

		String str = "<html><head><title>title1 element1<\\/title><script>script2 element2</script></head><body>body3 element3 hello/world</body></html>";

		Reader reader = new StringReader(str);
		TokenStream stream = analyzer.tokenStream("", reader);

		while (stream.incrementToken()) {
			CharTermAttribute term = stream.getAttribute(CharTermAttribute.class);
			System.out.print(term.toString() + "\t");
		}
		// => title element body element
	}

	static class MyAnalyzer extends Analyzer {
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
}