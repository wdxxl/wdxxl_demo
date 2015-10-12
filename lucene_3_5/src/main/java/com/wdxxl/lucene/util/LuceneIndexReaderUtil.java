package com.wdxxl.lucene.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;

// 单例
public class LuceneIndexReaderUtil {
    public static final LuceneIndexReaderUtil util = new LuceneIndexReaderUtil();// 静态
    private static Map<Directory, IndexReader> indexReaderMap;

    /** Don't let anyone else instantiate this class */
    private LuceneIndexReaderUtil() {// 私有构造
        indexReaderMap = new HashMap<>();
    }

    public static IndexReader getIndexReader(Directory directory) {// 全局静态方法
        IndexReader reader = indexReaderMap.get(directory);
        try {
            if (reader == null) {
                reader = IndexReader.open(directory, false);// 非只读
                indexReaderMap.put(directory, reader);
            } else {
                // Index是单例的话需要设置-- 因为是同一个Reader 所以部分删除的数据并不能知道消失掉
                IndexReader tr = IndexReader.openIfChanged(reader);// 为了打开一个新的Reader.
                if (tr != null) {
                    reader.close();
                    reader = tr;
                    indexReaderMap.put(directory, reader);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reader;
    }
}
