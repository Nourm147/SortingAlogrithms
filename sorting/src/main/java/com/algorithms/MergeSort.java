package com.algorithms;

import java.util.HashMap;
import java.util.Map;

import com.Data.SimpleOpData;
import com.Data.SimpleRunData;

import lombok.Getter;

@Getter
class Element {

    private final int value;
    private final int originalIndex;

    public Element(int value, int originalIndex) {
        this.value = value;
        this.originalIndex = originalIndex;
    }
}

public class MergeSort extends Sort {

    // this implementation add the complexity of retrieving the Elements from the Map
    private Map<Element, Integer> positionMap;

    public MergeSort(SimpleRunData currentRunData, boolean inVisualizationMode) {
        super(currentRunData, inVisualizationMode);
    }

    @Override
    public void performSort(int[] arr) {
        Element[] elements = new Element[arr.length];
        positionMap = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            elements[i] = new Element(arr[i], i);
            positionMap.put(elements[i], i);
        }

        mergeSort(elements, 0, arr.length - 1);

        // ReAssign the elements into main array
        for (int i = 0; i < arr.length; i++) {
            arr[i] = elements[i].getValue();
            System.out.println(arr[i]);
        }
    }

    private void mergeSort(Element[] arr, int left, int right) {
        if (left < right) {

            int mid = (right - left) / 2 + left;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            merge(arr, left, mid, right);
        }
    }

    private void merge(Element[] arr, int left, int mid, int right) {

        int n1 = mid - left + 1, n2 = right - mid;
        Element[] leftArr = new Element[n1];
        Element[] rightArr = new Element[n2];

        // Copying the elements into the new arrays
        for (int i = 0; i < n1; i++) {
            leftArr[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArr[j] = arr[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            // Compare using original indices
            compare(leftArr[i].getOriginalIndex(), rightArr[j].getOriginalIndex());

            if (leftArr[i].getValue() <= rightArr[j].getValue()) {
                // Find where this element currently is
                int currentPos = positionMap.get(leftArr[i++]);
                if (currentPos != k) {
                    swap(arr, currentPos, k);
                }
            } else {
                int currentPos = positionMap.get(rightArr[j++]);
                if (currentPos != k) {
                    swap(arr, currentPos, k);
                }
            }
            k++;
        }

        // add remainning elements
        while (i < n1) {
            int currentPos = positionMap.get(leftArr[i++]);
            if (currentPos != k) {
                swap(arr, currentPos, k);
            }
            k++;
        }
        while (j < n2) {
            int currentPos = positionMap.get(rightArr[j++]);
            if (currentPos != k) {
                swap(arr, currentPos, k);
            }
            k++;
        }
    }

    private void swap(Element[] arr, int i, int j) {
        // Update position map
        positionMap.put(arr[i], j);
        positionMap.put(arr[j], i);

        Element temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

        // Visualize using original indices 
        onSwapEvent.invoke(new SimpleOpData(arr[i].getOriginalIndex(), arr[j].getOriginalIndex()));
        handleVisualizationDelay();
    }

}
