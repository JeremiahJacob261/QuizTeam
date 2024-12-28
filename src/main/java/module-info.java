module com.fuoye.quiz.fuoyequiz {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.fuoye.quiz.fuoyequiz to javafx.fxml;
    exports com.fuoye.quiz.fuoyequiz;
}