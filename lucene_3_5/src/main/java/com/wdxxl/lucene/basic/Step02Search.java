package com.wdxxl.lucene.basic;

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

public class Step02Search {
    private IndexSearcher searcher;

    /**
     * Steps for search method. 
     * 1. Create Directory 
     * 2. Create IndexReader 
     * 3. Create IndexSearcher 
     * 4. Create Query 
     * 5. Searcher and return TopDocs 
     * 6. ScoreDocs 
     * 7. Get Documents 
     * 8. Get result in Document
     */
    public static void main(String[] args) {
        new Step02Search().search();
    }

    public void search() {
        try {
            // 1. Create Directory
            Directory directory =
                    FSDirectory.open(new File(System.getProperty("user.dir")
                            + "/lib/lucene35/index"));
            // 2. Create IndexReader
            IndexReader reader = IndexReader.open(directory);
            searcher = new IndexSearcher(reader);
            // 4. Create Query
            QueryParser parser =
                    new QueryParser(Version.LUCENE_35, "content", new StandardAnalyzer(
                            Version.LUCENE_35));

            Query query = parser.parse("likes");
            // 5. Searcher and return TopDocs
            TopDocs tds = searcher.search(query, 10);
            // 6. ScoreDocs
            ScoreDoc[] sds = tds.scoreDocs;
            for (ScoreDoc sd : sds) {
                // 7. Get Documents
                Document d = searcher.doc(sd.doc);
                // 8. Get result in Document
                System.out.println("[path] - " + d.get("path"));
                System.out.println("[filename] - " + d.get("filename"));
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
