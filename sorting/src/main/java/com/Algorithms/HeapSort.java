package com.Algorithms;

import com.Data.SimpleRunData;

public class HeapSort extends Sort {

    public HeapSort(SimpleRunData currentRunData, boolean inVisualizationMode) {
        super(currentRunData, inVisualizationMode);
    }

    @Override
    public void performSort(int[] arr) {
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            heapify(arr, arr.length, i);
        }

        for (int i = arr.length - 1; i > 0; i--) {
            swap(arr, i, 0);
            heapify(arr, i, 0); // ReInsert the last element after swapping
        }

    }

    public void heapify(int[] arr, int n, int index) {
        int largest = index;

        int left = 2 * index + 1;
        int right = 2 * index + 2;

        if (left < n) {
            compare(left, largest);
            if (arr[left] > arr[largest]) {
                largest = left;
            }
        }

        if (right < n) {
            compare(right, largest);
            if (arr[right] > arr[largest]) {
                largest = right;
            }
        }

        if (largest != index) {
            swap(arr, index, largest);

            heapify(arr, n, largest); // move to the next level
        }
    }
}
