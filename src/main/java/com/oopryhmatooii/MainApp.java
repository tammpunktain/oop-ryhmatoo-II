package com.oopryhmatooii;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class MainApp extends Application {

    public static void start(Stage lava){
        BorderPane juur = new BorderPane();
        Scene stseen = new Scene(juur, 900,600);



        lava.setTitle("Autosimulaator");
        lava.setScene(stseen);
        lava.show();
    }





    public static void main(String[] args) {
        launch();
    }
}
