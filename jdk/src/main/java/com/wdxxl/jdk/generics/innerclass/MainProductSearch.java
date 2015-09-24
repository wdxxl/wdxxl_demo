package com.wdxxl.jdk.generics.innerclass;

public class MainProductSearch {
	public static void main(String[] args) {
		// level1 Generic
		ProductSearchParserA parserA = new ProductSearchParserA(new Search());
		parserA.getEntityCollector().addEntity(new Object(), 1, 1);
		parserA.getEntityCollector().reset();

		// implements... because of the inner class, only implement
		// ProductSearchParserA is not use for
		ProductSearchParserASon parserASon = new ProductSearchParserASon(
				new Search());
		parserASon.getEntityCollector().addEntity(new Object(), 1, 1);
		parserASon.getEntityCollector().reset();

		System.out.println("============================");

		// Generic one more level
		ProductSearchParserB parserB = new ProductSearchParserB(
				new ProductSearchParserB.ParsingResult());
		parserB.getEntityCollector().addEntity(new Object(), 1, 1);
		parserB.getEntityCollector().reset();

		// implements... because of the inner class, only implement
		// ProductSearchParserA is not use for
		ProductSearchParserBSon parserBSon = new ProductSearchParserBSon(
				new ProductSearchParserBSon.ParsingResult());
		parserBSon.getEntityCollector().addEntity(new Object(), 1, 1);
		parserBSon.getEntityCollector().reset();
	}

}
