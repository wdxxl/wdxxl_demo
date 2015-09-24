package com.wdxxl.jdk.generics.innerclass;

public class ProductSearchParserASon extends ProductSearchParserA {

	public ProductSearchParserASon(Search search) {
		super(search, new ParsingResult()); // the key code.
	}

	public static class ParsingResult extends
			ProductSearchParserA.ParsingResult {
		@Override
		public void addEntity(Object entity, Integer startOffset,
				Integer endOffset) {
			System.out
					.println("ProductSearchParserASon ParsingResult addEntity.");
			out();
		}

		public void out() {
			System.out.println("ProductSearchParserASon ParsingResult out.");
		}
	}
}
