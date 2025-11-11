package com.solvd.musicstreamingservice.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbPlaylist {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "playlistitem")
    private List<JaxbPlaylistItem> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JaxbPlaylistItem> getItems() {
        return items;
    }

    public void setItems(List<JaxbPlaylistItem> items) {
        this.items = items;
    }
}


