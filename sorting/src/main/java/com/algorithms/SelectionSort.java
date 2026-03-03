package com.algorithms;

import com.Data.SimpleRunData;

public class SelectionSort extends Sort {

    public SelectionSort(SimpleRunData currentRunData, boolean inVisualizationMode) {
        super(currentRunData, inVisualizationMode);
    }

    @Override
    public void performSort(int[] arr) {

        for (int i = 0; i < arr.length; i++) {
            int min = arr[0], minIndex = 0;

            for (int j = i; j < arr.length; j++) {
                compare(j, minIndex);
                if (min > arr[j]) {
                    min = arr[j];
                    minIndex = j;
                }
            }
            swap(arr, i, minIndex);

        }
    }

}
