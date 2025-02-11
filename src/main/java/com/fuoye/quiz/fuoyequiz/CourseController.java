package com.fuoye.quiz.fuoyequiz;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class CourseController implements Initializable {

    Parent root;
    @FXML
    private ListView<String> lists;

    @FXML
    private Label selected;

    String selectedCourse;
    String[] courses = {"CSC 102: Introduction to Computing","CHM 102: Organic Chemistry", "BIO 102: Into to Animal Science Biology","MTH 102: Introduction to Calculus", "GST 103: English"};
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lists.getItems().addAll(courses);

        lists.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                selectedCourse = lists.getSelectionModel().getSelectedItem();
                selected.setText("Selected Course: " + selectedCourse);
            }
        });
    }

    @FXML
    public void attempt(ActionEvent event) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("question.fxml"));
        root = fxmlLoader.load();
        QuestionController questioncontroller = fxmlLoader.getController();
        questioncontroller.getCourse(selectedCourse);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
