package com.wdxxl.lucene.basic;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

public class Step03Field {
    public static String[] ID = {"1", "2", "3", "4", "5", "6"};
    public static String[] EMAIL = {"aa@wdxxl.com", "bb@wdxxl.com", "cc@wdxxl.com", "dd@sina.org",
            "ee@sinobot.org", "ff@sina.org"};
    public static String[] CONTENT = {"welcome to visit the space", "hello boy", "my name is cc",
            "I like football", "I like football and basketball", "I like movie and swim"};
    public static int[] ATTACH = {2, 3, 1, 4, 5, 6};
    public static String[] NAME = {"zhangsan", "lisi", "john", "jetty", "mike", "jake"};

    public static void main(String args[]) {
        Step03Field step03Field = new Step03Field();
        step03Field.index();
        step03Field.query();
    }

    public void index() {
        IndexWriter writer = null;
        try {
            // 1. Create Directory
            Directory directory =
                    FSDirectory.open(new File(System.getProperty("user.dir")
                            + "/lib/lucene35/field"));

            // 2. Create IndexWriter
            IndexWriterConfig iwc =
                    new IndexWriterConfig(Version.LUCENE_35,
                            new StandardAnalyzer(Version.LUCENE_35));
            writer = new IndexWriter(directory, iwc);

            // 3. Create Document
            Document doc = null;
            for (int i = 0; i < ID.length; i++) {
                doc = new Document();

                // 4. Add Field to Document
                doc.add(new Field("id", ID[i], Field.Store.YES,
                        Field.Index.NOT_ANALYZED_NO_NORMS));
                doc.add(new Field("email", EMAIL[i], Field.Store.YES,
                        Field.Index.NOT_ANALYZED));
                doc.add(new Field("content", CONTENT[i], Field.Store.NO,
                        Field.Index.ANALYZED));// like new Field("content", new FileReader(file))
                doc.add(new Field("name", NAME[i], Field.Store.YES,
                        Field.Index.NOT_ANALYZED_NO_NORMS));

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

    /**
     * Field.Store.YES或者NO（存储域选项）
     * 设置为YES表示把这个域中的内容完全存储到文件中，方便进行文本的的还原。
     * 设置为NO表示把这个域中的内容不存储到文件中，但是可以被索引，此时内容无法完全还原(doc.get)
     *
     * Field.Index(索引选项)
     * Index.ANALYZED: 进行索引和分词，适用于标题和内容等
     * Index.NOT_ANALYZED:进行索引，但不进行分词，比如身份证号，姓名，ID等，适用于精确搜索
     * Index.ANALYZED_NOT_NORMS: 进行分词但是不存储norms信息 (norms中包括-创建索引的时间和权值等信息)
     * Index.NOT_ANALYZED_NOT_NORMS: 即不进行分词也不存储norms信息
     * Index.NO: 不进行索引
     */
    public void query() {
        try {
            Directory directory =
                    FSDirectory.open(new File(System.getProperty("user.dir")
                            + "/lib/lucene35/field"));
            IndexReader reader = IndexReader.open(directory);
            System.out.println("numDocs: " + reader.numDocs());
            System.out.println("maxDocs: " + reader.maxDoc());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}