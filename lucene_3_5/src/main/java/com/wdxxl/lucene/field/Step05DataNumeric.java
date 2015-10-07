package com.wdxxl.lucene.field;

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

public class Step05DataNumeric {
    private static Directory directory;
    private IndexSearcher searcher;

    static {
        try {
            directory =
                    FSDirectory.open(new File(System.getProperty("user.dir")
                            + "/lib/lucene35/dataNumeric"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * (4-0.44857934)email:ee@sinobot.org,name:mike,attach:5,date:2017-05-12
     * (1-0.42292467)email:bb@wdxxl.com,name:lisi,attach:3,date:2014-05-12
     * (3-0.42292467)email:dd@sina.org,name:jetty,attach:4,date:2016-05-12
     * (5-0.42292467)email:ff@sina.org,name:jake,attach:6,date:2013-05-12
     * (0-0.3171935)email:aa@wdxxl.com,name:zhangsan,attach:2,date:2013-05-12
     * (2-0.3171935)email:cc@wdxxl.com,name:john,attach:1,date:2015-05-12
     */
    public static void main(String[] args) {
        Step05DataNumeric step05DataNumeric = new Step05DataNumeric();
        // step05DataNumeric.index();
        step05DataNumeric.search();
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
            IndexReader reader = IndexReader.open(directory);
            searcher = new IndexSearcher(reader);
            TermQuery query = new TermQuery(new Term("content", "like"));
            TopDocs tds = searcher.search(query, 10);
            for (ScoreDoc sd : tds.scoreDocs) {
                Document doc = searcher.doc(sd.doc);
                // sd.score 评分有对应的公式：加权值和文档所出现的次数有关
                System.out.println("(" + sd.doc + "-" + sd.score + ")" + "email:"
                        + doc.get("email") + ",name:" + doc.get("name") + ",attach:"
                        + doc.get("attach") + ",date:" + formatDate(doc.get("date")));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatDate(String dateStr) {
        Date date = new Date(Long.valueOf(dateStr));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}