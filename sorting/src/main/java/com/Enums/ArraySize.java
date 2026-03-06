package com.Enums;

public enum ArraySize {

    EXTRA_TINY(20),
    TINY(50),
    EXTRA_SMALL(100),
    SMALL(500),
    MEDIUM(1000),
    LARGE(5000),
    EXTRA_LARGE(7500),
    HUGE(10000);

    private final int size;

    private ArraySize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public int getValue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
