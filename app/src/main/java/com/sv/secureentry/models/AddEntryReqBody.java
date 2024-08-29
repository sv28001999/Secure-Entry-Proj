package com.sv.secureentry.models;

public class AddEntryReqBody {
    public String personName;
    public String mobileNumber;
    public String work;
    public String place;
    public String memberRoomNo;
    public String imageUrl;
    public String orgUniqueCode;

    public AddEntryReqBody() {
    }

    public AddEntryReqBody(String personName, String mobileNumber, String work, String place, String memberRoomNo, String imageUrl, String orgUniqueCode) {
        this.personName = personName;
        this.mobileNumber = mobileNumber;
        this.work = work;
        this.place = place;
        this.memberRoomNo = memberRoomNo;
        this.imageUrl = imageUrl;
        this.orgUniqueCode = orgUniqueCode;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getMemberRoomNo() {
        return memberRoomNo;
    }

    public void setMemberRoomNo(String memberRoomNo) {
        this.memberRoomNo = memberRoomNo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOrgUniqueCode() {
        return orgUniqueCode;
    }

    public void setOrgUniqueCode(String orgUniqueCode) {
        this.orgUniqueCode = orgUniqueCode;
    }
}
