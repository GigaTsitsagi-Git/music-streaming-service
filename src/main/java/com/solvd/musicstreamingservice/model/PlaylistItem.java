package com.solvd.musicstreamingservice.model;

public class PlaylistItem {

    private Song song;
    private String playlistName;
    private int position;

    public PlaylistItem(Song song, String playlistName, int position) {
        this.song = song;
        this.playlistName = playlistName;
        this.position = position;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public String getName() {
        return playlistName;
    }

    public void setName(String name) {
        this.playlistName = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }
}
