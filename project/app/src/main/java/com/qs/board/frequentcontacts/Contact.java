package com.qs.board.frequentcontacts;

public class Contact {

    private String name, id, thumbnail;

    String getContactName() {
        return name;
    }

    public void setContactName(String name) {
        this.name = name;
    }

    String getContactId() {
        return id;
    }

    public void setContactId(String id) {
        this.id = id;
    }

    String getContactThumbnail() {
        return thumbnail;
    }

    public void setContactThumbnail(String thumbnail) {

        this.thumbnail = thumbnail;
    }
}
