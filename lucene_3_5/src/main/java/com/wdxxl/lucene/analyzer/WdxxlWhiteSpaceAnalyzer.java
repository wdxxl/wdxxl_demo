package com.wdxxl.lucene.analyzer;

import java.io.Reader;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceTokenizer;
import org.apache.lucene.util.Version;

public class WdxxlWhiteSpaceAnalyzer extends Analyzer {

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		final WhitespaceTokenizer tokenizer = new WhitespaceTokenizer(Version.LUCENE_35, reader);
		TokenStream tokenStream = new WdxxlPatternReplaceFilter(tokenizer, Pattern.compile("[\\p{Punct}]"), "", true);
	    tokenStream = new LowerCaseFilter(Version.LUCENE_35, tokenizer);
		return tokenStream;
	}

}
