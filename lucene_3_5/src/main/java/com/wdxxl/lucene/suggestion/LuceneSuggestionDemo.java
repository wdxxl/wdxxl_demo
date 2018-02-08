package com.wdxxl.lucene.suggestion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class LuceneSuggestionDemo {
	static Directory SOURCES_DIR = new RAMDirectory();
	static Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
	static String[] BookNames = { "engine/pylon java core", "pylon java swift core</z>", "engine\\pylon core basic",
			"java engine@pylon", "java tutorial 11-12-13" };
	static String[] DeleteFlags = { "false", "false", "false", "false", "false" };

	public static void main(String[] args) throws CorruptIndexException, IOException, ParseException {
		index(SOURCES_DIR, "true");
		System.out.println("-------------------SOURCES_DIR-------Case_1-----------------");
		display(SOURCES_DIR, searcher(SOURCES_DIR, "BookNames", "pylon"));
		System.out.println("-------------------SOURCES_DIR------Case_2------------------");
		display(SOURCES_DIR, searcher(SOURCES_DIR, "BookNames", "11-12-13"));
		System.out.println("-------------------SOURCES_DIR------Case_3------------------");
		display(SOURCES_DIR, searcher(SOURCES_DIR, "BookNames", "z"));
		System.out.println("-------------------Suggestion------------------------");
		getSuggestion("pyl", 5);
	}

	public static List<String> getSuggestion(String word, int numSug) throws IOException {
		List<String> result = new ArrayList<String>();
		IndexReader reader = null;
		SpellChecker spellChecker = null;
		try {
			reader = IndexReader.open(SOURCES_DIR);
			spellChecker = new SpellChecker(SOURCES_DIR);

			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35));
			spellChecker.indexDictionary(new LuceneDictionary(reader, "BookNames"), iwc, false);
			String[] suggestions = spellChecker.suggestSimilar(word, numSug);

			if (suggestions != null && suggestions.length > 0) {
				result = Arrays.asList(suggestions);
				System.out.println("Suggestions found for word:" + word);
				for (String str : suggestions) {
					System.out.println("Did you mean:" + str);
				}
			} else {
				System.out.println("No suggestions found for word:" + word);
			}
		} finally {
			spellChecker.close();
			reader.close();
			SOURCES_DIR.close();
		}
		return result;
	}

	// 按照关键字查询图书
	public static TopDocs searcher(Directory dir, String fieldName, String keyWords)
			throws CorruptIndexException, IOException, ParseException {
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		QueryParser queryParser = new QueryParser(Version.LUCENE_35, fieldName, analyzer);
		Query query = queryParser.parse(keyWords);
		TopDocs topDocs = searcher.search(query, 25);
		searcher.close();
		return topDocs;
	}

	// 把查询到的图书进行输出
	public static void display(Directory dir, TopDocs topDocs) throws CorruptIndexException, IOException {
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		ScoreDoc[] scoreDoc = topDocs.scoreDocs;
		for (int i = 0; i < scoreDoc.length; i++) {
			Document doc = searcher.doc(scoreDoc[i].doc);
			System.out.println("BookNames: " + doc.get("BookNames") + ", DeleteFlags: " + doc.get("DeleteFlags"));
		}
		searcher.close();
	}

	// 对图书名称进行索引
	public static void index(Directory dir, String deleteFlag)
			throws CorruptIndexException, LockObtainFailedException, IOException {
		IndexWriter writer = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_35, analyzer));
		for (int i = 0; i < BookNames.length; i++) {
			Document doc = new Document();
			doc.add(new Field("BookNames", BookNames[i], Field.Store.YES, Field.Index.ANALYZED));
			doc.add(new Field("DeleteFlags", deleteFlag, Field.Store.YES, Field.Index.ANALYZED));
			writer.addDocument(doc);
		}
		writer.close();
	}

}
