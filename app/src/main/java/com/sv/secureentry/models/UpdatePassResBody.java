package com.sv.secureentry.models;

public class UpdatePassResBody {
    public boolean isSuccess;
    public String msg;

    public UpdatePassResBody() {
    }

    public UpdatePassResBody(boolean isSuccess, String msg) {
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
