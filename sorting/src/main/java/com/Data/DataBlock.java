package com.Data;

import com.Enums.SortingType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataBlock {

    private SortingType sortingAlgorithmName;
    private int arraySize;
    private String arrayGenerationMode;

    private double averageRuntime;
    private double minRuntime;
    private double maxRuntime;

    private long numberOfRuns;
    private long comparisonsNumber;
    private long interchangesNumber;
}
