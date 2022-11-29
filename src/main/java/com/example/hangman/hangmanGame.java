package com.example.hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class hangmanGame {
    // Oject oriented version of the game of hangman
    // Global variables
    // Number of guesses
    private int guesses = 0;

    private int wins = 0;
    private int losses = 0;

    private String word = "";
    private String wordDisplay = "";

    private int wrongGuesses = 0;
    private String[] wrongLetters = new String[6]; // Array to hold the wrong letters


    public void gameData(){
        // Load win and loss data from file

        try {
            //get current directory
           // String currentDir = System.getProperty("user.dir");
            //create a new file object
           // File file = new File(currentDir + "\\src\\main\\java\\com\\example\\hangman\\gameData.txt");

            //get current directory
            String currentDir = System.getProperty("user.dir");

            File file = new File(currentDir + "\\stats.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                // First int is wins and second is losses
                String[] data = sc.nextLine().split(" ");
                wins = Integer.parseInt(data[0]);
                losses = Integer.parseInt(data[1]);



            }
            sc.close();
        } catch (FileNotFoundException e) {
            // If file doesn't exist, create it
            System.out.println("An error occurred.");
        }

    }

    // get the word from a list of words
    public hangmanGame(int wordLength){
        gameData();
        newGame(wordLength);
    }

    // setters and getters
    public int getGuesses() {
        return guesses;
    }

    public void setGuesses(int guesses) {
        this.guesses = guesses;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public String getWord() {
        return word;
    }

    // Methods

    public void newGame(int wordLength){
        // Start a new game
        // Reset guesses
        guesses = 0;

        // Get a new word
        word = getWord(wordLength);

    }

    public String getWord(int wordLength) {
        // 3 different word lists {4, 6, 8} letters long | computer terms
        String[] wordList4 = { "java"};
        String[] wordList6 = { "python"};
        String[] wordList8 = {"computer"};

        switch (wordLength) {
            case 4 -> {
                // get wordList4 length
                int wordList4Length = wordList4.length;
                // get random number between 1 and wordList4Length
                int randomNum = (int) (Math.random() * wordList4Length);
                // return word at randomNum
                return wordList4[randomNum];
            }
            case 6 -> {
                // get wordList6 length
                int wordList6Length = wordList6.length;
                // get random number between 1 and wordList6Length
                int randomNum = (int) (Math.random() * wordList6Length);
                // return word at randomNum
                return wordList6[randomNum];
            }
            case 8 -> {
                // get wordList8 length
                int wordList8Length = wordList8.length;
                // get random number between 1 and wordList8Length
                int randomNum = (int) (Math.random() * wordList8Length);
                // return word at randomNum
                return wordList8[randomNum];
            }
            default -> {
                return "error";
            }
        }

    }

    public boolean guessLetter(String letterGuess){
        // Get letter from user
        guesses++;
        String letter = letterGuess;
        // Check if letter is in word
        if (word.contains(String.valueOf(letter))){
            return true;
        } else {
            // Add letter to wrongLetters array
            wrongGuesses++;
            wrongLetters[wrongGuesses - 1] = letter;
            return false;
        }

    }

    // getter for wrongLetters
    public String[] getWrongLetters() {
        return wrongLetters;
    }


}
