package com.sv.secureentry.models;

public class RegistrationResBody {

    public boolean isSuccess;
    public String msg;
    public Data data;

    public RegistrationResBody() {
    }

    public RegistrationResBody(boolean isSuccess, String msg, Data data) {
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
        public String email;
        public String username;
        public String mobileNumber;
        public SocietyAddress societyAddress;
        public String orgUniqueCode;
        public String roleType;

        public Data() {
        }

        public Data(String fullName, String email, String username, String mobileNumber, SocietyAddress societyAddress, String orgUniqueCode, String roleType) {
            this.fullName = fullName;
            this.email = email;
            this.username = username;
            this.mobileNumber = mobileNumber;
            this.societyAddress = societyAddress;
            this.orgUniqueCode = orgUniqueCode;
            this.roleType = roleType;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public SocietyAddress getSocietyAddress() {
            return societyAddress;
        }

        public void setSocietyAddress(SocietyAddress societyAddress) {
            this.societyAddress = societyAddress;
        }

        public String getOrgUniqueCode() {
            return orgUniqueCode;
        }

        public void setOrgUniqueCode(String orgUniqueCode) {
            this.orgUniqueCode = orgUniqueCode;
        }

        public String getRoleType() {
            return roleType;
        }

        public void setRoleType(String roleType) {
            this.roleType = roleType;
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
