package com.coursesystem.ui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.coursesystem.settings.DAOSettings;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.imageio.ImageIO;

public class TeacherHomePage extends JFrame {
    public static String teaname;
    public static int teaid;
    private JLabel nameLabel;
    private JLabel genderLabel;
    private JLabel birthdayLabel;

    private static final String URL = DAOSettings.URL;
    private static final String USERNAME = DAOSettings.USERNAME;
    private static final String PASSWORD = DAOSettings.PASSWORD;

    public TeacherHomePage() {
        setTitle("教师主页");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);

        // 创建左侧面板，并添加按钮
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(0, 1));

        JButton basicInfoButton = createButton("基本信息");
        JButton courseButton = createButton("我的课程");

        leftPanel.add(basicInfoButton);
        leftPanel.add(courseButton);

        // 创建右侧面板，用于显示选中按钮对应的内容
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        // 添加左右两个面板到主窗口
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        // 添加按钮的点击事件监听器
        basicInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayBasicInfo(rightPanel);
            }
        });

        courseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayCourseInfo(rightPanel);
            }
        });
        displayBasicInfo(rightPanel);   //初始显示个人信息
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(250, 50)); // 设置按钮大小
        button.setFont(new Font("宋体", Font.BOLD, 30)); // 设置按钮字体
        button.setFocusPainted(false); // 隐藏按钮的焦点框
        return button;
    }

    private void photoProcess(String path) {
        try {
            File inputFile = new File(path);
            BufferedImage inputImage = ImageIO.read(inputFile);

            int newWidth = 200; // 新宽度
            int newHeight = 200; // 新高度

            BufferedImage outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = outputImage.createGraphics();
            graphics2D.drawImage(inputImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, newWidth, newHeight, null);
            graphics2D.dispose();   
            // String currentWorkingDirectory = System.getProperty("user.dir");
            File outputFile = new File("src/main/resources/photo");
            ImageIO.write(outputImage, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private JPanel createBasicInfoPanel() {
        nameLabel = new JLabel("Name:");
        genderLabel = new JLabel("Gender:");
        birthdayLabel = new JLabel("Birthday:");

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;//左对齐
        gbc.insets = new Insets(10, 10, 10, 10); // 设置组件间距

        // 添加图片（头像）
        photoProcess("src/main/resources/photo/nullAvatar.png");
        ImageIcon icon = new ImageIcon("src/main/resources/photo/avatar.png");
        JLabel avatarLabel = new JLabel(icon);
        panel.add(avatarLabel, gbc);
        gbc.gridy++;

        // 查询语句
        String sql = "SELECT * FROM teacher WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 设置查询参数
            pstmt.setString(1, teaname);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // 从结果集中提取用户信息
                    int teaId = rs.getInt("teaId");
                    teaid=teaId;
                    String gender = rs.getString("username");
                    String birthday = rs.getString("password");


                    // 设置标签文本
                    nameLabel.setText("教工号: " + teaId);
                    genderLabel.setText("姓名: " + gender);
                    birthdayLabel.setText("密码: " + birthday);

                } else {
                    JOptionPane.showMessageDialog(null, "未找到用户: " + teaname);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "数据库错误: " + e.getMessage());
        }


        // 设置标签的字体、大小和颜色
        Font labelFont = new Font("SimHei", Font.PLAIN, 35);
        Color labelColor = Color.BLACK;
        nameLabel.setFont(labelFont);
        genderLabel.setFont(labelFont);
        birthdayLabel.setFont(labelFont);
        nameLabel.setForeground(labelColor);
        genderLabel.setForeground(labelColor);
        birthdayLabel.setForeground(labelColor);

        // 添加标签到面板
        panel.add(nameLabel, gbc);
        gbc.gridy++;
        panel.add(genderLabel, gbc);
        gbc.gridy++;
        panel.add(birthdayLabel, gbc);
        gbc.gridy++;
        return panel;
    }

    private JPanel createCoursePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

         // 创建课程列表的表格
        JTable courseTable;
        // 创建一个空的表格模型
        DefaultTableModel tableModel = new DefaultTableModel();
        courseTable = new JTable(tableModel);
        exportDataFromDatabase(tableModel, teaid);

        // 设置表格的字体、大小和颜色
        Font tableFont = new Font("SimHei", Font.PLAIN, 12);
        Color tableColor = Color.BLACK;
        courseTable.setFont(tableFont);
        courseTable.setForeground(tableColor);

        JScrollPane scrollPane = new JScrollPane(courseTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton courseSelectionButton = new JButton("我要开课");
        courseSelectionButton.addActionListener(e -> {
            // 在这里添加按钮点击后的操作
            System.out.println("按钮被点击：我要开课");
            new AddCoursePanel().createAddCourseFrame();
        });
        panel.add(courseSelectionButton, BorderLayout.AFTER_LAST_LINE);
        return panel;
    }

    private void displayBasicInfo(JPanel rightPanel) {
        rightPanel.removeAll();
        rightPanel.add(createBasicInfoPanel(), BorderLayout.CENTER);
        rightPanel.revalidate();
        rightPanel.repaint();
    }

    private void displayCourseInfo(JPanel rightPanel) {
        rightPanel.removeAll();
        rightPanel.add(createCoursePanel(), BorderLayout.CENTER);
        rightPanel.revalidate();
        rightPanel.repaint();
    }

    private void exportDataFromDatabase(DefaultTableModel tableModel,int teaid) {
        try {

            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // 执行查询
            String query = "SELECT * FROM course WHERE teaId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, teaid);
            ResultSet resultSet = statement.executeQuery();

            // 获取元数据，以便提取列名
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 清除现有数据和列标题
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);

            // 添加列名到表格模型
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(metaData.getColumnName(i));
            }

            // 添加数据行到表格模型
            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = resultSet.getObject(i + 1);
                }
                tableModel.addRow(rowData);
            }

            // 关闭连接
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving data from database: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentHomePage::new);
    }
}
