package com.example.ds6;


import org.litepal.crud.DataSupport;

public class Book extends DataSupport {
    String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }


}
