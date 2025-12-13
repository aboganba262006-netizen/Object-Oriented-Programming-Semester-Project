import java.util.List;
import java.util.Scanner;

public class Quiz {
    private String title;
    private List<Question> questions;
    private int score;

    
    public Quiz(String title,List<Question> questions) {
        this.title = title;
        this.questions = questions;
        this.score = 0;
    }

    public void startQuiz() {
        System.out.println("Starting Quiz...");
        score = 0;

        Scanner sc = new Scanner(System.in);
    for (Question q : questions) {
          q.show();
          System.out.print("Your answer: ");
          int choice = sc.nextInt();
        if (choice == q.getCorrectOption()) score++;
}
    }

    public int calculateScore() {
        return score;
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    public List<Question> getQuestions() {
        return questions;
    }
    public String getTitle() {
        return title; 
    }

}