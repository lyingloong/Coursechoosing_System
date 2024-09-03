package com.coursesystem.ui;

import javax.swing.*;

import com.coursesystem.service.impl.UserServiceImpl;

public class RegisterPage extends JFrame {
    public RegisterPage() {
        setTitle("注册账户");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        // 选项框
        String[] userTypes = { "学生", "教师" };
        JComboBox<String> userTypeComboBox = new JComboBox<>(userTypes);
        userTypeComboBox.setBounds(50, 20, 139, 25);
        panel.add(userTypeComboBox);

        JLabel userLabel = new JLabel("用户名:");
        userLabel.setBounds(50, 50, 80, 25);
        panel.add(userLabel);

        JTextField userTextField = new JTextField(20);
        userTextField.setBounds(150, 50, 165, 25);
        panel.add(userTextField);

        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(50, 100, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBounds(150, 100, 165, 25);
        panel.add(passwordField);

        JButton registerButton = new JButton("注册");
        registerButton.setBounds(150, 150, 80, 25);
        panel.add(registerButton);

        registerButton.addActionListener(e -> {
            String username = userTextField.getText();
            String password = new String(passwordField.getPassword());
            String usertype = (String) userTypeComboBox.getSelectedItem(); // 获取选中的用户类型

            // 调用注册逻辑
            if (registerAccount(usertype, username, password)) {
                JOptionPane.showMessageDialog(null, "注册成功！");
                dispose(); // 关闭注册页面
                new Login().CreateJFrame("Login Window"); // 返回到登录页
            } else {
                JOptionPane.showMessageDialog(null, "注册失败，请重试！");
            }
        });

        add(panel);
        setVisible(true);
    }

    private boolean registerAccount(String usertype, String username, String password) {
        try {
            UserServiceImpl userService = new UserServiceImpl();
            switch(usertype){
                case "学生":
                    userService.insertStudent(username, password);
                    break;
                case "教师":
                    userService.insertTeacher(username, password);
                    break;
            }
            // 提示成功
            JOptionPane.showMessageDialog(null, "注册成功!");
            
            //关闭添加课程窗口
            SwingUtilities.getWindowAncestor(this).dispose();
            
            //打开teacherhomepage窗口
            SwingUtilities.invokeLater(TeacherHomePage::new);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
