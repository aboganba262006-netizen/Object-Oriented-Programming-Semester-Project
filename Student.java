import java.io.*;
import java.util.ArrayList;

public class Student extends Person {
    private ArrayList<Course> myCourses;

    public Student(String fname, String lname,String email, String password) {
        super(fname, lname, email, password);
        myCourses = new ArrayList<>();
    }

    @Override
    public void displayMenu() {
        System.out.println("Student Menu:");
        System.out.println("1. View Available Courses");
        System.out.println("2. View My Courses");
        System.out.println("3. Take Quiz");
    }


    public void addCourse(Course c) {
        myCourses.add(c);
    }

    public void removeCourse(Course c) {
        myCourses.remove(c); // بس سطر واحد، شرط إن equals موجود في Course
    }

    public void printMyCourses() {
        System.out.println("My Courses:");
        for (Course c : myCourses) {
            System.out.println(c.getCourseName() + " ; " + c.getID() + " ; " + c.getCreditHours());
        }
    }

    public void printAvailableCourses() {
        System.out.println("Available Courses:");
        Course.getCourses() ;
    }


    public void saveMyCoursesToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("student_" + getEmail() + "_courses.txt"));
            for (Course c : myCourses) {
                bw.write(c.getCourseName() + ";" + c.getID() + ";" + c.getCreditHours());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving student's courses");
        }
    }

    public void loadMyCoursesFromFile() {
        myCourses.clear();
        try {
            BufferedReader br = new BufferedReader(new FileReader("student_" + getEmail() + "_courses.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                String name = data[0];
                int id = Integer.parseInt(data[1]);
                double hours = Double.parseDouble(data[2]);
                myCourses.add(new Course(name, id, hours));
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error loading student's courses");
        }
    }

    public ArrayList<Course> getMyCourses() {
        return myCourses;
    }
}