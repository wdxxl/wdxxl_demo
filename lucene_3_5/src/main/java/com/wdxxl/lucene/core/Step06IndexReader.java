package com.wdxxl.lucene.core;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.wdxxl.lucene.util.LuceneUtil;
import com.wdxxl.lucene.util.mockdata.MockDataUtil;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
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

/**
 * 项目周期 Reader 只有一个，存在于Application中 或者Writer都只会有一个，或者几个线程池中
 */
public class Step06IndexReader {
    private static Directory directory;
    private IndexReader reader;

    static {
        try {
            directory =
                    FSDirectory.open(new File(System.getProperty("user.dir")
                            + "/lib/lucene35/indexReader"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Step06IndexReader step06IndexReader = new Step06IndexReader();
        step06IndexReader.index();
        step06IndexReader.search();
        System.out.println("-----------------------------");
        step06IndexReader.deleteByTermByWriter();
        step06IndexReader.search();// IndexReader.openIfChanged(reader)
        System.out.println("-----------------------------");
        step06IndexReader.deleteByTermByReader();// 可以通过Reader来删除文档， 好处是，马上会更新索引信息
        step06IndexReader.search();
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
            for (int i = 0; i < MockDataUtil.IDS.length; i++) {
                doc = new Document();
                // 4. Add Field to Document
                doc.add(new Field("id", MockDataUtil.IDS[i], Field.Store.YES,
                        Field.Index.NOT_ANALYZED_NO_NORMS));
                doc.add(new Field("email", MockDataUtil.EMAILS[i], Field.Store.YES,
                        Field.Index.NOT_ANALYZED));
                doc.add(new Field("content", MockDataUtil.CONTENTS[i], Field.Store.NO,
                        Field.Index.ANALYZED));
                doc.add(new Field("name", MockDataUtil.NAMES[i], Field.Store.YES,
                        Field.Index.NOT_ANALYZED_NO_NORMS));
                // 存储数字
                doc.add(new NumericField("attach", Field.Store.YES, true)
                        .setIntValue(MockDataUtil.ATTACHS[i]));
                // 存储日期
                doc.add(new NumericField("date", Field.Store.YES, true)
                        .setLongValue(MockDataUtil.DATES[i].getTime()));
                // 5. IndexWriter add the document to Lucene Index
                writer.addDocument(doc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            LuceneUtil.closeWriter(writer);
        }
    }

    public void search() {
        try {
            TermQuery query = new TermQuery(new Term("content", "like"));
            TopDocs tds = getSeacher().search(query, 10);
            for (ScoreDoc sd : tds.scoreDocs) {
                Document doc = getSeacher().doc(sd.doc);
                // sd.score 评分有对应的公式：加权值和文档所出现的次数有关
                System.out.println("(" + sd.doc + "-" + sd.score + ")" + "email:"
                        + doc.get("email") + ",name:" + doc.get("name") + ",attach:"
                        + doc.get("attach") + ",date:" + formatDate(doc.get("date")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatDate(String dateStr) {
        Date date = new Date(Long.valueOf(dateStr));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public void deleteByTermByWriter() {
        IndexWriter writer = null;
        try {
            IndexWriterConfig iwc =
                    new IndexWriterConfig(Version.LUCENE_35,
                            new StandardAnalyzer(Version.LUCENE_35));
            writer = new IndexWriter(directory, iwc);
            // 参数是一个选项，可以是一个Query，也可以是一个term，term是一个精确查找的值
            writer.deleteDocuments(new Term("id", "1"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            LuceneUtil.closeWriter(writer);
        }
    }

    public void deleteByTermByReader() {
        try {
            // 参数是一个选项，可以是一个Query，也可以是一个term，term是一个精确查找的值
            getReader().deleteDocuments(new Term("id", "2"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private IndexSearcher getSeacher() {
        try {
            if (reader == null) {
                reader = IndexReader.open(directory, false);// 非只读
            } else {
                // Index是单例的话需要设置-- 因为是同一个Reader 所以部分删除的数据并不能知道消失掉
                IndexReader tr = IndexReader.openIfChanged(reader);// 为了打开一个新的Reader.
                if (tr != null) {
                    reader.close();
                    reader = tr;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new IndexSearcher(reader);
    }

    private IndexReader getReader() {
        try {
            if (reader == null) {
                reader = IndexReader.open(directory, false);// 非只读
            } else {
                // Index是单例的话需要设置-- 因为是同一个Reader 所以部分删除的数据并不能知道消失掉
                IndexReader tr = IndexReader.openIfChanged(reader);// 为了打开一个新的Reader.
                if (tr != null) {
                    reader.close();
                    reader = tr;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reader;
    }
}
