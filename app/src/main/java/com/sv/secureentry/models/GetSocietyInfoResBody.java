package com.sv.secureentry.models;

public class GetSocietyInfoResBody {
    public boolean isSuccess;
    public String msg;
    public Data data;

    public GetSocietyInfoResBody() {
    }

    public GetSocietyInfoResBody(boolean isSuccess, String msg, Data data) {
        this.isSuccess = isSuccess;
        this.msg = msg;
        this.data = data;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        public String fullName;
        public SocietyAddress societyAddress;

        public Data() {
        }

        public Data(String fullName, SocietyAddress societyAddress) {
            this.fullName = fullName;
            this.societyAddress = societyAddress;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public SocietyAddress getSocietyAddress() {
            return societyAddress;
        }

        public void setSocietyAddress(SocietyAddress societyAddress) {
            this.societyAddress = societyAddress;
        }
    }

    public class SocietyAddress {
        public String state;
        public String city;
        public String street;
        public String societyName;
        public int pinCode;

        public SocietyAddress() {
        }

        public SocietyAddress(String state, String city, String street, String societyName, int pinCode) {
            this.state = state;
            this.city = city;
            this.street = street;
            this.societyName = societyName;
            this.pinCode = pinCode;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getSocietyName() {
            return societyName;
        }

        public void setSocietyName(String societyName) {
            this.societyName = societyName;
        }

        public int getPinCode() {
            return pinCode;
        }

        public void setPinCode(int pinCode) {
            this.pinCode = pinCode;
        }
    }

}