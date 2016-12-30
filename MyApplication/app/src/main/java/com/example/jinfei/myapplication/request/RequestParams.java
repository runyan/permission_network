package com.example.jinfei.myapplication.request;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestParams {

    ConcurrentHashMap<String, String> urlParams = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, Object> fileParams = new ConcurrentHashMap<>();

    public RequestParams(Map<String, Object> src) {
        if (null != src) {
            for (Map.Entry<String, Object> entry : src.entrySet()) {
                if (entry.getValue() instanceof File) {
                    put(entry.getKey(), entry.getValue());
                } else {
                    put(entry.getKey(), (String) entry.getValue());
                }
            }
        }
    }

    private void put(String key, String value) {
        if (null == key) {
            return;
        }
        urlParams.put(key, value);
    }

    private void put(String key, Object value) {
        fileParams.put(key, value);
    }
}
