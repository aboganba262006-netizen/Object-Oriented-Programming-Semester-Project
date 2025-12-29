import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("\n ===== identify your self ======");
            System.out.println("\n 1) Admin");
            System.out.println("\n 2) Student");
            int userType = sc.nextInt();
            sc.nextLine();

            if (userType == 2) { // Student
                System.out.println("\n ===== identify your self ======");
                System.out.println("\n 1) Sign In");
                System.out.println("\n 2) Sign Up");
                int authChoice = sc.nextInt();
                sc.nextLine();

                if (authChoice == 2) {
                    System.out.println("Enter your first name:");
                    String fname = sc.nextLine();
                    System.out.println("Enter your last name:");
                    String lname = sc.nextLine();
                    System.out.println("Enter your email:");
                    String email = sc.nextLine();
                    System.out.println("Enter your password:");
                    String password = sc.nextLine();

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("students.txt", true))) {
                        writer.write(fname + ";" + lname + ";" + email + ";" + password);
                        writer.newLine();
                        System.out.println("Sign-up successful! You can now sign in.");
                    } catch (IOException e) {
                        System.out.println("Error saving student info: " + e.getMessage());
                    }
                }

                if (authChoice == 1) {
                    System.out.println("Enter your email:");
                    String email = sc.nextLine();
                    System.out.println("Enter your password:");
                    String password = sc.nextLine();

                    boolean authenticated = false;
                    try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] data = line.split(";");
                            if (data.length == 4 && data[2].equals(email) && data[3].equals(password)) {
                                System.out.println("Welcome back, " + data[0] + " " + data[1] + "!");
                                authenticated = true;
                                break;
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Error reading student info: " + e.getMessage());
                    }

                    if (!authenticated) {
                        System.out.println("Invalid email or password. Please try again.");
                        return;
                    }
                }

                // Student-specific options...
                System.out.println("\n ====== Options =======");
                System.out.println("\n  1) Choose course");
                System.out.println("\n 2) Take quiz");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        if (Course.getCourses().isEmpty()) {
                            Course.loadCoursesFromFile("courses.txt");
                        }

                        System.out.println("\nAvailable courses:");
                        Course.printCourses();

                        System.out.println("\nHow many courses do you want to choose?");
                        int k = sc.nextInt();
                        sc.nextLine();

                        java.util.ArrayList<Course> chosenCourses = new java.util.ArrayList<>();

                        for (int i = 0; i < k; i++) {
                            System.out.println("\nEnter course id #" + (i + 1) + ":");
                            int chosenId = sc.nextInt();
                            sc.nextLine();

                            Course chosen = null;
                            for (Course c : Course.getCourses()) {
                                if (String.valueOf(c.getID()).equals(String.valueOf(chosenId))) {
                                    chosen = c;
                                    break;
                                }
                            }

                            if (chosen != null) {
                                boolean alreadyChosen = false;
                                for (Course cc : chosenCourses) {
                                    if (cc.getID() == chosen.getID()) {
                                        alreadyChosen = true;
                                        break;
                                    }
                                }
                                if (alreadyChosen) {
                                    System.out.println("You already chose this course. Skipped.");
                                    i--;
                                } else {
                                    chosenCourses.add(chosen);
                                    System.out.println("Added: " + chosen.getCourseName() +
                                            " (Hours: " + chosen.getCreditHours() + ")");
                                }
                            } else {
                                System.out.println("Course not found. Try again.");
                                i--;
                            }
                        }

                        System.out.println("\nYou selected " + chosenCourses.size() + " course(s):");
                        for (Course c : chosenCourses) {
                            System.out.println("- " + c.getCourseName() + " (ID: " + c.getID() +
                                    ", Hours: " + c.getCreditHours() + ")");
                        }
                        break;

                    case 2:
                        if (Course.getCourses().isEmpty()) {
                            Course.loadCoursesFromFile("courses.txt");
                        }

                        System.out.println("Available courses with quizzes:");

                        boolean found = false;
                        for (Course c : Course.getCourses()) {
                            if (Quiz.courseHasQuiz(c.getID(), "quizzes.txt", "questions.txt")) {
                                System.out.println(
                                        "Name: " + c.getCourseName() +
                                        " | ID: " + c.getID() +
                                        " | Hours: " + c.getCreditHours()
                                );
                                found = true;
                            }
                        }

                        if (!found) {
                            System.out.println("No courses with quizzes available at the moment.");
                            break;
                        }

                        System.out.println("Enter the course ID to take a quiz:");
                        int courseIdToTakeQuiz = sc.nextInt();
                        sc.nextLine();

                        Course courseToTakeQuiz = null;
                        for (Course c : Course.getCourses()) {
                            if (String.valueOf(c.getID()).equals(String.valueOf(courseIdToTakeQuiz))) {
                                courseToTakeQuiz = c;
                                break;
                            }
                        }

                        if (courseToTakeQuiz == null) {
                            System.out.println("Invalid course ID. Returning to menu.");
                            break;
                        }
                        if (!Quiz.courseHasQuiz(courseIdToTakeQuiz, "quizzes.txt", "questions.txt")) {
                            System.out.println("This course does not have any quizzes.");
                            break;
                        }

                        // Load quizzes for the selected course
                        courseToTakeQuiz.loadQuizzesForThisCourse();

                        // Check if quizzes are available for the course
                        if (courseToTakeQuiz.getQuizzes().isEmpty()) {
                            System.out.println("No quizzes available for this course.");
                            break;
                        }

                        // Display available quizzes for the course
                        System.out.println("Available quizzes for the course:");
                        for (Quiz quiz : courseToTakeQuiz.getQuizzes()) {
                            System.out.println("- " + quiz.getTitle());
                        }

                        // Prompt user to select a quiz by title
                        System.out.println("Enter the title of the quiz you want to take:");
                        String quizTitle = sc.nextLine();

                        // Find the selected quiz
                        Quiz quizToTake = null;
                        for (Quiz quiz : courseToTakeQuiz.getQuizzes()) {
                            if (quiz.getTitle().equalsIgnoreCase(quizTitle)) {
                                quizToTake = quiz;
                                break;
                            }
                        }

                        // If the quiz is not found, inform the user
                        if (quizToTake == null) {
                            System.out.println("Invalid quiz title. Returning to menu.");
                            break;
                        }

                        // Start the quiz
                        quizToTake.takeQuiz();
                        break;

                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            } else if (userType == 1) {
                // Admin-specific logic...
                System.out.println("Enter Admin password");
                String password = sc.next();
                int attempts = 3;
                while (attempts > 0) {
                    if (password == null) {
                        System.out.println("Password cannot be null");
                        return;
                    }

                    if (password.equals(Admin.getPassword())) {
                        break;
                    } else {
                        attempts--;
                        if (attempts > 0) {
                            System.out.println("Wrong password. You have " + attempts + " attempt(s) left.");
                            System.out.println("Enter Admin password:");
                            password = sc.next();
                        } else {
                            System.out.println("Too many failed attempts. System shutting down.");
                            System.exit(0);
                        }
                    }
                }

                if (password.equals(Admin.getPassword())) {
                    System.out.println("\n ===== Admin Success =====");
                    System.out.println("\n ==== Options ====");
                    System.out.println("\n  1) add course");
                    System.out.println("\n 2) delete course");
                    System.out.println("\n 3) make quiz");
                    int choice = sc.nextInt();
                    sc.nextLine();
                    switch (choice) {
                        case 1:
                            while (true) {
                                System.out.println("Do you want to add course? (y/n)");
                                char choice2 = sc.next().charAt(0);
                                sc.nextLine();
                                if (choice2 == 'y' || choice2 == 'Y') {
                                    System.out.println("Enter course name:");
                                    String courseName = sc.nextLine();
                                    sc.nextLine();

                                    System.out.println("Enter course id:");
                                    int courseId = sc.nextInt();
                                    sc.nextLine();

                                    System.out.println("Enter course hours:");
                                    double creditHours = sc.nextDouble();
                                    sc.nextLine();

                                    Course course = new Course(courseName, courseId, creditHours);

                                    System.out.println("Course added: " + course.getCourseName());

                                    Course.saveCoursesToFile("courses.txt");
                                    System.out.println("Courses saved to file.");

                                } else if (choice2 == 'n' || choice2 == 'N') {
                                    System.out.println("Do you want to delete course? (y/n)");
                                    char choice3 = sc.next().charAt(0);

                                    if (choice3 == 'y' || choice3 == 'Y') {

                                    }
                                    if (choice3 == 'n' || choice3 == 'N') {
                                        System.out.println("Thank you for using our System ");
                                        sc.close();
                                        System.exit(0);
                                    } else {
                                        System.out.println("invalid choice ");
                                    }
                                }
                            }

                        case 2:
                            break;

                        case 3:
                            if (Course.getCourses().isEmpty()) {
                                Course.loadCoursesFromFile("courses.txt");
                            }

                            System.out.println("Available courses:");
                            Course.printCourses();

                            System.out.println("Enter the course ID for which you want to create a quiz:");
                            int courseId = sc.nextInt();
                            sc.nextLine();

                            Course selectedCourse = null;
                            for (Course c : Course.getCourses()) {
                                if (String.valueOf(c.getID()).equals(String.valueOf(courseId))) {
                                    selectedCourse = c;
                                    break;
                                }
                            }

                            if (selectedCourse == null) {
                                System.out.println("Invalid course ID. Returning to menu.");
                                break;
                            }

                            System.out.println("Enter quiz title:");
                            String quizTitle = sc.nextLine();
                            // Removed unused variable quizID

                            Quiz currentQuiz = new Quiz(quizTitle, selectedCourse.getID(), new java.util.ArrayList<Question>());

                            System.out.println("Enter number of questions:");
                            int questions = sc.nextInt();
                            sc.nextLine();

                            for (int i = 0; i < questions; i++) {
                                System.out.println("Enter question number " + (i + 1) + ":");
                                String question = sc.nextLine();

                                System.out.print("Enter question right choice (A, B, C, D):");
                                char choice2 = sc.next().charAt(0);
                                sc.nextLine();

                                System.out.println("Enter choice A:");
                                String A = sc.nextLine();

                                System.out.println("Enter choice B:");
                                String B = sc.nextLine();

                                System.out.println("Enter choice C:");
                                String C = sc.nextLine();

                                System.out.println("Enter choice D:");
                                String D = sc.nextLine();

                                System.out.println("Enter score for this question:");
                                int score = sc.nextInt();
                                sc.nextLine();

                                Question q = new Question(question, A, B, C, D, choice2, score);
                                currentQuiz.addQuestion(q);
                            }

                            selectedCourse.addQuiz(currentQuiz);
                            System.out.println("Quiz added to course: " + selectedCourse.getCourseName());

                            System.out.println("\nQuiz Summary:");
                            System.out.println("Title: " + currentQuiz.getTitle());
                            System.out.println("Questions:");
                            for (int i = 0; i < currentQuiz.getQuestions().size(); i++) {
                                Question q = currentQuiz.getQuestions().get(i);
                                System.out.println((i + 1) + ". " + q.getQuestionText());
                                System.out.println("   A) " + q.getChoiceA());
                                System.out.println("   B) " + q.getChoiceB());
                                System.out.println("   C) " + q.getChoiceC());
                                System.out.println("   D) " + q.getChoiceD());
                                System.out.println("   Correct Answer: " + q.getCorrectChoice());
                                System.out.println("   Score: " + q.getScore());
                            }
                            System.out.println("Quiz created successfully!");

                            currentQuiz.saveQuizToFile("quizzes.txt", "questions.txt");
                            System.out.println("Quiz and questions saved to files.");

                            break;
                    }
                }
            }
        } finally {
            sc.close();
        }
    }
}
