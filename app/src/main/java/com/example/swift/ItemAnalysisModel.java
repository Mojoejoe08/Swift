package com.example.swift;

public class ItemAnalysisModel {

    int item_no;
    String values;
    String remarks;

    public ItemAnalysisModel(int item_no, String values, String remarks) {
        this.item_no = item_no;
        this.values = values;
        this.remarks = remarks;
    }

    public int getItem_no() {
        return item_no;
    }

    public void setItem_no(int item_no) {
        this.item_no = item_no;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
