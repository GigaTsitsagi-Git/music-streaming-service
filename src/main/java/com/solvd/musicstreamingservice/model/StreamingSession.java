package com.solvd.musicstreamingservice.model;

import java.util.ArrayList;
import java.util.List;

public class StreamingSession {

    private String username;
    private Song currentSong;
    private List<Song> playedSongs;

    public StreamingSession(String username) {
        this.username = username;
        this.playedSongs = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(Song currentSong) {
        this.currentSong = currentSong;
    }

    public List<Song> getPlayedSongs() {
        return playedSongs;
    }

    public void addPlayedSong(Song song) {
        playedSongs.add(song);
    }
}
