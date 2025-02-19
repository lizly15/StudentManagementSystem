import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UpdateStudent extends CustomPanel {
    private static final long serialVersionUID = 1L;
    private ListStudent ls = new ListStudent();
    private JTextField idField;
    private JTextField nameField;
    private JTextField genderField;
    private JTextField phoneField;
    private JTextField gpaField;
    private JTextField addressField;

    public UpdateStudent(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Khoảng cách giữa các thành phần

        // Cột 0 - Nhãn
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        add(new JLabel("ID:"), gbc);

        gbc.gridy += 2;
        add(new JLabel("Gender: "), gbc);

        gbc.gridy += 2;
        add(new JLabel("GPA: "), gbc);
        
        // Cột 1 - Nhãn
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(new JLabel("Name: "), gbc);

        gbc.gridy += 2;
        add(new JLabel("Phone Number:"), gbc);

        gbc.gridy += 2;
        add(new JLabel("Address:"), gbc);

        // Cột 0 - Trường nhập
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1; 
        idField = new JTextField(15);
        add(idField, gbc);

        gbc.gridy += 2;
        genderField = new JTextField(15);
        add(genderField, gbc);

        gbc.gridy += 2;
        gpaField = new JTextField(15);
        add(gpaField, gbc);

        // Cột 1 - Trường nhập
        gbc.gridx = 1;
        gbc.gridy = 1;
        nameField = new JTextField(15);
        add(nameField, gbc);

        gbc.gridy += 2;
        phoneField = new JTextField(15);
        add(phoneField, gbc);

        gbc.gridy += 2;
        addressField = new JTextField(15);
        add(addressField, gbc);

        // Nút Find
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton findButton = new JButton("Find");
        findButton.setPreferredSize(new Dimension(200, 50));
        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findStudent();
            }
        });
        JPanel findButtonPanel = new JPanel(); 
        findButtonPanel.add(findButton); 
        findButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        add(findButtonPanel, gbc);

        // Nút Update
        gbc.gridy++;
        JButton updateButton = new JButton("Update");
        updateButton.setPreferredSize(new Dimension(200, 50));
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateStudent();
            }
        });
        JPanel updateButtonPanel = new JPanel(); 
        updateButtonPanel.add(updateButton); 
        updateButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        add(updateButtonPanel, gbc);

        gbc.gridy--;
        gbc.gridx++;
        JButton cancelButton = new JButton("Back to Main Menu");
        cancelButton.setPreferredSize(new Dimension(200, 50));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "MainScreen");
            }
        });
        JPanel cancelButtonPanel = new JPanel(); 
        cancelButtonPanel.add(cancelButton); 
        cancelButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        add(cancelButtonPanel, gbc);
    }

    private void findStudent() {
        String studentId = idField.getText().trim();
        
        ArrayList<Student> students = ls.getStudents();

        for (Student student : students) {
            if (student.getId().equals(studentId)) {
                nameField.setText(student.getName());
                genderField.setText(student.getGender());
                phoneField.setText(student.getPhoneNumber());
                gpaField.setText(String.valueOf(student.getGPA())); // Chuyển đổi sang chuỗi
                addressField.setText(student.getAddress());
                return; // Kết thúc tìm kiếm sau khi tìm thấy
            }
        }

        // Nếu không tìm thấy
        JOptionPane.showMessageDialog(this, "No student found with ID: " + studentId);
        clearFields(); // Xóa các trường nếu không tìm thấy
    }

    private void updateStudent() {
        String studentId = idField.getText().trim();
        String name = nameField.getText().trim();
        String gender = genderField.getText().trim();
        String phone = phoneField.getText().trim();
        String gpaStr = gpaField.getText().trim();
        String address = addressField.getText().trim();

        if (studentId.isEmpty() || name.isEmpty() || gender.isEmpty() || phone.isEmpty() || gpaStr.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        float gpa; // Đổi sang kiểu float
        try {
            gpa = Float.parseFloat(gpaStr); // Chuyển đổi sang float
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "GPA must be a number.");
            return;
        }

        boolean updated = ls.updateStudentById(new Student(studentId, name, gender, phone, gpa, address));

        if (updated) {
            JOptionPane.showMessageDialog(this, "Student information updated successfully.");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update student information.");
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        genderField.setText("");
        phoneField.setText("");
        gpaField.setText("");
        addressField.setText("");
    }

    @Override
    public void reload() {
    	System.out.println("reload update screen");
    	ls = new ListStudent();
    }
}