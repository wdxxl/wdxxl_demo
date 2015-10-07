package com.wdxxl.lucene.util;

import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;

public class LuceneUtil {

    public static void closeWriter(IndexWriter writer) {
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