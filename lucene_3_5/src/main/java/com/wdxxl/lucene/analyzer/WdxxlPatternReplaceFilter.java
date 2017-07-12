package com.wdxxl.lucene.analyzer;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class WdxxlPatternReplaceFilter extends TokenFilter {
	private final String replacement;
	private final boolean all;
	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
	private final Matcher m;

	public WdxxlPatternReplaceFilter(TokenStream in, Pattern p, String replacement, boolean all) {
		super(in);
		this.replacement = (null == replacement) ? "" : replacement;
		this.all = all;
		this.m = p.matcher(termAtt);
	}

	@Override
	public boolean incrementToken() throws IOException {
		if (!input.incrementToken())
			return false;
		m.reset();
		if (m.find()) {
			// replaceAll/replaceFirst will reset() this previous find.
			String transformed = all ? m.replaceAll(replacement) : m.replaceFirst(replacement);
			termAtt.setEmpty().append(transformed);
		}
		return true;
	}

}