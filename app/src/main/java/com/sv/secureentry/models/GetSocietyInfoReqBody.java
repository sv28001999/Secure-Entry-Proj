package com.sv.secureentry.models;

public class GetSocietyInfoReqBody {
    private String orgUniqueCode;

    public GetSocietyInfoReqBody() {
    }

    public GetSocietyInfoReqBody(String orgUniqueCode) {
        this.orgUniqueCode = orgUniqueCode;
    }

    public String getOrgUniqueCode() {
        return orgUniqueCode;
    }

    public void setOrgUniqueCode(String orgUniqueCode) {
        this.orgUniqueCode = orgUniqueCode;
    }
}
