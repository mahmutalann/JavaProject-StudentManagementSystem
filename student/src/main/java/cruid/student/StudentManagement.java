package cruid.student;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class StudentManagement extends JFrame {
    private JTextField txtStudentId;
    private JTextField txtName;
    private JTextField txtSurname;
    private JTextField txtDepartment;
    private JButton add;
    private JButton update;
    private JButton delete;

    public StudentManagement() {
        setTitle("Student Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2));

        panel.add(new JLabel("Student Id:"));
        txtStudentId = new JTextField();
        panel.add(txtStudentId);

        panel.add(new JLabel("Name:"));
        txtName = new JTextField();
        panel.add(txtName);

        panel.add(new JLabel("Surname:"));
        txtSurname = new JTextField();
        panel.add(txtSurname);

        panel.add(new JLabel("Department:"));
        txtDepartment = new JTextField();
        panel.add(txtDepartment);

        add = new JButton("Add");
        update = new JButton("Update");
        delete = new JButton("Delete");

        panel.add(add);
        panel.add(update);
        panel.add(delete);

        add(panel, BorderLayout.CENTER);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStudent();
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });
    }
    private void addStudent() {
        String sql = "INSERT INTO students (stu_id, name, surname, department) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(txtStudentId.getText()));
            pstmt.setString(2, txtName.getText());
            pstmt.setString(3, txtSurname.getText());
            pstmt.setString(4, txtDepartment.getText());

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student added.");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "When student added, ERROR!");
        }
    }

    private void updateStudent() {
        String sql = "UPDATE students SET name = ?, surname = ?, department = ? WHERE stu_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, txtName.getText());
            pstmt.setString(2, txtSurname.getText());
            pstmt.setString(3, txtDepartment.getText());
            pstmt.setInt(4, Integer.parseInt(txtStudentId.getText()));

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student updated.");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "When student updated, ERROR!");
        }
    }

    private void deleteStudent() {
        String sql = "DELETE FROM students WHERE stu_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(txtStudentId.getText()));

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student deleted.");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "When student deleted, ERROR!");
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentManagement app = new StudentManagement();
            app.setVisible(true);
        });
    }
}