import java.util.ArrayList;
import java.util.List;

public class Course {

    private String courseName;
    private List<Quiz> quizzes;

    public Course(String courseName) {
        this.courseName = courseName;
        this.quizzes = new ArrayList<>();
    }

    public String getCourseName() {
        return courseName;
    }

    public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }
}
