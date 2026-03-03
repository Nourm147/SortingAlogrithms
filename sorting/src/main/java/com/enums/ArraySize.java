package com.enums;

public enum ArraySize {

    TINY(100),
    SMALL(500),
    MEDIUM(1000),
    LARGE(5000),
    EXTRA_LARGE(7500),
    HUGE(10000);

    private final long size;

    ArraySize(long size) {
        this.size = size;
    }

    public long getSize() {
        return size;
    }
}
