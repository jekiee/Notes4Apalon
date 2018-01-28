package com.example.jek.notes4apalon.model;

import io.realm.RealmObject;

public class Note extends RealmObject {
    private String id;
    private String title;
    private String body;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String noteTitle) {
        this.title = noteTitle;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String noteBody) {
        this.body = noteBody;
    }
}
