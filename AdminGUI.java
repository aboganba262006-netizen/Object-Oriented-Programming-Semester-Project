import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AdminGUI extends JFrame {

    private Admin admin;
    private DefaultListModel<Course> courseModel = new DefaultListModel<>();
    private JList<Course> courseList = new JList<>(courseModel);

    public AdminGUI(Admin admin) {
        this.admin = admin;

        setTitle("Admin Panel");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton addCourse = new JButton("Add Course");
        JButton removeCourse = new JButton("Remove Course");
        JButton addQuiz = new JButton("Add Quiz");
        JButton addQuestion = new JButton("Add Question to Quiz");

        addCourse.addActionListener(e -> addCourse());
        removeCourse.addActionListener(e -> removeCourse());
        addQuiz.addActionListener(e -> addQuiz());
        addQuestion.addActionListener(e -> addQuestionToQuiz());

        JPanel btnPanel = new JPanel();
        btnPanel.add(addCourse);
        btnPanel.add(removeCourse);
        btnPanel.add(addQuiz);
        btnPanel.add(addQuestion);

        add(new JScrollPane(courseList), BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        refresh();
        setVisible(true);
    }

    private void addCourse() {
        String name = JOptionPane.showInputDialog(this, "Course name:");
        if (name != null && !name.isEmpty()) {
            Course c = new Course(name);
            admin.addCourse(c);
            refresh();
        }
    }

    private void removeCourse() {
        Course c = courseList.getSelectedValue();
        if (c != null) {
            admin.removeCourse(c);
            refresh();
        }
    }

    private void addQuiz() {
        Course c = courseList.getSelectedValue();
        if (c == null) return;

        String title = JOptionPane.showInputDialog(this, "Quiz title:");
        if (title != null) {
            Quiz quiz = new Quiz(title, new ArrayList<Question>());
            c.addQuiz(quiz);
            JOptionPane.showMessageDialog(this, "Quiz added to course: " + c.getCourseName());
        }
    }

    // Ensure the QuestionGUI is displayed properly when invoked
    private void addQuestionToQuiz() {
        Course c = courseList.getSelectedValue();
        if (c == null || c.getQuizzes().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No quizzes available for the selected course.");
            return;
        }

        // Let the admin select a quiz to add questions to
        Object[] quizArray = c.getQuizzes().toArray();
        Quiz selectedQuiz = (Quiz) JOptionPane.showInputDialog(
            this,
            "Select a quiz:",
            "Add Question to Quiz",
            JOptionPane.PLAIN_MESSAGE,
            null,
            quizArray,
            quizArray[0]
        );

        if (selectedQuiz != null) {
            SwingUtilities.invokeLater(() -> new QuestionGUI(selectedQuiz));
        }
    }

    private void refresh() {
        courseModel.clear();
        for (Course c : admin.getManagedCourses()) {
            courseModel.addElement(c);
        }
    }
}