package com.solvd.musicstreamingservice;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class JaxbParser {

    public <T> T parse(String path, Class<T> clazz) throws Exception {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object result = unmarshaller.unmarshal(new File(path));
        return clazz.cast(result);
    }
}


