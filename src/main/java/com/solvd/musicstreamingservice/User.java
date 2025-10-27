package com.solvd.musicstreamingservice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String email;
    private LocalDate createdDate;
    private LocalDateTime createdDateTime;
    private List<Playlist> playlists;
    private StreamingSession streamingSession;
    private SubscriptionPlan subscriptionPlan;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.createdDate = LocalDate.now();
        this.createdDateTime = LocalDateTime.now();
        this.playlists = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    public StreamingSession getStreamingSession() {
        return streamingSession;
    }

    public void setStreamingSession(StreamingSession streamingSession) {
        this.streamingSession = streamingSession;
    }

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    public void setCreatedDate(LocalDate localDate) {
        this.createdDate = localDate;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
}
