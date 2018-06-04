package com.birutekno.umrah.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by No Name on 7/31/2017.
 */

public class Kalkulasi extends RealmObject {

    @PrimaryKey
    private int id;

    public Kalkulasi() {
    }

    public Kalkulasi(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
