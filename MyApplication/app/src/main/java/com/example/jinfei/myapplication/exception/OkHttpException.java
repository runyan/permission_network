package com.example.jinfei.myapplication.exception;

public class OkHttpException extends Exception {

    private int ecode;
    private Object emsg;

    public OkHttpException(int ecode, Object emsg) {
        this.ecode = ecode;
        this.emsg = emsg;
    }

    public int getEcode() {
        return ecode;
    }

    public Object getEmsg() {
        return emsg;
    }

}
