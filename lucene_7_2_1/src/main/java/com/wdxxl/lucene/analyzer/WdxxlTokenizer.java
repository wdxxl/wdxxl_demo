package com.wdxxl.lucene.analyzer;

import java.io.IOException;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

public class WdxxlTokenizer extends Tokenizer {
	private int position = 0;
	protected CharTermAttribute charAttr = addAttribute(CharTermAttribute.class);
	protected TypeAttribute typeAttr = addAttribute(TypeAttribute.class);
	private final PositionIncrementAttribute positionAttr = addAttribute(PositionIncrementAttribute.class);
	private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);

	@Override
	public boolean incrementToken() throws IOException {
		clearAttributes();

		char[] c = new char[1];
		int count = this.input.read(c, this.position, 1);

		if (count == -1) {
			return false;
		}
		charAttr.append(c[0]);
		typeAttr.setType("Char");
		positionAttr.setPositionIncrement(position + 1);
		offsetAtt.setOffset(correctOffset(position), correctOffset(position + 1));
		return true;
	}

	@Override
	public void reset() throws IOException {
		super.reset();
		this.position = 0;
	}

}
