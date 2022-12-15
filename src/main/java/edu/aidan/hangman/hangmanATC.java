package edu.aidan.hangman;

//**************************
//hangmanATC.java
//
//Author: Aidan Connaughton
//Date: 12/06/2022
//Class: COMSC110 LAB
//Instructor: Dr. Omar
//
//Purpose: This program is a hangman game that allows the user to play a game with 3 difficulty levels and keeps track of wins and losses.
//**************************

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
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
import java.util.concurrent.atomic.AtomicReference;


public class hangmanATC extends Application {

    //------------------------------------ Game Start ------------------------------------
    // This method is the main method for the hangman game. (Start up)
    @Override
    public void start(Stage primaryStage) {
        // This is the main window of the game

        //Current directory (Used because of discrepancies between IDEs)
        String currentDir = System.getProperty("user.dir");

        // Logo
        Image image = new Image(currentDir + "\\Hangman.png");
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

        // Selection box for the number of letters
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(
                "Hard (4 letters)",
                "Medium (6 letters)",
                "Easy (8 letters)"
        );

        // Default selection
        comboBox.setValue("Hard (4 letters)");

        // Make a demo game checkbox
        CheckBox demoGame = new CheckBox("Demo Game");
        demoGame.setSelected(false);

        // Start button
        Button b1 = new Button("Start Game");
        b1.setStyle("-fx-font: 22 arial;");
        // center the button below the text

        // Start button action
        b1.setOnAction(e -> {
            // switch statement for the number of letters and pass it to the hangman game
            switch (comboBox.getValue()) {
                case "Hard (4 letters)" ->
                    // start the game with 4 letters and a boolean for demo game
                        gameGUI(4, demoGame.isSelected());
                case "Medium (6 letters)" ->
                    // start the game with 6 letters and a boolean for demo game
                        gameGUI(6, demoGame.isSelected());
                case "Easy (8 letters)" ->
                    // start the game with 8 letters and a boolean for demo game
                        gameGUI(8, demoGame.isSelected());
            }

            // hide the startup GUI
            primaryStage.hide();

        });

        // HBox for the logo on top
        HBox logoBox = new HBox();
        logoBox.getChildren().add(logo);
        logoBox.setAlignment(Pos.CENTER);

        // VBox to hold the logo, text and selection box
        VBox vBox = new VBox(50);
        vBox.getChildren().add(t1);
        vBox.getChildren().add(comboBox);
        vBox.getChildren().add(demoGame);
        vBox.getChildren().add(b1);

        // This makes the checkbox and button closer
        VBox.setMargin(demoGame, new Insets(0, 0, -40, 0));

        // center the VBox
        vBox.setStyle("-fx-alignment: center;");

        // BorderPane to hold the VBox
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(logoBox);
        borderPane.setCenter(vBox);

        // set the scene color to white
        borderPane.setStyle("-fx-background-color: white;");



        // Scene for the startup GUI
        Scene scene = new Scene(borderPane, 400, 400);
        primaryStage.setTitle("Hangman | Start");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(currentDir + "\\gameIcon.png"));


    }

    // ---------------------------- Game GUI ----------------------------
    // Game GUI (Pass in the number of letters)
    public void gameGUI(int numLetters, boolean gameDemo) {
        // This is the game GUI

        // New stage
        Stage gameStage = new Stage();

        // Creates a new game
        hangmanGameATC game = new hangmanGameATC(numLetters, gameDemo);

        // guesses used
        int guesses = game.getGuesses();

        // String to hold the word to guess (with underscores)
        String[] blankWordArray = new String[numLetters];
        StringBuilder blankWord = new StringBuilder();
        for (int i = 0; i < numLetters; i++) {
            blankWordArray[i] = "_";
            blankWord.append("_ ");
        }


        // get current directory
        String currentDir = System.getProperty("user.dir");

        // Logo
        Image image = new Image(currentDir + "\\Hangman.png");
        ImageView logo = new ImageView(image);
        logo.setFitHeight(100);
        logo.setFitWidth(359);

        // Create a new image view | Atomic reference is used to change the image later
        AtomicReference<Image> image2 = new AtomicReference<>(new Image(currentDir + "\\hangmanStages\\hangmanStick-0.png"));
        ImageView hangmanStick = new ImageView(image2.get());
        hangmanStick.setFitHeight(200);
        hangmanStick.setFitWidth(200);

        // Text to display the word to guess
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
            // This check if the length of the text is greater than 1
            if (newValue.length() > 1) {
                // This will only allow one character to be entered by changing the text to the first character
                textField.setText(newValue.substring(0, 1));
            }
            // This checks if the text is a number, special character
            if (!newValue.matches("[a-zA-Z]*")) {
                // This will only allow letters to be entered by changing the text to nothing
                textField.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
            // Prevent spaces from being entered
            if (newValue.matches(" ")) {
                // This will only allow letters to be entered by changing the text to nothing
                textField.setText(newValue.replaceAll(" ", ""));
            }
        });


        // button needs to be defined
        Button b1 = new Button("Guess");
        b1.setStyle("-fx-font: 22 arial;");

        // button action for the guess button
        b1.setOnAction(e -> {
            // get the letter from the text box
            String letter = textField.getText();
            letter = letter.toUpperCase();


            if (letter.length() != 1 || letter.equals(" ")) {
                // This makes sure a space or more than one letter is not entered or no letter is entered
            } else {
                //Set the text box to blank
                textField.setText("");

                // This checks if the letter is in the word by passing the letter to the game class
                if (game.guessLetter(letter)) {
                    // ----------- Correct Letter ------------

                    // This updates the number of guesses
                    t2.setText("Total Guesses: " + game.getGuesses());

                    // This updates the blank word array
                    StringBuilder newWord = new StringBuilder();
                    for (int i = 0; i < numLetters; i++) {
                        // for each letter in the word check if it is in the correct letters array. If show the letter, if not show a blank
                        char[] wordToGuess = game.getWord().toCharArray();

                        if (wordToGuess[i] == letter.charAt(0)) {
                            // This sets the letter in the blank word array to the correct letter
                            blankWordArray[i] = letter;
                        }

                    }

                    // This takes the array and turns it into a string
                    for (int i = 0; i < numLetters; i++) {
                        // if null show "_" else show the letter
                        if (blankWordArray[i] == null) {
                            // This adds "_" to the string
                            newWord.append("_ ");
                        } else {
                            // This adds the letter to the string
                            newWord.append(blankWordArray[i]).append(" ");
                        }
                    }
                    // Updates the blank word on the screen
                    word.setText(newWord.toString());

                    // This checks if the word has been guessed by if there is any "_" left
                    if (!newWord.toString().contains("_")) {
                        // game is over
                        gameOverGUI(game.getGuesses(), game.getWins(), game.getLosses(), true, game.getWord());

                        //hide the game GUI
                        gameStage.hide();

                    }


                } else {
                    // ----------- Wrong letter guessed ------------

                    // Updates the hangman image
                    image2.set(new Image(currentDir + "\\hangmanStages\\hangmanStick-" + game.getWrongGuesses() + ".png"));

                    // update the number of guesses
                    t2.setText("Total Guesses: " + game.getGuesses());

                    StringBuilder wrongLetters = new StringBuilder();

                    // update the wrong letters guessed using for loop
                    for (int i = 0; i < game.getWrongGuesses(); i++) {
                        wrongLetters.append(game.getWrongLetters()[i]).append(" ");
                    }

                    // update the wrong letters guessed
                    t5.setText("Wrong Letters Guessed: " + wrongLetters);


                    // check if the game is over by the array having 6 letters
                    if (game.getWrongGuesses() == 6) {
                        // game is over
                        gameOverGUI(game.getGuesses(), game.getWins(), game.getLosses(), false, game.getWord());

                        //hide the game GUI
                        gameStage.hide();
                    }

                    // Update the image
                    hangmanStick.setImage(image2.get());
                }

            }

            // This sets the focus back to the text box so the user can enter another letter
            textField.requestFocus();

        });

        // Listener for the Enter key
        textField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                // This calls the button action when the enter key is pressed
                b1.fire();
            }
        });


        // create a stack pane
        StackPane stackPane = new StackPane();

        // Organize the GUI elements

        // Just the Logo
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(logo);
        hBox.setAlignment(Pos.CENTER);

        // This has everything except the logo
        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(hangmanStick, t1, word, t2, t3, t4, t5, textField, b1);
        vBox.setAlignment(Pos.CENTER);


        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hBox); // Logo
        borderPane.setCenter(vBox); // Everything else
        borderPane.setPadding(new Insets(10, 10, 10, 10));

        // add the border pane to the stack pane
        stackPane.getChildren().add(borderPane);

        // This sets the style of the stack pane to white
        stackPane.setStyle("-fx-background-color: #FFFFFF;");

        // create a scene
        Scene scene = new Scene(stackPane, 600, 625);
        gameStage.setTitle("Hangman | Game");
        gameStage.setScene(scene);
        gameStage.show();
        gameStage.setResizable(false);
        gameStage.getIcons().add(new Image(currentDir + "\\gameIcon.png"));
    }

    //------------------------- Game Over GUI -------------------------
    // This method creates the game over GUI
    private void gameOverGUI(int guesses, int wins, int losses, boolean isWin, String word) {
        // This method creates the game over GUI (Get the number of guesses, wins, losses, if the game was won or lost, and the word)

        //Update the wins and losses
        if (isWin) {
            wins++;
        } else {
            losses++;
        }

        // get current directory
        String currentDir = System.getProperty("user.dir");

        // Game over stage
        Stage gameOverStage = new Stage();

        // Stack pane
        StackPane stackPane = new StackPane();

        // VBox for the game over GUI
        VBox vBox = new VBox(50);

        // logo
        Image image = new Image(currentDir + "\\Hangman.png");
        ImageView logo = new ImageView(image);
        logo.setFitHeight(100);
        logo.setFitWidth(359);


        // T1 is the word
        Text t1 = new Text("Word: " + word);
        t1.setFill(Color.BLACK);
        t1.setFont(Font.font("Verdana", FontWeight.BOLD, 20));


        Image image2 = new Image(currentDir + "\\gameIcon.png");
        ImageView overIcon = new ImageView(image2);
        overIcon.setFitHeight(100);
        overIcon.setFitWidth(100);

        // Sets image based on if the game was won or lost
        if (isWin) {
            overIcon.setImage(new Image(currentDir + "\\hangmanElements\\win.png"));
        } else {
            overIcon.setImage(new Image(currentDir + "\\hangmanElements\\lose.png"));
        }

        // T2 is the number of guesses
        Text t2 = new Text("Total Guesses: " + guesses);
        t2.setFill(Color.BLACK);
        t2.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        // T3 is the number of wins
        Text t3 = new Text("Wins: " + wins);
        t3.setFill(Color.BLACK);
        t3.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        // T4 is the number of losses
        Text t4 = new Text("Losses: " + losses);
        t4.setFill(Color.BLACK);
        t4.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        // T5 is "You Win" or "You Lose"
        Text t5 = new Text("You " + (isWin ? "Won!" : "Lose!"));
        t5.setFill(Color.BLACK);
        t5.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        // B1 is the play again button
        Button b1 = new Button("Play Again");
        b1.setStyle("-fx-font: 22 arial;");

        // button action for play again button
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


        } catch (IOException e) {
            // Error message
            e.printStackTrace();
        }

        // Organize the GUI elements to make it look nice and clean (t5 is the text that says "You Win!" or "You Lose!")
        vBox.getChildren().addAll(t5, overIcon, t1, t2, t3, t4, b1);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);


        // The border pane is used to organize the GUI elements
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(logo);
        hBox.setAlignment(Pos.CENTER);

        // The border pane is used to organize the GUI elements
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hBox);
        borderPane.setCenter(vBox);
        borderPane.setPadding(new Insets(0, 0, 0, 10));



        // add the vBox to the stack pane
        stackPane.getChildren().add(borderPane);

        // create a scene
        Scene scene = new Scene(stackPane, 400, 450);
        gameOverStage.setTitle("Hangman | Game Over");
        gameOverStage.setScene(scene);
        gameOverStage.show();
        gameOverStage.setResizable(false);
        gameOverStage.getIcons().add(new Image(currentDir + "\\gameIcon.png"));
    }

    // Launch the application
    public static void main(String[] args) {
        launch(args);
    }
}