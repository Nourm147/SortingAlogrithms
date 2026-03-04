package com.Visualizations;

import com.sorting.MainApp;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ModeSelectView {

    private Scene scene;
    private final MainApp mainApp;

    public ModeSelectView(MainApp mainApp) {
        this.mainApp = mainApp;
        buildScene();
    }

    private void buildScene() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #1e1e2e;");

        Label title = new Label("Choose Simulation Mode");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");

        Button visBtn = MainApp.createStyledButton("Visualization Mode", "#3498db");
        visBtn.setOnAction(e -> mainApp.showVisualization());

        Button compBtn = MainApp.createStyledButton("Comparison Mode", "#9b59b6");
        compBtn.setOnAction(e -> mainApp.showComparison());

        layout.getChildren().addAll(title, visBtn, compBtn);
        scene = new Scene(layout, 600, 400);
    }

    public Scene getScene() {
        return scene;
    }
}
