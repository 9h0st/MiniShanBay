package com.minishanbay.ghost.minishanbay.entity;

/**
 * Created by Ghost on 2016/3/24.
 */
public class Lesson {
    private String id;//课程编号
    private String title;//课程标题
    private String title_chinese;//标题汉译
    private String text;//课文原文
    private String text_chinese;//原文汉译

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_chinese() {
        return title_chinese;
    }

    public void setTitle_chinese(String title_chinese) {
        this.title_chinese = title_chinese;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText_chinese() {
        return text_chinese;
    }

    public void setText_chinese(String text_chinese) {
        this.text_chinese = text_chinese;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
