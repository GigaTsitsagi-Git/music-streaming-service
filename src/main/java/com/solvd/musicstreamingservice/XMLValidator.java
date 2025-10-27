package com.solvd.musicstreamingservice;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.InputStream;

public class XMLValidator {

    public static void main(String[] args) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            
            InputStream xsdStream = XMLValidator.class.getClassLoader()
                    .getResourceAsStream("music_streaming_data.xsd");
            
            if (xsdStream == null) {
                System.err.println("XSD file not found!");
                return;
            }
            
            Schema schema = factory.newSchema(new StreamSource(xsdStream));
            
            Validator validator = schema.newValidator();
            
            InputStream xmlStream = XMLValidator.class.getClassLoader()
                    .getResourceAsStream("music_streaming_data.xml");
            
            if (xmlStream == null) {
                System.err.println("XML file not found!");
                return;
            }
            
            validator.validate(new StreamSource(xmlStream));
            
            System.out.println("✓ XML is valid against the XSD schema!");
            
        } catch (Exception e) {
            System.err.println("✗ Validation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

