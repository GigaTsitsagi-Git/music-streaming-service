package com.solvd.musicstreamingservice.parser;

import com.solvd.musicstreamingservice.model.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SAXParserHandler extends DefaultHandler {

    private User currentUser;
    private Playlist currentPlaylist;
    private PlaylistItem currentPlaylistItem;
    private Song currentSong;
    private StreamingSession currentStreamingSession;
    private SubscriptionPlan currentSubscriptionPlan;

    private String currentElement;
    private StringBuilder text;
    private List<String> playedSongTitles;

    private User rootUser;

    @Override
    public void startDocument() throws SAXException {
        text = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = qName;
        text.setLength(0);

        switch (qName) {
            case "playlist":
                if (currentUser != null) {
                    currentPlaylist = new Playlist("", currentUser.getUsername());
                }
                break;
            case "playlistitem":
                currentPlaylistItem = new PlaylistItem(null, "", 0);
                break;
            case "song":
                currentSong = new Song("", new Artist(""), new Album("", new Artist("")));
                break;
            case "currentSong":
                currentSong = new Song("", new Artist(""), new Album("", new Artist("")));
                break;
            case "streamingsession":
                currentStreamingSession = new StreamingSession("");
                playedSongTitles = new ArrayList<>();
                break;
            case "subscriptionplan":
                currentSubscriptionPlan = new SubscriptionPlan("", 0.0);
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        text.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        String content = text.toString().trim();

        switch (qName) {
            case "username":
                if (currentUser == null) {
                    currentUser = new User(content, "");
                } else {
                    currentUser.setUsername(content);
                }
                break;
            case "email":
                if (currentUser != null) {
                    currentUser.setEmail(content);
                }
                break;
            case "createdDate":
                if (currentUser != null) {
                    currentUser.setCreatedDate(LocalDate.parse(content));
                }
                break;
            case "createdDateTime":
                if (currentUser != null) {
                    currentUser.setCreatedDateTime(LocalDateTime.parse(content));
                }
                break;

            case "name":
                if (currentSubscriptionPlan != null) {
                    currentSubscriptionPlan.setName(content);
                } else if (currentPlaylist != null) {
                    currentPlaylist.setName(content);
                }
                break;
            case "playlist":
                if (currentPlaylist != null && currentUser != null) {
                    currentUser.addPlaylist(currentPlaylist);
                    currentPlaylist = null;
                }
                break;

            case "title":
                if (currentSong != null) {
                    currentSong.setTitle(content);
                }
                break;
            case "artist":
                if (currentSong != null && currentSong.getArtist() != null) {
                    currentSong.getArtist().setName(content);
                }
                break;
            case "album":
                if (currentSong != null && currentSong.getAlbum() != null) {
                    currentSong.getAlbum().setTitle(content);
                    // Also set the artist on the album
                    if (currentSong.getArtist() != null) {
                        currentSong.getAlbum().setArtist(currentSong.getArtist());
                    }
                }
                break;

            case "position":
                if (currentPlaylistItem != null) {
                    currentPlaylistItem.setPosition(Integer.parseInt(content));
                }
                break;
            case "song":
                if (currentPlaylistItem != null && currentSong != null) {
                    // Attach song to playlist item
                    currentPlaylistItem.setSong(currentSong);
                    if (currentPlaylist != null) {
                        currentPlaylistItem.setPlaylistName(currentPlaylist.getName());
                    }
                }
                break;
            case "playlistitem":
                if (currentPlaylist != null && currentPlaylistItem != null) {
                    currentPlaylist.addSong(currentPlaylistItem);
                }
                currentPlaylistItem = null;
                break;

            case "currentSong":
                if (currentStreamingSession != null && currentSong != null) {
                    currentStreamingSession.setCurrentSong(currentSong);
                }
                break;
            case "playedSong":
                if (currentStreamingSession != null && !content.isEmpty()) {
                    playedSongTitles.add(content);
                }
                break;
            case "streamingsession":
                if (currentStreamingSession != null && playedSongTitles != null) {
                    // Convert played song titles to Song objects
                    for (String title : playedSongTitles) {
                        Song playedSong = new Song(title, new Artist(""), new Album("", new Artist("")));
                        currentStreamingSession.addPlayedSong(playedSong);
                    }
                    if (currentUser != null) {
                        currentUser.setStreamingSession(currentStreamingSession);
                    }
                }
                currentStreamingSession = null;
                playedSongTitles = null;
                break;

            case "price":
                if (currentSubscriptionPlan != null) {
                    currentSubscriptionPlan.setPrice(Double.parseDouble(content));
                }
                break;
            case "subscriptionplan":
                if (currentUser != null && currentSubscriptionPlan != null) {
                    currentUser.setSubscriptionPlan(currentSubscriptionPlan);
                }
                currentSubscriptionPlan = null;
                break;
        }

        currentElement = null;
        text.setLength(0);
    }

    @Override
    public void endDocument() throws SAXException {
        rootUser = currentUser;
    }

    public User getResult() {
        return rootUser;
    }
}
