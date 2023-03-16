package com.QLTC.Module;

public class Detail {

    public String mType;
    public String mCategory;
    public String mDetail;
    public String mTime;
    public String mMoney;

    public Detail(String mType, String mCategory, String mDetail, String mTime, String mMoney) {
        this.mType = mType;
        this.mCategory = mCategory;
        this.mDetail = mDetail;
        this.mTime = mTime;
        this.mMoney = mMoney;
    }

    public Detail(){

    }


    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getmDetail() {
        return mDetail;
    }

    public void setmDetail(String mDetail) {
        this.mDetail = mDetail;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmMoney() {
        return mMoney;
    }

    public void setmMoney(String mMoney) {
        this.mMoney = mMoney;
    }

}
