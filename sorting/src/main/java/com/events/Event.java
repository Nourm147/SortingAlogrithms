package com.events;

import java.util.ArrayList;
import java.util.List;

public class Event<T> {
    
    private List<Observer<T>> listeners = new ArrayList<>();

    public void addListener(Observer<T> listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(Observer<T> listener) {
        listeners.remove(listener);
    }

    public void removeAllListeners(){
        listeners = new ArrayList<>();
    }

    public void invoke(T data) {
        for (Observer<T> listener : listeners) {
            listener.update(data);
        }
    }
}
