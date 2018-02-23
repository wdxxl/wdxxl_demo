package com.wdxxl.lucene.boost.score;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.FieldInvertState;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.DefaultSimilarity;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Similarity;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class ScoreDemo {

	public static void main(String[] args) throws Exception {
		Similarity similarity = new WdxxlSimilarity();
		new ScoreDemo().testWdxxlScore(similarity, "book chinese about");
	}

	private void testWdxxlScore(Similarity similarity, String queryStr) throws Exception {
		Directory dir = new RAMDirectory();
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
		IndexWriterConfig indexWriterConfig =new IndexWriterConfig(Version.LUCENE_35, analyzer);
		indexWriterConfig.setSimilarity(similarity); // Index Writer Also need Similarity.
		IndexWriter writer = new IndexWriter(dir, indexWriterConfig);
//		Document doc1 = new Document();
//		Field f1 = new Field("contents", "this book is about english", Field.Store.YES, Field.Index.ANALYZED);
//		f1.setBoost(10);
//		doc1.add(f1);
//		writer.addDocument(doc1);
		Document doc2 = new Document();
		Field f2 = new Field("contents", "this book is about chinese", Field.Store.YES, Field.Index.ANALYZED);
		doc2.add(f2);
		//doc2.setBoost(20);
		writer.addDocument(doc2);
//		Document doc3 = new Document();
//		Field f3 = new Field("contents", "this book is about japanese", Field.Store.YES, Field.Index.ANALYZED);
//		doc3.add(f3);
//		writer.addDocument(doc3);
		writer.close();

		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		searcher.setSimilarity(similarity);
		QueryParser parser = new QueryParser(Version.LUCENE_35, "contents", new StandardAnalyzer(Version.LUCENE_35));
		Query query = parser.parse(queryStr);
		TopDocs docs = searcher.search(query, 10);
		for (ScoreDoc doc : docs.scoreDocs) {
			System.out.println("docid: " + doc.doc + ", score:" + doc.score + ", contents:"
					+ searcher.doc(doc.doc).get("contents"));
		}
	}
}

class WdxxlSimilarity extends DefaultSimilarity {
	private static final long serialVersionUID = 1L;

	@Override
	public float computeNorm(String field, FieldInvertState state) {
		final int numTerms;
		if (discountOverlaps)
			numTerms = state.getLength() - state.getNumOverlap();
		else
			numTerms = state.getLength();
		System.out.println("computeNorm= " + state.getBoost() * ((float) (1.0 / Math.sqrt(numTerms))) + ", field="
				+ field + ", state.getLength()=" + state.getLength() + ", state.getNumOverlap()="
				+ state.getNumOverlap() + ", numTerms=" + numTerms + ", state.getBoost()= " + state.getBoost());
		return state.getBoost() * ((float) (1.0 / Math.sqrt(numTerms)));
	}

	@Override
	public float queryNorm(float sumOfSquaredWeights) {
		System.out.println("queryNorm= " + (float) (1.0 / Math.sqrt(sumOfSquaredWeights)) + ", sumOfSquaredWeights="
				+ sumOfSquaredWeights);
		return (float) (1.0 / Math.sqrt(sumOfSquaredWeights));
	}

	/**
	 * tf(t in d)
	 * term t在文档d中出现的个数，它的计算公式官网给出的是：
	 * 比如有个文档叫做”this is book about chinese book”， 我的搜索项为”book”，那么这个搜索项对应文档的freq就为2，那么tf值就为根号2，即1.4142135
	 */
	@Override
	public float tf(float freq) {
		// System.out.println("tf= " + (float) Math.sqrt(freq) + ", freq=" + freq);
		return (float) Math.sqrt(freq);
	}


	/**
	 * idf(t)
	 * 关联到反转文档频率，文档频率指出现 term t 的文档数docFreq。docFreq 越少 idf 就越高（物以稀为贵），但在同一个查询下些值是相同的。默认实现：
	 */
	@Override
	public float idf(int docFreq, int numDocs) {
		System.out.println("idf= " + (float) (Math.log(numDocs / (double) (docFreq + 1)) + 1.0) + ", docFreq=" + docFreq
				+ ", numDocs=" + numDocs);
		return (float) (Math.log(numDocs / (double) (docFreq + 1)) + 1.0);
	}

	@Override
	public float coord(int overlap, int maxOverlap) {
		System.out.println(
				"coord= " + overlap / (float) maxOverlap + ", overlap=" + overlap + ", maxOverlap=" + maxOverlap);
		return overlap / (float) maxOverlap;
	}

}
