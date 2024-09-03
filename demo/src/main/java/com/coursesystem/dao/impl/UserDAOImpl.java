// 完善时间：2024.06.10 23：26
// 完善者：王昊

package com.coursesystem.dao.impl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.coursesystem.settings.DAOSettings;

import com.coursesystem.dao.*;
import com.coursesystem.model.Student;
import com.coursesystem.model.Teacher;
public class UserDAOImpl implements UserDao{
	private static final String URL = DAOSettings.URL;
    private static final String USERNAME = DAOSettings.USERNAME;
    private static final String PASSWORD = DAOSettings.PASSWORD;
	
	public Student selectStuById(int id) {
	    Student student = null;
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	        String sql = "SELECT * FROM student WHERE stuId=?";
			//预处理语句，防止SQL注入攻击
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setInt(1, id); //将给定的id设置为预处理语句中的第一个参数（位置从1开始计数）
	        resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            student = new Student();
	            student.setStuId(resultSet.getInt("stuId"));
	            student.setStuPass(resultSet.getString("password"));
	            student.setStuName(resultSet.getString("username"));
	            student.setInsName(resultSet.getString("insname"));
	            student.setInsId(resultSet.getInt("insId"));
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (resultSet != null) {
	            try {
	                resultSet.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    return student;
	}

	public Student selectStuByName(String username) {
        Student student = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM student WHERE username=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                student = new Student();
                student.setStuId(resultSet.getInt("stuId"));
                student.setStuPass(resultSet.getString("password"));
                student.setStuName(resultSet.getString("username"));
                student.setInsName(resultSet.getString("insname"));
                student.setInsId(resultSet.getInt("insId"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
	            try {
	                resultSet.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
        }

        return student;
    }

	public Teacher selectTeaById(int id) {
	    Teacher teacher = null;
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	        String sql = "SELECT * FROM teacher WHERE teaId=?";
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setInt(1, id);
	        resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) { //若有结果
	            teacher = new Teacher();
	            teacher.setTeaId(resultSet.getInt("teaId"));
	            teacher.setTeaName(resultSet.getString("username"));
	            teacher.setTeaPass(resultSet.getString("password"));
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (resultSet != null) {
	            try {
	                resultSet.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    return teacher;
	}

	public Teacher selectTeaByName(String username) {
        Teacher teacher = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM teacher WHERE username=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                teacher = new Teacher();
                teacher.setTeaId(resultSet.getInt("teaId"));
                teacher.setTeaName(resultSet.getString("username"));
                teacher.setTeaPass(resultSet.getString("password"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
	            try {
	                resultSet.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
        }

        return teacher;
    }

	public void updateStuPass(Student student) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	        String sql = "UPDATE Student SET password=? WHERE stuId=?";
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, student.getStuPass());
	        preparedStatement.setInt(2, student.getStuId());
	        preparedStatement.executeUpdate();
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}

	public void updateTeaPass(Teacher teacher) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	        String sql = "UPDATE Teacher SET password=? WHERE teaId=?";
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, teacher.getTeaPass());
	        preparedStatement.setInt(2, teacher.getTeaId());
	        preparedStatement.executeUpdate();
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}

	public List<Teacher> queryAllTeacher() {
	    List<Teacher> teacherList = new ArrayList<>();
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	        String sql = "SELECT * FROM Teacher";
	        preparedStatement = connection.prepareStatement(sql);
	        resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	            Teacher teacher = new Teacher();
	            teacher.setTeaId(resultSet.getInt("teaId"));
	            teacher.setTeaName(resultSet.getString("username"));
	            teacher.setTeaPass(resultSet.getString("password"));
	            teacherList.add(teacher);
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (resultSet != null) {
	            try {
	                resultSet.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    return teacherList;
	}
	
    public void insertStudent(String username, String password) {
		Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO Student(username,password) VALUES (?,?)";
            stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, username);
            stmt.setString(2, password);
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
	
    public void insertTeacher(String username, String password) {
		Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO Teacher(username,password) VALUES (?,?)";
            stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, username);
            stmt.setString(2, password);
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
}
