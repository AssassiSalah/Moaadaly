package com.example.moaadaly;

public class Module_Only_Exame {
    String name_Module;
    int couf;
    int cred;
    float EXAM_Note;
    boolean TD_Exist;
    boolean TP_Exist;

    Module_Only_Exame(String name, int couf, int cred) {
        this.name_Module = name;
        this.couf = couf;
        this.cred = cred;
        this.TD_Exist = false;
        this.TP_Exist = false;
    }

    protected void setName_Module(String name_Module) {
        this.name_Module = name_Module;
    }
    protected String getName_Module() {
        return name_Module;
    }
    protected void setCouf(int couf) {
        this.couf = couf;
    }
    protected int getCouf() {
        return couf;
    }
    protected void setCred(int cred) {
        this.cred = cred;
    }
    protected int getCred() {
        return cred;
    }
    protected void setEXAM_Note(float EXAM_Note) {
        this.EXAM_Note = EXAM_Note;
    }
    protected float getExam_Note() {
        return EXAM_Note;
    }
    public String getExam_Note_String() {
        return String.valueOf(EXAM_Note);
    }
    protected boolean isTD_Exist() {
        return TD_Exist;
    }
    protected boolean isTP_Exist() {
        return TP_Exist;
    }
}
