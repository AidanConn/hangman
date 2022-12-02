package com.example.hangman;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;


import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


public class hangmanATC extends Application {

    @Override
    public void start(Stage primaryStage) {
        // This is the main window of the game


        //get current directory
        String currentDir = System.getProperty("user.dir");

        // Logo
        Image image = new Image(currentDir + "\\Hangman.png");
        System.out.println(currentDir + "\\Hangman.png");
        ImageView logo = new ImageView(image);
        logo.setFitHeight(100);
        logo.setFitWidth(359);


        // text needs to be defined
        Text t1 = new Text("Select a difficulty to start");
        t1.setFill(Color.BLACK);
        t1.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        t1.setStyle("-fx-font: 30 arial;");
        // center the text
        t1.setStyle("-fx-alignment: center;");

        // combo box for the number of letters

        ComboBox comboBox = new ComboBox();
        comboBox.getItems().addAll(
                "Hard (4 letters)",
                "Medium (6 letters)",
                "Easy (8 letters)"
        );

        comboBox.setValue("Hard (4 letters)");

        // button needs to be defined
        Button b1 = new Button("Start Game");
        b1.setStyle("-fx-font: 22 arial;");
        // center the button below the text

        //b1.setStyle("-fx-alignment: center;");
        // button action
        b1.setOnAction(e -> {
            // switch statement for the number of letters and pass it to the hangman game
            switch (comboBox.getValue().toString()) {
                case "Hard (4 letters)" ->
                    // start the game with 4 letters
                        gameGUI(4);
                case "Medium (6 letters)" ->
                    // start the game with 6 letters
                        gameGUI(6);
                case "Easy (8 letters)" ->
                    // start the game with 8 letters
                        gameGUI(8);
            }

            // hide the startup GUI
            primaryStage.hide();

        });

        // button needs to go below the text
        VBox vBox = new VBox(50);
        vBox.getChildren().add(logo);
        vBox.getChildren().add(t1);
        vBox.getChildren().add(comboBox);
        vBox.getChildren().add(b1);


        vBox.setStyle("-fx-alignment: center;");


        Scene scene = new Scene(vBox, 500, 500);
        primaryStage.setTitle("Hangman | Start");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(currentDir + "\\gameIcon.png"));


    }


    // Game GUI (Pass in the number of letters)
    public void gameGUI(int numLetters) {
        // This is the game GUI

        // Secondary stage
        Stage gameStage = new Stage();
        AtomicInteger wrongGuesses = new AtomicInteger(); // number of wrong guesses

        // create a new game
        hangmanGame game = new hangmanGame(numLetters);

        int guesses = game.getGuesses();

        String[] blankWordArray = new String[numLetters];
        StringBuilder blankWord = new StringBuilder();
        for (int i = 0; i < numLetters; i++) {
            blankWordArray[i] = "_";
            blankWord.append("_ ");
        }

        // Hangman startup GUI
        // Needs to ask how many letters in the word

        String currentDir = System.getProperty("user.dir");

        // Logo
        Image image = new Image(currentDir + "\\Hangman.png");
        ImageView logo = new ImageView(image);
        logo.setFitHeight(100);
        logo.setFitWidth(359);

        // hangman Stick figure image "hangmanStick.png"
        AtomicReference<Image> image2 = new AtomicReference<>(new Image(currentDir + "\\hangmanStages\\hangmanStick-0.png"));
        ImageView hangmanStick = new ImageView(image2.get());
        hangmanStick.setFitHeight(200);
        hangmanStick.setFitWidth(200);


        Text word = new Text(blankWord.toString());
        word.setFill(Color.BLACK);
        word.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        // Display the number of letters
        Text t1 = new Text("The word has " + numLetters + " letters");
        t1.setFill(Color.BLACK);
        t1.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        // Display the number of guesses
        Text t2 = new Text("Total Guesses: " + guesses);
        t2.setFill(Color.BLACK);
        t2.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        // Display the number of wins
        Text t3 = new Text("Wins: " + game.getWins());
        t3.setFill(Color.BLACK);
        t3.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        // Display the number of losses
        Text t4 = new Text("Losses: " + game.getLosses());
        t4.setFill(Color.BLACK);
        t4.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        // Wong letters guessed
        Text t5 = new Text("Wrong Letters Guessed: ");
        t5.setFill(Color.BLACK);
        t5.setFont(Font.font("Verdana", FontWeight.BOLD, 20));


        // Text box for the user to enter a letter
        TextField textField = new TextField();
        textField.setPromptText("Enter a letter");
        textField.setPrefColumnCount(10);
        textField.setMaxSize(100, 100);
        //text field only accepts one letter and no numbers
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 1) {
                textField.setText(newValue.substring(0, 1));
            }
            if (!newValue.matches("[a-zA-Z]*")) {
                textField.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });


        // button needs to be defined
        Button b1 = new Button("Guess");
        b1.setStyle("-fx-font: 22 arial;");

        // button action
        b1.setOnAction(e -> {
            // get the letter from the text box
            String letter = textField.getText();
            letter = letter.toUpperCase();

            if (letter.length() != 1 || letter.equals(" ")) {
                // do nothing
            } else {

                //Set the text box to blank
                textField.setText("");

                // check if the letter is in the word
                if (game.guessLetter(letter)) {
                    System.out.println("Correct");

                    // update the number of guesses
                    t2.setText("Total Guesses: " + game.getGuesses());


                    StringBuilder newWord = new StringBuilder();
                    for (int i = 0; i < numLetters; i++) {
                        // for each letter in the word check if it is in the correct letters array. if show the letter, if not show a blank
                        char[] wordToGuess = game.getWord().toCharArray();

                        if (wordToGuess[i] == letter.charAt(0)) {
                            blankWordArray[i] = letter;
                        }

                    }

                    for (int i = 0; i < numLetters; i++) {
                        // if null show "_" else show the letter
                        if (blankWordArray[i] == null) {
                            newWord.append("_ ");
                        } else {
                            newWord.append(blankWordArray[i]).append(" ");
                        }
                    }

                    word.setText(newWord.toString());

                    // check if the game is over by checking if the word is complete with no blanks
                    if (!newWord.toString().contains("_")) {
                        // game is over
                        gameOverGUI(game.getGuesses(), game.getWins(), game.getLosses(), true, game.getWord());

                        //hide the game GUI
                        gameStage.hide();

                    }


                } else {

                    wrongGuesses.getAndIncrement();

                    System.out.println(wrongGuesses);

                    image2.set(new Image(currentDir + "\\hangmanStages\\hangmanStick-" + wrongGuesses + ".png"));

                    System.out.println(currentDir + "\\hangmanStages\\hangmanStick-" + wrongGuesses + ".png");

                    System.out.println("Wrong");

                    // update the number of guesses
                    t2.setText("Total Guesses: " + game.getGuesses());

                    StringBuilder wrongLetters = new StringBuilder();

                    // update the wrong letters guessed using for loop
                    for (int i = 0; i < wrongGuesses.get(); i++) {
                        wrongLetters.append(game.getWrongLetters()[i]).append(" ");
                    }

                    // update the wrong letters guessed
                    t5.setText("Wrong Letters Guessed: " + wrongLetters);


                    // check if the game is over by the array having 6 letters
                    if (game.getWrongLetters()[5] != null) {
                        // game is over
                        gameOverGUI(game.getGuesses(), game.getWins(), game.getLosses(), false, game.getWord());

                        //hide the game GUI
                        gameStage.hide();
                    }

                    // Update the image
                    hangmanStick.setImage(image2.get());
                }

            }

            // set the focus back to the text box
            textField.requestFocus();

        });

        // Listener for the Enter key
        textField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                b1.fire();
            }
        });

        char[] wordToGuess = game.getWord().toCharArray();
        // print the array
        for (char toGuess : wordToGuess) {
            System.out.println(toGuess);
        }


        // create a stack pane
        StackPane stackPane = new StackPane();

        // Organize the GUI elements

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(hangmanStick, t1, word, t2, t3, t4, t5, textField, b1);
        vBox.setAlignment(Pos.CENTER);

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(logo);
        hBox.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hBox);
        borderPane.setCenter(vBox);
        borderPane.setPadding(new Insets(10, 10, 10, 10));

        // add the border pane to the stack pane
        stackPane.getChildren().add(borderPane);


        // add the vBox to the stack pane
        stackPane.setStyle("-fx-background-color: #FFFFFF;");

        // create a scene
        Scene scene = new Scene(stackPane, 650, 700);
        gameStage.setTitle("Hangman | Game");
        gameStage.setScene(scene);
        gameStage.show();
        gameStage.setResizable(false);
        gameStage.getIcons().add(new Image(currentDir + "\\gameIcon.png"));


    }

    private void gameOverGUI(int guesses, int wins, int losses, boolean b, String word) {
        // This method creates the game over GUI

        if (b) {
            wins++;
        } else {
            losses++;
        }

        // get current directory
        String currentDir = System.getProperty("user.dir");

        // create a new stage
        Stage gameOverStage = new Stage();

        // create a stack pane
        StackPane stackPane = new StackPane();

        // create a vBox
        VBox vBox = new VBox(50);

        // logo
        Image image = new Image(currentDir + "\\Hangman.png");
        ImageView logo = new ImageView(image);
        logo.setFitHeight(100);
        logo.setFitWidth(359);


        // create a text
        Text t1 = new Text("Word: " + word);
        t1.setFill(Color.BLACK);
        t1.setFont(Font.font("Verdana", FontWeight.BOLD, 20));


        Image image2 = new Image(currentDir + "\\gameIcon.png");
        ImageView overIcon = new ImageView(image2);
        overIcon.setFitHeight(100);
        overIcon.setFitWidth(100);

        if (b) {
            overIcon.setImage(new Image(currentDir + "\\hangmanElements\\win.png"));
        } else {
            overIcon.setImage(new Image(currentDir + "\\hangmanElements\\lose.png"));
        }

        // create a text
        Text t2 = new Text("Total Guesses: " + guesses);
        t2.setFill(Color.BLACK);
        t2.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        // create a text
        Text t3 = new Text("Wins: " + wins);
        t3.setFill(Color.BLACK);
        t3.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        // create a text
        Text t4 = new Text("Losses: " + losses);
        t4.setFill(Color.BLACK);
        t4.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        // create a text
        Text t5 = new Text("You " + (b ? "Won!" : "Lose!"));
        t5.setFill(Color.BLACK);
        t5.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        // create a button
        Button b1 = new Button("Play Again");
        b1.setStyle("-fx-font: 22 arial;");

        // button action
        b1.setOnAction(e -> {
            // close the current stage
            gameOverStage.close();

            // restart the game at the main menu
            start(gameOverStage);
        });

        // Save the wins and losses to a file
        try {
            // create a file
            File file = new File(currentDir + "\\stats.txt");

            // create a file writer
            FileWriter fileWriter = new FileWriter(file);

            // create a buffered writer
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // write the wins and losses to the file
            bufferedWriter.write(wins + " " + losses);

            // close the buffered writer
            bufferedWriter.close();

            System.out.println(file.getAbsolutePath());
            System.out.println("Saved");

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Organize the GUI elements
        vBox.getChildren().addAll(logo, t1, overIcon, t5, t2, t3, t4, b1);
        vBox.setAlignment(Pos.CENTER);


        // add the vBox to the stack pane
        stackPane.getChildren().add(vBox);

        // create a scene
        Scene scene = new Scene(stackPane, 500, 750);
        gameOverStage.setTitle("Hangman | Game Over");
        gameOverStage.setScene(scene);
        gameOverStage.show();
        gameOverStage.setResizable(false);
        gameOverStage.getIcons().add(new Image(currentDir + "\\gameIcon.png"));
    }


    public static void main(String[] args) {
        // launch the application
        launch(args);
    }
}
