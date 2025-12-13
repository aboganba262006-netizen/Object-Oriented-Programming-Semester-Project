public class Student extends Person {

    public Student(String name, String email, String password) {
        super(name, email, password);
    }

    @Override
    public void displayMenu() {
        System.out.println("Student Menu:");
        System.out.println("1. View Courses");
        System.out.println("2. Take Quiz");
    }
}
