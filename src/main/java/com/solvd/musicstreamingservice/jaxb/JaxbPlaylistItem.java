package com.solvd.musicstreamingservice.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbPlaylistItem {

    @XmlElement(name = "song")
    private JaxbSong song;

    @XmlElement(name = "position")
    private int position;

    public JaxbSong getSong() {
        return song;
    }

    public void setSong(JaxbSong song) {
        this.song = song;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}


