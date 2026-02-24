package com.sorting;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class MainApp extends Application {
    private static Stage stage;

    @Override
    public void start(@SuppressWarnings("exports") Stage s) throws IOException {
        stage=s;
        stage.setTitle("Sorting Algorithms");
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/primary.fxml")));
        stage.setScene(scene);
        Image icon = new Image(getClass().getResourceAsStream("/assets/sort.png"));
        stage.getIcons().add(icon);

        stage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }

}
