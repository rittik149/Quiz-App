import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuizApp extends JFrame implements ActionListener {
    String[][] questions = {
        {"What is the capital of France?", "Berlin", "London", "Paris", "Rome", "3"},
        {"Which language is used for Android development?", "Swift", "Java", "Kotlin", "Dart", "3"},
        {"Who wrote 'Hamlet'?", "Shakespeare", "Charles Dickens", "Tolkien", "Rowling", "1"},
        {"What is 5 + 7?", "10", "12", "11", "13", "2"},
        {"What does CPU stand for?", "Central Process Unit", "Central Processing Unit", "Computer Personal Unit", "Central Performance Unit", "2"}
    };

    JLabel questionLabel, timerLabel, scoreLabel;
    JRadioButton[] options = new JRadioButton[4];
    ButtonGroup bg = new ButtonGroup();
    JButton nextButton;
    Timer timer;
    int currentQuestion = 0, score = 0, timeLeft = 15;

    public QuizApp() {
        setTitle("Online Quiz Application");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.white);

        questionLabel = new JLabel("Question");
        questionLabel.setBounds(50, 30, 500, 30);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(questionLabel);

        int y = 80;
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            options[i].setBounds(70, y, 400, 30);
            options[i].setFont(new Font("Arial", Font.PLAIN, 14));
            options[i].setBackground(Color.white);
            add(options[i]);
            bg.add(options[i]);
            y += 40;
        }

        timerLabel = new JLabel("Time left: 15s");
        timerLabel.setBounds(450, 10, 100, 20);
        add(timerLabel);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(50, 10, 100, 20);
        add(scoreLabel);

        nextButton = new JButton("Next");
        nextButton.setBounds(250, 250, 100, 30);
        nextButton.addActionListener(this);
        add(nextButton);

        showQuestion();

        timer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time left: " + timeLeft + "s");
            if (timeLeft == 0) {
                evaluateAnswer();
                nextQuestion();
            }
        });
        timer.start();

        setVisible(true);
    }

    void showQuestion() {
        timeLeft = 15;
        timerLabel.setText("Time left: 15s");

        questionLabel.setText("Q" + (currentQuestion + 1) + ": " + questions[currentQuestion][0]);
        for (int i = 0; i < 4; i++) {
            options[i].setText(questions[currentQuestion][i + 1]);
            options[i].setSelected(false);
        }
    }

    void evaluateAnswer() {
        String correct = questions[currentQuestion][5];
        if (options[Integer.parseInt(correct) - 1].isSelected()) {
            score++;
            scoreLabel.setText("Score: " + score);
        }
    }

    void nextQuestion() {
        currentQuestion++;
        if (currentQuestion >= questions.length) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Quiz Over! Your score: " + score);
            System.exit(0);
        } else {
            showQuestion();
        }
    }

    public void actionPerformed(ActionEvent e) {
        evaluateAnswer();
        nextQuestion();
    }

    public static void main(String[] args) {
        new QuizApp();
    }
}