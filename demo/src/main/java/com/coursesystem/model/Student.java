package com.coursesystem.model;

public class Student {
    private int stuId;
    private String password;
    private String username;
    private int insId;
    private String InsName;
    private int tempScore;

    public void setStuId(int stuId) {
        this.stuId = stuId;
    }

    public void setStuPass(String stuPass) {
        this.password = stuPass;
    }

    public void setStuName(String stuName) {
        this.username = stuName;
    }

    public void setInsName(String insName) {
        InsName = insName;
    }

    public void setInsId(int insId) {
        this.insId = insId;
    }

    public void setTempScore(int tempScore) {
        this.tempScore = tempScore;
    }

    public int getStuId() {
        return stuId;
    }

    public String getStuPass() {
        return password;
    }

    public String getStuName() {
        return username;
    }

    public String getInsName() {
        return InsName;
    }

    public int getInsId() {
        return insId;
    }

    public int getTempScore() {
        return tempScore;
    }
}
