package com.sv.secureentry.models;

public class VerifyOtpResBody {

    public boolean isSuccess;
    public String msg;

    public VerifyOtpResBody() {
    }

    public VerifyOtpResBody(boolean isSuccess, String msg) {
        this.isSuccess = isSuccess;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
