// 完善时间：2024.06.11 21：41
// 完善者：王昊

package com.coursesystem.service.impl;

import com.coursesystem.dao.impl.*;
import com.coursesystem.model.Student;
import com.coursesystem.model.Teacher;
import com.coursesystem.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
	private UserDAOImpl userDao= new UserDAOImpl();
	
    @Override
    public int checkAccount(int id, String pass) {
        if(Integer.toString(id).charAt(4)=='1'){  //按id区分用户类型
            if(userDao.selectTeaById(id).getTeaPass().equals(pass))
                return 2;
            else
                return 0;
        }
        else{
            if(userDao.selectStuById(id).getStuPass().equals(pass))
                return 1;
            else
                return 0;
        }
    }

    @Override
    public String getStuNameById(int id) {
        try {
            return userDao.selectStuById(id).getStuName();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return ""; //表示查无此人
        }
    }

    @Override
    public String getTeaNameById(int id) {
        try {
            return userDao.selectTeaById(id).getTeaName();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return ""; //表示查无此人
        }
    }

    @Override
    public int getStuIdByName(String username) {
        try {
            return userDao.selectStuByName(username).getStuId();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return -1; //表示查无此人
        }
    }

    @Override
    public int getTeaIdByName(String username) {
        try {
            return userDao.selectTeaByName(username).getTeaId();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return -1; //表示查无此人
        }
    }

    @Override
    public Student getStuInfoById(int id) {
        return userDao.selectStuById(id);
    }

    @Override
    public Teacher getTeaInfoById(int id) {
        return userDao.selectTeaById(id);
    }

    @Override
    public void changeStuPass(Student student) {
        userDao.updateStuPass(student);
    }

    @Override
    public void changeTeaPass(Teacher teacher) {
        userDao.updateTeaPass(teacher);
    }

    @Override
    public List<Teacher> queryAllTeacher() {
        return userDao.queryAllTeacher();
    }

    @Override
    public void insertStudent(String username, String password) {
        userDao.insertStudent(username, password);
    }

    @Override
    public void insertTeacher(String username, String password) {
        userDao.insertTeacher(username, password);
    }
}
