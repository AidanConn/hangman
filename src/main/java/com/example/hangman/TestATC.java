//**************************
//JavaFXGraphicsATC.java
//
//Author: Aidan Connaughton
//Date: 11/16/2022
//Class: COMSC110 LAB
//Instructor: Dr. Bai
//
//Purpose: Demo how to create graphics with JavaFX
//**************************

package com.example.hangman;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TestATC extends Application {
    @Override
    public void start(Stage primaryStage) {
        //r1 needs to be defined
        Rectangle r1 = new Rectangle(200, 300);
        r1.setFill(Color.BLUE);

        // text needs to be defined
        Text t1 = new Text("Go Hawks!");
        t1.setFill(Color.GOLD);
        t1.setFont(Font.font("Verdana", FontWeight.BOLD, 30));

        // image needs to be defined
        Image image = new Image("hawk.jpg");
        ImageView hawk = new ImageView(image);
        hawk.setFitHeight(100);
        hawk.setFitWidth(100);

        // create a circle
        Circle c1 = new Circle(80);
        c1.setStroke(Color.GOLD);
        c1.setFill(Color.WHITE);

        //grouping objects c1 + hawk => sPane
        StackPane sPane = new StackPane();
        sPane.getChildren().add(c1);
        sPane.getChildren().add(hawk);

        //grouping: sPane + t1 => vBox

        VBox vBox = new VBox(50);
        vBox.getChildren().add(sPane);
        vBox.getChildren().add(t1);



        //sPane2 needs to be defined
        StackPane sPane2 = new StackPane();
        sPane2.getChildren().add(r1);
        sPane2.getChildren().add(vBox);

        //Make everything stick to the center and middle
        vBox.setStyle("-fx-alignment: center;");



        //create a scene and place it in the stage
        Scene scene = new Scene(sPane2, 200, 300);
        primaryStage.setTitle("JavaFXGraphicsATC");
        primaryStage.setScene(scene);
        //set max height 400
        primaryStage.setMaxHeight(300);
        primaryStage.setMinHeight(300);
        //set max width 300
        primaryStage.setMaxWidth(200);
        primaryStage.setMinWidth(200);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
