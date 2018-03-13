package com.wdxxl.query.spanquery;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.spans.SpanMultiTermQueryWrapper;
import org.apache.lucene.search.spans.SpanPositionRangeQuery;
import org.apache.lucene.search.spans.SpanQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class SpanPositionRangeQueryDemo {
	Directory dir = new RAMDirectory();

	public static void main(String[] args) throws IOException {
		SpanPositionRangeQueryDemo demo = new SpanPositionRangeQueryDemo();
		demo.index();
		demo.search();
	}

	private void index() throws IOException {
		IndexWriter indexWriter = new IndexWriter(dir, new IndexWriterConfig(new StandardAnalyzer()));

		Document doc = new Document();
		doc.add(new TextField("text", "quick brown fox", Field.Store.YES));
		indexWriter.addDocument(doc);

		doc = new Document();
		doc.add(new TextField("text", "jumps over the broun cat", Field.Store.YES));
		indexWriter.addDocument(doc);

		doc = new Document();
		doc.add(new TextField("text", "jumps over extremely very lazy broxn dog", Field.Store.YES));
		indexWriter.addDocument(doc);

		indexWriter.close();
	}

	private void search() throws IOException {
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);

		FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term("text", "broan"));
		SpanQuery sfq = new SpanMultiTermQueryWrapper<>(fuzzyQuery);
		SpanQuery query = new SpanPositionRangeQuery(sfq, 3, 5); // SpanFirstQuery(sfq, 5)

		TopDocs topDocs = searcher.search(query, 100);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (int i = 0; i < scoreDocs.length; i++) {
			// System.out.println(searcher.explain(query, scoreDocs[i].doc));
			int docId = scoreDocs[i].doc;
			Document document = searcher.doc(docId);
			String path = document.get("text");
			System.out.println("text:" + path);
		}
	}
}
