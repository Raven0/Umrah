package com.birutekno.umrah.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by No Name on 8/7/2017.
 */

public class Payment extends RealmObject {

    @PrimaryKey
    private int id;
    private String desc;
    private String value;
    private String status;

    public Payment() {
    }

    public Payment(int id, String desc, String value, String status) {
        this.id = id;
        this.desc = desc;
        this.value = value;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
