package com.Data;

import java.time.Duration;

import com.enums.ArraySize;
import com.enums.ArrayType;
import com.enums.SortingType;

import lombok.Data;

@Data
public class DataBlock {

    private SortingType sortingAlgorithmName;
    private ArraySize arraySize;
    private ArrayType arrayGenerationMode;

    private long numberOfRuns;
    private Duration averageRuntime;
    private Duration minRuntime;
    private Duration maxRuntime;
    private long comparisonsNumber;
    private long interchangesNumber;
}
