package com.arrayGeneration;

import java.util.Arrays;

public class sortedGenerator implements arrayGenerator {

    @Override
    public int[] generate(int size) {
        int[] result = new int[size];
        Arrays.setAll(result, i -> i);
        
        return result;
    }
    
}
