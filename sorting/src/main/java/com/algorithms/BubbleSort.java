package com.algorithms;

import com.Data.SimpleRunData;

public class BubbleSort extends Sort {

    public BubbleSort(SimpleRunData currentRunData, boolean inVisualizationMode) {
        super(currentRunData, inVisualizationMode);
    }

    @Override
    public void performSort(int[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {
            boolean performSwap = false; // best case(sorted) O(n)
            for (int j = 0; j < arr.length - i - 1; j++) {
                compare(j, j + 1);
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    performSwap = true;
                }
            }
            if (!performSwap) {
                break;
            }
        }
    }

}
