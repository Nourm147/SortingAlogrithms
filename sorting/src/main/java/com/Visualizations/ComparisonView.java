package com.Visualizations;

import com.sorting.MainApp;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ComparisonView {

    private Scene scene;
    private final MainApp mainApp;

    public ComparisonView(MainApp mainApp) {
        this.mainApp = mainApp;
        buildScene();
    }

    private void buildScene() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #1e1e2e;");

        Label label = new Label("Comparison Mode (Coming Soon)");
        label.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");

        Button backBtn = MainApp.createStyledButton("← Back", "#7f8c8d");
        backBtn.setOnAction(e -> mainApp.showModeSelection());

        layout.getChildren().addAll(label, backBtn);
        scene = new Scene(layout, 1200, 800);
    }

    public Scene getScene() {
        return scene;
    }
}
