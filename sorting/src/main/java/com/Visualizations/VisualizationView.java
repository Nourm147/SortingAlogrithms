package com.Visualizations;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Enums.SortingType;
import com.Factories.ArrayGenerator;
import com.Utils.FileReaderUtil;
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
import javafx.stage.FileChooser;

public class VisualizationView {

    private Scene scene;
    private final MainApp mainApp;

    // Visualization UI Elements
    private FlowPane displayArea;
    private final List<SortingBlock> blocks = new ArrayList<>();
    private ComboBox<SortingType> algoSelector;
    private ComboBox<String> arrayTypeSelector;
    private TextField sizeInput;
    private TextField speedInput;
    private Button startAllBtn;
    private Button addBtn;

    private Map<String, int[]> currentData;

    // handle file loading
    private Button loadBtn;
    FileChooser fileChooser = new FileChooser();
    private Button deleteBtn;

    private int currentActiveBlocks = 0;

    public VisualizationView(MainApp mainApp) {
        this.mainApp = mainApp;
        buildScene();
    }

    private void buildScene() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #181825;");

        // Toolbar
        HBox toolbar = new HBox(15);
        toolbar.setAlignment(Pos.CENTER_LEFT);
        toolbar.setStyle("-fx-background-color: #2a2b38; -fx-padding: 15; -fx-background-radius: 8;");

        algoSelector = new ComboBox<>();
        algoSelector.getItems().addAll(SortingType.values());
        algoSelector.getSelectionModel().selectFirst();

        List<String> arrayTypes = new ArrayList<>() {
            {
                add("Random");
                add("Sorted");
                add("Reversed");
            }
        };

        arrayTypeSelector = new ComboBox<>();

        arrayTypeSelector.getItems().addAll(arrayTypes);
        arrayTypeSelector.getSelectionModel().selectFirst();

        sizeInput = new TextField("50");
        sizeInput.setPrefWidth(60);

        speedInput = new TextField("1");
        speedInput.setPrefWidth(60);

        // Buttons
        addBtn = MainApp.createStyledButton("+ Add Block", "#3498db");
        startAllBtn = MainApp.createStyledButton("▶ Start All", "#2ecc71");
        Button backBtn = MainApp.createStyledButton("← Back", "#7f8c8d");
        loadBtn = MainApp.createStyledButton("Load File", "#3498db");
        deleteBtn = MainApp.createStyledButton("Delete File", "#e74c3c");

        Label sizeLabel = new Label("Array Size:");
        sizeLabel.setStyle("-fx-text-fill: white;");

        Label speedLabel = new Label("Speed:");
        speedLabel.setStyle("-fx-text-fill: white;");

        toolbar.getChildren().addAll(backBtn, sizeLabel, sizeInput,
                speedLabel, speedInput, algoSelector,
                arrayTypeSelector, loadBtn, deleteBtn, addBtn, startAllBtn);

        // Display Area
        displayArea = new FlowPane(15, 15);
        displayArea.setAlignment(Pos.TOP_LEFT);
        VBox.setVgrow(displayArea, Priority.ALWAYS);

        root.getChildren().addAll(toolbar, displayArea);
        scene = new Scene(root, 1800, 1400);

        currentData = new HashMap<>();

        // Actions
        backBtn.setOnAction(e -> mainApp.showModeSelection());
        addBtn.setOnAction(e -> addSortingBlock());
        startAllBtn.setOnAction(e -> startAllBlocks());
        fileChooser.setTitle("Open File");

        loadBtn.setOnAction(e -> {
            List<File> files = fileChooser.showOpenMultipleDialog(this.scene.getWindow());
            if (files != null) {
                Map<String, int[]> data = FileReaderUtil.readFiles(files);
                currentData.putAll(data);
                for (Map.Entry<String, int[]> entry : data.entrySet()) {
                    if (!arrayTypeSelector.getItems().contains(entry.getKey())) {
                        arrayTypeSelector.getItems().add(entry.getKey());
                    }
                }
            }
        });

        deleteBtn.setOnAction(e -> {
            String currentFile = arrayTypeSelector.getSelectionModel().getSelectedItem();
            if (currentData.containsKey(currentFile)) {
                currentData.remove(currentFile);
                arrayTypeSelector.getItems().remove(currentFile);
            }
        });

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
        algoSelector.getSelectionModel().select((blocks.size() + 1) % SortingType.values().length);

        // Block-specific Actions
        block.getOnEndEvent().addListener(x -> {
            block.getDeleteBtn().setDisable(false);
            block.getRunBtn().setDisable(false);
            checkForEnable();
        });

        block.getDeleteBtn().setOnAction(e -> {
            blocks.remove(block);
            displayArea.getChildren().remove(block);

            block.getOnRunEvent().removeListener(x -> {
                checkForDisable(block);
            });
        });

        block.getRunBtn().setOnAction(e -> {
            int size = Integer.parseInt(sizeInput.getText());
            double speed = Double.parseDouble(speedInput.getText());
            setAllButtons(true);

            block.run(size, speed);
        });

        blocks.add(block);
        displayArea.getChildren().add(block);

        block.getOnRunEvent().addListener(x -> {
            checkForDisable(block);
        });

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
        if (blocks.isEmpty()) {
            return;
        }
        int size = Integer.parseInt(sizeInput.getText());
        double speed = Double.parseDouble(speedInput.getText());
        for (SortingBlock block : blocks) {
            block.run(size, speed);
        }
    }

    private void setAllButtons(boolean disable) {
        sizeInput.setDisable(disable);
        arrayTypeSelector.setDisable(disable);
        startAllBtn.setDisable(disable);
        addBtn.setDisable(disable);
        algoSelector.setDisable(disable);
        deleteBtn.setDisable(disable);
        loadBtn.setDisable(disable);
    }

    private void checkForDisable(SortingBlock block) {

        if (currentActiveBlocks == 0) {
            setAllButtons(true);
        }
        block.getDeleteBtn().setDisable(true);
        block.getRunBtn().setDisable(true);

        currentActiveBlocks++;
    }

    public void checkForEnable() {
        currentActiveBlocks--;
        if (currentActiveBlocks == 0) {
            setAllButtons(false);
        }
    }

    private int[] generateData(int size) {
        try {
            return ArrayGenerator.generateArray(size, arrayTypeSelector.getSelectionModel().getSelectedItem());

        } catch (Exception e) {
            int[] original = currentData.get(arrayTypeSelector.getSelectionModel().getSelectedItem());
            return Arrays.copyOf(original, original.length);
        }
    }

    public Scene getScene() {
        return scene;
    }
}
