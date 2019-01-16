package com.example.qrcode.bean;

/**
 * 快递动态
 */
public class ExpressDynamic {
    public String expressNo;
    public String date;
    public String dynamic;

    public ExpressDynamic(String expressNo, String date, String dynamic) {
        this.expressNo = expressNo;
        this.date = date;
        this.dynamic = dynamic;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDynamic() {
        return dynamic;
    }

    public void setDynamic(String dynamic) {
        this.dynamic = dynamic;
    }
}
