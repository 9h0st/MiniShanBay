package com.minishanbay.ghost.minishanbay.entity;

import com.minishanbay.ghost.minishanbay.adapter.TableAdapter;

import java.io.Serializable;

/**
 * Created by Ghost on 2016/3/24.
 */
public class Word implements Serializable{
    private static final long serialVersionUID = 7348434271427814364L;
    private int level;
    private String word;
    private String explanation;
    public Word() {
        this.word = "";
        this.level = -1;
    }
    public Word(String word){
        this.word = word;
        this.level = -1;
    }
    public Word(String word,int level){
        this.word = word;
        this.level = level >= 0 && level < 6 ? level:-1;
    }
    public Word(String word,String explanation){
        this.word = word;
        this.explanation= explanation;
    }
    public Word(String word,int level,String explanation){
        this.word = word;
        this.level = level >= 0 && level < 6 ? level:-1;
        this.explanation = explanation;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level >= 0 && level < 6 ? level:-1;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
