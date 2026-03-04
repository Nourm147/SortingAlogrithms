package com.Algorithms;

import com.Data.SimpleRunData;

public class InsertionSort extends Sort {

    public InsertionSort(SimpleRunData currentRunData, boolean inVisualizationMode) {
        super(currentRunData, inVisualizationMode);
    }

    @Override
    public void performSort(int[] arr) {

        for (int i = 1; i < arr.length; i++) {
            while (arr[i - 1] > arr[i]) {
                compare(i - 1, i);
                swap(arr, i - 1, i);
            }
            compare(i - 1, i); // Add the comparison that breaks the loop
        }
    }

}
