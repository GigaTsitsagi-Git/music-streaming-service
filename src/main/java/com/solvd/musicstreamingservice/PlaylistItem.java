package com.solvd.musicstreamingservice;

public class PlaylistItem {
    private Song song;
    private String name;
    private int position;

    public PlaylistItem(Song song, String playlistName, int position) {
        this.song = song;
        this.name = playlistName;
        this.position = position;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
