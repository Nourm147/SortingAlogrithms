package com.Data;

import java.time.Duration;

import com.Enums.ArraySize;
import com.Enums.ArrayType;
import com.Enums.SortingType;

import lombok.Data;

@Data
public class DataBlock {

    private SortingType sortingAlgorithmName;
    private ArraySize arraySize;
    private ArrayType arrayGenerationMode;

    private Duration averageRuntime;
    private Duration minRuntime;
    private Duration maxRuntime;

    private long numberOfRuns;
    private long comparisonsNumber;
    private long interchangesNumber;
}
