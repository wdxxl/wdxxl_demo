package com.wdxxl.lucene.boost;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PayloadAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Payload;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.DefaultSimilarity;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.payloads.MaxPayloadFunction;
import org.apache.lucene.search.payloads.PayloadTermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 * 由于Lucene引入了payload，因而可以存储一些自己的信息，用户可以根据自己存储的信息，来影响Lucene的打分
 * QueryParser----------------------------------------------------
 * docid: 0, score:0.2972674, contents:common hey world
 * docid: 1, score:0.2972674, contents:common <b>hey</b> world
 * PayloadTermQuery----------------------------------------------------
 * It is not a bold char.
 * It is a bold char.
 * docid: 1, score:2.101998, contents:common <b>hey</b> world
 * docid: 0, score:0.2101998, contents:common hey world
 * @author wdxxl
 *
 */
public class SimilarityScorePayloadDemo {

	public static void main(String[] args) throws Exception {
		SimilarityScorePayloadDemo similarityScorePayloadDemo = new SimilarityScorePayloadDemo();
		QueryParser parser = new QueryParser(Version.LUCENE_35, "contents", new BoldAnalyzer());
		Query query = parser.parse("common");
		System.out.println("QueryParser----------------------------------------------------");
		similarityScorePayloadDemo.testPayloadScore(query);
		System.out.println("PayloadTermQuery----------------------------------------------------");
		PayloadTermQuery query2 = new PayloadTermQuery(new Term("contents", "hey"), new MaxPayloadFunction());
		similarityScorePayloadDemo.testPayloadScore(query2);
	}

	public void testPayloadScore(Query query) throws Exception {
		Directory dir = new RAMDirectory();
		IndexWriter writer = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_35, new BoldAnalyzer()));
		Document doc1 = new Document();
		Field f1 = new Field("contents", "common hey world", Field.Store.YES, Field.Index.ANALYZED);
		doc1.add(f1);
		writer.addDocument(doc1);
		Document doc2 = new Document();
		Field f2 = new Field("contents", "common <b>hey</b> world", Field.Store.YES, Field.Index.ANALYZED);
		doc2.add(f2);
		writer.addDocument(doc2);
		writer.close();

		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		searcher.setSimilarity(new PayloadSimilarity());
		TopDocs docs = searcher.search(query, 10);
		for (ScoreDoc doc : docs.scoreDocs) {
			System.out.println("docid: " + doc.doc + ", score:" + doc.score + ", contents:"
					+ searcher.doc(doc.doc).get("contents"));
		}
	}
}

// 实现自己的Similarity，从payload中读出信息，根据信息来打分。
class PayloadSimilarity extends DefaultSimilarity {
	private static final long serialVersionUID = 1L;

	@Override
	public float scorePayload(int docId, String fieldName, int start, int end, byte[] payload, int offset, int length) {
		int isBold = BoldFilter.bytes2int(payload);
		if (isBold == BoldFilter.IS_BOLD) {
			System.out.println("It is a bold char.");
			return 10;
		} else {
			System.out.println("It is not a bold char.");
			return 1;
		}
	}
}

class BoldAnalyzer extends Analyzer {
	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		TokenStream result = new WhitespaceTokenizer(Version.LUCENE_35, reader);
		result = new BoldFilter(result);
		return result;
	}
}

class BoldFilter extends TokenFilter {
	public static int IS_NOT_BOLD = 0;
	public static int IS_BOLD = 1;
	private CharTermAttribute charTermAtt;
	private PayloadAttribute payloadAtt;

	protected BoldFilter(TokenStream input) {
		super(input);
		charTermAtt = addAttribute(CharTermAttribute.class);
		payloadAtt = addAttribute(PayloadAttribute.class);
	}

	@Override
	public boolean incrementToken() throws IOException {
		if (input.incrementToken()) {
			String tokenString = charTermAtt.toString();
			if (tokenString.startsWith("<b>") && tokenString.endsWith("</b>")) {
				tokenString = tokenString.replace("<b>", "");
				tokenString = tokenString.replace("</b>", "");
				charTermAtt.setEmpty().append(tokenString);
				payloadAtt.setPayload(new Payload(int2bytes(IS_BOLD)));
			} else {
				payloadAtt.setPayload(new Payload(int2bytes(IS_NOT_BOLD)));
			}
			return true;
		} else {
			return false;
		}
	}

	public static int bytes2int(byte[] b) {
		int mask = 0xff;
		int temp = 0;
		int res = 0;
		for (int i = 0; i < 4; i++) {
			res <<= 8;
			temp = b[i] & mask;
			res |= temp;
		}
		return res;
	}

	public static byte[] int2bytes(int num) {
		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			b[i] = (byte) (num >>> (24 - i * 8));
		}
		return b;
	}

}