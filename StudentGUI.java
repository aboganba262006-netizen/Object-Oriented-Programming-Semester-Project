import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentGUI extends JFrame {

    private Student student;
    private DefaultListModel<Course> courseModel = new DefaultListModel<>();
    private JList<Course> list = new JList<>(courseModel);

    public StudentGUI(Student student) {
        this.student = student;

        setTitle("Student Panel");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton addCourse = new JButton("Add Course");
        JButton takeQuiz = new JButton("Take Quiz");

        addCourse.addActionListener(e -> addCourse());
        takeQuiz.addActionListener(e -> takeQuiz());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addCourse);
        buttonPanel.add(takeQuiz);

        add(new JScrollPane(list), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        refresh();
        setVisible(true);
    }

    private void refresh() {
        courseModel.clear();
        for (Course c : Course.getCourses()) {
            courseModel.addElement(c);
        }
    }

    private void addCourse() {
        Course selectedCourse = list.getSelectedValue();
        if (selectedCourse != null) {
            student.addCourse(selectedCourse);
            JOptionPane.showMessageDialog(this, "Course added: " + selectedCourse.getCourseName());
        } else {
            JOptionPane.showMessageDialog(this, "Please select a course to add.");
        }
    }

    private void takeQuiz() {
        Course selectedCourse = list.getSelectedValue();
        if (selectedCourse == null || selectedCourse.getQuizzes().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No quizzes available for the selected course.");
            return;
        }

        Quiz quiz = selectedCourse.getQuizzes().get(0);
        StringBuilder quizContent = new StringBuilder("Quiz: " + quiz.getTitle() + "\n\n");

        for (Question question : quiz.getQuestions()) {
            quizContent.append(question.getQuestionText()).append("\n");
            String[] options = question.getOptions();
            for (int i = 0; i < options.length; i++) {
                quizContent.append((i + 1)).append(") ").append(options[i]).append("\n");
            }
            quizContent.append("\n");
        }

        JOptionPane.showMessageDialog(this, quizContent.toString(), "Quiz Questions", JOptionPane.INFORMATION_MESSAGE);

        int score = quiz.startQuiz();
        JOptionPane.showMessageDialog(this, "Score: " + score);
    }
}
