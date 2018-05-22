package com.birutekno.umrah.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by No Name on 04/08/2017.
 */

public class Schedule extends RealmObject {

    @PrimaryKey
    private int id;
    private String Subject;
    private String room;
    private String time;

    public Schedule() {
    }

    public Schedule(int id, String subject, String room, String time) {
        this.id = id;
        Subject = subject;
        this.room = room;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
