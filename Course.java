import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Course {

    private String courseName;
    private int ID; // Changed from int to String
    private double creditHours;
    private List<Quiz> quizzes;

    private static ArrayList<Course> courses = new ArrayList<>();

    static {
        loadCoursesFromFile("courses.txt"); // Automatically load courses when the class is loaded
    }

    public Course(String courseName, int ID, double creditHours) {
        this.courseName = courseName;
        this.ID = ID; // Updated to use String
        this.creditHours = creditHours;
        this.quizzes = new ArrayList<>();

        courses.add(this);
    }

    public Course(String name) {
        //TODO Auto-generated constructor stub
    }

    public String getCourseName() {
        return courseName;
    }

    public int getID() {
        return ID; // Updated getter to use String
    }

    public double getCreditHours() {
        return creditHours;
    }

    public static ArrayList<Course> getCourses() {
        return courses;
    }

    public static void saveCoursesToFile(String fileName) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("courses.txt", true));
            for (Course c : courses) {
                bw.write(c.courseName + ";" + c.ID + ";" + c.creditHours);
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving courses to file");
        }
    }

    public static void loadCoursesFromFile(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("courses.txt"));

            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");

                String name = data[0];
                int id = Integer.parseInt(data[1]);
                double hours = Double.parseDouble(data[2]);

                new Course(name, id, hours);
            }

            br.close();
        } catch (IOException e) {
            System.out.println("Error loading courses from file");
        }
    }

    public static void addCourse(String name, int id, double hours) {
        new Course(name, id, hours);
    }

    public static void removeCourse(Course course) {
        courses.remove(course);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Course)) return false;

        Course course = (Course) obj;
        return this.ID ==(course.ID); // Updated to use String comparison
    }

    public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
        System.out.println("Quiz added to course: " + courseName + " | Quiz title: " + quiz.getTitle());
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public static void printCourses() {
        for (Course c : courses) {
            System.out.println(
                    "Name: " + c.courseName +
                            " | ID: " + c.ID +
                            " | Hours: " + c.creditHours
            );
        }
    }
    
    public void loadQuizzesForThisCourse() {
        quizzes.clear();

        try {
            BufferedReader quizReader = new BufferedReader(new FileReader("quizzes.txt"));
            String line;

            while ((line = quizReader.readLine()) != null) {
                String[] quizData = line.split(";");
                int fileCourseId = Integer.parseInt(quizData[0]);
                String quizTitle = quizData[1];

                if (fileCourseId == this.ID) {
                    Quiz quiz = Quiz.loadQuizFromFile(
                            quizTitle,
                            this.ID,
                            "quizzes.txt",
                            "questions.txt"
                    );
                    if (quiz != null) {
                        quizzes.add(quiz);
                    } else {
                        System.out.println("Skipping quiz due to loading error: " + quizTitle);
                    }
                }
            }
            quizReader.close();
        } catch (Exception e) {
            System.out.println("Error loading quizzes: " + e.getMessage());
        }
    }
    
}