package quizApp;
import java.util.*;
class Question {
    String question;
    String[] options;
    int correctAnswer;

    Question(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
}

public class QuizApp {
    static Scanner sc = new Scanner(System.in);
    static int score = 0;

    public static void main(String[] args) {
        System.out.println("===== Welcome to the Quiz Application =====\n");

        // Create questions
        Question[] questions = {
            new Question("Which language is platform-independent?",
                    new String[]{"1. C", "2. C++", "3. Java", "4. Python"}, 3),
            new Question("What is the size of int in Java?",
                    new String[]{"1. 2 bytes", "2. 4 bytes", "3. 8 bytes", "4. Depends on OS"}, 2),
            new Question("Which of these is not OOP concept in Java?",
                    new String[]{"1. Encapsulation", "2. Inheritance", "3. Polymorphism", "4. Compilation"}, 4),
            new Question("Which package is automatically imported in Java?",
                    new String[]{"1. java.util", "2. java.lang", "3. java.io", "4. java.net"}, 2)
        };

        // Ask each question
        for (Question q : questions) {
            askQuestion(q);
        }

        System.out.println("\n===== Quiz Over! =====");
        System.out.println("Your final score: " + score + " out of " + questions.length);
    }

    static void askQuestion(Question q) {
        System.out.println(q.question);
        for (String option : q.options) {
            System.out.println(option);
        }

        System.out.println("You have 10 seconds to answer...");

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("\n⏰ Time's up! Moving to next question.");
                synchronized (sc) {
                    sc.notify();  // notify scanner to move on
                }
            }
        };

        timer.schedule(task, 10000); // 10 seconds timer

        int answer = -1;
        synchronized (sc) {
            try {
                if (sc.hasNextInt()) {
                    answer = sc.nextInt();
                }
                sc.wait(10000);  // wait until notified or timeout
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        timer.cancel();

        if (answer == q.correctAnswer) {
            System.out.println("✅ Correct!\n");
            score++;
        } else if (answer == -1) {
            System.out.println("❌ No answer given.\n");
        } else {
            System.out.println("❌ Wrong answer! Correct was option " + q.correctAnswer + "\n");
        }
    }
}
