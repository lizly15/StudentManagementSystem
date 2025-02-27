import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.io.File;

public class ListStudent {
    private ArrayList<Student> listStudent;
    private static final String FILE_PATH = "students.dat";

    public ListStudent() {
        listStudent = new ArrayList<>();
        loadStudents();
    }

    private void loadStudents() {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_PATH, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                Student student = new Student();
                student.readStudent(raf);
                listStudent.add(student); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void writeStudents() {
    	try (RandomAccessFile raf = new RandomAccessFile(FILE_PATH, "rw")) {
    		for(Student s: listStudent) {
    			s.writeStudent(raf);
    		}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Student> getStudents() {
        return listStudent;
    }
    
    private void clearStudents() {
        try (PrintWriter printWriter = new PrintWriter(new File(FILE_PATH))) {
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public boolean removeStudentById(String id) {
        boolean isDelete = listStudent.removeIf(student -> student.getId().equals(id));
        if(isDelete) {
        	clearStudents();
        	writeStudents();
        }
        return isDelete;
    }
    
    public boolean updateStudentById(Student newInfo) {
        for (Student s : listStudent) {
            if (s.getId().equals(newInfo.getId())) {
                s.setName(newInfo.getName());
                s.setGender(newInfo.getGender());
                s.setPhoneNumber(newInfo.getPhoneNumber());
                s.setYear(newInfo.getYear());
                s.setAddress(newInfo.getAddress());
                s.setFaculty(newInfo.getFaculty());
                s.setProgram(newInfo.getProgram());
                s.setStatus(newInfo.getStatus());
                s.setDob(newInfo.getDob());
                clearStudents();
            	writeStudents();
                return true; 
            }
        }
        return false;
    }
}