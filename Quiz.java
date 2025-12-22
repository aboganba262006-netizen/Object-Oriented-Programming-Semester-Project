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

            char ans;
            while (true) {
                System.out.print("Your answer (A/B/C/D): ");
                String input = sc.next().trim().toUpperCase();
                if (input.length() == 1 && "ABCD".contains(input)) {
                    ans = input.charAt(0);
                    break;
                } else {
                    System.out.println("Invalid input. Please enter A, B, C, or D.");
                }
            }

            maxScore += questions.get(i).getScore();

            if (questions.get(i).isCorrect(ans)) {
                System.out.println("Correct! +" + questions.get(i).getScore() + " points");
                totalScore += questions.get(i).getScore();
            } else {
                System.out.println("Wrong! Correct answer was: " + questions.get(i).correctChoice);
            }
        }

        System.out.println("\nQuiz finished! Your score: " + totalScore + " out of " + maxScore);
        sc.close();
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

    public static Quiz loadQuizFromFile(String quizTitle, int courseId,
        String quizFile, String questionFile) {

        Quiz quiz = null;

        try (BufferedReader quizReader = new BufferedReader(new FileReader(quizFile));
             BufferedReader questionReader = new BufferedReader(new FileReader(questionFile))) {

            // Load quiz details
            quiz = loadQuizDetails(quizReader, quizTitle, courseId);
            if (quiz == null) {
                System.out.println("No matching quiz found in file.");
                return null;
            }

            // Load questions for the quiz
            loadQuizQuestions(questionReader, quiz, quizTitle, courseId);

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }

        return quiz;
    }

    private static Quiz loadQuizDetails(BufferedReader quizReader, String quizTitle, int courseId) throws IOException {
        String line;
        while ((line = quizReader.readLine()) != null) {
            String[] quizData = line.split(";");

            if (quizData.length < 2) continue; // Skip malformed lines

            int fileCourseId = Integer.parseInt(quizData[0]);
            String fileQuizTitle = quizData[1];

            if (fileCourseId == courseId && fileQuizTitle.equalsIgnoreCase(quizTitle)) {
                return new Quiz(quizTitle, courseId);
            }
        }
        return null;
    }

    private static void loadQuizQuestions(BufferedReader questionReader, Quiz quiz, String quizTitle, int courseId) throws IOException {
        String line;
        while ((line = questionReader.readLine()) != null) {
            String[] data = line.split(";");

            if (data.length < 8) {
                System.out.println("Skipping malformed question line.");
                continue;
            }

            int fileCourseId = Integer.parseInt(data[0]);
            String fileQuizTitle = data[1];

            if (fileCourseId == courseId && fileQuizTitle.equalsIgnoreCase(quizTitle)) {
                Question q = Question.fromFileFormat(data);
                if (q != null) {
                    quiz.addQuestion(q);
                    System.out.println("Loaded question: " + q.getQuestionText());
                } else {
                    System.out.println("Skipping malformed question.");
                }
            }
        }
    }

    public int startQuiz() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startQuiz'");
    }
    public static boolean courseHasQuiz(int courseId, String quizFile, String questionFile) {
        boolean hasQuiz = false;
        boolean hasQuestion = false;
    
        try {
            BufferedReader quizReader = new BufferedReader(new FileReader(quizFile));
            String line;
    
            while ((line = quizReader.readLine()) != null) {
                String[] data = line.split(";");
                if (Integer.parseInt(data[0]) == courseId) {
                    hasQuiz = true;
                    break;
                }
            }
            quizReader.close();
    
            if (!hasQuiz) return false;
    
            BufferedReader questionReader = new BufferedReader(new FileReader(questionFile));
            while ((line = questionReader.readLine()) != null) {
                String[] data = line.split(";");
                if (Integer.parseInt(data[0]) == courseId) {
                    hasQuestion = true;
                    break;
                }
            }
            questionReader.close();
    
        } catch (Exception e) {
            return false;
        }
    
        return hasQuiz && hasQuestion;
    }
    
}