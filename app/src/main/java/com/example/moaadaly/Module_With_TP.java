package com.example.moaadaly;

public class Module_With_TP extends Module_Only_Exame {
    private float TP_Note;
    private float TP_Percent;

    Module_With_TP(String name, int couf, int cred, float TP_Percent) {
        super(name, couf, cred);
        TD_Exist = false;
        TP_Exist = true;
        this.TP_Percent = TP_Percent;
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

    public String getTP_Note_String() {
        return String.valueOf(TP_Note); // Convert float to String, which is a CharSequence
    }
}
