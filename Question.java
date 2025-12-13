import java.util.Scanner;


public class Question {
    String text;
    String[] options;
    int correct;

    public Question(String text, String[] options, int correct) {
        this.text = text;
        this.options = options;
        this.correct = correct;
    }

    public void show() {
        System.out.println(text);
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }

    public void answer() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Choose option number: ");
        int choice = sc.nextInt();

        if (choice == correct) {
            System.out.println("Correct!");
        } else {
            System.out.println("Wrong!");
        }
    }

    public String getQuestionText() {
        return text;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectOption() {
        return correct;
    }

}