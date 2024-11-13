package com.example.java_project.allworker;

public class ProfileBean {
    String wname;
    String address;
    String mobile;
    String splz;

    public ProfileBean(String wname, String address, String mobile, String splz) {
        this.wname = wname;
        this.address = address;
        this.mobile = mobile;
        this.splz = splz;
    }

    public ProfileBean() {
    }

    public String getWname() {
        return wname;
    }

    public void setWname(String email) {
        this.wname = wname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String email) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String email) {
        this.mobile = mobile;
    }

    public String getSplz() {
        return splz;
    }

    public void setSplz(String email) {
        this.splz = splz;
    }
}
