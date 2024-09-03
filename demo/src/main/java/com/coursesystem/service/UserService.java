// 完善时间：2024.06.11 21：40
// 完善者：王昊

package com.coursesystem.service;

import com.coursesystem.model.Student;
import com.coursesystem.model.Teacher;

import java.util.List;

public interface UserService {
    public int checkAccount(int id,String pass);//输入id和密码，检查是否可以登录，并确定用户类型，其中返回值是2则为教师，1为学生，0为密码错误
    public String getStuNameById(int id);//输入学生id，得到姓名
    public String getTeaNameById(int id);//同上
    public int getStuIdByName(String username);//输入学生姓名，得到id
    public int getTeaIdByName(String username);//同上
    public Student getStuInfoById(int id);//输入学生id，得到学生信息
    public Teacher getTeaInfoById(int id);//同上
    public void changeStuPass(Student student);
    public void changeTeaPass(Teacher teacher);
    public List<Teacher> queryAllTeacher();//得到所有教师的列表
    public void insertStudent(String username, String password);
    public void insertTeacher(String username, String password);    
}
