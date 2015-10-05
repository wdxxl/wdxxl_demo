package com.wdxxl.lucene.basic;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

public class Step01Index {

    /**
     * Steps for index method. 
     * 1. Create Directory 
     * 2. Create IndexWriter 
     * 3. Create Document 
     * 4. Add Field to Document 
     * 5. IndexWriter add the document to Lucene Index
     */
    public static void main(String[] args) {
        new Step01Index().index();
    }

    public void index() {
        IndexWriter writer = null;
        try {
            // 1. Create Directory
            Directory directory =
                    FSDirectory.open(new File(System.getProperty("user.dir")
                            + "/lib/lucene35/index"));

            // 2. Create IndexWriter
            IndexWriterConfig iwc =
                    new IndexWriterConfig(Version.LUCENE_35,
                            new StandardAnalyzer(Version.LUCENE_35));
            writer = new IndexWriter(directory, iwc);

            // 3. Create Document
            Document doc = null;
            File f = new File(System.getProperty("user.dir") + "/lib/lucene35/datafiles");
            for (File file : f.listFiles()) {
                doc = new Document();

                // 4. Add Field to Document
                doc.add(new Field("content", new FileReader(file)));
                doc.add(new Field("filename", file.getName(), Field.Store.YES, Index.NOT_ANALYZED));
                doc.add(new Field("path", file.getAbsolutePath(), Field.Store.YES,
                        Index.NOT_ANALYZED));

                // 5. IndexWriter add the document to Lucene Index
                writer.addDocument(doc);
            }
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (LockObtainFailedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    // 6. writer must be closed.
                    writer.close();
                } catch (CorruptIndexException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
