package com.solvd.musicstreamingservice.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbUser {

    @XmlElement(name = "username")
    private String username;

    @XmlElement(name = "email")
    private String email;

    @XmlElement(name = "createdDate")
    private String createdDate;

    @XmlElement(name = "createdDateTime")
    private String createdDateTime;

    @XmlElement(name = "playlist")
    private List<JaxbPlaylist> playlists;

    @XmlElement(name = "streamingsession")
    private JaxbStreamingSession streamingSession;

    @XmlElement(name = "subscriptionplan")
    private JaxbSubscriptionPlan subscriptionPlan;

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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public List<JaxbPlaylist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<JaxbPlaylist> playlists) {
        this.playlists = playlists;
    }

    public JaxbStreamingSession getStreamingSession() {
        return streamingSession;
    }

    public void setStreamingSession(JaxbStreamingSession streamingSession) {
        this.streamingSession = streamingSession;
    }

    public JaxbSubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(JaxbSubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }
}


