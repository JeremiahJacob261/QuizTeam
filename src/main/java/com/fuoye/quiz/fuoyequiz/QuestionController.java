package com.fuoye.quiz.fuoyequiz;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import javafx.collections.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;



class Questions{
    private String quest;
    private String option1;
    private String option2;
    private String answer;
    public String getQuestion() {
        return quest;
    }
    public String getOption1() {
        return option1;
    }
    public String getOption2() {
        return option2;
    }
    public String getAnswer() {
        return answer;
    }
    public void showQuestion(String quest, String option1, String option2, String answer){
        quest = this.quest;
        option1 = this.option1;
        option2 = this.option2;
        answer = this.answer;
    }
}



public class QuestionController extends Application {

    @FXML
    private Label question;
    @FXML
    private RadioButton option1;
    @FXML
    private RadioButton option2;

    ToggleGroup tg = new ToggleGroup();

    public String title;
    public int currentquestion = 0;
    public int userScore = 0;

    //start of JSON conversion
    String jsonResponse = "[{\"quest\":\"What is my name?\",\"option1\":\"Jerry\",\"option2\":\"Kanayo\",\"answer\":\"option1\"},{\"quest\":\"What is my age?\",\"option2\":\"19\",\"option1\":\"24\",\"answer\":\"option2\"}]"; //json string from server
    Gson gson = new Gson();
    JsonArray jsonArray = JsonParser.parseString(jsonResponse).getAsJsonArray(); //get the length of the Json array
    int jsonlength = jsonArray.size();
    Type userListType = new TypeToken<List<Questions>>() {}.getType();
    List<Questions> users = gson.fromJson(jsonResponse, userListType);
    //end of JSON conversion
    
    public void getCourse(String course) {
        title = course;

        option2.setToggleGroup(tg);
        option1.setToggleGroup(tg);

        Questions firstUser = users.get(0); // Get the first element
        question.setText(firstUser.getQuestion());
        option1.setText(firstUser.getOption1());
        option2.setText(firstUser.getOption2());
        String answer = firstUser.getAnswer();
        //this step collects the option chosen
        if (option1.isSelected()){
            //if the first option is selected
            if (Objects.equals(answer, "option1")){
                userScore++;
                System.out.println(answer + ":" +option1);
            }

        } else if (option2.isSelected()) {
            if (Objects.equals(answer, "option2")){
                userScore++;
                System.out.println(answer + ":" +option2);
            }
        }else {
            //when no option is selected.
        }
    }

    @FXML
    public void nextquestion(ActionEvent event)throws Exception{
        if(currentquestion < jsonlength){

            //this set displays the question and options
            Questions firstUser = users.get(currentquestion); // Get the first element
            question.setText(firstUser.getQuestion());
            option1.setText(firstUser.getOption1());
            option2.setText(firstUser.getOption2());
            String answer = firstUser.getAnswer();
            //this step collects the option chosen
            if (option1.isSelected()){
                //if the first option is selected
                if (Objects.equals(answer, "option1")){
                    userScore++;
                    System.out.println(answer + ":" +option1);
                }

            } else if (option2.isSelected()) {
                if (Objects.equals(answer, "option2")){
                    userScore++;
                    System.out.println(answer + ":" +option2);
                }
            }else {
                //when no option is selected.
            }
            currentquestion++;
        }else{
            question.setText("Your final score is :" + userScore);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        launch();
    }
}
