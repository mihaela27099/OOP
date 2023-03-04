package com.example.project;

import java.util.List;

public class Quizzes {
    int ID;
    String name;
    List<Questions> intrebari;
    List<Integer> auCompletat;
    int ID_creator;
    private static int staticID=0;

    public Quizzes() {
        ID=staticID;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Questions> getIntrebari() {
        return intrebari;
    }

    public void setIntrebari(List<Questions> intrebari) {
        this.intrebari = intrebari;
    }
    public String details(String username, List<User>utilizatori){
        int ID=0;
        for(int i=0; i<utilizatori.size();i++)
        {
            if(username.equals(utilizatori.get(i).username))
            {
                ID= utilizatori.get(i).ID;
            }
        }
        return String.format("{\"quizz_id\" : \"%d\", \"quizz_name\" : \"%s\", \"is_completed\" : %s}",this.ID+1, name,(auCompletat.contains(ID) ? "\"True\"" : "\"False\""));
    }
    @Override
    public String toString() {
        return String.format("{\"quizz_id\" : \"%d\", \"quizz_name\" : \"%s\"}",ID+1, name);
    }
}