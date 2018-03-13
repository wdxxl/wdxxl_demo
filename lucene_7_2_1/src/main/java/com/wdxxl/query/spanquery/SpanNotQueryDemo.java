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
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.spans.SpanNearQuery;
import org.apache.lucene.search.spans.SpanNotQuery;
import org.apache.lucene.search.spans.SpanQuery;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

// TODO for detail specific requirement.
public class SpanNotQueryDemo {
	Directory dir = new RAMDirectory();

	public static void main(String[] args) throws IOException {
		SpanNotQueryDemo demo = new SpanNotQueryDemo();
		demo.index();
		demo.search();
	}

	private void index() throws IOException {
		IndexWriter indexWriter = new IndexWriter(dir, new IndexWriterConfig(new StandardAnalyzer()));

		Document doc = new Document();
		doc.add(new TextField("text", "the quick brown fox jumps over the lazy dog", Field.Store.YES));
		indexWriter.addDocument(doc);

		doc = new Document();
		doc.add(new TextField("text", "the quick red fox jumps over the sleepy cat", Field.Store.YES));
		indexWriter.addDocument(doc);

		doc = new Document();
		doc.add(new TextField("text", "the quick brown fox jumps over the lazy dog", Field.Store.YES));
		indexWriter.addDocument(doc);

		doc = new Document();
		doc.add(new TextField("text",
				"the quick brown adult slave nice fox winde felt testcase gox quick jumps over the lazy dog",
				Field.Store.YES));
		indexWriter.addDocument(doc);

		doc = new Document();
		doc.add(new TextField("text", "the quick brown fox quick jumps over the lazy dog", Field.Store.YES));
		indexWriter.addDocument(doc);

		indexWriter.close();
	}

	private void search() throws IOException {
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);

		String queryStrStart = "dog";
		String queryStrEnd = "quick";
		String excludeString = "fox";
		SpanQuery queryStart = new SpanTermQuery(new Term("text", queryStrStart));
		SpanQuery queryEnd = new SpanTermQuery(new Term("text", queryStrEnd));
		SpanQuery excludeQuery = new SpanTermQuery(new Term("text", excludeString));
		SpanQuery spanNearQuery = new SpanNearQuery(new SpanQuery[] { queryStart, queryEnd }, 12, false);
		SpanQuery spanNotQuery = new SpanNotQuery(spanNearQuery, excludeQuery, 4, 3);
		TopDocs topDocs = searcher.search(spanNotQuery, 100);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (int i = 0; i < scoreDocs.length; i++) {
			// System.out.println(searcher.explain(spanNearQuery,
			// scoreDocs[i].doc));
			int docId = scoreDocs[i].doc;
			Document document = searcher.doc(docId);
			String path = document.get("text");
			System.out.println("text:" + path);
		}
	}
}
