import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DeleteStudent extends CustomPanel {
    private static final long serialVersionUID = 1L;
    
    private JTextField idField;
    private JTable studentTable;
    private DefaultTableModel model;
    private ListStudent ls = new ListStudent();

    public DeleteStudent(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new BorderLayout());

        // Panel nhập ID
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        inputPanel.add(new JLabel("ID:"));
        idField = new JTextField(15);
        inputPanel.add(idField);

        JButton findButton = new JButton("Find");
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findStudent();
            }
        });
        inputPanel.add(findButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });
        inputPanel.add(deleteButton);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	model.setRowCount(0);
                cardLayout.show(mainPanel, "MainScreen"); // Quay lại màn hình chính
            }
        });
        inputPanel.add(backButton);

        add(inputPanel, BorderLayout.NORTH);

        // Bảng hiển thị thông tin sinh viên
        String[] columnNames = {"ID", "Name", "Gender", "Phone Number", "GPA", "Address"};
        model = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void findStudent() {
        String studentId = idField.getText().trim();

        model.setRowCount(0);

        ArrayList<Student> students = ls.getStudents();

        // Tìm kiếm sinh viên theo ID
        for (Student student : students) {
            if (student.getId().equals(studentId)) {
                model.addRow(new Object[]{
                    student.getId(),
                    student.getName(),
                    student.getGender(),
                    student.getPhoneNumber(),
                    student.getGPA(),
                    student.getAddress()
                });
                return; // Kết thúc tìm kiếm sau khi tìm thấy
            }
        }

        // Nếu không tìm thấy, bảng sẽ rỗng (đã được làm trước đó)
        JOptionPane.showMessageDialog(this, "No student found with ID: " + studentId);
    }

    private void deleteStudent() {
        String studentId = idField.getText().trim();

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No student found. Please search first.");
            return;
        }

        boolean deleted = ls.removeStudentById(studentId);

        if (deleted) {
            JOptionPane.showMessageDialog(this, "Student with ID: " + studentId + " has been deleted.");
            model.setRowCount(0);
            idField.setText(""); 
        } else {
            JOptionPane.showMessageDialog(this, "No student found with ID: " + studentId);
        }
    }

    @Override
    public void reload() {
    	System.out.println("reload delete screen");
    	ls = new ListStudent();
    }
}