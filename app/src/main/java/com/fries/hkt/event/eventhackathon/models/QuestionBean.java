package com.fries.hkt.event.eventhackathon.models;

import java.io.Serializable;

/**
 * Created by hungtran on 3/11/17.
 */

public class QuestionBean implements Serializable{
    private String questionId;
    private String content;
    private String as1;
    private String as2;
    private String as3;
    private String as4;


    public QuestionBean() {
    }

    public QuestionBean(String questionId, String content, String as1, String as2, String as3, String as4) {
        this.questionId = questionId;
        this.content = content;
        this.as1 = as1;
        this.as2 = as2;
        this.as3 = as3;
        this.as4 = as4;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAs1() {
        return as1;
    }

    public void setAs1(String as1) {
        this.as1 = as1;
    }

    public String getAs2() {
        return as2;
    }

    public void setAs2(String as2) {
        this.as2 = as2;
    }

    public String getAs3() {
        return as3;
    }

    public void setAs3(String as3) {
        this.as3 = as3;
    }

    public String getAs4() {
        return as4;
    }

    public void setAs4(String as4) {
        this.as4 = as4;
    }
}
