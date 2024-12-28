package com.fuoye.quiz.fuoyequiz;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class CourseController implements Initializable {

    @FXML
    private ListView<String> lists;

    String selectedCourse;
    String[] courses = {"CSC 101: Introduction to Computing", "BIO 101: Into to Plant Science Biology","PHY 101: Introduction to Physics", "GST 103: English"};
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
            }
        });
    }
}
