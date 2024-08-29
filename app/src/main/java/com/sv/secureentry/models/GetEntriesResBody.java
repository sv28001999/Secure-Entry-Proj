package com.sv.secureentry.models;

import java.util.ArrayList;
import java.util.Date;

public class GetEntriesResBody {
    public boolean isSuccess;
    public int totalRecords;
    public int currentPage;
    public int totalPages;
    public ArrayList<Datum> data;

    public GetEntriesResBody() {
    }

    public GetEntriesResBody(boolean isSuccess, int totalRecords, int currentPage, int totalPages, ArrayList<Datum> data) {
        this.isSuccess = isSuccess;
        this.totalRecords = totalRecords;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.data = data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

    public class Datum {
        public String _id;
        public String personName;
        public String mobileNumber;
        public String memberRoomNo;
        public String work;
        public String place;
        public String imageUrl;
        public boolean isEntered;
        public String comment;
        public String orgUniqueCode;
        public Date dateIn;
        public int __v;

        public Datum() {
        }

        public Datum(String _id, String personName, String mobileNumber, String memberRoomNo, String work, String place, String imageUrl, boolean isEntered, String comment, String orgUniqueCode, Date dateIn, int __v) {
            this._id = _id;
            this.personName = personName;
            this.mobileNumber = mobileNumber;
            this.memberRoomNo = memberRoomNo;
            this.work = work;
            this.place = place;
            this.imageUrl = imageUrl;
            this.isEntered = isEntered;
            this.comment = comment;
            this.orgUniqueCode = orgUniqueCode;
            this.dateIn = dateIn;
            this.__v = __v;
        }

        public Datum(String personName, String mobileNumber, String memberRoomNo, String work, String place) {
            this.personName = personName;
            this.mobileNumber = mobileNumber;
            this.memberRoomNo = memberRoomNo;
            this.work = work;
            this.place = place;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
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
}
