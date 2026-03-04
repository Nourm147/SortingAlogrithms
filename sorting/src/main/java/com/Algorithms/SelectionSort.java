package com.Algorithms;

import com.Data.SimpleRunData;

public class SelectionSort extends Sort {

    public SelectionSort(SimpleRunData currentRunData, boolean inVisualizationMode) {
        super(currentRunData, inVisualizationMode);
    }

    @Override
    public void performSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;

            for (int j = i + 1; j < arr.length; j++) {
                compare(j, minIndex);
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            if (i != minIndex) {
                swap(arr, i, minIndex);
            }
        }
    }

}
