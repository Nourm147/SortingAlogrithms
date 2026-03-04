package com.Enums;

public enum ArrayType {
    RANDOM("Random"),
    SORTED("Sorted"),
    REVERSED("Reversed");

    private final String displayName;

    private ArrayType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
