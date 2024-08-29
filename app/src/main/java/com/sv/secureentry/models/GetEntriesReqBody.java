package com.sv.secureentry.models;

public class GetEntriesReqBody {
    public String orgUniqueCode;

    public GetEntriesReqBody() {
    }

    public GetEntriesReqBody(String orgUniqueCode) {
        this.orgUniqueCode = orgUniqueCode;
    }

    public String getOrgUniqueCode() {
        return orgUniqueCode;
    }

    public void setOrgUniqueCode(String orgUniqueCode) {
        this.orgUniqueCode = orgUniqueCode;
    }
}
