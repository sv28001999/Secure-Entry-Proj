package com.sv.secureentry.models;

import java.util.Date;

public class AddEntryResBody {
    public String personName;
    public String mobileNumber;
    public String memberRoomNo;
    public String work;
    public String place;
    public String imageUrl;
    public boolean isEntered;
    public String comment;
    public String orgUniqueCode;
    public String _id;
    public Date dateIn;
    public int __v;

    public AddEntryResBody() {
    }

    public AddEntryResBody(String personName, String mobileNumber, String memberRoomNo, String work, String place, String imageUrl, boolean isEntered, String comment, String orgUniqueCode, String _id, Date dateIn, int __v) {
        this.personName = personName;
        this.mobileNumber = mobileNumber;
        this.memberRoomNo = memberRoomNo;
        this.work = work;
        this.place = place;
        this.imageUrl = imageUrl;
        this.isEntered = isEntered;
        this.comment = comment;
        this.orgUniqueCode = orgUniqueCode;
        this._id = _id;
        this.dateIn = dateIn;
        this.__v = __v;
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

    public String getMemberRoomNo() {
        return memberRoomNo;
    }

    public void setMemberRoomNo(String memberRoomNo) {
        this.memberRoomNo = memberRoomNo;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isEntered() {
        return isEntered;
    }

    public void setEntered(boolean entered) {
        isEntered = entered;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOrgUniqueCode() {
        return orgUniqueCode;
    }

    public void setOrgUniqueCode(String orgUniqueCode) {
        this.orgUniqueCode = orgUniqueCode;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Date getDateIn() {
        return dateIn;
    }

    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}
