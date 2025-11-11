package com.solvd.musicstreamingservice.parser;

import com.solvd.musicstreamingservice.model.Playlist;
import com.solvd.musicstreamingservice.model.PlaylistItem;
import com.solvd.musicstreamingservice.model.User;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;

/**
 *
 * This class provides methods to parse music streaming service data from XML format.
 *
 * <p><b> XPath to find elements in XML:</b></p>
 * <ul>
 *   <li><code>/musicstreamingservice/user/username</code> - Selects the username element directly under the user element</li>
 *   <li><code>//user/playlist[@name='My Favorites']</code> - Selects playlist elements with a specific name attribute</li>
 *   <li><code>//song[artist='Taylor Swift']</code> - Selects all songs by Taylor Swift anywhere in the document</li>
 *   <li><code>/musicstreamingservice/user/playlist/playlistitem/song/title</code> - Selects all song titles in playlists</li>
 *   <li><code>//playedSong[position()=1]</code> - Selects the first played song from the streaming session</li>
 * </ul>
 *
 */
public class XMLSaxParser {

    public User parseXML() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();

            SAXParser saxParser = factory.newSAXParser();

            SAXParserHandler handler = new SAXParserHandler();

            InputStream xmlStream = getClass().getClassLoader()
                    .getResourceAsStream("music_streaming_data.xml");

            if (xmlStream == null) {
                throw new RuntimeException("XML file not found in resources!");
            }

            saxParser.parse(xmlStream, handler);

            User result = handler.getResult();

            if (result == null) {
                throw new RuntimeException("Failed to parse XML - no user data found!");
            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException("Error parsing XML: " + e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        XMLSaxParser parser = new XMLSaxParser();

        try {
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
    }
}

