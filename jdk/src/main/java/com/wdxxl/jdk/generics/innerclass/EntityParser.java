package com.wdxxl.jdk.generics.innerclass;

import com.wdxxl.jdk.generics.innerclass.EntityParser.EntityCollector;

public class EntityParser<V, T extends EntityCollector<V>> {
	private final T entityCollector;
	private Search search;

	public EntityParser(T collector) {
		entityCollector = collector;
	}

	public EntityParser(Search search, T collector) {
		this.setSearch(search);
		entityCollector = collector;
	}
	
	public interface EntityCollector<V> {
		public void addEntity(V entity, Integer startOffset, Integer endOffset);
		public void reset();
	}

	public T getEntityCollector() {
		return entityCollector;
	}

	public Search getSearch() {
		return search;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

}
