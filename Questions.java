package com.example.project;

import java.util.List;

class Answer {
    int ID;
    String text;
    Boolean valoare;
    private static int staticID=0;

    public Answer() {
        ID=staticID;
        staticID++;
    }

    public static void ResetID() {
        staticID = 0;
    }

    @Override
    public String toString() {
        return String.format("{\"answer_name\":\"%s\", \"answer_id\":\"%d\"}", text,ID+1);
    }
}

public class Questions {
    int ID;
    String question;
    Boolean isSingle;
    private static int staticID=0;
    List<Answer> raspuns ;

    public Questions() {
        ID= staticID;
        staticID++;
    }

    public static void ResetID() {
        staticID = 0;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Boolean getSingle() {
        return isSingle;
    }

    public void setSingle(Boolean single) {
        isSingle = single;
    }

    public List<Answer> getRaspuns() {
        return raspuns;
    }

    public void setRaspuns(List<Answer> raspuns) {
        this.raspuns = raspuns;
    }
    public String details(int index)
    {
        return String.format("{\"question-name\":\"%s\", \"question_index\":\"%d\", \"question_type\":\"%s\", \"answers\":\"%s\"}",question,index,isSingle?"single":"multiple",raspuns.toString());
    }
    @Override
    public String toString() {
        return String.format("{\"question_id\" : \"%d\", \"question_name\" : \"%s\"}",ID+1, question);
    }
}