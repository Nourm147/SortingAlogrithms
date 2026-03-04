package com.Visualizations;

import java.util.ArrayList;
import java.util.List;

import com.Enums.ArrayType;
import com.Enums.SortingType;
import com.Factories.ArrayGenerator;
import com.sorting.MainApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class VisualizationView {

    private Scene scene;
    private final MainApp mainApp;

    // Visualization UI Elements
    private FlowPane displayArea;
    private final List<SortingBlock> blocks = new ArrayList<>();
    private ComboBox<SortingType> algoSelector;
    private ComboBox<ArrayType> arrayTypeSelector;
    private TextField sizeInput;
    private TextField speedInput;
    private Button startAllBtn;
    private Button addBtn;

    private int currentActiveBlocks = 0;

    public VisualizationView(MainApp mainApp) {
        this.mainApp = mainApp;
        buildScene();
    }

    private void buildScene() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #181825;"); // Dark background

        // Toolbar
        HBox toolbar = new HBox(15);
        toolbar.setAlignment(Pos.CENTER_LEFT);
        toolbar.setStyle("-fx-background-color: #2a2b38; -fx-padding: 15; -fx-background-radius: 8;");

        algoSelector = new ComboBox<>();
        algoSelector.getItems().addAll(SortingType.values());
        algoSelector.getSelectionModel().selectFirst();

        arrayTypeSelector = new ComboBox<>();
        arrayTypeSelector.getItems().addAll(ArrayType.values());
        arrayTypeSelector.getSelectionModel().selectFirst();

        sizeInput = new TextField("50");
        sizeInput.setPrefWidth(60);

        speedInput = new TextField("1");
        speedInput.setPrefWidth(60);

        // Buttons
        addBtn = MainApp.createStyledButton("+ Add Block", "#3498db");
        startAllBtn = MainApp.createStyledButton("▶ Start All", "#2ecc71");
        Button backBtn = MainApp.createStyledButton("← Back", "#7f8c8d");

        Label sizeLabel = new Label("Array Size:");
        sizeLabel.setStyle("-fx-text-fill: white;");

        Label speedLabel = new Label("Speed:");
        speedLabel.setStyle("-fx-text-fill: white;");

        toolbar.getChildren().addAll(backBtn, sizeLabel, sizeInput, speedLabel, speedInput, algoSelector, arrayTypeSelector, addBtn, startAllBtn);

        // Display Area
        displayArea = new FlowPane(15, 15);
        displayArea.setAlignment(Pos.TOP_LEFT);
        VBox.setVgrow(displayArea, Priority.ALWAYS);

        root.getChildren().addAll(toolbar, displayArea);
        scene = new Scene(root, 1800, 1400);

        // Actions
        backBtn.setOnAction(e -> mainApp.showModeSelection());
        addBtn.setOnAction(e -> addSortingBlock());
        startAllBtn.setOnAction(e -> startAllBlocks());

        arrayTypeSelector.setOnAction(x -> onArraySizeChange());
        sizeInput.setOnAction(x -> onArraySizeChange());
        speedInput.setOnAction(x -> {
            for (SortingBlock block : blocks) {
                block.adjustSpeed(Double.parseDouble(speedInput.getText()));
            }
        });
    }

    private void addSortingBlock() {
        if (blocks.size() >= 6) {
            return;
        }

        double width = (scene.getWidth() / 3) - 30;
        double height = (scene.getHeight() / 2) - 60;
        if (width <= 0 || height <= 0) {
            return;
        }

        SortingType selectedAlgo = algoSelector.getSelectionModel().getSelectedItem();
        SortingBlock block = new SortingBlock(width, height, selectedAlgo);

        // Block-specific Actions
        block.getDeleteBtn().setOnAction(e -> {
            blocks.remove(block);
            displayArea.getChildren().remove(block);
        });

        blocks.add(block);
        displayArea.getChildren().add(block);

        try {
            int size = Integer.parseInt(sizeInput.getText());
            block.updateBars(generateData(size));
        } catch (NumberFormatException e) {
            System.out.println("Invalid size");
        }
    }

    private void onArraySizeChange() {
        int size = Integer.parseInt(sizeInput.getText());
        for (SortingBlock block : blocks) {
            block.updateBars(generateData(size));
        }
    }

    private void startAllBlocks() {
        int size = Integer.parseInt(sizeInput.getText());
        double speed = Double.parseDouble(speedInput.getText());
        disableAllButtons();
        for (SortingBlock block : blocks) {
            block.run(size, speed);
        }
    }

    private void disableAllButtons() {
        sizeInput.setDisable(true);
        arrayTypeSelector.setDisable(true);
        startAllBtn.setDisable(true);
        addBtn.setDisable(true);
        algoSelector.setDisable(true);

        for (SortingBlock block : blocks) {
            block.getDeleteBtn().setDisable(true);
            block.getOnEndEvent().addListener(x -> {
                block.getDeleteBtn().setDisable(false);
                checkForEnable();
            });
        }
        currentActiveBlocks = blocks.size();
    }

    public void checkForEnable() {
        currentActiveBlocks--;
        if (currentActiveBlocks == 0) {
            enableAllButtons();
        }
    }

    private void enableAllButtons() {
        arrayTypeSelector.setDisable(false);
        sizeInput.setDisable(false);
        startAllBtn.setDisable(false);
        addBtn.setDisable(false);
        algoSelector.setDisable(false);
    }

    private int[] generateData(int size) {
        return ArrayGenerator.generateArray(size, arrayTypeSelector.getSelectionModel().getSelectedItem());
    }

    public Scene getScene() {
        return scene;
    }
}
