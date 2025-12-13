import java.util.ArrayList;
import java.util.List;

public class Admin extends Person {

    private List<Course> managedCourses;

    public Admin(String name, String email, String password) {
        super(name, email, password);
        this.managedCourses = new ArrayList<>();
    }
    
    @Override
    public void displayMenu() {
        System.out.println("Admin Menu:");
        System.out.println("1. Add Course");
        System.out.println("2. Remove Course");
        System.out.println("3. Create Quiz for Course");
    }

    public void addCourse(Course course) {
        managedCourses.add(course);
        System.out.println("Course added: " + course.getCourseName());
    }

    public void removeCourse(Course course) {
        managedCourses.remove(course);
        System.out.println("Course removed: " + course.getCourseName());
    }

    public void createQuiz(Course course, Quiz quiz) {
        if (managedCourses.contains(course)) {
            course.addQuiz(quiz);
            System.out.println("Quiz created: " + quiz.getTitle() + " in " + course.getCourseName());
        } else {
            System.out.println("Admin does not manage this course.");
        }
    }

    public void listManagedCourses() {
        System.out.println("Managed Courses:");
        for (Course c : managedCourses) {
            System.out.println("- " + c.getCourseName());
        }
    }

    public boolean managesCourse(String courseName) {
        for (Course c : managedCourses) {
            if (c.getCourseName().equalsIgnoreCase(courseName)) {
                return true;
            }
        }
        return false;
    }

    public List<Course> getManagedCourses() {
        return managedCourses;
    }
}