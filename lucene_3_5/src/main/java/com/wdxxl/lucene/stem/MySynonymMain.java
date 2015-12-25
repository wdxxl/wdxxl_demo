package com.wdxxl.lucene.stem;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class MySynonymMain {

    public static void main(String[] args)
            throws CorruptIndexException, LockObtainFailedException, IOException {
        Analyzer analyzer = new MySynonymAnalyzer();
        String str = "我来自中国,我的名字叫什么";
        Directory directory = new RAMDirectory();
        IndexWriter indexWriter =
                new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_35, analyzer));
        Document document = new Document();
        document.add(new Field("content", str, Field.Store.YES, Field.Index.ANALYZED));
        // 在下面方法的时候就把对应的同义词设置了
        indexWriter.addDocument(document);
        indexWriter.close();

        IndexReader indexReader = IndexReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        TopDocs tds = indexSearcher.search(new TermQuery(new Term("content", "大陆")), 10);
        ScoreDoc[] scoreDocs = tds.scoreDocs;

        Document doc = indexSearcher.doc(scoreDocs[0].doc);
        System.out.println(doc.get("content"));
        indexSearcher.close();
        indexReader.close();
    }

    @SuppressWarnings("unused")
    private static void runWithFolder()
            throws CorruptIndexException, LockObtainFailedException, IOException {
        Analyzer analyzer = new MySynonymAnalyzer();
        String str = "我来自中国,我的名字叫什么";
        Directory directory =
                FSDirectory.open(new File(System.getProperty("user.dir") + "/lib/lucene35/stem"));
        IndexWriter indexWriter =
                new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_35, analyzer));
        Document document = new Document();
        document.add(new Field("content", str, Field.Store.YES, Field.Index.ANALYZED));
        // 在下面方法的时候就把对应的同义词设置了
        indexWriter.addDocument(document);
        indexWriter.close();

        IndexReader indexReader = IndexReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        TopDocs tds = indexSearcher.search(new TermQuery(new Term("content", "大陆")), 10);
        ScoreDoc[] scoreDocs = tds.scoreDocs;

        Document doc = indexSearcher.doc(scoreDocs[0].doc);
        System.out.println(doc.get("content"));
        indexSearcher.close();
        indexReader.close();
    }

}
