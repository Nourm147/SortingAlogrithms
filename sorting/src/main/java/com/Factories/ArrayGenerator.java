package com.Factories;

import java.util.Random;

public class ArrayGenerator {

    private ArrayGenerator() {
    }

    public static int[] generateArray(int size, String type) {
        int n = size;
        int[] arr = new int[n];

        switch (type) {
            case "Random":
                Random rand = new java.util.Random();
                for (int i = 0; i < n; i++) {
                    arr[i] = rand.nextInt(n);
                }
                break;
            case "Sorted":
                for (int i = 0; i < n; i++) {
                    arr[i] = i + 1;
                }
                break;
            case "Reversed":
                for (int i = 0; i < n; i++) {
                    arr[i] = n - i;
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown array type: " + type);
        }
        return arr;
    }

}
