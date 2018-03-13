package com.wdxxl.lucene.index;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

class MultiReaderDemo {
	static Directory dir1 = new RAMDirectory();
	static Directory dir2 = new RAMDirectory();
	static Directory dir3 = new RAMDirectory();
	static Directory dir4 = new RAMDirectory();
	static Directory dir5 = new RAMDirectory();

	public static void main(String[] args) throws IOException {
		MultiReaderDemo demo = new MultiReaderDemo();
		demo.index(dir1, 1);
		demo.index(dir2, 2);
		demo.index(dir3, 3);
		demo.index(dir4, 4);
		demo.index(dir5, 5);

		demo.search();
	}

	private void index(Directory dir, int i) throws IOException {
		IndexWriter indexWriter = new IndexWriter(dir, new IndexWriterConfig(new StandardAnalyzer()));
		Document doc = new Document();
		if (i % 2 == 0) {
			doc.add(new TextField("text", "good dog " + i, Field.Store.YES));
		} else {
			doc.add(new TextField("text", "dog " + i, Field.Store.YES));
		}
		indexWriter.addDocument(doc);
		indexWriter.close();
	}

	private void search() throws IOException {
		IndexReader reader1 = DirectoryReader.open(dir1);
		IndexReader reader2 = DirectoryReader.open(dir2);
		IndexReader reader3 = DirectoryReader.open(dir3);
		IndexReader reader4 = DirectoryReader.open(dir4);
		IndexReader reader5 = DirectoryReader.open(dir5);
		MultiReader multiReader = new MultiReader(reader1, reader2, reader3, reader4, reader5);
		IndexSearcher searcher = new IndexSearcher(multiReader);
		String queryStr = "dog";
		Query query = new TermQuery(new Term("text", queryStr));
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
