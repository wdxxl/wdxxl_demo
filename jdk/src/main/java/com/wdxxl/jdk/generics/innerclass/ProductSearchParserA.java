package com.wdxxl.jdk.generics.innerclass;

import com.wdxxl.jdk.generics.innerclass.ProductSearchParserA.ParsingResult;

public class ProductSearchParserA extends EntityParser<Object, ParsingResult> {

	public ProductSearchParserA(Search search) {
		super(search, new ParsingResult());
	}

	public ProductSearchParserA(Search search, ParsingResult parsingResult) {
		super(search, parsingResult);
	}

	public static class ParsingResult implements
			EntityParser.EntityCollector<Object> {
		@Override
		public void addEntity(Object entity, Integer startOffset,
				Integer endOffset) {
			System.out.println("ProductSearchParserA ParsingResult addEntity.");
		}

		@Override
		public void reset() {
			System.out.println("ProductSearchParserA ParsingResult reset.");
		}
	}
}
