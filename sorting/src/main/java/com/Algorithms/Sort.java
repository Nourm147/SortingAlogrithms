package com.Algorithms;

import com.Data.SimpleOpData;
import com.Data.SimpleRunData;
import com.Events.Event;

import javafx.application.Platform;
import lombok.Getter;
import lombok.Setter;

public abstract class Sort {

    protected final SimpleRunData currentRunData;

    @Getter
    protected Event<SimpleOpData> onCompareEvent;
    @Getter
    protected Event<SimpleOpData> onSwapEvent;
    @Getter
    protected Event<SimpleOpData> onInsertEvent;
    @Getter
    protected Event<SimpleRunData> onEndEvent;

    protected double duration = 50; // time to sleep in ms
    @Setter
    protected double speed = 1;

    private final boolean inVisualizationMode;

    public Sort(SimpleRunData currentRunData, boolean inVisualizationMode) {
        this.currentRunData = currentRunData;
        this.inVisualizationMode = inVisualizationMode;

        onCompareEvent = new Event<>();
        onSwapEvent = new Event<>();
        onInsertEvent = new Event<>();
        onEndEvent = new Event<>();

        onCompareEvent.addListener(x -> this.currentRunData.increaseComparisons());
        onSwapEvent.addListener(x -> this.currentRunData.increaseinterchanges());
        onInsertEvent.addListener(x -> this.currentRunData.increaseinterchanges());
    }

    public void sort(int[] arr) {

        currentRunData.startRun();

        performSort(arr); // Call template Method

        currentRunData.endRun();
        onEndEvent.invoke(currentRunData);
    }

    // Template method for each sorting algorithm
    protected abstract void performSort(int[] arr);

    // Helper function to handle delay for visualization
    protected void handleVisualizationDelay() {

        if (!inVisualizationMode) {
            return;
        }
        try {
            Thread.sleep((long) (duration / speed));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Helper methods for comparison, swap, and insertion
    protected void compare(int i, int j) {

        Platform.runLater(() -> { // ensure that UI updates while this thread is suspended
            onCompareEvent.invoke(new SimpleOpData(i, j));
        });

        handleVisualizationDelay();
    }

    protected void swap(int[] arr, int i, int j) {

        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

        Platform.runLater(() -> {
            onSwapEvent.invoke(new SimpleOpData(i, j));
        });

        handleVisualizationDelay();
    }

    protected void insert(int[] arr, int index, int value) {

        arr[index] = value;
        Platform.runLater(() -> {
            onInsertEvent.invoke(new SimpleOpData(index, value));
        });

        handleVisualizationDelay();
    }
}
