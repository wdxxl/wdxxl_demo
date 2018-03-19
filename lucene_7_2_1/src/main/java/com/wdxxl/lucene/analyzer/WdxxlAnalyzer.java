package com.wdxxl.lucene.analyzer;

import org.apache.lucene.analysis.Analyzer;

public class WdxxlAnalyzer extends Analyzer {

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		return new TokenStreamComponents(new WdxxlTokenizer());
	}

}
