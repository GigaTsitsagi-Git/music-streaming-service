package com.solvd.musicstreamingservice;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class JaxbParser {

    public <T> T parse(String path, Class<T> clazz) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(path), clazz);
    }
}


