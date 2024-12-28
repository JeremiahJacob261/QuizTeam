package com.fuoye.quiz.fuoyequiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    
    private Scene scene;
    Parent root;
    private Stage stage;
    
    @FXML
    private Label welcomeText;
    
    private int clicktimes = 0;

    @FXML
    public void mainpage(ActionEvent event) throws Exception {

    }


    @FXML
    public void onHelloButtonClick(ActionEvent event) throws IOException {
        if (clicktimes < 1){
            welcomeText.setText("Welcome to our FUOYE QUIZ Application!");
            clicktimes++;
        }else {
             root = FXMLLoader.load(getClass().getResource("course.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

            }
}