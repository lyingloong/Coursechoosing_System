package com.coursesystem.dao;

import com.coursesystem.model.Student;
import com.coursesystem.model.Teacher;

import java.util.List;

public interface UserDao {
    public Student selectStuById(int id);
    public Student selectStuByName(String username);
    public Teacher selectTeaById(int id);
    public Teacher selectTeaByName(String username);
    public void updateStuPass(Student student);
    public void updateTeaPass(Teacher teacher);
    public List<Teacher> queryAllTeacher();
    public void insertStudent(String username, String password);
    public void insertTeacher(String username, String password);
}
