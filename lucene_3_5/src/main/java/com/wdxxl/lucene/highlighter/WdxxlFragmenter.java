package com.wdxxl.lucene.highlighter;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.search.highlight.Fragmenter;

public class WdxxlFragmenter implements Fragmenter {
	private static final long DEFAULT_FRAGMENT_SIZE = 100l;
	private long currentNumFrags;
	private long fragmentSize;
	private OffsetAttribute offsetAtt;

	public WdxxlFragmenter() {
		this(DEFAULT_FRAGMENT_SIZE);
	}

	public WdxxlFragmenter(long fragmentSize) {
		this.fragmentSize = fragmentSize;
	}

	public void start(String originalText, TokenStream stream) {
		offsetAtt = stream.addAttribute(OffsetAttribute.class);
		currentNumFrags = 1;
	}

	public boolean isNewFragment() {
		boolean isNewFrag = offsetAtt.endOffset() >= (fragmentSize * currentNumFrags);
		if (isNewFrag) {
			currentNumFrags++;
		}
		return isNewFrag;
	}

	public long getFragmentSize() {
		return fragmentSize;
	}

	public void setFragmentSize(long size) {
		fragmentSize = size;
	}

}
