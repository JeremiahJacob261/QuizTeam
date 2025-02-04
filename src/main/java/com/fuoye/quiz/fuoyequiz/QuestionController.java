package com.fuoye.quiz.fuoyequiz;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;

import java.lang.reflect.Type;
import java.util.List;
import java.io.*;
import java.nio.charset.StandardCharsets;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.concurrent.Task;
import com.fuoye.quiz.fuoyequiz.SpeechSource;

class Questions {
    private String quest;
    private String option1;
    private String option2;
    private String answer;

    public String getQuestion() { return quest; }
    public String getOption1() { return option1; }
    public String getOption2() { return option2; }
    public String getAnswer() { return answer; }
}

public class QuestionController extends Application {

    @FXML private Label question;
    @FXML private RadioButton option1;
    @FXML private RadioButton option2;
    private ToggleGroup tg = new ToggleGroup();

    public Boolean speak = false;
    private String title;
    private int currentQuestion = 0;
    private int userScore = 0;
    private List<Questions> questionsList;
    private int totalQuestions = 0;
    private final Gson gson = new Gson();
    private Thread speechThread; // Track the speech thread

    @FXML
    public void initialize() {
        option1.setToggleGroup(tg);
        option2.setToggleGroup(tg);
    }

    @FXML
    public void handleMicButtonClick() {
        speak = !speak;
        if (speak) {
            speakCurrentQuestion();
        } else {
            // Stop ongoing speech without shutting down the engine
            if (speechThread != null && speechThread.isAlive()) {
                speechThread.interrupt(); // Interrupt speech thread
            }
        }
    }

    public void getCourse(String course) {
        title = course;
        loadQuestionsFromFile();
        if (questionsList != null && !questionsList.isEmpty()) {
            showQuestion(currentQuestion);
        }
    }

    private void loadQuestionsFromFile() {
        String[] parts = title.split(":");
        String coursecode = parts[0].trim().toLowerCase();
        String coursetitle = parts[1].trim();

        try (InputStream is = getClass().getResourceAsStream("/json/" + coursecode + ".json")) {
            if (is == null) {
                throw new FileNotFoundException(coursecode + ".json not found in resources.");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            Type listType = new TypeToken<List<Questions>>() {}.getType();
            questionsList = gson.fromJson(jsonArray, listType);
            totalQuestions = questionsList.size();
        } catch (IOException e) {
            e.printStackTrace();
            question.setText("Error loading questions.");
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            question.setText("Invalid question format.");
        }
    }

    private void showQuestion(int index) {
        Questions q = questionsList.get(index);
        question.setText(q.getQuestion());
        option1.setText(q.getOption1());
        option2.setText(q.getOption2());
        tg.selectToggle(null); // Clear previous selection

        if (speak) {
            // Short delay to allow UI to render before speech
            PauseTransition pause = new PauseTransition(Duration.millis(50));
            pause.setOnFinished(e -> speakCurrentQuestion());
            pause.play();
        }
    }

    private void speakCurrentQuestion() {
        if (questionsList == null || currentQuestion < 0 || currentQuestion >= questionsList.size()) {
            return;
        }

        // Cancel previous speech if running
        if (speechThread != null && speechThread.isAlive()) {
            try {
                speechThread.interrupt(); // Stop current speech
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Questions q = questionsList.get(currentQuestion);
        String textToSpeak = q.getQuestion() + ", option one" + q.getOption1() + ", option one" + q.getOption2();

        // Run speech in a background thread
        Task<Void> speechTask = new Task<>() {
            @Override
            protected Void call() {
                SpeechSource.speakText(textToSpeak); // Assume this handles interruptions
                return null;
            }
        };

        speechThread = new Thread(speechTask);
        speechThread.setDaemon(true); // Daemon thread won't block app exit
        speechThread.start();
    }

    @FXML
    public void nextquestion(ActionEvent event) {
        if (questionsList == null) return;

        checkAnswer();

        if (currentQuestion < totalQuestions - 1) {
            currentQuestion++;
            showQuestion(currentQuestion); // Will auto-speak if enabled
        } else {
            question.setText("Final Score: " + userScore);
            option1.setVisible(false);
            option2.setVisible(false);
            if (speak) {
                speak = false; // Disable speech at the end of the quiz
            }
        }

        // Stop speech when moving to next question
        if (speechThread != null && speechThread.isAlive()) {
            speechThread.interrupt(); // Interrupt instead of shutdown
        }
    }

    private void checkAnswer() {
        RadioButton selected = (RadioButton) tg.getSelectedToggle();
        if (selected == null) return;

        String correctAnswer = questionsList.get(currentQuestion).getAnswer();
        if (selected.getText().equals(questionsList.get(currentQuestion).getOption1()) && "option1".equals(correctAnswer)) {
            userScore++;
        } else if (selected.getText().equals(questionsList.get(currentQuestion).getOption2()) && "option2".equals(correctAnswer)) {
            userScore++;
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        launch();
    }
}