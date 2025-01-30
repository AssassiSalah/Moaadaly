package com.example.moaadaly;

public class Module_With_TD extends Module_Only_Exame {
    private float TD_Note;
    private float TD_Percent;

    Module_With_TD(String name, int couf, int cred, float TD_Percent) {
        super(name, couf, cred);
        TD_Exist = true;
        this.TD_Percent = TD_Percent;
        TP_Exist = false;
    }

    public void setTD_Note(float TD_Note) {
        this.TD_Note = TD_Note;
    }
    public float getTD_Note() {
        return TD_Note;
    }
    public void setTD_Percent(float TD_Percent) {
        this.TD_Percent = TD_Percent;
    }
    public float getTD_Percent() {
        return TD_Percent;
    }

    public String getTD_Note_String() {
        return String.valueOf(TD_Note);
    }
}
