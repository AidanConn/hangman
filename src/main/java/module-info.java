module com.example.hangman {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.aidan.hangman to javafx.fxml;
    exports edu.aidan.hangman;
}