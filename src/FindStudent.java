import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FindStudent extends CustomPanel {
    private static final long serialVersionUID = 1L;
    
    private JTextField idField;
    private JComboBox<String> facultyBox;
    private JTable studentTable;
    private DefaultTableModel model;
    private ListStudent ls = new ListStudent();

    public FindStudent(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new BorderLayout());

        // Panel nhập ID
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        inputPanel.add(new JLabel("ID:"));
        idField = new JTextField(15);
        inputPanel.add(idField);
        
        inputPanel.add(new JLabel("Faculty:"));
        facultyBox = new JComboBox<>(loadData("faculty.txt"));
        inputPanel.add(facultyBox);

        JButton findButton = new JButton("Find");
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findStudent();
            }
        });
        inputPanel.add(findButton);

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
        String[] columnNames = {"ID", "Name", "DOB", "Gender", "Phone Number", "Address", "Year", "Faculty", "Program", "Status"};
        model = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void findStudent() {
        String studentId = idField.getText().trim();
        String faculty = (String) facultyBox.getSelectedItem();

        model.setRowCount(0);

        ArrayList<Student> students = ls.getStudents();

        for (Student student : students) {
        	if(studentId.isEmpty() == false && student.getId().equals(studentId) == false) continue;
        	if(faculty.isEmpty() == false && student.getFaculty().equals(faculty) == false) continue;
            model.addRow(new Object[]{
        		student.getId(),
                student.getName(),
                student.getDob(),
                student.getGender(),
                student.getPhoneNumber(),
                student.getAddress(),
                String.valueOf(student.getYear()),
                student.getFaculty(),
                student.getProgram(),
                student.getStatus()
            });
        }
    }
    
    private String[] loadData(String fileName) {
        List<String> list = new ArrayList<>();
        list.add("");
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
    	System.out.println("reload delete screen");
    	ls = new ListStudent();
    }
}