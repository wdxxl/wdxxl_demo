package com.wdxxl.lucene.counts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.DocsEnum;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.BytesRef;

// https://stackoverflow.com/questions/2249364/in-a-lucene-lucene-net-search-how-do-i-count-the-number-of-hits-per-document
public class UseDocIdToCountTerms {
	private static FSDirectory directory;
    static{
    	try {
		 directory = FSDirectory.open(new File(System.getProperty("user.dir")
					+ "/lib/lucene44/index"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	// http://www.cnblogs.com/LBSer/p/4419052.html
	public static void main(String[] args) throws IOException {
		IndexReader indexReader = DirectoryReader.open(directory);
		List<Integer> docIds = getDocIds();
		TermsEnum termEnum = MultiFields.getTerms(indexReader, "content").iterator(null);
		Bits liveDocs = MultiFields.getLiveDocs(indexReader);
		BytesRef bytesRef;
		while ((bytesRef = termEnum.next()) != null) {
        	if(bytesRef.utf8ToString().equals("like")){
		        if (termEnum.seekExact(bytesRef, true)) {
		            DocsEnum docsEnum = termEnum.docs(liveDocs, null);
		            if (docsEnum != null) {
		                int doc;
		                while ((doc = docsEnum.nextDoc()) != DocIdSetIterator.NO_MORE_DOCS) {
		                	System.out.println("docID: " + docsEnum.docID());
		                    System.out.println("field content, " + bytesRef.utf8ToString() + " in docId " + doc + ": " + docsEnum.freq());
		                }
		            }
		        }
        	}
        }
	}

	public static List<Integer> getDocIds() throws IOException{
		List<Integer> results = new ArrayList<>();
		IndexReader reader = IndexReader.open(directory);
	    IndexSearcher searcher = new IndexSearcher(reader);
	    Query query = new TermQuery(new Term("content", "like"));
        TopDocs tds = searcher.search(query, 10);
        ScoreDoc[] sds = tds.scoreDocs;
        for (ScoreDoc sd : sds) {
        	results.add(sd.doc);
        	Document d = searcher.doc(sd.doc);
        	System.out.println("[docID] - "+sd.doc + ", [id] - " + d.get("id") + ", [content] - " + d.get("content"));
        }
        reader.close();
		return results;
	}
	 

}
