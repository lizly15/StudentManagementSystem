import java.io.IOException;
import java.io.RandomAccessFile;

public class Student {
    private String id;
    private String name;
    private String gender;
    private String phoneNumber;
    private float GPA;
    private String address;
    private String faculty;
    private String program;
    private String status;

    public Student() {
    	
    }
    
    public Student(String id, String name, String gender, String phoneNumber, float GPA, String address, 
    		String faculty, String program, String status) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.GPA = GPA;
        this.address = address;
        this.faculty = faculty;
        this.program = program;
        this.status = status;
    }
    
    public String getId() { return id; }
    public String getName() { return name; }
    public String getGender() { return gender; }
    public String getPhoneNumber() { return phoneNumber; }
    public float getGPA() { return GPA; }
    public String getAddress() { return address; }
    public String getFaculty() { return faculty; }
    public String getProgram() { return program; }
    public String getStatus() { return status; }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGPA(float GPA) {
        this.GPA = GPA;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public void show() {
    	System.out.println("Đã đọc đối tượng Student từ file: ");
        System.out.println("ID: " + id);
        System.out.println("Tên: " + name);
        System.out.println("Giới tính: " + gender);
        System.out.println("Số điện thoại: " + phoneNumber);
        System.out.println("GPA: " + GPA);
        System.out.println("Địa chỉ: " + address);
        System.out.println("Khoa: " + faculty);
        System.out.println("Chương trình: " + program);
        System.out.println("Tình trạng sinh viên: " + status);
    }

    public void writeStudent(RandomAccessFile raf) throws IOException {
    		raf.seek(raf.length());
    		raf.writeUTF(this.id);
            raf.writeUTF(this.name);
            raf.writeUTF(this.gender);
            raf.writeUTF(this.phoneNumber);
            raf.writeFloat(this.GPA);
            raf.writeUTF(this.address);
            raf.writeUTF(this.faculty);
            raf.writeUTF(this.program);
            raf.writeUTF(this.status);
    }

    public void readStudent(RandomAccessFile raf) throws IOException {
        this.id = raf.readUTF();
        this.name = raf.readUTF();
        this.gender = raf.readUTF();
        this.phoneNumber = raf.readUTF();
        this.GPA = raf.readFloat();
        this.address = raf.readUTF();
        this.faculty = raf.readUTF();
        this.program = raf.readUTF();
        this.status = raf.readUTF();
    }
}