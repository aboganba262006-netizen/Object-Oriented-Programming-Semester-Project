import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminGUI extends JFrame {

    private Admin admin;
    private DefaultListModel<String> courseListModel;
    private JList<String> courseList;

    public AdminGUI(Admin admin) {
        this.admin = admin;

        setTitle("Admin Panel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Welcome, " + admin.getName(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        courseListModel = new DefaultListModel<>();
        courseList = new JList<>(courseListModel);
        courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(courseList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        JButton addCourseBtn = new JButton("Add Course");
        JButton removeCourseBtn = new JButton("Remove Course");
        JButton createQuizBtn = new JButton("Create Quiz");

        btnPanel.add(addCourseBtn);
        btnPanel.add(removeCourseBtn);
        btnPanel.add(createQuizBtn);

        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        add(mainPanel);

        addCourseBtn.addActionListener(e -> addCourse());
        removeCourseBtn.addActionListener(e -> removeCourse());
        createQuizBtn.addActionListener(e -> createQuiz());

        refreshCourseList();
    }

    private void addCourse() {
        String name = JOptionPane.showInputDialog(this, "Enter Course Name:");
        if (name != null && !name.isEmpty()) {
            Course course = new Course(name);
            admin.addCourse(course);
            refreshCourseList();
        }
    }

    private void removeCourse() {
        String selected = courseList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select a course first!");
            return;
        }

        Course toRemove = null;
        for (Course c : admin.getManagedCourses()) {
            if (c.getCourseName().equals(selected)) {
                toRemove = c;
                break;
            }
        }

        if (toRemove != null) {
            admin.removeCourse(toRemove);
            refreshCourseList();
        }
    }

    private void createQuiz() {
        String selected = courseList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select a course first!");
            return;
        }

        String quizTitle = JOptionPane.showInputDialog(this, "Enter Quiz Title:");
        if (quizTitle != null && !quizTitle.isEmpty()) {
            Course course = null;
            for (Course c : admin.getManagedCourses()) {
                if (c.getCourseName().equals(selected)) {
                    course = c;
                    break;
                }
            }

            if (course != null) {
                Quiz quiz = new Quiz(quizTitle, null);
                admin.createQuiz(course, quiz);
                JOptionPane.showMessageDialog(this, "Quiz '" + quizTitle + "' added!");
            }
        }
    }

    private void refreshCourseList() {
        courseListModel.clear();
        for (Course c : admin.getManagedCourses()) {
            courseListModel.addElement(c.getCourseName());
        }
    }
}
