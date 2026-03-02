package com.enums;

public enum SortingType {
    SELECTION("Selection Sort"),
    INSERTION("Insertion Sort"),
    BUBBLE("Bubble Sort"),
    MERGE("Merge Sort"),
    HEAP("Heap Sort"),
    QUICK("Quick Sort");

    private final String displayName;

    SortingType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
