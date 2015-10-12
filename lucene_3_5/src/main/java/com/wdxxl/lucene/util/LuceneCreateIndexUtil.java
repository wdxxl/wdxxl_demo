package com.wdxxl.lucene.util;

import java.io.File;
import java.io.IOException;

import com.wdxxl.lucene.util.mockdata.MockDataUtil;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class LuceneCreateIndexUtil {

    // like "/lib/lucene35/field"
    public static void createIndexbyPath(String path) {
        try {
            Directory directory = FSDirectory.open(new File(System.getProperty("user.dir") + path));
            creatIndex(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void creatIndex(Directory directory) {
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
            for (int i = 0; i < MockDataUtil.IDS.length; i++) {
                doc = new Document();
                // 4. Add Field to Document
                doc.add(new Field("id", MockDataUtil.IDS[i], Field.Store.YES,
                        Field.Index.NOT_ANALYZED_NO_NORMS));

                doc.add(new Field("email", MockDataUtil.EMAILS[i], Field.Store.YES,
                        Field.Index.NOT_ANALYZED));
                // 内容不存储还原，分词
                doc.add(new Field("content", MockDataUtil.CONTENTS[i], Field.Store.NO,
                        Field.Index.ANALYZED));
                //
                doc.add(new Field("name", MockDataUtil.NAMES[i], Field.Store.YES,
                        Field.Index.NOT_ANALYZED_NO_NORMS));
                // 存储数字
                doc.add(new NumericField("attach", Field.Store.YES, true)
                        .setIntValue(MockDataUtil.ATTACHS[i]));
                // 存储日期
                doc.add(new NumericField("date", Field.Store.YES, true)
                        .setLongValue(MockDataUtil.DATES[i].getTime()));
                // 加权
                String et =
                        MockDataUtil.EMAILS[i].substring(MockDataUtil.EMAILS[i].indexOf("@") + 1);
                // 默认是1.0 越高权值越高
                if (MockDataUtil.SCORES.containsKey(et)) {
                    doc.setBoost(MockDataUtil.SCORES.get(et));
                } else {
                    doc.setBoost(0.5f);
                }
                // 5. IndexWriter add the document to Lucene Index
                writer.addDocument(doc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            LuceneUtil.closeWriter(writer);
        }
    }
}
