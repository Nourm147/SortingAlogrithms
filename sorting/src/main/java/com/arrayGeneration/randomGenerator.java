package com.arrayGeneration;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class randomGenerator implements arrayGenerator{

    @Override
    public int[] generate(int size) {
        int[] result = new int[size];
        Arrays.setAll(result, i -> ThreadLocalRandom.current().nextInt(size));
        
        return result;
    }
    
}
