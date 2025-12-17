import java.util.ArrayList;
import java.io.*;

public class Quiz {
    String title;
    int courseId; // Added courseId to link quiz to a course
    ArrayList<Question> questions;

    public Quiz(String title, int courseId) {
        this.title = title;
        this.courseId = courseId;
        this.questions = new ArrayList<>();
    }

    public Quiz(String title, int courseId, ArrayList<Question> questions) {
        this.title = title;
        this.courseId = courseId;
        this.questions = questions;
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    public void takeQuiz() {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        int totalScore = 0;
        int maxScore = 0;

        System.out.println("\nStarting Quiz: " + title + " for Course ID: " + courseId);
        for (int i = 0; i < questions.size(); i++) {
            System.out.println("\nQuestion " + (i + 1) + ":");
            questions.get(i).printQuestion();
            System.out.print("Your answer (A/B/C/D): ");
            char ans = sc.next().charAt(0);

            maxScore += questions.get(i).getScore();

            if (questions.get(i).isCorrect(ans)) {
                System.out.println("Correct! +" + questions.get(i).getScore() + " points");
                totalScore += questions.get(i).getScore();
            } else {
                System.out.println("Wrong! Correct answer was: " + questions.get(i).correctChoice);
            }
        }

        System.out.println("\nQuiz finished! Your score: " + totalScore + " out of " + maxScore);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public void saveQuizToFile(String quizFile, String questionFile) {
        try {
            BufferedWriter quizWriter = new BufferedWriter(new FileWriter(quizFile, true));
            quizWriter.write(courseId + ";" + title); // Save courseId with quiz title
            quizWriter.newLine();
            quizWriter.close();

            BufferedWriter questionWriter = new BufferedWriter(new FileWriter(questionFile, true));
            for (Question q : questions) {
                questionWriter.write(courseId + ";" + title + ";" + q.toFileFormat()); // Save courseId with question
                questionWriter.newLine();
            }
            questionWriter.close();
        } catch (IOException e) {
            System.out.println("Error saving quiz to file: " + e.getMessage());
        }
    }

    public static Quiz loadQuizFromFile(String quizTitle, int courseId, String quizFile, String questionFile) {
        Quiz quiz = null;
        try {
            BufferedReader quizReader = new BufferedReader(new FileReader(quizFile));
            String line;
            while ((line = quizReader.readLine()) != null) {
                String[] quizData = line.split(";");
                if (Integer.parseInt(quizData[0]) == courseId && quizData[1].equals(quizTitle)) {
                    quiz = new Quiz(quizTitle, courseId);
                    break;
                }
            }
            quizReader.close();

            if (quiz != null) {
                BufferedReader questionReader = new BufferedReader(new FileReader(questionFile));
                while ((line = questionReader.readLine()) != null) {
                    String[] data = line.split(";");
                    if (Integer.parseInt(data[0]) == courseId && data[1].equals(quizTitle)) {
                        Question q = Question.fromFileFormat(data);
                        quiz.addQuestion(q);
                    }
                }
                questionReader.close();
            }
        } catch (IOException e) {
            System.out.println("Error loading quiz from file: " + e.getMessage());
        }
        return quiz;
    }

    public int startQuiz() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startQuiz'");
    }
}