package com.wdxxl.jdk.generics.innerclass;

import com.wdxxl.jdk.generics.innerclass.ProductSearchParserBSon.ParsingResult;

public class ProductSearchParserBSon extends
		ProductSearchParserB<Object, ParsingResult> {

	public ProductSearchParserBSon(ParsingResult collector) {
		super(collector);
	}

	public static class ParsingResult extends
			ProductSearchParserB.ParsingResult {
		@Override
		public void addEntity(Object entity, Integer startOffset,
				Integer endOffset) {
			System.out
					.println("ProductSearchParserBSon ParsingResult addEntity.");
			out();
		}

		public void out() {
			System.out.println("ProductSearchParserBSon ParsingResult out.");
		}
	}
}
