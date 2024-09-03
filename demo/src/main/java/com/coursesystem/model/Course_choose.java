package com.coursesystem.model;

//选课表
public class Course_choose {
    private int chooseId;
    private int stuId;
    private int classId;

    public void setStuId(int stuId) {
        this.stuId = stuId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public void setChooseId(int chooseId) {
        this.chooseId = chooseId;
    }

    public int getStuId() {
        return stuId;
    }

    public int getClassId() {
        return classId;
    }

    public int getChooseId() {
        return chooseId;
    }
}
