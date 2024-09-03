// 完善时间：2024.06.11 12：48
// 完善者：王昊

package com.coursesystem.service.impl;

import com.coursesystem.dao.impl.CourseDAOImpl;
import com.coursesystem.dao.impl.UserDAOImpl;
import com.coursesystem.model.*;
import com.coursesystem.service.CourseService;

import java.util.ArrayList;
import java.util.List;

public class CourseServiceImpl implements CourseService {
	private CourseDAOImpl courseDao=new CourseDAOImpl();
	private UserDAOImpl userDao=new UserDAOImpl();

    @Override
    public List<Course> queryAllById(int id) {
        List<Course> course_list= courseDao.queryCourseById(id);
        for(Course c:course_list){
            c.setClassLimitInsName(new ArrayList<>());
            List<Integer> limit_list=courseDao.selectInsIdByClassId(c.getClassId());
            for(Integer i:limit_list){
                c.getClassLimitInsName().add(courseDao.selectNameByInsId(i));
            }
        }
        return course_list;
    }

    @Override
    public List<String> queryInsNameByCourse(int id) {
        List<String> insNameList=new ArrayList<>();
        List<Integer> insIdList=courseDao.queryInsIdByCourseId(id);
        for(int i:insIdList){
            insNameList.add(courseDao.selectNameByInsId(i));
        }
        return insNameList;
    }

    @Override
    public List<Institution> queryAllIns() {
        return courseDao.queryAllIns();
    }

    @Override
    public int insertCourse(String name,int teaid) {
        Course course=new Course();
        course.setClassName(name);
        course.setClassChooseNum(0);
        course.setTeaId(teaid);
        courseDao.insertCourse(course);
        return course.getClassId();
    }

    @Override
    public void insertInsLimit(String det,int classId) {
        String[] insList=det.split(",");
        for(String in:insList){
            Course_limit course_limit=new Course_limit();
            course_limit.setClassId(classId);
            course_limit.setInsId(Integer.parseInt(in));
            courseDao.insertInsLimit(course_limit);
        }
    }

    @Override
    public Course queryInfoById(int id) {
        return courseDao.queryCourseInfoById(id);
    }

    @Override
    public List<Integer> selectCourseLimit(int classId) {
        return courseDao.selectCourseLimit(classId);
    }

    @Override
    public int updateCourse(String name,String num,int teaid) {
        Course course=new Course();
        course.setTeaId(teaid);
        course.setClassChooseNum(0);
        course.setClassName(name);
        course.setClassId(courseDao.selectMaxCourseId());
        courseDao.updateCourse(course);
        return course.getClassId();
    }

    @Override
    public void updateInsLimit(String det, int classId) {
        String[] insList=det.split(",");
        courseDao.deleteInsLimit(classId);
        for(String ins:insList){
            Course_limit course_limit=new Course_limit();
            course_limit.setClassId(classId);
            course_limit.setInsId(Integer.parseInt(ins));
            courseDao.insertInsLimit(course_limit);
        }
    }

    @Override
    public void deleteCourse(int id) {
        courseDao.deleteCourseById(id);
        //解除选课表关联
        courseDao.deleteStuByClassId(id);
        //解除学院限制表关联
        courseDao.deleteLimitByClassId(id);
    }

    @Override
    public List<Student> queryStuByCourseId(int id) {
        List<Student> stu_list=new ArrayList<>();
        List<Course_choose> id_list=courseDao.queryStuIdByCourseId(id);
        for(Course_choose i:id_list){
            Student student=userDao.selectStuById(i.getStuId());
            stu_list.add(student);
        }
        return stu_list; //得到学生名单及其本课程成绩
    }

    @Override
    public List<Course> queryAllCourse(int stuid){
        List<Course> course_list= courseDao.queryAllCourse();
        List<Integer> stu_courselist=courseDao.queryCourseIdByStuId(stuid);
        for(Course c:course_list){
            c.setIsChoose(0);
            for(int i:stu_courselist){
                if(c.getClassId()==i){
                    c.setIsChoose(1);
                }
            }
        } //得到学生的选课表，已选则Ischoose设为1
        return course_list;
    }

    @Override
    public Course queryCourse(int id) {
        Course course=courseDao.selectCourseByClassId(id);
        return course;
    }

    @Override
    public void chooseSuccess(int classId, int stuId) {
        courseDao.addChooseNum(classId);
        Course_choose course_choose=new Course_choose();
        course_choose.setClassId(classId);
        course_choose.setStuId(stuId);
        courseDao.addCourseChoose(course_choose);
    }

    @Override
    public boolean checkStuIns(int classId, int stuId) {
        int stu_insId=userDao.selectStuById(stuId).getInsId();
        List<Integer> class_insId=courseDao.queryInsIdByCourseId(classId);
        for(int i:class_insId){
            if(stu_insId==i)
                return true;
        }
        return false; //学生所在学院是否可以选课
    }

    @Override
    public void deleteCourseChoose(int stuId, int classId) {
        courseDao.downChooseNum(classId);
        Course_choose course_choose=new Course_choose();
        course_choose.setStuId(stuId);
        course_choose.setClassId(classId);
        courseDao.deleteCourseChoose(course_choose);
    }

    @Override
    public List<Course> queryStuCourse(int stuId) {
        List<Integer> classid_list = courseDao.queryCourseIdByStuId(stuId); //返回该学生所选的course的id列表
        List<Course> course_list = new ArrayList<>();
        for(int i:classid_list){
            Course course = courseDao.queryCourseInfoById(i);
            course.setTeaName(courseDao.selectTeaNameByTeaId(course.getTeaId()));
            Course_choose course_choose = new Course_choose();
            course_choose.setClassId(i);
            course_choose.setStuId(stuId);
            course_list.add(course);
        }
        return course_list;
    }

    @Override
    public List<Course> queryAllByInsId(int id) {
        List<Course> course_list= courseDao.queryAllCourse();
        List<Course> course_Inslist=new ArrayList<>();
        for(Course c:course_list){
            List<Integer> limit_list=courseDao.selectInsIdByClassId(c.getClassId());
            for(int li:limit_list){
                if(id==li){
                    course_Inslist.add(c);
                    break;
                }
            }
        } //得到某院系能选的所有课
        for(Course cc:course_Inslist){
            cc.setClassLimitInsName(new ArrayList<>());
            List<Integer> limit_list=courseDao.selectInsIdByClassId(cc.getClassId());
            for(Integer i:limit_list){
                cc.getClassLimitInsName().add(courseDao.selectNameByInsId(i));
            }
        } //得到某课的所有limit院系
        return course_Inslist;
    }
}
