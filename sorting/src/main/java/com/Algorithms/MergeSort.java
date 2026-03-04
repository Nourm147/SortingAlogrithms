package com.Algorithms;

import com.Data.SimpleRunData;

public class MergeSort extends Sort {

    public MergeSort(SimpleRunData currentRunData, boolean inVisualizationMode) {
        super(currentRunData, inVisualizationMode);
    }

    @Override
    public void performSort(int[] arr) {

        mergeSort(arr, 0, arr.length - 1);
    }

    private void mergeSort(int[] arr, int left, int right) {
        if (left < right) {

            int mid = (right - left) / 2 + left;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            merge(arr, left, mid, right);
        }
    }

    private void merge(int[] arr, int left, int mid, int right) {

        int n1 = mid - left + 1, n2 = right - mid;
        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];

        // Copying the elements into the new arrays
        for (int i = 0; i < n1; i++) {
            leftArr[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArr[j] = arr[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            compare(leftArr[i], rightArr[j]);

            if (leftArr[i] <= rightArr[j]) {
                insert(arr, k, leftArr[i]);
                i++;
            } else {
                insert(arr, k, rightArr[j]);
                j++;
            }
            k++;
        }

        // add remainning elements
        while (i < n1) {
            insert(arr, k, leftArr[i]);
            i++;
            k++;
        }
        while (j < n2) {
            insert(arr, k, rightArr[j]);
            j++;
            k++;
        }
    }

}
