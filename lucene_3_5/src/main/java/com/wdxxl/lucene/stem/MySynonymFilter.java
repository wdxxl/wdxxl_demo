package com.wdxxl.lucene.stem;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.util.AttributeSource;

public class MySynonymFilter extends TokenFilter {
    // 保存相应的词汇
    private CharTermAttribute termAttr = null;
    // 保存词与词之间的位置增量
    private PositionIncrementAttribute posiAttr = null;
    // 定义一个状态
    private AttributeSource.State current = null;
    // 用栈保存同义词集合
    private Stack<String> synonyms = null;

    protected MySynonymFilter(TokenStream input) {
        super(input);
        termAttr = this.addAttribute(CharTermAttribute.class);
        posiAttr = this.addAttribute(PositionIncrementAttribute.class);
        synonyms = new Stack<>();
    }

    @Override
    public boolean incrementToken() throws IOException {
        if (synonyms.size() > 0) {
            //将元素出栈,并获取同义词
            String str = synonyms.pop();
            // 还原状态
            restoreState(current);
            // 先清空,再添加
            termAttr.setEmpty();
            termAttr.append(str);
            // 设置位置为0,表示同义词
            posiAttr.setPositionIncrement(0);
            return true;
        }

        if (!input.incrementToken()) {
            return false;
        }

        // 如果词中有同义词,捕获当前状态
        if (getSynonyms(termAttr.toString())) {
            current = captureState();
        }

        return true;
    }

    // 定义同义词字典,并判断如果有同义词就返回true
    private boolean getSynonyms(String key) {
        Map<String, String[]> maps = new HashMap<>();
        maps.put("我", new String[] {"咱", "俺"});
        maps.put("中国", new String[] {"大陆", "天朝"});

        if (maps.get(key) != null) {
            for (String value : maps.get(key)) {
                synonyms.push(value);
            }
        }

        return synonyms.size() > 0 ? true : false;
    }
}
