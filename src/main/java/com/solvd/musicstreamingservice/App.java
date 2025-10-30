package com.solvd.musicstreamingservice;

import com.jayway.jsonpath.JsonPath;
import java.io.InputStream;

public class App 
{
    public static void main( String[] args )
    {
        XMLSaxParser parser = new XMLSaxParser();
        
        try {
            System.out.println("Parsing XML file...\n");
            User user = parser.parseXML();
            
            System.out.println("✓ XML parsed successfully!");
            System.out.println("\n=== Parsed User Data ===\n");
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Created Date: " + user.getCreatedDate());
            System.out.println("Created DateTime: " + user.getCreatedDateTime());
            System.out.println("\nPlaylists: " + user.getPlaylists().size());
            
            for (Playlist playlist : user.getPlaylists()) {
                System.out.println("\n  - " + playlist.getName());
                System.out.println("    Items: " + playlist.getSongs().size());
                for (PlaylistItem item : playlist.getSongs()) {
                    System.out.println("      " + item.getPosition() + ". " + item.getSong().getTitle() 
                            + " by " + item.getSong().getArtist().getName());
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
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            System.out.println("\nParsing JSONPath queries...\n");
            InputStream is = App.class.getClassLoader().getResourceAsStream("music_streaming_data.json");
            if (is == null) {
                throw new RuntimeException("JSON file not found in resources!");
            }

            String username = JsonPath.parse(is).read("$.username", String.class);
            // Reopen stream for subsequent reads
            is = App.class.getClassLoader().getResourceAsStream("music_streaming_data.json");
            String firstPlaylist = JsonPath.parse(is).read("$.playlists[0].name", String.class);
            is = App.class.getClassLoader().getResourceAsStream("music_streaming_data.json");
            String currentSong = JsonPath.parse(is).read("$.streamingSession.currentSong.title", String.class);
            is = App.class.getClassLoader().getResourceAsStream("music_streaming_data.json");
            String firstPlayed = JsonPath.parse(is).read("$.streamingSession.playedSongs[0]", String.class);
            is = App.class.getClassLoader().getResourceAsStream("music_streaming_data.json");
            Double price = JsonPath.parse(is).read("$.subscriptionPlan.price", Double.class);

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
}
