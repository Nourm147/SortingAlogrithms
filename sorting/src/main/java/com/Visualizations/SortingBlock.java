package com.Visualizations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.Algorithms.Sort;
import com.Data.SimpleOpData;
import com.Data.SimpleRunData;
import com.Enums.SortingType;
import com.Events.Event;
import com.Factories.SortingFactory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

// Changed to VBox for far superior UI styling capabilities
public class SortingBlock extends VBox {

    private final Pane chartContainer;
    private final Button deleteBtn;
    private final Label titleLabel;
    private final SortingType type;

    private double barWidth;
    private double barHeight;
    private double maxVal;
    private double actualWidth;
    private double actualHeight;

    private final List<Bar> bars = new ArrayList<>();
    private final List<Bar> selectedBars = new ArrayList<>();

    private final Event<SimpleRunData> onEndEvent;
    private Sort sorter;

    private int[] currentArray;

    public SortingBlock(double width, double height, SortingType algoType) {
        // Card Styling
        this.setStyle("-fx-background-color: #2a2b38; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #3e4053; -fx-border-width: 2;");
        this.setPadding(new Insets(10));
        this.setSpacing(10);
        this.setPrefSize(width, height);

        // Top Bar (Header)
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        this.type = algoType;
        titleLabel = new Label(algoType.toString());
        titleLabel.setStyle("-fx-text-fill: #e0e0e0; -fx-font-weight: bold; -fx-font-size: 14px;");

        // Spacer pushes buttons to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        deleteBtn = new Button("✖");
        deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand;");

        header.getChildren().addAll(titleLabel, spacer, deleteBtn);

        // Chart Area
        chartContainer = new Pane();
        VBox.setVgrow(chartContainer, Priority.ALWAYS); // Fill remaining space

        onEndEvent = new Event<>();
        this.getChildren().addAll(header, chartContainer);
    }

    public void updateBars(int[] arr) {
        chartContainer.getChildren().clear();
        // Recalculate based on current container size
        actualWidth = this.getPrefWidth() - 20; // minus padding
        actualHeight = this.getPrefHeight() - 50; // minus header and padding

        currentArray = arr;

        barWidth = actualWidth / arr.length;
        maxVal = Arrays.stream(arr).max().orElse(1);
        bars.clear();

        for (int i = 0; i < arr.length; i++) {
            barHeight = (arr[i] / maxVal) * (actualHeight - 10);
            Bar bar = new Bar(barHeight, barWidth - 1, i * barWidth, actualHeight - barHeight);
            chartContainer.getChildren().add(bar);
            bars.add(bar);
            bar.deselect();
        }
    }

    public void run(int size, double speed) {
        SimpleRunData runData = new SimpleRunData();

        sorter = SortingFactory.createSorter(this.type, runData, true);
        sorter.setSpeed(speed);

        // add listener to update bars status
        sorter.getOnCompareEvent().addListener(data -> compareBars(data));
        sorter.getOnSwapEvent().addListener(data -> swapBars(data));
        sorter.getOnInsertEvent().addListener(data -> insertBar(data));
        sorter.getOnEndEvent().addListener(data -> onEndEvent.invoke(data));

        Thread sortingThread = new Thread(() -> {
            sorter.sort(currentArray);
        });
        sortingThread.setDaemon(true);
        sortingThread.start();
    }

    public void adjustSpeed(double speed) {
        if (sorter == null) {
            return;
        }
        sorter.setSpeed(speed);
    }

    private void swapBars(SimpleOpData data) {
        int i = data.getFirstIndex();
        int j = data.getSecondIndex();

        deselectBars();

        Bar firstBar = bars.get(i);
        Bar secondBar = bars.get(j);

        // select bars
        firstBar.select();
        secondBar.select();
        selectedBars.add(firstBar);
        selectedBars.add(secondBar);

        // move bars
        double targetXForFirst = j * barWidth;
        double targetXForSecond = i * barWidth;
        firstBar.setX(targetXForFirst);
        secondBar.setX(targetXForSecond);

        // swap bars
        bars.set(i, secondBar);
        bars.set(j, firstBar);
    }

    private void compareBars(SimpleOpData data) {
        int i = data.getFirstIndex();
        int j = data.getSecondIndex();
        deselectBars();

        // select bars
        bars.get(i).select();
        bars.get(j).select();
        selectedBars.add(bars.get(i));
        selectedBars.add(bars.get(j));
    }

    private void insertBar(SimpleOpData data) {
        int index = data.getFirstIndex();
        int value = data.getSecondIndex();

        deselectBars();

        Bar bar = bars.get(index);
        bar.select();
        selectedBars.add(bar);

        // adjust height
        bar.setHeight((value / maxVal) * (actualHeight - 10));

        // adjust y position
        bar.setY(actualHeight - bar.getHeight());
    }

    private void deselectBars() {
        for (Bar bar : selectedBars) {
            bar.deselect();
        }
        selectedBars.clear();
    }

    // Getters so MainApp can assign logic to these buttons
    public Button getDeleteBtn() {
        return deleteBtn;
    }

    public String getalgoType() {
        return titleLabel.getText();
    }

    public Event<SimpleRunData> getOnEndEvent() {
        return onEndEvent;
    }
}
