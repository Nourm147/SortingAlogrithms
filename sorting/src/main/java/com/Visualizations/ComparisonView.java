package com.Visualizations;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Data.DataBlock;
import com.Data.SimpleRunData;
import com.Enums.ArraySize;
import com.Enums.SortingType;
import com.Factories.ArrayGenerator;
import com.Factories.SortingFactory;
import com.Utils.DataExporter;
import com.Utils.FileReaderUtil;
import com.sorting.MainApp;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;

public class ComparisonView {

    private Scene scene;
    private final MainApp mainApp;

    private ComboBox<String> arrayTypeSelector;
    private TextField runsInput;
    private Button compareBtn;
    private Button loadBtn;
    private Button deleteBtn;
    private Button exportBtn;

    private TableView<DataBlock> table;
    private final Map<String, int[]> currentData = new HashMap<>();
    private final FileChooser fileChooser = new FileChooser();

    public ComparisonView(MainApp mainApp) {
        this.mainApp = mainApp;
        buildScene();
    }

    @SuppressWarnings("unchecked")
    private void buildScene() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #181825;");

        HBox toolbar = new HBox(15);
        toolbar.setAlignment(Pos.CENTER_LEFT);
        toolbar.setStyle("-fx-background-color: #2a2b38; -fx-padding: 15; -fx-background-radius: 8;");

        arrayTypeSelector = new ComboBox<>();
        arrayTypeSelector.getItems().addAll("Random", "Sorted", "Reversed");
        arrayTypeSelector.getSelectionModel().selectFirst();

        runsInput = new TextField("10");
        runsInput.setPrefWidth(60);

        compareBtn = MainApp.createStyledButton("▶ Compare", "#2ecc71");
        loadBtn = MainApp.createStyledButton("Load File", "#3498db");
        deleteBtn = MainApp.createStyledButton("Delete File", "#e74c3c");
        Button backBtn = MainApp.createStyledButton("← Back", "#7f8c8d");
        exportBtn = MainApp.createStyledButton("📊 Visualize", "#9b59b6");

        Label runsLabel = new Label("Runs:");
        runsLabel.setStyle("-fx-text-fill: white;");

        Label modeLabel = new Label("Array Mode:");
        modeLabel.setStyle("-fx-text-fill: white;");

        toolbar.getChildren().addAll(
                backBtn, runsLabel, runsInput,
                modeLabel, arrayTypeSelector,
                loadBtn, deleteBtn, compareBtn, exportBtn);

        table = new TableView<>();
        table.setStyle("-fx-background-color: #2a2b38; -fx-text-fill: #ebecf6;");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(table, Priority.ALWAYS);

        table.getColumns().addAll(
                col("Algorithm", DataBlock::getSortingAlgorithmName, 150),
                col("Array Size", DataBlock::getArraySize, 120),
                col("Mode", DataBlock::getArrayGenerationMode, 120),
                col("Runs", DataBlock::getNumberOfRuns, 80),
                col("Avg Runtime (ms)", DataBlock::getAverageRuntime, 140),
                col("Min Runtime (ms)", DataBlock::getMinRuntime, 140),
                col("Max Runtime (ms)", DataBlock::getMaxRuntime, 140),
                col("Comparisons", DataBlock::getComparisonsNumber, 130),
                col("Interchanges", DataBlock::getInterchangesNumber, 130)
        );

        root.getChildren().addAll(toolbar, table);
        scene = new Scene(root, 1800, 1400);

        // Actions
        backBtn.setOnAction(e -> mainApp.showModeSelection());

        exportBtn.setOnAction(e -> DataExporter.exportAndVisualize(table));

        loadBtn.setOnAction(e -> {
            List<File> files = fileChooser.showOpenMultipleDialog(scene.getWindow());
            if (files != null) {
                Map<String, int[]> data = FileReaderUtil.readFiles(files);
                currentData.putAll(data);
                for (String key : data.keySet()) {
                    if (!arrayTypeSelector.getItems().contains(key)) {
                        arrayTypeSelector.getItems().add(key);
                    }
                }
            }
        });

        deleteBtn.setOnAction(e -> {
            String selected = arrayTypeSelector.getSelectionModel().getSelectedItem();
            if (currentData.containsKey(selected)) {
                currentData.remove(selected);
                arrayTypeSelector.getItems().remove(selected);
            }
        });

        compareBtn.setOnAction(e -> runComparison());
    }

    private void runComparison() {
        int runs = Integer.parseInt(runsInput.getText());
        String mode = arrayTypeSelector.getSelectionModel().getSelectedItem();

        setToolbarDisabled(true);

        // Run each algo × each size on a background thread
        Thread compareTread = new Thread(() -> {
            for (SortingType algo : SortingType.values()) {
                for (ArraySize size : ArraySize.values()) {

                    List<Double> runtimes = new ArrayList<>();
                    long totalComparisons = 0;
                    long totalInterchanges = 0;

                    for (int run = 0; run < runs; run++) {

                        int[] data = generateData(size.getSize(), mode);

                        SimpleRunData runData = new SimpleRunData();
                        var sorter = SortingFactory.createSorter(algo, runData, false); // false = no animation

                        sorter.sort(data);

                        double elapsed = runData.getRunTimeDuration().toNanos();
                        runtimes.add(elapsed);
                        totalComparisons += runData.getComparisonsNumber();
                        totalInterchanges += runData.getInterchangesNumber();
                    }

                    // Build DataBlock
                    double min = Collections.min(runtimes) / 1000000;

                    double max = Collections.max(runtimes) / 1000000;
                    double avg = (double) runtimes.stream().mapToDouble(Double::doubleValue).average().orElse(0) / 1000000;

                    // rounded to the nearest 4 digits after the decimal
                    avg = Math.round(avg * 10000.0) / 10000.0;
                    min = Math.round(min * 10000.0) / 10000.0;
                    max = Math.round(max * 10000.0) / 10000.0;

                    DataBlock block = new DataBlock(
                            algo, size.getSize(), mode,
                            avg,
                            min,
                            max,
                            runs,
                            totalComparisons / runs,
                            totalInterchanges / runs
                    );

                    Platform.runLater(() -> table.getItems().add(block));
                }
            }

            Platform.runLater(() -> setToolbarDisabled(false));
        });
        compareTread.setDaemon(true);
        compareTread.start();
    }

    private int[] generateData(int size, String mode) {
        try {
            return ArrayGenerator.generateArray(size, mode);
        } catch (Exception e) {
            int[] original = currentData.get(mode);
            return Arrays.copyOf(original, original.length);
        }
    }

    private void setToolbarDisabled(boolean val) {
        compareBtn.setDisable(val);
        loadBtn.setDisable(val);
        deleteBtn.setDisable(val);
        runsInput.setDisable(val);
        arrayTypeSelector.setDisable(val);
        exportBtn.setDisable(val);
    }

    // Helper function to create columns for the table
    private <T> TableColumn<DataBlock, T> col(String title, Callback<DataBlock, T> getter, double width) {
        TableColumn<DataBlock, T> col = new TableColumn<>(title);
        col.setCellValueFactory(cell -> new SimpleObjectProperty<>(getter.call(cell.getValue())));
        col.setPrefWidth(width);
        col.setStyle("-fx-alignment: CENTER;");
        return col;
    }

    public Scene getScene() {
        return scene;
    }
}
