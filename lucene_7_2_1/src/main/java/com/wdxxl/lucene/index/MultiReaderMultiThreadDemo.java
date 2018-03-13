package com.wdxxl.lucene.index;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

// No Ranking
public class MultiReaderMultiThreadDemo {
	static Directory dir1 = new RAMDirectory();
	static Directory dir2 = new RAMDirectory();
	static Directory dir3 = new RAMDirectory();
	static Directory dir4 = new RAMDirectory();
	static Directory dir5 = new RAMDirectory();

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		MultiReaderMultiThreadDemo demo = new MultiReaderMultiThreadDemo();
		demo.index(dir1, 1);
		demo.index(dir2, 2);
		demo.index(dir3, 3);
		demo.index(dir4, 4);
		demo.index(dir5, 5);

		demo.searchMultiThread();
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

	private void searchMultiThread() throws IOException, InterruptedException, ExecutionException {
		int count = 5;
		ExecutorService pool = Executors.newFixedThreadPool(count);

		IndexReader reader1 = DirectoryReader.open(dir1);
		IndexReader reader2 = DirectoryReader.open(dir2);
		IndexReader reader3 = DirectoryReader.open(dir3);
		IndexReader reader4 = DirectoryReader.open(dir4);
		IndexReader reader5 = DirectoryReader.open(dir5);

		IndexSearcher[] searchers = new IndexSearcher[5];
		searchers[0] = new IndexSearcher(reader1);
		searchers[1] = new IndexSearcher(reader2);
		searchers[2] = new IndexSearcher(reader3);
		searchers[3] = new IndexSearcher(reader4);
		searchers[4] = new IndexSearcher(reader5);

		String queryStr = "dog";
		Query query = new TermQuery(new Term("text", queryStr));

		List<Future<List<Document>>> futures = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			futures.add(pool.submit(new CalculationThread(searchers[i], query)));
		}

		int t = 0;
		for (Future<List<Document>> future : futures) {
			List<Document> list = future.get();
			if (null == list || list.size() <= 0) {
				t++;
				continue;
			}

			for (Document doc : list) {
				String path = doc.get("text");
				System.out.println("text:" + path);
			}
		}
		pool.shutdown();
		if (t == count) {
			System.out.println("No Results.");
		}
	}
}

class CalculationThread implements Callable<List<Document>> {
	IndexSearcher indexSearcher;
	Query query;

	public CalculationThread(IndexSearcher indexSearcher, Query query) {
		this.indexSearcher = indexSearcher;
		this.query = query;
	}

	@Override
	public List<Document> call() throws Exception {
		List<Document> results = new ArrayList<>();
		TopDocs topDocs = indexSearcher.search(query, 100);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (int i = 0; i < scoreDocs.length; i++) {
			int docId = scoreDocs[i].doc;
			Document document = indexSearcher.doc(docId);
			results.add(document);
		}
		return results;
	}
}
