package com.coursesystem.model;

public class Teacher {
    private int teaId;
    private String username;
    private String password;

    public void setTeaId(int teaId) {
        this.teaId = teaId;
    }

    public void setTeaName(String teaName) {
        this.username = teaName;
    }

    public void setTeaPass(String teaPass) {
        this.password = teaPass;
    }

    public int getTeaId() {
        return teaId;
    }

    public String getTeaName() {
        return username;
    }

    public String getTeaPass() {
        return password;
    }
}
