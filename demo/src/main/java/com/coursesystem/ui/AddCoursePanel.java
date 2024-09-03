package com.coursesystem.ui;

import javax.swing.*;

import com.coursesystem.service.impl.CourseServiceImpl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCoursePanel extends JPanel {

    private JTextField classNameField;
    private JTextField teaIdField;
    private JTextField classChooseNumField;

    public AddCoursePanel() {
        // 设置主面板布局
        setLayout(new BorderLayout(10, 10));

        // 创建中间的表单面板
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        // 创建标签和文本框
        JLabel classNameLabel = new JLabel("className:");
        classNameField = new JTextField();
        
        JLabel teaIdLabel = new JLabel("teaId:");
        teaIdField = new JTextField();
        
        JLabel classChooseNumLabel = new JLabel("classChooseNum:");
        classChooseNumField = new JTextField();

        // 将组件添加到表单面板
        formPanel.add(classNameLabel);
        formPanel.add(classNameField);
        formPanel.add(teaIdLabel);
        formPanel.add(teaIdField);
        formPanel.add(classChooseNumLabel);
        formPanel.add(classChooseNumField);

        // 创建添加课程按钮
        JButton addCourseButton = new JButton("添加课程");
        addCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCourseToDatabase();
            }
        });

        // 将表单面板添加到主面板中央
        add(formPanel, BorderLayout.CENTER);

        // 将按钮添加到主面板的底部
        add(addCourseButton, BorderLayout.SOUTH);

    }

    public void createAddCourseFrame() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 创建窗口
                JFrame frame = new JFrame("添加课程");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setBounds(100, 50, 1000, 800);

                // 添加 AddCoursePanel 面板到窗口
                frame.add(new AddCoursePanel());

                // 显示窗口
                frame.setVisible(true);
            }
        });
    }

    private void addCourseToDatabase() {
        String className = classNameField.getText();
        int teaId = Integer.parseInt(teaIdField.getText()); //String转化为int
        try {
            CourseServiceImpl courseService = new CourseServiceImpl();
            courseService.insertCourse(className, teaId);

            // 提示成功
            JOptionPane.showMessageDialog(null, "课程已成功添加!");
            
            //关闭添加课程窗口
            SwingUtilities.getWindowAncestor(this).dispose();
            
            //打开teacherhomepage窗口
            SwingUtilities.invokeLater(TeacherHomePage::new);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "数据库错误: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new AddCoursePanel().createAddCourseFrame();
    }
}
