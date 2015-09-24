package com.wdxxl.jdk.generics.innerclass;

import com.wdxxl.jdk.generics.innerclass.EntityParser.EntityCollector;

public class ProductSearchParserB<V, T extends EntityCollector<V>> extends
		EntityParser<V, T> {

	public ProductSearchParserB(T collector) {
		super(collector);
	}

	public static class ParsingResult implements
			EntityParser.EntityCollector<Object> {
		@Override
		public void addEntity(Object entity, Integer startOffset,
				Integer endOffset) {
			System.out.println("ProductSearchParserB ParsingResult addEntity.");
		}

		@Override
		public void reset() {
			System.out.println("ProductSearchParserB ParsingResult reset.");
		}
	}
}
