package com.wdxxl.lucene.basic;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
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
import org.apache.lucene.util.Version;

// 加权信息在luke中用norm查看 (baidu的竞价排名)
public class Step04Boost {
    public static String[] ID = {"1", "2", "3", "4", "5", "6"};
    public static String[] EMAIL = {"aa@wdxxl.com", "bb@wdxxl.com", "cc@wdxxl.com", "dd@sina.org",
            "ee@sinobot.org", "ff@sina.org"};
    public static String[] CONTENT = {
        "welcome to visit the space, hope you will like it",
        "hello like boy",
        "my name is cc I like nothing",
        "I like football",
        "I like football and I like basketball",
        "I like movie and swim"};
    public static int[] ATTACH = {2, 3, 1, 4, 5, 6};
    public static String[] NAME = {"zhangsan", "lisi", "john", "jetty", "mike", "jake"};
    private static Map<String, Float> SCORES = new HashMap<>();

    private static Directory directory;
    static {
        SCORES.put("wdxxl.com", 2.0f);
        SCORES.put("sinobot.org", 1.5f);

        try {
            directory =
                    FSDirectory.open(new File(System.getProperty("user.dir")
                            + "/lib/lucene35/boost"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Step04Boost boost = new Step04Boost();
        // boost.index();
        boost.search();
    }

    public void index() {
        IndexWriter writer = null;
        try {
            // 1. Create Directory
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
                doc.add(new Field("id", ID[i], Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
                doc.add(new Field("email", EMAIL[i], Field.Store.YES, Field.Index.NOT_ANALYZED));
                doc.add(new Field("content", CONTENT[i], Field.Store.NO, Field.Index.ANALYZED));
                doc.add(new Field("name", NAME[i], Field.Store.YES,
                        Field.Index.NOT_ANALYZED_NO_NORMS));
                String et = EMAIL[i].substring(EMAIL[i].indexOf("@")+1);
                // 默认是1.0 越高权值越高
                if (SCORES.containsKey(et)) {
                    doc.setBoost(SCORES.get(et));
                } else {
                    doc.setBoost(0.5f);
                }
                // 5. IndexWriter add the document to Lucene Index
                writer.addDocument(doc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeWriter(writer);
        }
    }

    public void search() {
        try {
            IndexReader reader = IndexReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);
            TermQuery query = new TermQuery(new Term("content", "like"));
            TopDocs tds = searcher.search(query, 10);
            for (ScoreDoc sd : tds.scoreDocs) {
                Document doc = searcher.doc(sd.doc);
                // sd.score 评分有对应的公式：加权值和文档所出现的次数有关
                System.out.println("(" + sd.doc + "-" + sd.score + ")"
                        + "email:" + doc.get("email") + ",name:" + doc.get("name"));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeWriter(IndexWriter writer) {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
