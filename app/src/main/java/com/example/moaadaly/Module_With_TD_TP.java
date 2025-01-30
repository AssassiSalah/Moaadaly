package com.example.moaadaly;

public class Module_With_TD_TP extends Module_Only_Exame {
    private float TD_Note;
    private float TD_Percent;
    private float TP_Note;
    private float TP_Percent;

    Module_With_TD_TP(String name, int couf, int cred, float TD_Percent, float TP_Percent) {
        super(name, couf, cred);
        TD_Exist = true;
        this.TD_Percent = TD_Percent;
        TP_Exist = true;
        this.TP_Percent = TP_Percent;
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
    public void setTP_Note(float TP_Note) {
        this.TP_Note = TP_Note;
    }
    public float getTP_Note() {
        return TP_Note;
    }
    public void setTP_Percent(float TP_Percent) {
        this.TP_Percent = TP_Percent;
    }
    public float getTP_Percent() {
        return TP_Percent;
    }
    public String getTD_Note_String() {
        return String.valueOf(TD_Note);
    }
    public String getTP_Note_String() {
        return String.valueOf(TP_Note); // Convert float to String, which is a CharSequence
    }
}
