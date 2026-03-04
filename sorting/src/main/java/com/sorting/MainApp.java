package com.sorting;

import com.Visualizations.ComparisonView;
import com.Visualizations.ModeSelectView;
import com.Visualizations.VisualizationView;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage window;
    private ModeSelectView modeSelectView;
    private VisualizationView visualizationView;
    private ComparisonView comparisonView;

    @Override
    public void start(Stage stage) {
        this.window = stage;
        window.setTitle("Algorithm Visualizer");

        // Initialize all views
        modeSelectView = new ModeSelectView(this);
        visualizationView = new VisualizationView(this);
        comparisonView = new ComparisonView(this);

        // Start on Mode Selection
        showModeSelection();
        window.show();
    }

    // Scene Switching Methods
    public void showModeSelection() {
        window.setScene(modeSelectView.getScene());
    }

    public void showVisualization() {
        window.setScene(visualizationView.getScene());
    }

    public void showComparison() {
        window.setScene(comparisonView.getScene());
    }

    // Shared Utilities
    public static Button createStyledButton(String text, String colorHex) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: " + colorHex + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 5;");
        return btn;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
