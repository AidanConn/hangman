package edu.aidan.hangman;

//**************************
//hangmanGameATC.java
//
//Author: Aidan Connaughton
//Date: 12/06/2022
//Class: COMSC110 LAB
//Instructor: Dr. Omar
//
//Purpose: This class stores variables and methods for the hangman game
//**************************

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class hangmanGameATC {
    // This is the main class for the hangman game.

    // This keeps track of the number of guesses the user used.
    private int guesses = 0;

    // Wins and losses are tracked here.
    private int wins = 0;
    private int losses = 0;

    // This is the word that the user is trying to guess.
    private String word = "";

    // This keeps the number of wrong guesses the user has made.
    private int wrongGuesses = 0;
    private String[] wrongLetters = new String[6]; // Array to hold the wrong letters


    public void gameData() {
        // Load win and loss data from file

        try {

            //get current directory
            String currentDir = System.getProperty("user.dir");

            File file = new File(currentDir + "\\stats.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                // First int is wins and second is losses
                String[] data = scanner.nextLine().split(" ");
                wins = Integer.parseInt(data[0]);
                losses = Integer.parseInt(data[1]);


            }
            scanner.close();
        } catch (FileNotFoundException e) {
            // If file doesn't exist, create it
            System.out.println("File not found, using default values.");
            wins = 0;
            losses = 0;
        }

    }

    // get the word from a list of words
    public hangmanGameATC(int wordLength, boolean gameDemo) {
        gameData();
        newGame(wordLength, gameDemo);
    }

    // setters and getters
    public int getGuesses() {
        return guesses;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public String getWord() {
        return word;
    }

    // Methods

    public void newGame(int wordLength, boolean gameDemo) {
        // Start a new game
        // Reset guesses
        guesses = 0;

        // Get a new word
        word = getWord(wordLength, gameDemo);

    }

    public String getWord(int wordLength, boolean gameDemo) {
        // 3 different word lists {4, 6, 8} letters long to choose from
        // make the words technology related (computer terms) 4 letters, 6 letters, 8 letters
        // 4-letter words array
        String[] wordList4 = {"JAVA", "BYTE", "CODE", "DATA", "FILE", "LINK", "LIST", "LOOP", "MAIN", "NULL", "PAGE", "PATH", "PORT", "READ", "ROOT", "SIZE", "TEXT", "TRUE", "TYPE", "VOID", "WORD", "ZERO"};
        // 6-letter words
        String[] wordList6 = {"COOKIE", "MEMORY", "NUMBER", "OBJECT", "OUTPUT", "STRING", "SYSTEM", "SERVER"};
        // 8-letter words
        String[] wordList8 = {"VARIABLE", "FUNCTION", "DATABASE", "SOFTWARE", "HARDWARE", "OPERATOR", "COMPUTER", "LANGUAGE", "COMPILER", "DEBUGGER", "KEYBOARD", "INTERNET", "SOFTWARE", "HARDWARE", "OPERATOR", "COMPUTER", "LANGUAGE", "COMPILER", "DEBUGGER", "INTERNET"};

        if (gameDemo) {
            return switch (wordLength) {
                case 4 -> "JAVA";
                case 6 -> "MEMORY";
                case 8 -> "VARIABLE";
                default -> "ERROR";
            };
        } else {
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
                    return "ERROR";
                }
            }
        }
    }

    public boolean guessLetter(String letterGuess) {
        // Get letter from user
        guesses++;
        // Check if letter is in word
        if (word.contains(String.valueOf(letterGuess))) {
            return true;
        } else {
            // Add letter to wrongLetters array
            wrongGuesses++;
            wrongLetters[wrongGuesses - 1] = letterGuess;
            return false;
        }

    }

    // getter for wrongLetters
    public String[] getWrongLetters() {
        return wrongLetters;
    }

    // getter for wrongGuesses
    public int getWrongGuesses() {
        return wrongGuesses;
    }


}
