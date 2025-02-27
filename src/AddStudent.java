import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AddStudent extends CustomPanel {
    private static final long serialVersionUID = 1L;
    
    private JTextField idField;
    private JTextField nameField;
    private JTextField genderField;
    private JTextField phoneNumberField;
    private JTextField yearField;
    private JTextField addressField;
    private JTextField dobField;
    private JComboBox<String> facultyBox, programBox, statusBox;
    
    public AddStudent(CardLayout cardLayout, JPanel mainPanel) {
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
        add(new JLabel("Date of birth: "), gbc);
        
        gbc.gridy += 2;
        add(new JLabel("Year:"), gbc);
        
        gbc.gridy += 2;
        add(new JLabel("Faculty:"), gbc);
        
        // Cột 1 - Nhãn
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(new JLabel("Name: "), gbc);

        gbc.gridy += 2;
        add(new JLabel("Phone Number:"), gbc);

        gbc.gridy += 2;
        add(new JLabel("Address:"), gbc);
        
        gbc.gridy += 2;
        add(new JLabel("Program:"), gbc);
        
        gbc.gridy += 2;
        add(new JLabel("Status:"), gbc);

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
        dobField = new JTextField(15);
        add(dobField, gbc);

        gbc.gridy += 2;
        yearField = new JTextField(15);
        add(yearField, gbc);
        
        gbc.gridy += 2;
        facultyBox = new JComboBox<>(loadData("faculty.txt"));
        add(facultyBox, gbc);

        // Cột 1 - Trường nhập
        gbc.gridx = 1;
        gbc.gridy = 1;
        nameField = new JTextField(15);
        add(nameField, gbc);

        gbc.gridy += 2;
        phoneNumberField = new JTextField(15);
        add(phoneNumberField, gbc);

        gbc.gridy += 2;
        addressField = new JTextField(15);
        add(addressField, gbc);
        
        gbc.gridy += 2;
        programBox = new JComboBox<>(loadData("program.txt"));
        add(programBox, gbc);
        
        gbc.gridy += 2;
        statusBox = new JComboBox<>(loadData("status.txt"));
        add(statusBox, gbc);

     // Nút Save
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(200, 50));
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveStudent();
            }
        });
        JPanel saveButtonPanel = new JPanel(); 
        saveButtonPanel.add(saveButton); 
        saveButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        add(saveButtonPanel, gbc);

        // Nút Cancel
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
    
    private void saveStudent() {
        if (idField.getText().isEmpty() || nameField.getText().isEmpty() ||
            genderField.getText().isEmpty() || phoneNumberField.getText().isEmpty() ||
            yearField.getText().isEmpty() || addressField.getText().isEmpty() || dobField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (RandomAccessFile raf = new RandomAccessFile("students.dat", "rw")) {
            Student newStudent = new Student(idField.getText(), nameField.getText(),
            		genderField.getText(), phoneNumberField.getText(),
            		Integer.parseInt(yearField.getText()), addressField.getText(), 
            		(String) facultyBox.getSelectedItem(), (String) programBox.getSelectedItem(), (String) statusBox.getSelectedItem(), dobField.getText());
            newStudent.writeStudent(raf);
            JOptionPane.showMessageDialog(this, "Student added successfully!");
            idField.setText(""); 
            nameField.setText(""); 
            genderField.setText(""); 
            phoneNumberField.setText(""); 
            yearField.setText(""); 
            dobField.setText(""); 
            addressField.setText(""); 
            ((CardLayout) getParent().getLayout()).show(getParent(), "MainScreen");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving student.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String[] loadData(String fileName) {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list.toArray(new String[0]);
    }
    
    @Override
    public void reload() {
    	System.out.println("reload add screen");
    }
}