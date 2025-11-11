package com.solvd.musicstreamingservice.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "musicstreamingservice")
@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbMusicService {

    @XmlElement(name = "user")
    private JaxbUser user;

    public JaxbUser getUser() {
        return user;
    }

    public void setUser(JaxbUser user) {
        this.user = user;
    }
}


