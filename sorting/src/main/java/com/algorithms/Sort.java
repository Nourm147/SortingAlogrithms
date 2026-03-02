package com.algorithms;

import com.Data.DataBlock;
import com.Data.SimpleOpData;
import com.events.Event;

public abstract class Sort {
    public Event<SimpleOpData> onCompareEvent;
    public Event<SimpleOpData> onSwapEvent;
    public Event<DataBlock> onEndEvent;

    public abstract void sort(int[] arr);
}
