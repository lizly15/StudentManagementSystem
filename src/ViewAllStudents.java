import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Comparator;

public class ViewAllStudents extends CustomPanel {
    private static final long serialVersionUID = 1L;
    private JTable studentTable;
    private DefaultTableModel model;
    private static final String FILE_PATH = "students.dat";

    public ViewAllStudents(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("All Students", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Name", "DOB", "Gender", "Phone Number", "Address", "Year", "Faculty", "Program", "Status"};
        model = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(model);
        
        loadStudents(); // Load students initially

        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane, BorderLayout.CENTER);

        String[] sortOptions = {"Sort by ID", "Sort by Name"};
        JComboBox<String> sortComboBox = new JComboBox<>(sortOptions);
        sortComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortStudents(sortComboBox.getSelectedItem().toString());
            }
        });

        JPanel sortPanel = new JPanel();
        sortPanel.add(new JLabel("Sort by: "));
        sortPanel.add(sortComboBox);
        add(sortPanel, BorderLayout.NORTH);

        // Buttons for Import and Export
        JButton importButton = new JButton("Import from CSV");
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importFromCSV();
            }
        });

        JButton exportButton = new JButton("Export to CSV");
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportToCSV();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(importButton);
        buttonPanel.add(exportButton);
        add(buttonPanel, BorderLayout.SOUTH);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "MainScreen");
            }
        });

        buttonPanel.add(backButton);
    }
    
    private void loadStudents() {
        model.setRowCount(0); // Clear existing rows
        ListStudent ls = new ListStudent();
        ArrayList<Student> students = ls.getStudents();
        for (Student student : students) {
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

    private void sortStudents(String criteria) {
        ListStudent ls = new ListStudent();
        ArrayList<Student> students = ls.getStudents();
        
        if (criteria.equals("Sort by ID")) {
            students.sort(Comparator.comparing(Student::getId));
        } else if (criteria.equals("Sort by Name")) {
            students.sort(Comparator.comparing(Student::getName));
        }

        model.setRowCount(0);
        for (Student student : students) {
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

    private void importFromCSV() {
        String filePath = JOptionPane.showInputDialog(this, "Enter CSV file path:");
        if (filePath != null && !filePath.trim().isEmpty()) {
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(","); 
                    if (data.length == 10) {
                        Student student = new Student(data[0], data[1], data[3], data[4], Integer.parseInt(data[6]), data[5], data[7], data[8], data[9], data[2]);
                        
                        try (RandomAccessFile raf = new RandomAccessFile(FILE_PATH, "rw")) {
                        	student.writeStudent(raf);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                loadStudents();
                JOptionPane.showMessageDialog(this, "Import successful!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error parsing Year. Please ensure it's a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exportToCSV() {
        String filePath = JOptionPane.showInputDialog(this, "Enter path to save CSV file:");
        if (filePath != null && !filePath.trim().isEmpty()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (int i = 0; i < model.getRowCount(); i++) {
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        sb.append(model.getValueAt(i, j));
                        if (j < model.getColumnCount() - 1) {
                            sb.append(",");
                        }
                    }
                    bw.write(sb.toString());
                    bw.newLine();
                }
                JOptionPane.showMessageDialog(this, "Export successful!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error writing to file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void reload() {
    	System.out.println("reload view screen");
        loadStudents(); 
    }
}