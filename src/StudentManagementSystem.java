import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentManagementSystem extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    private String[] listButtonText = {"View All Students", "Add Student", "Update Student", "Delete Student", "Find Student"};
    private String[] listButtonObject = {"ViewAllScreen", "AddScreen", "UpdateScreen", "DeleteScreen", "FindScreen"};
    JButton[] listButton = new JButton[listButtonText.length];
    CustomPanel[] listPanel = new CustomPanel[listButtonText.length];

    public StudentManagementSystem() {
        setTitle("Student Management System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        JPanel mainScreen = createMainScreen();
        mainPanel.add(mainScreen, "MainScreen");

        listPanel[0] = new ViewAllStudents(cardLayout, mainPanel);
        mainPanel.add(listPanel[0], "ViewAllScreen");

        listPanel[1] = new AddStudent(cardLayout, mainPanel);
        mainPanel.add(listPanel[1], "AddScreen");
        
        listPanel[2] = new UpdateStudent(cardLayout, mainPanel);
        mainPanel.add(listPanel[2], "UpdateScreen");
        
        listPanel[3] = new DeleteStudent(cardLayout, mainPanel);
        mainPanel.add(listPanel[3], "DeleteScreen");
        
        listPanel[4] = new FindStudent(cardLayout, mainPanel);
        mainPanel.add(listPanel[4], "FindScreen");

        add(mainPanel);
    }

    private JPanel createMainScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0; 
        gbc.gridy = 0;
        gbc.weightx = 0; 
        gbc.insets = new Insets(10, 10, 10, 10); 

        for(int i = 0; i < listButtonText.length; ++i) {
        	listButton[i] = new JButton(listButtonText[i]);
        	listButton[i].setPreferredSize(new Dimension(200, 50));
        	
        	final int index = i; 
        	listButton[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	listPanel[index].reload();
                    cardLayout.show(mainPanel, listButtonObject[index]);
                }
            });
        	
        	panel.add(listButton[i], gbc);
            gbc.gridy++;
        }
        
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentManagementSystem frame = new StudentManagementSystem();
            frame.setVisible(true);
        });
    }
}