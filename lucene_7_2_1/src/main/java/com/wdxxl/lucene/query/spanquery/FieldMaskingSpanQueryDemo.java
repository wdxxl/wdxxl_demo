package com.wdxxl.lucene.query.spanquery;

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
import org.apache.lucene.search.spans.FieldMaskingSpanQuery;
import org.apache.lucene.search.spans.SpanNearQuery;
import org.apache.lucene.search.spans.SpanQuery;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

//http://iamyida.iteye.com/blog/2195761
public class FieldMaskingSpanQueryDemo {
	Directory dir = new RAMDirectory();

	public static void main(String[] args) throws IOException {
		FieldMaskingSpanQueryDemo demo = new FieldMaskingSpanQueryDemo();
		demo.index();
		demo.search();
	}

	private void index() throws IOException {
		IndexWriter indexWriter = new IndexWriter(dir, new IndexWriterConfig(new StandardAnalyzer()));

		Document doc = new Document();
		doc.add(new TextField("teacher_id", "1", Field.Store.YES));
		doc.add(new TextField("student_firstname", "james", Field.Store.YES));
		doc.add(new TextField("student_surname", "jones", Field.Store.YES));
		indexWriter.addDocument(doc);

		doc = new Document();
		doc.add(new TextField("teacher_id", "2", Field.Store.YES));
		doc.add(new TextField("student_firstname", "james", Field.Store.YES));
		doc.add(new TextField("student_surname", "simth", Field.Store.YES));
		doc.add(new TextField("student_firstname", "sally", Field.Store.YES));
		doc.add(new TextField("student_surname", "jones", Field.Store.YES));
		indexWriter.addDocument(doc);

		indexWriter.close();
	}

	private void search() throws IOException {
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		SpanQuery query1 = new SpanTermQuery(new Term("student_firstname", "james"));
		SpanQuery query2 = new SpanTermQuery(new Term("student_surname", "jones"));

		SpanQuery q2m = new FieldMaskingSpanQuery(query2, "student_firstname");

		SpanQuery query = new SpanNearQuery(new SpanQuery[] { query1, q2m }, -1, false);
		TopDocs topDocs = searcher.search(query, 100);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (int i = 0; i < scoreDocs.length; i++) {
			System.out.println(searcher.explain(query, scoreDocs[i].doc));
			int docId = scoreDocs[i].doc;
			Document document = searcher.doc(docId);
			String path = document.get("teacher_id");
			System.out.println("teacher_id: " + path);
		}
	}
}
