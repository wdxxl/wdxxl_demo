package com.wdxxl.lucene.stem;

import java.io.Reader;

import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.MaxWordSeg;
import com.chenlb.mmseg4j.analysis.MMSegTokenizer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

public class MySynonymAnalyzer extends Analyzer {

    @Override
    public TokenStream tokenStream(String fieldName, Reader reader) {
        // 获取中文分词器的字段,我这里使用的是MMSeg4j的中文分词器
        Dictionary dic =
                Dictionary.getInstance(System.getProperty("user.dir") + "/lib/mmseg4j-1.8.5/data");
        return new MySynonymFilter(new MMSegTokenizer(new MaxWordSeg(dic), reader));
    }
}
