package com.solvd.musicstreamingservice.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbStreamingSession {

    @XmlElement(name = "currentSong")
    private JaxbSong currentSong;

    @XmlElement(name = "playedSong")
    private List<String> playedSongs;

    public JaxbSong getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(JaxbSong currentSong) {
        this.currentSong = currentSong;
    }

    public List<String> getPlayedSongs() {
        return playedSongs;
    }

    public void setPlayedSongs(List<String> playedSongs) {
        this.playedSongs = playedSongs;
    }
}


