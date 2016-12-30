package com.example.jinfei.myapplication.listener;

public class DisposeDataHandler {

    public DisposeDataListener listener;
    public Class<?> clazz;

    public DisposeDataHandler(DisposeDataListener listener) {
        this.listener = listener;
    }

    public DisposeDataHandler(DisposeDataListener listener, Class<?> clazz) {
        this.listener = listener;
        this.clazz = clazz;
    }
}
