package com.solvd.musicstreamingservice;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String name;
    private String ownerUsername;
    private List<PlaylistItem> songs;

    public Playlist(String name, String ownerUsername) {
        this.name = name;
        this.ownerUsername = ownerUsername;
        this.songs = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public List<PlaylistItem> getSongs() {
        return songs;
    }

    public void addSong(PlaylistItem song) {
        songs.add(song);
    }
}
