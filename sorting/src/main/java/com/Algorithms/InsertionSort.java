package com.Algorithms;

import com.Data.SimpleRunData;

public class InsertionSort extends Sort {

    public InsertionSort(SimpleRunData currentRunData, boolean inVisualizationMode) {
        super(currentRunData, inVisualizationMode);
    }

    @Override
    public void performSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int j = i;
            while (j > 0) {
                compare(j - 1, j);
                if (arr[j - 1] > arr[j]) {
                    swap(arr, j - 1, j);
                    j--;
                } else {
                    break;
                }
            }
        }
    }

}
