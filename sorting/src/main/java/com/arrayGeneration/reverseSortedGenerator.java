package com.arrayGeneration;

import java.util.Arrays;

public class reverseSortedGenerator implements arrayGenerator {

    @Override
    public int[] generate(int size) {
        int[] result = new int[size];
        Arrays.setAll(result, i -> size - i);
        
        return result;
    }
    
}
