package com.sv.secureentry.models;

public class RegistrationReqBody {
    public String fullName;
    public String email;
    public String username;
    public String accountRole;
    public SocietyAddress societyAddress;
    public String mobileNumber;
    public String password;
    public String orgUniqueCode;

    public RegistrationReqBody() {
    }

    public RegistrationReqBody(String fullName, String email, String username, String accountRole, SocietyAddress societyAddress, String mobileNumber, String password) {
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.accountRole = accountRole;
        this.societyAddress = societyAddress;
        this.mobileNumber = mobileNumber;
        this.password = password;
    }

    public RegistrationReqBody(String fullName, String email, String username, String accountRole, SocietyAddress societyAddress, String mobileNumber, String password, String orgUniqueCode) {
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.accountRole = accountRole;
        this.societyAddress = societyAddress;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.orgUniqueCode = orgUniqueCode;
    }

    public String getFirstName() {
        return fullName;
    }

    public void setFirstName(String firstName) {
        this.fullName = firstName;
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

    public String getAccountRole() {
        return accountRole;
    }

    public void setAccountRole(String accountRole) {
        this.accountRole = accountRole;
    }

    public SocietyAddress getSocietyAddress() {
        return societyAddress;
    }

    public void setSocietyAddress(SocietyAddress societyAddress) {
        this.societyAddress = societyAddress;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOrgUniqueCode() {
        return orgUniqueCode;
    }

    public void setOrgUniqueCode(String orgUniqueCode) {
        this.orgUniqueCode = orgUniqueCode;
    }

    public static class SocietyAddress {
        public String state;
        public String city;
        public String street;
        public String societyName;
        public int pinCode;

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
