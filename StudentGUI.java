import javax.swing.*;
import java.awt.*;

public class StudentGUI extends JFrame {

    private Student student;

    public StudentGUI(Student student) {
        this.student = student;

        System.out.println("Student logged in: " + student.getName()); // Explicitly reference the student field

        if (student.getMyCourses().isEmpty()) {
            student.addCourse(Course.getCourses().get(0)); // Assign a default course for testing
        }

        setTitle("Student Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel welcomeLabel = new JLabel("Welcome, " + student.getName() + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JButton viewCoursesButton = new JButton("View Courses");
        JButton chooseCourseButton = new JButton("Choose Course");
        JButton takeQuizButton = new JButton("Take Quiz");
        JButton logoutButton = new JButton("Logout");

        viewCoursesButton.addActionListener(e -> {
            StringBuilder coursesList = new StringBuilder("All Available Courses:\n\n");
            for (Course course : Course.getCourses()) {
                coursesList.append("- ")
                    .append(course.getCourseName())
                    .append(" (ID: ")
                    .append(course.getID())
                    .append(", Hours: ")
                    .append(course.getCreditHours())
                    .append(")\n");
            }
            JOptionPane.showMessageDialog(this, coursesList.toString(), "View Courses", JOptionPane.INFORMATION_MESSAGE);
        });

        chooseCourseButton.addActionListener(e -> {
            String[] courseNames = Course.getCourses().stream()
                .map(Course::getCourseName)
                .toArray(String[]::new);

            String selectedCourse = (String) JOptionPane.showInputDialog(
                this,
                "Select a course to add to your courses:",
                "Choose Course",
                JOptionPane.PLAIN_MESSAGE,
                null,
                courseNames,
                courseNames[0]
            );

            if (selectedCourse != null) {
                Course course = Course.getCourses().stream()
                    .filter(c -> c.getCourseName().equals(selectedCourse))
                    .findFirst()
                    .orElse(null);

                if (course != null) {
                    if (!student.getMyCourses().contains(course)) {
                        student.addCourse(course);
                        JOptionPane.showMessageDialog(this, "Course added: " + course.getCourseName(), "Choose Course", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "You have already added this course.", "Choose Course", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        takeQuizButton.addActionListener(e -> {
            if (student.getMyCourses().isEmpty()) {
                JOptionPane.showMessageDialog(this, "You have no courses to take quizzes for.", "Take Quiz", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String[] courseNames = student.getMyCourses().stream().map(Course::getCourseName).toArray(String[]::new);
            String selectedCourse = (String) JOptionPane.showInputDialog(this, "Select a course to take a quiz:", "Take Quiz", JOptionPane.PLAIN_MESSAGE, null, courseNames, courseNames[0]);

            if (selectedCourse != null) {
                Course course = student.getMyCourses().stream().filter(c -> c.getCourseName().equals(selectedCourse)).findFirst().orElse(null);
                if (course != null && !course.getQuizzes().isEmpty()) {
                    Quiz quiz = course.getQuizzes().get(0); // Assuming the first quiz
                    quiz.takeQuiz();
                } else {
                    JOptionPane.showMessageDialog(this, "No quizzes available for the selected course.", "Take Quiz", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        logoutButton.addActionListener(e -> {
            dispose(); // Close the current window
            new RoleSelectionGUI(); // Navigate back to the Role Selection GUI
        });

        setLayout(new GridLayout(5, 1, 10, 10));
        add(welcomeLabel);
        add(viewCoursesButton);
        add(chooseCourseButton);
        add(takeQuizButton);
        add(logoutButton);

        setVisible(true);
    }
}
