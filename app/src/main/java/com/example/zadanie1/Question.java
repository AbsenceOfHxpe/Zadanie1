package com.example.zadanie1;

public class Question {
    private int questionId;
    private boolean trueAnswer;

    public Question(int questionId, boolean trueAnswer) {
        this.questionId = questionId;
        this.trueAnswer = trueAnswer;
    }


    public boolean isTrueAnwser() {
        return trueAnswer;
    }

    public int getQuestionId() {
        return questionId;
    }
    public void setTrueAnswer(boolean trueAnswer) {
        this.trueAnswer = trueAnswer;
    }


}