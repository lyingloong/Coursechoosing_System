package com.coursesystem.service;

import com.coursesystem.model.*;

import java.util.List;

public interface CourseService {
    public List<Course> queryAllById(int id); //通过输入教师id得到教课的列表
    public List<String> queryInsNameByCourse(int id); //通过课程id得到可选的院系名称列表
    public List<Institution> queryAllIns(); //得到所有院系的列表
    public int insertCourse(String name,int teaid); //输入课程名称，教师编号，插入课程
    public void insertInsLimit(String det,int classId); //通过输入英文逗号间隔的院系id,和课程id改变可选该课程的院系
    public Course queryInfoById(int id); //通过课程id得到课程信息
    public List<Integer> selectCourseLimit(int classId); //通过课程id得到可以选的院系的id列表
    public int updateCourse(String name,String num,int teaid); //输入课程名，课程id，教师id,将所选人数清零，并返回课程id
    public void updateInsLimit(String det,int classId); //与insertInslimit类似，更新课程可选院系
    public void deleteCourse(int id); //通过课程id删除课程
    public List<Student> queryStuByCourseId(int id); //通过输入课程id，得到选此课的学生列表
    public List<Course> queryAllCourse(int stuid); //输入学生id，返回所有课程的列表，其中学生已选的课程的Ischoose设为1
    public Course queryCourse(int id); //通过course的id访问course
    public void chooseSuccess(int classId,int stuId); //查看某课程选择成功
    public boolean checkStuIns(int classId,int stuId); //某学生是否可以选课(通过院系）
    public void deleteCourseChoose(int stuId,int classId); //学生删除course_choose
    public List<Course> queryStuCourse(int stuId); //输入学生id,得到选课的列表
    public List<Course> queryAllByInsId(int id); //输入院系id，得到院系能选的课
}
