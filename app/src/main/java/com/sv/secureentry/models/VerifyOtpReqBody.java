package com.sv.secureentry.models;

public class VerifyOtpReqBody {
    public String email;
    public String otp;
    public boolean changePasswordFlag;

    public VerifyOtpReqBody() {
    }

    public VerifyOtpReqBody(String email, String otp, boolean changePasswordFlag) {
        this.email = email;
        this.otp = otp;
        this.changePasswordFlag = changePasswordFlag;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public boolean isChangePasswordFlag() {
        return changePasswordFlag;
    }

    public void setChangePasswordFlag(boolean changePasswordFlag) {
        this.changePasswordFlag = changePasswordFlag;
    }
}
