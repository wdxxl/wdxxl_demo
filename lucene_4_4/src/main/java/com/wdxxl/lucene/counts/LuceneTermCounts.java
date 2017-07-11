package com.wdxxl.lucene.counts;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.DocsEnum;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
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
import org.apache.lucene.util.Version;

// https://stackoverflow.com/questions/19423889/getting-term-counts-in-lucene-4-index
public class LuceneTermCounts {

	public static void main(String[] args) throws IOException {
		FSDirectory directory = FSDirectory.open(new File(System.getProperty("user.dir")
                + "/lib/lucene44/index"));
	    IndexWriter writer = new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_44, new StandardAnalyzer(Version.LUCENE_44)));
	    Document document = null;
        for (int i = 0; i < MockDataUtil.LENGTH; i++) {
        	document = new Document();
            document.add(new TextField("id", MockDataUtil.IDS[i], Field.Store.YES));
    	    document.add(new TextField("content", MockDataUtil.CONTENTS[i], Field.Store.YES));
    	    document.add(new TextField("email", MockDataUtil.EMAILS[i], Field.Store.YES));
    	    document.add(new TextField("name", MockDataUtil.NAMES[i], Field.Store.YES));
    	    writer.addDocument(document);
        }
	    writer.commit();
	    writer.close(true);
	    
	    //
	    IndexReader reader = IndexReader.open(directory);
	    IndexSearcher searcher = new IndexSearcher(reader);
	    Query query = new TermQuery(new Term("content", "like"));
        TopDocs tds = searcher.search(query, 10);
        ScoreDoc[] sds = tds.scoreDocs;
        for (ScoreDoc sd : sds) {
        	System.out.println("[docID] - "+sd.doc);
        	Document d = searcher.doc(sd.doc);
            System.out.println("[id] - " + d.get("id"));
            System.out.println("[content] - " + d.get("content"));
        }
        reader.close();
	    
	    
	    // 
	    IndexReader indexReader = DirectoryReader.open(directory);
	    Bits liveDocs = MultiFields.getLiveDocs(indexReader);
        TermsEnum termEnum = MultiFields.getTerms(indexReader, "content").iterator(null);
        BytesRef bytesRef;
        while ((bytesRef = termEnum.next()) != null) {
        	if(bytesRef.utf8ToString().equals("like")){
		        if (termEnum.seekExact(bytesRef, true)) {
		            DocsEnum docsEnum = termEnum.docs(liveDocs, null);
		            if (docsEnum != null) {
		                int doc;
		                while ((doc = docsEnum.nextDoc()) != DocIdSetIterator.NO_MORE_DOCS) {
		                    System.out.println("field : content, " + bytesRef.utf8ToString() + " in doc " + doc + ": " + docsEnum.freq());
		                }
		            }
		        }
        	}
        }
	    
/*
	    IndexReader indexReader = DirectoryReader.open(directory);
	    Bits liveDocs = MultiFields.getLiveDocs(indexReader);
	    Fields fields = MultiFields.getFields(indexReader);
	    for (String field : fields) {
	        TermsEnum termEnum = MultiFields.getTerms(indexReader, field).iterator(null);
	        BytesRef bytesRef;
	        while ((bytesRef = termEnum.next()) != null) {
	            if (termEnum.seekExact(bytesRef, true)) {
	                DocsEnum docsEnum = termEnum.docs(liveDocs, null);
	                if (docsEnum != null) {
	                    int doc;
	                    while ((doc = docsEnum.nextDoc()) != DocIdSetIterator.NO_MORE_DOCS) {
	                        System.out.println("field : "+ field + ", " +bytesRef.utf8ToString() + " in doc " + doc + ": " + docsEnum.freq());
	                    }
	                }
	            }
	        }
	    }

	    for (String field : fields) {
	        TermsEnum termEnum = MultiFields.getTerms(indexReader, field).iterator(null);
	        BytesRef bytesRef;
	        while ((bytesRef = termEnum.next()) != null) {
	            int freq = indexReader.docFreq(new Term(field, bytesRef));
	            System.out.println(bytesRef.utf8ToString() + " in " + freq + " documents");

	        }
	    }
*/
	}

}
