package com.solvd.musicstreamingservice.parser;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class JsonParser {

    public <T> T parse(String path, Class<T> clazz) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(path), clazz);
    }
}


