package com.wdxxl.lucene.customscorequery;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import com.wdxxl.lucene.util.LuceneUtil;
import com.wdxxl.lucene.util.mockdata.MockDataUtil;


// 1. http://www.jianshu.com/p/eebbe2ea482d
// 2. http://iamyida.iteye.com/blog/2200742
/**
 * 
 * scoreQuery.createWeight(searcher)
 * Field.TermVector.YES
 * 
 */
public class CustomerScoreTest {
	static Directory dir = new RAMDirectory();

	public static void main(String[] args) throws ParseException {
		createIndex();
		// searchByScoreQuery();
		searchByScoreBooleanQuery();
	}
	
	private static void searchByScoreBooleanQuery() throws ParseException{
        try {
            IndexSearcher searcher = new IndexSearcher(IndexReader.open(dir));
            BooleanQuery query = new BooleanQuery();
            query.add(new TermQuery(new Term("name", "mike")), Occur.SHOULD);
            query.add(new TermQuery(new Term("content", "football")), Occur.SHOULD);
            query.add(new TermQuery(new Term("content", "like")), Occur.SHOULD);
            
            WdxxlCustomScoreQuery scoreQuery = new WdxxlCustomScoreQuery(query);
            TopDocs tds = searcher.search(scoreQuery, 3); // this also works
            //TopDocs tds = searcher.search(scoreQuery.createWeight(searcher), null, 20);//使用自定义的query
            System.out.println("TotalHits: " + tds.totalHits);
            for (ScoreDoc sd : tds.scoreDocs) {
				Document doc = searcher.doc(sd.doc);
				// sd.score 评分有对应的公式：加权值和文档所出现的次数有关
                System.out.println("[ DocId - " + sd.doc + ", Score - " + sd.score + ", Hits - " + (int) sd.score +"]" + " email:"
                        + doc.get("email") + ", name:" + doc.get("name") + ", attach:"
                        + doc.get("attach") + ", date:" + doc.get("date") + ", content:" + doc.get("content"));
            }
            searcher.close();
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	private static void searchByScoreQuery() throws ParseException{
        try {
            IndexSearcher searcher = new IndexSearcher(IndexReader.open(dir));
            QueryParser queryParser = new QueryParser(Version.LUCENE_35, "content", new StandardAnalyzer(Version.LUCENE_35));
            Query query = queryParser.parse("like");
            // FuzzyQuery query = new FuzzyQuery(new Term("content", "lik*"),0.4f,0); //Query content:lik*~0.4 does not implement createWeight & not work now
    		
            WdxxlCustomScoreQuery scoreQuery = new WdxxlCustomScoreQuery(query);
            TopDocs tds = searcher.search(scoreQuery, 3); // this also works
            //TopDocs tds = searcher.search(scoreQuery.createWeight(searcher), null, 20);//使用自定义的query
            System.out.println("TotalHits: " + tds.totalHits);
            for (ScoreDoc sd : tds.scoreDocs) {
				Document doc = searcher.doc(sd.doc);
				// sd.score 评分有对应的公式：加权值和文档所出现的次数有关
                System.out.println("[ DocId - " + sd.doc + ", Score - " + sd.score + ", Hits - " + (int) sd.score +"]" + " email:"
                        + doc.get("email") + ", name:" + doc.get("name") + ", attach:"
                        + doc.get("attach") + ", date:" + doc.get("date") + ", content:" + doc.get("content"));
            }
            searcher.close();
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	private static void createIndex() {
		IndexWriter writer = null;
		try {
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35));
			writer = new IndexWriter(dir, iwc);

			// 3. Create Document
			Document doc = null;
			for (int i = 0; i < MockDataUtil.IDS.length; i++) {
				doc = new Document();
				// 4. Add Field to Document
				doc.add(new Field("id", MockDataUtil.IDS[i], Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
				doc.add(new Field("email", MockDataUtil.EMAILS[i], Field.Store.YES, Field.Index.NOT_ANALYZED));
				doc.add(new Field("content", MockDataUtil.CONTENTS[i], Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.YES));
				doc.add(new Field("name", MockDataUtil.NAMES[i], Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
				// 存储数字
				doc.add(new NumericField("attach", Field.Store.YES, true).setIntValue(MockDataUtil.ATTACHS[i]) );
				// 存储日期
				doc.add(new NumericField("date", Field.Store.YES, true).setLongValue(MockDataUtil.DATES[i].getTime()));
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
