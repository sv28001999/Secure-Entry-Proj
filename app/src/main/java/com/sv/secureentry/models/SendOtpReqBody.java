package com.sv.secureentry.models;

public class SendOtpReqBody {
    public String email;

    public SendOtpReqBody() {
    }

    public SendOtpReqBody(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
