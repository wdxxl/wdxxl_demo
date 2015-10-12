package com.wdxxl.lucene.basic;

import java.io.File;
import java.io.IOException;

import com.wdxxl.lucene.util.mockdata.MockDataUtil;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Step03Field {

    public static void main(String args[]) {
        Step03Field step03Field = new Step03Field();
        // step03Field.index();// numDocs: 6 maxDocs: 6 deleteDocs: 0
        // step03Field.deleteByTerm();// numDocs: 5 maxDocs: 6 deleteDocs: 1(删除内容存放于回收站)
        // step03Field.deleteByQuery();
        // step03Field.undelete();// numDocs: 6 maxDocs: 6 deleteDocs: 0
        // step03Field.forceMergeDeletes();// numDocs: 5 maxDocs: 5 deleteDocs: 0(强制删除，就是清空回收站)
        // step03Field.forceMerge();// numDocs: 20 maxDocs: 21 deleteDocs: 1
        // step03Field.updateDocument();// numDocs: 22 maxDocs: 23 deleteDocs: 1
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
            for (int i = 0; i < MockDataUtil.LENGTH; i++) {
                doc = new Document();

                // 4. Add Field to Document
                doc.add(new Field("id", MockDataUtil.IDS[i], Field.Store.YES,
                        Field.Index.NOT_ANALYZED_NO_NORMS));
                doc.add(new Field("email", MockDataUtil.EMAILS[i], Field.Store.YES,
                        Field.Index.NOT_ANALYZED));
                doc.add(new Field("content", MockDataUtil.CONTENTS[i], Field.Store.NO,
                        Field.Index.ANALYZED));// like new Field("content", new FileReader(file))
                doc.add(new Field("name", MockDataUtil.NAMES[i], Field.Store.YES,
                        Field.Index.NOT_ANALYZED_NO_NORMS));

                // 5. IndexWriter add the document to Lucene Index
                writer.addDocument(doc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeWriter(writer);
        }
    }

    /**
     * Field.Store.YES或者NO（存储域选项） 设置为YES表示把这个域中的内容完全存储到文件中，方便进行文本的的还原。
     * 设置为NO表示把这个域中的内容不存储到文件中，但是可以被索引，此时内容无法完全还原(doc.get)
     *
     * Field.Index(索引选项) Index.ANALYZED: 进行索引和分词，适用于标题和内容等
     * Index.NOT_ANALYZED:进行索引，但不进行分词，比如身份证号，姓名，ID等，适用于精确搜索 Index.ANALYZED_NOT_NORMS:
     * 进行分词但是不存储norms信息 (norms中包括-创建索引的时间和权值等信息) Index.NOT_ANALYZED_NOT_NORMS: 即不进行分词也不存储norms信息
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
            System.out.println("deleteDocs: " + reader.numDeletedDocs());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 参数是一个选项，可以是一个Query, 也可以是一个Term（精确查找）
    // 此时删除的文档并不会完全删除，而是存在于一个回收站中，可以恢复
    // 删除后 存在类似回收站的情况 （.del文件）
    public void deleteByTerm() {
        IndexWriter writer = null;
        try {
            Directory directory =
                    FSDirectory.open(new File(System.getProperty("user.dir")
                            + "/lib/lucene35/field"));
            IndexWriterConfig iwc =
                    new IndexWriterConfig(Version.LUCENE_35,
                            new StandardAnalyzer(Version.LUCENE_35));
            writer = new IndexWriter(directory, iwc);
            // 参数是一个选项，可以是一个Query，也可以是一个term，term是一个精确查找的值
            writer.deleteDocuments(new Term("id", "1"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeWriter(writer);
        }
    }

    public void deleteByQuery() {
        IndexWriter writer = null;
        try {
            Directory directory =
                    FSDirectory.open(new File(System.getProperty("user.dir")
                            + "/lib/lucene35/field"));
            IndexWriterConfig iwc =
                    new IndexWriterConfig(Version.LUCENE_35,
                            new StandardAnalyzer(Version.LUCENE_35));
            writer = new IndexWriter(directory, iwc);
            BooleanQuery query = new BooleanQuery();
            query.add(new TermQuery(new Term("id", "1")), Occur.SHOULD);
            query.add(new TermQuery(new Term("id", "2")), Occur.SHOULD);
            query.add(new TermQuery(new Term("id", "3")), Occur.SHOULD);
            query.add(new TermQuery(new Term("id", "4")), Occur.SHOULD);
            query.add(new TermQuery(new Term("id", "5")), Occur.SHOULD);
            query.add(new TermQuery(new Term("id", "6")), Occur.SHOULD);
            query.add(new TermQuery(new Term("id", "11")), Occur.SHOULD);
            // 参数是一个选项，可以是一个Query，也可以是一个term，term是一个精确查找的值
            writer.deleteDocuments(query);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeWriter(writer);
        }
    }

    // 还原回收站的数据
    // 恢复时候需要对indexReader的readonly设置为false
    public void undelete() {
        // 使用indexReader进行恢复回收站数据
        try {
            Directory directory =
                    FSDirectory.open(new File(System.getProperty("user.dir")
                            + "/lib/lucene35/field"));
            // 恢复时，必须吧indexReader的readonly设置为false
            IndexReader reader = IndexReader.open(directory, false);
            reader.undeleteAll();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 强制删除，就是清空回收站
    // 再Lucene3.5之前都是使用 optimize() 该操作消耗资源
    public void forceMergeDeletes() {
        IndexWriter writer = null;
        try {
            Directory directory =
                    FSDirectory.open(new File(System.getProperty("user.dir")
                            + "/lib/lucene35/field"));
            IndexWriterConfig iwc =
                    new IndexWriterConfig(Version.LUCENE_35,
                            new StandardAnalyzer(Version.LUCENE_35));
            writer = new IndexWriter(directory, iwc);

            writer.forceMergeDeletes();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeWriter(writer);
        }
    }

    // 会将索引合并为2段，着两端中的被删除的数据会被清空
    // 特别注意：此处Lucene在3.5之后不建议使用，因为会大量开销，lucene会自动优化
    public void forceMerge() {
        IndexWriter writer = null;
        try {
            Directory directory =
                    FSDirectory.open(new File(System.getProperty("user.dir")
                            + "/lib/lucene35/field"));
            IndexWriterConfig iwc =
                    new IndexWriterConfig(Version.LUCENE_35,
                            new StandardAnalyzer(Version.LUCENE_35));
            writer = new IndexWriter(directory, iwc);

            writer.forceMerge(2);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeWriter(writer);
        }
    }

    // Lucene并没有提供更新，这里的更新操作其实是如下两个操作
    // 先删除之后再添加
    public void updateDocument() {
        IndexWriter writer = null;
        try {
            Directory directory =
                    FSDirectory.open(new File(System.getProperty("user.dir")
                            + "/lib/lucene35/field"));
            IndexWriterConfig iwc =
                    new IndexWriterConfig(Version.LUCENE_35,
                            new StandardAnalyzer(Version.LUCENE_35));
            writer = new IndexWriter(directory, iwc);

            Document doc = new Document();
            doc.add(new Field("id", "11", Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
            doc.add(new Field("email", MockDataUtil.EMAILS[0], Field.Store.YES,
                    Field.Index.NOT_ANALYZED));
            doc.add(new Field("content", MockDataUtil.CONTENTS[0], Field.Store.NO,
                    Field.Index.ANALYZED));
            doc.add(new Field("name", MockDataUtil.NAMES[0], Field.Store.YES,
                    Field.Index.NOT_ANALYZED_NO_NORMS));

            writer.updateDocument(new Term("id", "1"), doc);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeWriter(writer);
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
