package com.wdxxl.lucene.customscorequery;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.function.CustomScoreProvider;

public class WdxxlCustomScoreProvider extends CustomScoreProvider {
	private Query query;
	private IndexReader thisReader;
	public WdxxlCustomScoreProvider(IndexReader reader, Query query) {
		super(reader);
		this.query = query;
		this.thisReader = reader;
	}

	// 此方法中subQueryScore表示默认文档的打分，valSrcScore表示的是评分域的打分
	@Override
	public float customScore(int doc, float subQueryScore, float valSrcScore) throws IOException {
		// 这是按评分的降序排，之前默认的是subQueryScore * valSrcScore
		// return subQueryScore / valSrcScore;
		// return subQueryScore * valSrcScore;
		 TermFreqVector freqVector = thisReader.getTermFreqVector(doc, "content");
         int freqs[] = freqVector.getTermFrequencies();
         Set<Term> terms = new HashSet<Term>();
         query.extractTerms(terms);
         int total = 0;
         for (Term term : terms) {
             if(term.field().equals("content")){
                 int index = freqVector.indexOf(term.text());
                 if (index != -1) {
                     total += freqs[index];
                 }
             }
         }
         return total;
	}

}
