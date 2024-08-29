package com.sv.secureentry.models;

public class LoginResBody {
    public boolean isSuccess;
    public String msg;
    public Data data;

    public LoginResBody() {
    }

    public LoginResBody(boolean isSuccess, String msg, Data data) {
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
        public String email;
        public String username;
        public boolean isActive;
        public String roleType;
        public String orgUniqueCode;
        public String token;

        public Data() {
        }

        public Data(String email, String username, boolean isActive, String roleType, String orgUniqueCode, String token) {
            this.email = email;
            this.username = username;
            this.isActive = isActive;
            this.roleType = roleType;
            this.orgUniqueCode = orgUniqueCode;
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getOrgUniqueCode() {
            return orgUniqueCode;
        }

        public void setOrgUniqueCode(String orgUniqueCode) {
            this.orgUniqueCode = orgUniqueCode;
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

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }

        public String getRoleType() {
            return roleType;
        }

        public void setRoleType(String roleType) {
            this.roleType = roleType;
        }
    }
}
