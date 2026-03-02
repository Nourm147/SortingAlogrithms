package com.algorithms;

import com.Data.DataBlock;
import com.Data.SimpleOpData;
import com.events.Event;

public abstract class Sort {

    protected Event<SimpleOpData> onCompareEvent;
    protected Event<SimpleOpData> onSwapEvent;
    protected Event<DataBlock> onEndEvent;
    protected double duration = 50; // time to sleep in ms
    protected double spead = 1;

    public abstract void sort(int[] arr);
}
