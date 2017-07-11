package com.wdxxl.lucene.pagination;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Step007Pagination {
	private static IndexSearcher searcher;

	public static void main(String[] args) {
		try {
			// 1. Create Directory
			Directory directory = FSDirectory.open(new File(System.getProperty("user.dir") + "/lib/lucene35/index"));
			// 2. Create IndexReader
			IndexReader reader = IndexReader.open(directory);
			searcher = new IndexSearcher(reader);
			// 4. Create Query
			QueryParser parser = new QueryParser(Version.LUCENE_35, "content", new StandardAnalyzer(Version.LUCENE_35));

			Query query = parser.parse("likes");
			// 5. Searcher and return TopDocs
			TopDocs tds = searcher.search(query, 2);
			// 6. ScoreDocs
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				// 7. Get Documents
				Document d = searcher.doc(sd.doc);
				// 8. Get result in Document
				System.out.println("[path1] - " + d.get("path"));
				System.out.println("[filename1] - " + d.get("filename"));
			}

			// pagination...
			TopDocs tds2 = searcher.searchAfter(sds[1], query, 4);
			ScoreDoc[] sds2 = tds2.scoreDocs;
			for (ScoreDoc sd : sds2) {
				Document d = searcher.doc(sd.doc);
				System.out.println("[path2] - " + d.get("path"));
				System.out.println("[filename2] - " + d.get("filename"));
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
}
