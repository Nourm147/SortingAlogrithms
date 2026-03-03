package com.Data;

import java.time.Duration;
import java.time.Instant;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class SimpleRunData {

    private Duration runTimeDuration;
    private long comparisonsNumber;
    private long interchangesNumber;

    @Getter(AccessLevel.NONE)
    private Instant start;

    public SimpleRunData() {
        comparisonsNumber = 0;
        interchangesNumber = 0;
    }

    public void startRun() {
        start = Instant.now();
    }

    public void endRun() {
        if (start != null) {
            runTimeDuration = Duration.between(start, Instant.now());
        }
    }

    public void increaseComparisons() {
        comparisonsNumber++;
    }

    public void increaseinterchanges() {
        interchangesNumber++;
    }

}
