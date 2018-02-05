package com.wdxxl.lucene.merge;

import java.io.IOException;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class IndexMergeDemo {
	static Directory DESTINATION_DIR = new RAMDirectory();
	static Directory SOURCES_DIR = new RAMDirectory();
	static Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
	static String[] BookNames = { "java core", "java swift core", "java core basic", "java developer",
			"java tutorial" };
	static String[] DeleteFlags = { "false", "false", "false", "false", "false" };

	public static void main(String[] args) throws CorruptIndexException, IOException, ParseException {
		index(DESTINATION_DIR, "false");
		index(SOURCES_DIR, "true");

		System.out.println("-------------------DESTINATION_DIR------------------------");
		display(DESTINATION_DIR, searcher(DESTINATION_DIR, "BookNames", "java"));

		System.out.println("-------------------SOURCES_DIR------------------------");
		display(SOURCES_DIR, searcher(SOURCES_DIR, "BookNames", "java"));

		System.out.println("-------------------After Merge---SOURCES_DIR----------------");
		Date start = new Date();
		mergeIndex();
		Date end = new Date();
		System.out.println("It took: " + ((end.getTime() - start.getTime())));

		display(DESTINATION_DIR, searcher(DESTINATION_DIR, "BookNames", "java"));
	}

	public static void mergeIndex() throws CorruptIndexException, LockObtainFailedException, IOException {
		IndexWriter writer = new IndexWriter(DESTINATION_DIR, new IndexWriterConfig(Version.LUCENE_35, analyzer));
		writer.addIndexes(SOURCES_DIR);
		writer.close();
	}

	// 按照关键字查询图书
	public static TopDocs searcher(Directory dir, String fieldName, String keyWords)
			throws CorruptIndexException, IOException, ParseException {
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		QueryParser queryParser = new QueryParser(Version.LUCENE_35, fieldName, analyzer);
		Query query = queryParser.parse(keyWords);
		TopDocs topDocs = searcher.search(query, 25);
		searcher.close();
		return topDocs;
	}

	// 把查询到的图书进行输出
	public static void display(Directory dir, TopDocs topDocs) throws CorruptIndexException, IOException {
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		ScoreDoc[] scoreDoc = topDocs.scoreDocs;
		for (int i = 0; i < scoreDoc.length; i++) {
			Document doc = searcher.doc(scoreDoc[i].doc);
			System.out.println("BookNames: " + doc.get("BookNames") + ", DeleteFlags: " + doc.get("DeleteFlags"));
		}
		searcher.close();
	}

	// 对图书名称进行索引
	public static void index(Directory dir, String deleteFlag)
			throws CorruptIndexException, LockObtainFailedException, IOException {
		IndexWriter writer = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_35, analyzer));
		for (int j = 0; j < 100000; j++) {// // Integer.MAX_VALUE
			for (int i = 0; i < BookNames.length; i++) {
				Document doc = new Document();
				doc.add(new Field("BookNames", BookNames[i], Field.Store.YES, Field.Index.ANALYZED));
				doc.add(new Field("DeleteFlags", deleteFlag, Field.Store.YES, Field.Index.ANALYZED));
				writer.addDocument(doc);
			}
		}
		writer.close();
	}
	/**
	 * if // Integer.MAX_VALUE
	 * Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
	 * at org.apache.lucene.util.BytesRef.<init>(BytesRef.java:65)
	 * at org.apache.lucene.store.DataOutput.writeString(DataOutput.java:102)
	 * at org.apache.lucene.index.FieldsWriter.writeField(FieldsWriter.java:200)
	 *
	 * Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "main"
	 * Exception in thread "Lucene Merge Thread #0" org.apache.lucene.index.MergePolicy$MergeException: java.lang.OutOfMemoryError: GC overhead limit exceeded
	 * at org.apache.lucene.index.ConcurrentMergeScheduler.handleMergeException(ConcurrentMergeScheduler.java:517)
	 */
}
