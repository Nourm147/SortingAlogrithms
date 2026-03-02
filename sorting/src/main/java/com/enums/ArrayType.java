package com.enums;

public enum ArrayType {
    RANDOM("Random"),
    SORTED("Sorted"),
    REVERSED("Reversed");

    private final String displayName;

    ArrayType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
