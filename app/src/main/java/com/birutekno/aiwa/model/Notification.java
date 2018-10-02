package com.birutekno.aiwa.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Aldio Firando on 8/15/2017.
 */

public class Notification extends RealmObject {

    @PrimaryKey
    private int id;

    public Notification() {
    }

    public Notification(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
