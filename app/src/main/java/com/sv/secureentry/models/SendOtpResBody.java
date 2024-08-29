package com.sv.secureentry.models;

public class SendOtpResBody {
    public boolean isSuccess;
    public Data data;

    public SendOtpResBody() {
    }

    public SendOtpResBody(boolean isSuccess, Data data) {
        this.isSuccess = isSuccess;
        this.data = data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        public String msg;

        public Data(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

}
