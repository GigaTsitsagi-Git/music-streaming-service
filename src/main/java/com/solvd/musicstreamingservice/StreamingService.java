package com.solvd.musicstreamingservice;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.solvd.musicstreamingservice.jaxb.JaxbMusicService;
import com.solvd.musicstreamingservice.jaxb.JaxbPlaylist;
import com.solvd.musicstreamingservice.jaxb.JaxbPlaylistItem;
import com.solvd.musicstreamingservice.jaxb.JaxbSong;
import com.solvd.musicstreamingservice.jaxb.JaxbStreamingSession;
import com.solvd.musicstreamingservice.jaxb.JaxbSubscriptionPlan;
import com.solvd.musicstreamingservice.jaxb.JaxbUser;
import com.solvd.musicstreamingservice.model.*;
import com.solvd.musicstreamingservice.parser.JaxbParser;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StreamingService {

    private final String name;
    private final List<User> users;
    private final List<SubscriptionPlan> subscriptionPlans;
    private final List<Artist> artists;
    private final List<Album> albums;
    private final List<Song> songs;
    private final List<Playlist> playlists;

    public StreamingService(String name) {
        this.name = name;
        this.users = new ArrayList<>();
        this.subscriptionPlans = new ArrayList<>();
        this.artists = new ArrayList<>();
        this.albums = new ArrayList<>();
        this.songs = new ArrayList<>();
        this.playlists = new ArrayList<>();
    }

    public static void main(String[] args) {
        StreamingService service = new StreamingService("MusicStreamingService");
        service.runDemo();
    }

    private void runDemo() {
        parseXmlUser();
        queryJsonWithJsonPath();
    }

    private void parseXmlUser() {
        JaxbParser parser = new JaxbParser();

        try {
            System.out.println("Parsing XML file via JAXB...\n");
            JaxbMusicService root = parser.parse("src/main/resources/music_streaming_data.xml", JaxbMusicService.class);

            if (root == null || root.getUser() == null) {
                throw new IllegalStateException("Failed to parse XML - no user data found!");
            }

            User user = toDomain(root.getUser());
            addUser(user);
            if (user.getSubscriptionPlan() != null) {
                addSubscriptionPlan(user.getSubscriptionPlan());
            }

            System.out.println("✓ XML parsed successfully!");
            System.out.println("\n=== Parsed User Data ===\n");
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Created Date: " + user.getCreatedDate());
            System.out.println("Created DateTime: " + user.getCreatedDateTime());
            System.out.println("\nPlaylists: " + user.getPlaylists().size());

            for (Playlist playlist : user.getPlaylists()) {
                addPlaylist(playlist);
                System.out.println("\n  - " + playlist.getName());
                System.out.println("    Items: " + playlist.getSongs().size());
                for (PlaylistItem item : playlist.getSongs()) {
                    Song song = item.getSong();
                    addSong(song);
                    addArtist(song.getArtist());
                    addAlbum(song.getAlbum());
                    System.out.println("      " + item.getPosition() + ". " + song.getTitle()
                            + " by " + song.getArtist().getName());
                }
            }

            if (user.getStreamingSession() != null) {
                System.out.println("\nStreaming Session:");
                System.out.println("  Current Song: " + user.getStreamingSession().getCurrentSong().getTitle());
                System.out.println("  Played Songs: " + user.getStreamingSession().getPlayedSongs().size());
            }

            if (user.getSubscriptionPlan() != null) {
                System.out.println("\nSubscription Plan:");
                System.out.println("  Name: " + user.getSubscriptionPlan().getName());
                System.out.println("  Price: $" + user.getSubscriptionPlan().getPrice());
            }

        } catch (Exception e) {
            System.err.println("Error parsing XML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void queryJsonWithJsonPath() {
        try (InputStream is = StreamingService.class.getClassLoader().getResourceAsStream("music_streaming_data.json")) {
            if (is == null) {
                throw new RuntimeException("JSON file not found in resources!");
            }

            DocumentContext json = JsonPath.parse(is);

            String username = json.read("$.username", String.class);
            String firstPlaylist = json.read("$.playlists[0].name", String.class);
            String currentSong = json.read("$.streamingSession.currentSong.title", String.class);
            String firstPlayed = json.read("$.streamingSession.playedSongs[0]", String.class);
            Double price = json.read("$.subscriptionPlan.price", Double.class);

            System.out.println("\nParsing JSONPath queries...\n");
            System.out.println("Username: " + username);
            System.out.println("First playlist: " + firstPlaylist);
            System.out.println("Current song: " + currentSong);
            System.out.println("First played song: " + firstPlayed);
            System.out.println("Plan price: $" + price);
        } catch (Exception e) {
            System.err.println("JSONPath Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<SubscriptionPlan> getSubscriptionPlans() {
        return Collections.unmodifiableList(subscriptionPlans);
    }

    public void addSubscriptionPlan(SubscriptionPlan plan) {
        subscriptionPlans.add(plan);
    }

    public List<Artist> getArtists() {
        return Collections.unmodifiableList(artists);
    }

    public void addArtist(Artist artist) {
        artists.add(artist);
    }

    public List<Album> getAlbums() {
        return Collections.unmodifiableList(albums);
    }

    public void addAlbum(Album album) {
        albums.add(album);
    }

    public List<Song> getSongs() {
        return Collections.unmodifiableList(songs);
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public List<Playlist> getPlaylists() {
        return Collections.unmodifiableList(playlists);
    }

    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    private User toDomain(JaxbUser source) {
        User user = new User(nvl(source.getUsername()), nvl(source.getEmail()));
        if (source.getCreatedDate() != null && !source.getCreatedDate().isEmpty()) {
            user.setCreatedDate(LocalDate.parse(source.getCreatedDate()));
        }
        if (source.getCreatedDateTime() != null && !source.getCreatedDateTime().isEmpty()) {
            user.setCreatedDateTime(LocalDateTime.parse(source.getCreatedDateTime()));
        }

        if (source.getPlaylists() != null) {
            for (JaxbPlaylist playlistElement : source.getPlaylists()) {
                Playlist playlist = new Playlist(nvl(playlistElement.getName()), user.getUsername());
                if (playlistElement.getItems() != null) {
                    for (JaxbPlaylistItem itemElement : playlistElement.getItems()) {
                        Song song = toSong(itemElement.getSong());
                        PlaylistItem item = new PlaylistItem(song, playlist.getName(), itemElement.getPosition());
                        playlist.addSong(item);
                    }
                }
                user.addPlaylist(playlist);
            }
        }

        if (source.getStreamingSession() != null) {
            JaxbStreamingSession sessionElement = source.getStreamingSession();
            StreamingSession session = new StreamingSession(user.getUsername());
            session.setCurrentSong(toSong(sessionElement.getCurrentSong()));
            if (sessionElement.getPlayedSongs() != null) {
                for (String title : sessionElement.getPlayedSongs()) {
                    if (title != null && !title.isEmpty()) {
                        session.addPlayedSong(new Song(title, new Artist(""), new Album("", new Artist(""))));
                    }
                }
            }
            user.setStreamingSession(session);
        }

        if (source.getSubscriptionPlan() != null) {
            JaxbSubscriptionPlan planElement = source.getSubscriptionPlan();
            user.setSubscriptionPlan(new SubscriptionPlan(nvl(planElement.getName()), planElement.getPrice()));
        }

        return user;
    }

    private Song toSong(JaxbSong songElement) {
        if (songElement == null) {
            return new Song("", new Artist(""), new Album("", new Artist("")));
        }
        Artist artist = new Artist(nvl(songElement.getArtist()));
        Album album = new Album(nvl(songElement.getAlbum()), artist);
        return new Song(nvl(songElement.getTitle()), artist, album);
    }

    private String nvl(String value) {
        return value == null ? "" : value;
    }
}
