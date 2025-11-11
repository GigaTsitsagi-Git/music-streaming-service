package com.solvd.musicstreamingservice.model;

public class Album {

    private String title;
    private Artist artist;

    public Album(String title, Artist artist) {
        this.title = title;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

}
