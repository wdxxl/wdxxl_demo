package com.wdxxl.lucene.stem;

import org.tartarus.snowball.ext.EnglishStemmer;

public class Stemmer {
    public static void main(String[] args) {
        EnglishStemmer english = new EnglishStemmer();
        String[] words = new String[] {"bank", "banker", "banking", "purse"};
        for (int i = 0; i < words.length; i++) {
            english.setCurrent(words[i]);
            english.stem();
            System.out.println(english.getCurrent());
        }
    }
}