import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminGUI extends JFrame {

    public AdminGUI() {
        setTitle("Admin Panel");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create buttons for admin actions
        JButton addCourseButton = new JButton("Add Course");
        JButton removeCourseButton = new JButton("Remove Course");
        JButton createQuizButton = new JButton("Create Quiz");
        JButton backButton = new JButton("Back");

        // Add action listeners to buttons
        addCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddCourse();
            }
        });

        removeCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRemoveCourse();
            }
        });

        createQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCreateQuiz();
            }
        });

        backButton.addActionListener(e -> {
            dispose(); // Close the current window
            new MainGUI(); // Navigate back to the Main GUI
        });

        // Set layout and add buttons
        setLayout(new GridLayout(4, 1, 10, 10));
        add(addCourseButton);
        add(removeCourseButton);
        add(createQuizButton);
        add(backButton); // Add the back button to the layout

        setVisible(true);
    }

    private void handleAddCourse() {
        String courseName = JOptionPane.showInputDialog(this, "Enter course name:");
        String courseId = JOptionPane.showInputDialog(this, "Enter course ID:");
        String creditHours = JOptionPane.showInputDialog(this, "Enter credit hours:");

        try {
            Course.addCourse(courseName, Integer.parseInt(courseId), Double.parseDouble(creditHours));
            JOptionPane.showMessageDialog(this, "Course added successfully.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding course: " + e.getMessage());
        }
    }

    private void handleRemoveCourse() {
        String courseId = JOptionPane.showInputDialog(this, "Enter course ID to remove:");

        try {
            Course courseToRemove = null;
            for (Course c : Course.getCourses()) {
                if (String.valueOf(c.getID()).equals(courseId)) {
                    courseToRemove = c;
                    break;
                }
            }

            if (courseToRemove != null) {
                Course.removeCourse(courseToRemove);
                JOptionPane.showMessageDialog(this, "Course removed successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Course not found.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error removing course: " + e.getMessage());
        }
    }

    private void handleCreateQuiz() {
        String courseId = JOptionPane.showInputDialog(this, "Enter course ID to create a quiz for:");

        try {
            Course selectedCourse = null;
            for (Course c : Course.getCourses()) {
                if (String.valueOf(c.getID()).equals(courseId)) {
                    selectedCourse = c;
                    break;
                }
            }

            if (selectedCourse == null) {
                JOptionPane.showMessageDialog(this, "Course not found.");
                return;
            }

            String quizTitle = JOptionPane.showInputDialog(this, "Enter quiz title:");
            Quiz newQuiz = new Quiz(quizTitle, selectedCourse.getID());
            selectedCourse.addQuiz(newQuiz);
            JOptionPane.showMessageDialog(this, "Quiz created successfully.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error creating quiz: " + e.getMessage());
        }
    }
}