package com.example.java_project.measurementsexplorer;

public class OrderDetails {

    private int orderId;
    private String mobile;
    private String dress;
    private int bill;
    private String worker;
    private String rstatus;
    public OrderDetails(int orderId, String mobile, String dress, int bill, String worker, String rstatus) {
        this.orderId = orderId;
        this.mobile = mobile;
        this.dress = dress;
        this.bill = bill;
        this.worker = worker;
        this.rstatus = rstatus;
    }
    public int getOrderId() {
        return orderId;
    }
    public String getMobile() {
        return mobile;
    }
    public String getDress() {
        return dress;
    }
    public int getBill() {
        return bill;
    }
    public String getWorker() {
        return worker;
    }
    public String getRstatus() {
        return rstatus;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public void setDress(String dress) {
        this.dress = dress;
    }
    public void setBill(int bill) {
        this.bill = bill;
    }
    public void setWorker(String worker) {
        this.worker = worker;
    }
    public void setRstatus(String rstatus) {
        this.rstatus = rstatus;
    }
}
