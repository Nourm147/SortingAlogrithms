package com.Factories;

import com.Algorithms.BubbleSort;
import com.Algorithms.HeapSort;
import com.Algorithms.InsertionSort;
import com.Algorithms.MergeSort;
import com.Algorithms.QuickSort;
import com.Algorithms.SelectionSort;
import com.Algorithms.Sort;
import com.Data.SimpleRunData;
import com.Enums.SortingType;

public class SortingFactory {

    private SortingFactory() {
    }

    public static Sort createSorter(SortingType type, SimpleRunData runData, boolean inVisualizationMode) {
        switch (type) {
            case SELECTION:
                return new SelectionSort(runData, inVisualizationMode);
            case INSERTION:
                return new InsertionSort(runData, inVisualizationMode);
            case BUBBLE:
                return new BubbleSort(runData, inVisualizationMode);
            case MERGE:
                return new MergeSort(runData, inVisualizationMode);
            case HEAP:
                return new HeapSort(runData, inVisualizationMode);
            case QUICK:
                return new QuickSort(runData, inVisualizationMode);
            default:
                throw new IllegalArgumentException("Unknown sorting type: " + type);
        }
    }

}
