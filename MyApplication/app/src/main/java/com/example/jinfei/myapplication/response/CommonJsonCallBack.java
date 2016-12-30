package com.example.jinfei.myapplication.response;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.example.jinfei.myapplication.exception.OkHttpException;
import com.example.jinfei.myapplication.listener.DisposeDataHandler;
import com.example.jinfei.myapplication.listener.DisposeDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class CommonJsonCallBack implements Callback {

    protected final String RESULT_CODE = "status";
    protected final int RESULT_CODE_SUCCESS = 1;
    protected final String ERROR_MSG = "message";
    protected final String EMPTY_MSG = "";

    protected final int NETWORK_ERROR = -1;
    protected final int JSON_ERROR = -2;
    protected final int OTHER_ERROR = -3;

    private DisposeDataListener listener;
    private Class<?> clazz;
    private Handler mDeliverHandler;

    public CommonJsonCallBack(DisposeDataHandler handler) {
        this.listener = handler.listener;
        this.clazz = handler.clazz;
        mDeliverHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(Call call, IOException e) {
        notifyFailure(e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String result = response.body().string();
        handleResponse(result);
    }

    private void handleResponse(String result) {
        if(TextUtils.isEmpty(result)) {
            notifyFailure(new OkHttpException(NETWORK_ERROR, EMPTY_MSG));
            return ;
        }
        try {
            JSONObject obj = new JSONObject(result);
            if(obj.has(RESULT_CODE)) {
                if(obj.optInt(RESULT_CODE) == RESULT_CODE_SUCCESS) {
                    if(null == clazz) {
                        notifySuccess(obj);
                    } else {
                        Object clazzObj;
                        try {
                            clazzObj = JSON.parseObject(result, clazz);
                            if(null == clazzObj) {
                                notifyFailure(new OkHttpException(JSON_ERROR, EMPTY_MSG));
                            } else {
                                notifySuccess(clazzObj);
                            }
                        } catch (Exception e) {
                            notifyFailure(new OkHttpException(JSON_ERROR, EMPTY_MSG));
                        }
                    }
                }
            }
        } catch (Exception e) {
            if(e instanceof JSONException){
                notifySuccess(result);
            }else {
                notifyFailure(new OkHttpException(OTHER_ERROR, EMPTY_MSG));
            }
        }
    }

    private void notifySuccess(final Object obj) {
        mDeliverHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(obj);
            }
        });
    }

    private void notifyFailure(final Object obj) {
        mDeliverHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onFailure(obj);
            }
        });
    }
}
