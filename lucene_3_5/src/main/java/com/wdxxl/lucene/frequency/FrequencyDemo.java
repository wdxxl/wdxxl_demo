package com.wdxxl.lucene.frequency;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.wdxxl.lucene.util.LuceneUtil;

public class FrequencyDemo {
	private static Directory directory;
	private IndexReader reader;

	static {
		try {
			directory = FSDirectory.open(new File(System.getProperty("user.dir") + "/lib/lucene35/frequency"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		FrequencyDemo FrequencyDemo = new FrequencyDemo();
		FrequencyDemo.index();
		System.out.println("-----------------------------------------");
		try {
			FrequencyDemo.calculate();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void calculate() throws IOException {
		BooleanQuery query = new BooleanQuery();
		query.add(new TermQuery(new Term("content", "random")), Occur.MUST);
        query.add(new TermQuery(new Term("content", "text")), Occur.MUST);
		//TermQuery query = new TermQuery(new Term("content", "random"));
        TopDocs tds = getSeacher().search(query, 10);
        ScoreDoc[] sds = tds.scoreDocs;
        for (ScoreDoc sd : sds) {
            Document d = getSeacher().doc(sd.doc);
            System.out.println("[id]-" + d.get("id") + ", [author]-" + d.get("author") + ", [content]-" + d.get("content"));
            
    		TermFreqVector tfv = getSeacher().getIndexReader().getTermFreqVector(sd.doc, "content");
    		System.out.println(Arrays.toString(tfv.getTermFrequencies()));
    		System.out.println(Arrays.toString(tfv.getTerms()));

        }		
	}

	public void index() {
		IndexWriter writer = null;
		try {
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35));
			writer = new IndexWriter(directory, iwc);

			Document doc = new Document();
			doc.add(new Field("author", "author1", Field.Store.YES, Field.Index.NOT_ANALYZED));
			doc.add(new Field("content", "random text", Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.YES));
			doc.add(new Field("id", "1", Field.Store.YES, Field.Index.NOT_ANALYZED));
			writer.addDocument(doc);

			doc = new Document();
			doc.add(new Field("author", "author1", Field.Store.YES, Field.Index.NOT_ANALYZED));
			doc.add(new Field("content", "some more random text", Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.YES));
			doc.add(new Field("id", "2", Field.Store.YES, Field.Index.NOT_ANALYZED));
			writer.addDocument(doc);

			doc = new Document();
			doc.add(new Field("author", "author1", Field.Store.YES, Field.Index.NOT_ANALYZED));
			doc.add(new Field("content", "some more random textual data", Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.YES));
			doc.add(new Field("id", "3", Field.Store.YES, Field.Index.NOT_ANALYZED));
			writer.addDocument(doc);

			doc = new Document();
			doc.add(new Field("author", "author2", Field.Store.YES, Field.Index.NOT_ANALYZED));
			doc.add(new Field("content", "some random text", Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.YES));
			doc.add(new Field("id", "4", Field.Store.YES, Field.Index.NOT_ANALYZED));
			writer.addDocument(doc);

			doc = new Document();
			doc.add(new Field("author", "author3", Field.Store.YES, Field.Index.NOT_ANALYZED));
			doc.add(new Field("content", "some text more random text", Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.YES));
			doc.add(new Field("id", "5", Field.Store.YES, Field.Index.NOT_ANALYZED));
			writer.addDocument(doc);

			doc = new Document();
			doc.add(new Field("author", "author3", Field.Store.YES, Field.Index.NOT_ANALYZED));
			doc.add(new Field("content", "random", Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.YES));
			doc.add(new Field("id", "6", Field.Store.YES, Field.Index.NOT_ANALYZED));
			writer.addDocument(doc);

			doc = new Document();
			doc.add(new Field("content", "random word stuck in alot of other text", Field.Store.YES,
					Field.Index.ANALYZED, Field.TermVector.YES));
			doc.add(new Field("id", "7", Field.Store.YES, Field.Index.NOT_ANALYZED));
			writer.addDocument(doc);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			LuceneUtil.closeWriter(writer);
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

}
