package com.coursesystem.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.coursesystem.service.impl.UserServiceImpl;

public class Login extends JFrame {

    private JTextField userTextField;
    private JTextField passWordTextField;
    public static String username;
    private UserServiceImpl userservice = new UserServiceImpl();

    public void CreateJFrame(String title) {
        JLabel userNameLabel, passWordLabel, login;
        JButton enterButton, registerButton;
        JFrame frame = new JFrame(title);

        Container container = frame.getContentPane();
        frame.setLayout(null);

        // 添加选项框
        String[] userTypes = { "学生", "老师" };
        JComboBox<String> userTypeComboBox = new JComboBox<>(userTypes);
        userTypeComboBox.setBounds(395, 210, 139, 25);
        container.add(userTypeComboBox);

        login = new JLabel("登录");
        login.setBounds(410, 120, 200, 100);
        container.add(login);
        Font originalFont = login.getFont();
        Font boldLargeFont = new Font(originalFont.getName(), Font.BOLD, 60);
        login.setFont(boldLargeFont);

        ImageIcon icon = new ImageIcon("src/main/resources/photo/back.jpg"); //背景图片
        JLabel label = new JLabel(icon);
        label.setBounds(0, 0, 1000, 800);
        container.add(label);

        userNameLabel = new JLabel("用户名：");
        userNameLabel.setBounds(350, 253, 54, 15);
        label.add(userNameLabel);

        userTextField = new JTextField(12);
        userTextField.setBounds(395, 253, 139, 21);
        label.add(userTextField);

        passWordLabel = new JLabel("密码：");
        passWordLabel.setBounds(350, 300, 54, 15);
        label.add(passWordLabel);

        passWordTextField = new JTextField(12);
        passWordTextField.setBounds(395, 300, 139, 21);
        label.add(passWordTextField);

        enterButton = new JButton("登录");
        enterButton.setBounds(400, 350, 70, 30);
        label.add(enterButton);

        registerButton = new JButton("注册账户");
        registerButton.setBounds(480, 350, 70, 30);
        label.add(registerButton);

        // 登录按钮的动作监听器
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userTextField.getText();
                StudentHomePage.stuname = userTextField.getText();
                TeacherHomePage.teaname = userTextField.getText();
                String password = passWordTextField.getText();
                String userType = (String) userTypeComboBox.getSelectedItem(); // 获取选中的用户类型
                if (checkLogin(username, password, userType.equals("学生") ? "student" : "teacher")) {
                    JOptionPane.showMessageDialog(null, "登录成功！");
                    // 登录成功后的逻辑
                    if (userType.equals("学生")) {
                        // 学生登录成功
                        SwingUtilities.invokeLater(StudentHomePage::new);
                        frame.dispose();
                    } else {
                        // 老师登录成功
                        SwingUtilities.invokeLater(TeacherHomePage::new);
                        frame.dispose();
                    }
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "用户名或密码错误！");
                }
            }
        });

        // 注册账户按钮的动作监听器
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 打开注册页面
                SwingUtilities.invokeLater(RegisterPage::new);
                frame.dispose();
            }
        });

        frame.setBounds(100, 50, 1000, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // 根据选项框选择查询不同的数据库
    private boolean checkLogin(String username, String password, String tableName) {
        int id = 0;
        if (tableName=="student") {
            try {
                id = userservice.getStuIdByName(username);
                if(id == -1){
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            try {
                id = userservice.getTeaIdByName(username);
                if(id == -1){
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        switch(userservice.checkAccount(id, password)){
            case 2: //checkAccount返回教师的情况
                if(tableName=="student"){
                    return false;
                }
                return true;
            case 1: //checkAccount返回学生的情况
                if(tableName=="teacher"){
                    return false;
                }
                return true;
            case 0:
                return false;
            default: //异常情况
                System.out.println("Error in return value");
                return false;
        }
    }

    public static void main(String[] args) {
        new Login().CreateJFrame("Login Window");
    }
}
