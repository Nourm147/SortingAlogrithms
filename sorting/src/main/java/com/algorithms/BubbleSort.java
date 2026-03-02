package com.algorithms;

import com.Data.SimpleOpData;

public class BubbleSort extends Sort {

    @Override
    public void sort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                onCompareEvent.invoke(new SimpleOpData(j, j + 1));
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    onSwapEvent.invoke(new SimpleOpData(j, j + 1));
                }
                try {
                    Thread.sleep((long) (duration / spead));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

}
