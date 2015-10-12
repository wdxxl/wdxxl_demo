package com.wdxxl.lucene.search;

import java.io.File;
import java.io.IOException;

import com.wdxxl.lucene.util.LuceneIndexReaderUtil;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Step07TermRange {
    private static final String PATH = "/lib/lucene35/termRange";
    private static Directory directory;

    static {
        try {
            directory = FSDirectory.open(new File(System.getProperty("user.dir") + PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // LuceneCreateIndexUtil.createIndexbyPath(PATH);
        Step07TermRange step07TermRange = new Step07TermRange();
        // step07TermRange.searchByTermQyery();
        step07TermRange.searchByTermRangeQuery();
        // step07TermRange.searchByNumericRangeQuery();
    }

    // TermQyery 精确匹配
    public void searchByTermQyery() {
        try {
            // 单例获取IndexReader
            IndexReader reader = LuceneIndexReaderUtil.getIndexReader(directory);
            Query query = new TermQuery(new Term("content", "like"));
            output(new IndexSearcher(reader), query);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TermRangeQuery 范围查询
    // Query query = new TermRangeQuery(field, start, end, true, true);
    // 查询field，起始值，结束值，起始是否开闭区间，结束是否开闭区间
    public void searchByTermRangeQuery() {
        try {
            // 单例获取IndexReader
            IndexReader reader = LuceneIndexReaderUtil.getIndexReader(directory);
            Query query = new TermRangeQuery("id", "1", "3", true, true);
            output(new IndexSearcher(reader), query);
            System.out.println("----------");
            query = new TermRangeQuery("name", "a", "s", true, true);
            output(new IndexSearcher(reader), query);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 数字范围查询
    // Query query = NumericRangeQuery.newIntRange(field, min, max, true,true)
    // 查询field，最小值，最大值，最小是否开闭区间，最大是否开闭区间
    public void searchByNumericRangeQuery() {
        try {
            // 单例获取IndexReader
            IndexReader reader = LuceneIndexReaderUtil.getIndexReader(directory);
            Query query = NumericRangeQuery.newIntRange("attach", 1, 3, true, true);
            output(new IndexSearcher(reader), query);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void output(IndexSearcher searcher, Query query) throws CorruptIndexException,
            IOException {
        TopDocs tds = searcher.search(query, 10);
        for (ScoreDoc sd : tds.scoreDocs) {
            Document doc = searcher.doc(sd.doc);
            // sd.score 评分有对应的公式：加权值和文档所出现的次数有关
            System.out.println("(" + sd.doc + "-" + sd.score + ")" + "email:" + doc.get("email")
                    + ",name:" + doc.get("name") + ",attach:" + doc.get("attach") + ",date:"
                    + doc.get("date"));
        }
    }
}
