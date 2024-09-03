package com.coursesystem.ui;
import javax.swing.*;
import javax.swing.table.*;

import com.coursesystem.settings.DAOSettings;

import java.awt.*;
import java.sql.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class CoursePage{
    private static JTable table;
    private static int stuId=StudentHomePage.stuid;
    private static final String URL = DAOSettings.URL;
    private static final String USERNAME = DAOSettings.USERNAME;
    private static final String PASSWORD = DAOSettings.PASSWORD;
    
    public static  void creatcoursepage(){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        final String URL = DAOSettings.URL;
        final String USERNAME = DAOSettings.USERNAME;
        final String PASSWORD = DAOSettings.PASSWORD;

        try {
            // 连接到数据库
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // 创建查询
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM course");

            // 创建表格模型
            DefaultTableModel tableModel = new DefaultTableModel();

            // 添加列名
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                tableModel.addColumn(metaData.getColumnName(columnIndex));
            }

            // 添加行数据
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                tableModel.addRow(row);
            }

            // 创建一个JFrame来放置组件
            JFrame frame = new JFrame();
            frame.setLayout(new BorderLayout());
            frame.setBounds(100, 50, 1000, 800);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // 创建一个JTable来显示数据
            table = new JTable(tableModel);

            // 将按钮列添加到表格
            int lastColumnIndex = table.getColumnCount(); // 获取表格当前列数作为按钮列的索引
            TableColumn buttonColumn = new TableColumn(lastColumnIndex);
            buttonColumn.setHeaderValue("选课");
            tableModel.addColumn(buttonColumn);

            // 设置按钮渲染器和编辑器
            table.getColumnModel().getColumn(lastColumnIndex).setCellRenderer(new ButtonRenderer());
            table.getColumnModel().getColumn(lastColumnIndex).setCellEditor(new ButtonEditor(new JCheckBox()));

            // 创建一个搜索栏和输入框
            JPanel searchPanel = new JPanel();
            JTextField searchField = new JTextField(20);
            searchField.setForeground(Color.GRAY);
            searchField.setText("请输入课程名称");
            searchField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (searchField.getText().equals("请输入课程名称")) {
                        searchField.setText("");
                        searchField.setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (searchField.getText().isEmpty()) {
                        searchField.setForeground(Color.GRAY);
                        searchField.setText("请输入课程名称");
                    }
                }
            });
            JButton searchButton = new JButton("搜索");
            searchPanel.add(new JLabel("搜索: "));
            searchPanel.add(searchField);
            searchPanel.add(searchButton);

            // 搜索按钮的点击事件处理
            searchButton.addActionListener(e -> {
                String searchText = searchField.getText().trim();
                if (!searchText.isEmpty()) {
                    for (int row = 0; row < table.getRowCount(); row++) {
                        for (int col = 0; col < table.getColumnCount(); col++) {
                            Object cellValue = table.getValueAt(row, col);
                            if (cellValue != null && searchText.equalsIgnoreCase(cellValue.toString())) {
                                // 定位到匹配的行并高亮显示
                                table.setRowSelectionInterval(row, row);
                                table.scrollRectToVisible(table.getCellRect(row, 0, true));
                                return;
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(null, "未找到匹配课程！");
                } else {
                    JOptionPane.showMessageDialog(null, "请输入要搜索的课程名称！");
                }
            });

            // 创建一个完成选课的按钮
            JButton completeButton = new JButton("选课完成");
            completeButton.addActionListener(e -> {
                // 处理选课完成按钮点击事件
                JOptionPane.showMessageDialog(null, "选课完成！");
                frame.dispose(); // 关闭当前窗口
                SwingUtilities.invokeLater(StudentHomePage::new); // 运行函数 StudentHomePage
            });

            // 将组件添加到JFrame中
            frame.add(searchPanel, BorderLayout.NORTH);
            frame.add(new JScrollPane(table), BorderLayout.CENTER);
            frame.add(completeButton, BorderLayout.SOUTH);

            // 设置JFrame并显示
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

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
    // 自定义的按钮渲染器，用于显示按钮
    static class ButtonRenderer extends JButton implements TableCellRenderer {
        ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("选课");
            return this;
        }
    }

    // 自定义的按钮编辑器，用于处理按钮事件
    public static class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        
        ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> {
                // 处理按钮点击事件
                int row = table.getSelectedRow(); // 获取当前选中行
                int classId = (int) table.getValueAt(row, 0); // 获取当前选中行的第一列数据
                int yourId=stuId;
                insertCourse(classId, yourId); // 将选课信息插入数据库
                JOptionPane.showMessageDialog(null, "选课成功！");
            });
        }

        private void insertCourse(int classId, int stuId) {
            Connection conn = null;
            PreparedStatement stmt = null;
            try {
                // 连接到数据库
                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    
                // 创建插入语句
                String sql = "INSERT INTO course_choose (classId, stuId) VALUES (?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, classId);
                stmt.setInt(2, stuId);
    
                // 执行插入语句
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

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return button;
        }
   }

public static void main(String[] args) {
    CoursePage.creatcoursepage();
}
}



