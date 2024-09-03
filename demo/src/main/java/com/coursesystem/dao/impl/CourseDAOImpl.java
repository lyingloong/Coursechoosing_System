// 完善时间：2024.06.11 00：05
// 完善者：王昊

package com.coursesystem.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.coursesystem.model.*;
import com.coursesystem.settings.DAOSettings;
import com.coursesystem.dao.*;

public class CourseDAOImpl implements CourseDao{

	private static final String URL = DAOSettings.URL;
    private static final String USERNAME = DAOSettings.USERNAME;
    private static final String PASSWORD = DAOSettings.PASSWORD;

    public List<Course> queryCourseById(int id) {
    	List<Course> courseList = new ArrayList<>();
    	Connection conn = null;
    	PreparedStatement stmt = null;
    	ResultSet rs = null;

    	    try {
    	        conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    	        String sql = "SELECT * FROM Course WHERE teaId=?";
    	        stmt = conn.prepareStatement(sql);
    	        stmt.setInt(1, id);
    	        rs = stmt.executeQuery();

    	        while (rs.next()) {
    	            Course course = new Course();
    	            course.setClassId(rs.getInt("classId"));
                    course.setClassName(rs.getString("className"));
                    course.setTeaId(rs.getInt("teaId"));
                    course.setClassChooseNum(rs.getInt("classChooseNum"));

                    // 将数据库中的字符串转换为 List<String>，假设数据以逗号分隔
                    String classLimitInsNamesString = rs.getString("classLimitInsName");
                    List<String> classLimitInsNamesList = Arrays.asList(classLimitInsNamesString.split(","));
                    course.setClassLimitInsName(classLimitInsNamesList);

    	            courseList.add(course);
    	        }
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    } finally {
    	        try {
    	            if (rs != null) rs.close();
    	            if (stmt != null) stmt.close();
    	            if (conn != null) conn.close();
    	        } catch (SQLException e) {
    	            e.printStackTrace();
    	        }
    	    }

    	    return courseList;
    	}

    public List<Institution> queryAllInstitutions() {
        List<Institution> institutions = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM Institution";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Institution institution = new Institution();
                institution.setInsId(rs.getInt("id"));
                institution.setInsName(rs.getString("name"));
                
                institutions.add(institution);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return institutions;
    }

    public String selectNameByInsId(int id) {
        String insName = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "SELECT name FROM Institution WHERE id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                insName = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return insName;
    }

    public List<Integer> queryInsIdByCourseId(int id) {
    	List<Integer> insIdList = new ArrayList<>();
    	Connection conn = null;
    	PreparedStatement stmt = null;
    	ResultSet rs = null;

    	    try {
    	        conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    	        String sql = "SELECT insId FROM Course_limit WHERE classId=?";
    	        stmt = conn.prepareStatement(sql);
    	        stmt.setInt(1, id);
    	        rs = stmt.executeQuery();

    	        while (rs.next()) {
    	            int insId = rs.getInt("insId");
    	            insIdList.add(insId);
    	        }
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    } finally {
    	        try {
    	            if (rs != null) rs.close();
    	            if (stmt != null) stmt.close();
    	            if (conn != null) conn.close();
    	        } catch (SQLException e) {
    	            e.printStackTrace();
    	        }
    	    }

    	    return insIdList;
    	}

    public void insertCourse(Course course) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO Course(className,teaId,classChooseNum) VALUES (?,?,?)";
            stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, course.getClassName());
            stmt.setInt(2, course.getTeaId());
            stmt.setInt(3, course.getClassChooseNum());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateCourse(Course course) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "UPDATE Course SET className=?, teaId=?, classChooseNum=? WHERE classId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, course.getClassName());
            stmt.setInt(2, course.getTeaId());
            stmt.setInt(3, course.getClassChooseNum());
            stmt.setInt(4, course.getClassId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertInsLimit(Course_limit courseLimit) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO Course_limit (classId, insId) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, courseLimit.getClassId());
            stmt.setInt(2, courseLimit.getInsId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void updateInsLimit(Course_limit courseLimit) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "UPDATE Course_limit SET insId=? WHERE classId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, courseLimit.getInsId());
            stmt.setInt(2, courseLimit.getClassId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } 
    
    public Course queryCourseInfoById(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Course course = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM Course WHERE classId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            rs = stmt.executeQuery();
            if (rs.next()) {
                course = new Course();
                course.setClassId(rs.getInt("classId"));
                course.setClassName(rs.getString("className"));
                course.setTeaId(rs.getInt("teaId"));
                course.setClassChooseNum(rs.getInt("classChooseNum"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return course;
    }
    
    public List<Integer> selectCourseLimit(int classId) {
    	Connection conn = null;
    	PreparedStatement stmt = null;
    	ResultSet rs = null;
    	List<Integer> insIdList = new ArrayList<>();

    	    try {
    	        conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    	        String sql = "SELECT insId FROM Course_limit WHERE classId=?";
    	        stmt = conn.prepareStatement(sql);
    	        stmt.setInt(1, classId);

    	        rs = stmt.executeQuery();
    	        while (rs.next()) {
    	            int insId = rs.getInt("insId");
    	            insIdList.add(insId);
    	        }
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    } finally {
    	        try {
    	            if (rs != null) rs.close();
    	            if (stmt != null) stmt.close();
    	            if (conn != null) conn.close();
    	        } catch (SQLException e) {
    	            e.printStackTrace();
    	        }
    	    }

    	    return insIdList;
    }
        
    public int selectMaxCourseId() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int maxCourseId = -1;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "SELECT MAX(classId) FROM Course";
            stmt = conn.prepareStatement(sql);

            rs = stmt.executeQuery();
            if (rs.next()) {
                maxCourseId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return maxCourseId;
    }
    
    public void deleteInsLimit(int classId) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "DELETE FROM Course_limit WHERE classId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void deleteLimitByClassId(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "DELETE FROM Course_limit WHERE classId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteCourseById(int classId) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "DELETE FROM Course WHERE classId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void deleteStuByClassId(int classId) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "DELETE FROM Course_choose WHERE classId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Course_choose> queryStuIdByCourseId(int classId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Course_choose> courseChooseList = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM Course_choose WHERE classId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Course_choose courseChoose = new Course_choose();
                
                courseChoose.setClassId(rs.getInt("ClassId"));
                courseChoose.setStuId(rs.getInt("stuId"));
                courseChoose.setChooseId(rs.getInt("chooseId"));
                

                courseChooseList.add(courseChoose);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return courseChooseList;
    }

    public List<Course> queryAllCourse() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Course> courseList = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM Course";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Course course = new Course();
                course.setClassId(rs.getInt("classId"));
                course.setClassName(rs.getString("className"));
                course.setTeaId(rs.getInt("teaId"));
                course.setClassChooseNum(rs.getInt("classChooseNum"));
                courseList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return courseList;
    }

    public List<Institution> queryAllIns() {
        List<Institution> institutions = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM Institution";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Institution institution = new Institution();
                institution.setInsId(rs.getInt("id"));
                institution.setInsName(rs.getString("name"));
                institutions.add(institution);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return institutions;
    }

    public List<Integer> selectInsIdByClassId(int classId) {
    	Connection conn = null;
    	PreparedStatement stmt = null;
    	ResultSet rs = null;
    	List<Integer> insIdList = new ArrayList<>(); // 默认值，表示未找到对应记录

    	    try {
    	        conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    	        String sql = "SELECT insId FROM Course_limit where classId=?";
    	        stmt = conn.prepareStatement(sql);
    	        stmt.setInt(1, classId);
    	        rs = stmt.executeQuery();

    	        while (rs.next()) {
    	            int insId = rs.getInt("insId");
    	            insIdList.add(insId);
    	        }
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    } finally {
    	        try {
    	            if (rs != null) rs.close();
    	            if (stmt != null) stmt.close();
    	            if (conn != null) conn.close();
    	        } catch (SQLException e) {
    	            e.printStackTrace();
    	        }
    	    }

    	    return insIdList;
    }

    public String selectTeaNameByTeaId(int teaId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String teaName = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "SELECT teaName FROM Teacher where teaId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, teaId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                teaName = rs.getString("teaName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return teaName;
    }

    public Course selectCourseByClassId(int classId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Course course = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM Course WHERE classId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                course = new Course();
                course.setClassId(rs.getInt("classId"));
                course.setClassName(rs.getString("className"));
                course.setTeaId(rs.getInt("teaId"));
                course.setClassChooseNum(rs.getInt("classChooseNum"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return course;
    } 

    public void addChooseNum(int classId) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "UPDATE Course SET classChooseNum = classChooseNum+1 WHERE classId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addCourseChoose(Course_choose courseChoose) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO Course_choose (stuId, classId) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, courseChoose.getStuId());
            stmt.setInt(2, courseChoose.getClassId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Integer> queryCourseIdByStuId(int stuId) {
    	Connection conn = null;
    	PreparedStatement stmt = null;
    	ResultSet rs = null;
    	List<Integer> classIdList = new ArrayList<>();

    	try {
    	    conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    	    String sql = "SELECT classId FROM Course_choose WHERE stuId=?";
    	    stmt = conn.prepareStatement(sql);
    	    stmt.setInt(1, stuId);
    	    rs = stmt.executeQuery();
    	    while (rs.next()) {
    	        int classId = rs.getInt("classId");
    	        classIdList.add(classId);
    	    }
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	} finally {
    	    try {
    	        if (rs != null) rs.close();
    	        if (stmt != null) stmt.close();
    	        if (conn != null) conn.close();
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    }
    	}
    	return classIdList;
    }

    public void downChooseNum(int classId) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "UPDATE Course SET classChooseNum = classChooseNum-1 WHERE classId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteCourseChoose(Course_choose courseChoose) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "DELETE FROM Course_choose WHERE stuId=? AND classId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, courseChoose.getStuId());
            stmt.setInt(2, courseChoose.getClassId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int selectScore(Course_choose courseChoose) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int score = -1;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "SELECT score FROM Course_choose WHERE classId=? AND stuId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, courseChoose.getClassId());
            stmt.setInt(2, courseChoose.getStuId());
            rs = stmt.executeQuery();

            if (rs.next()) {
                score = rs.getInt("score");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return score;
    }
}