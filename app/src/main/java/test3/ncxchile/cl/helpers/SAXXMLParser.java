package test3.ncxchile.cl.helpers;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.util.Log;

import test3.ncxchile.cl.greenDAO.User;

public class SAXXMLParser {


    public static List<Object> parse(InputStream is, String fixture) {
        List<Object> objects = null;
        try {
            // create a XMLReader from SAXParser
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            // create a SAXXMLHandler
            SAXXMLHandler saxHandler = new SAXXMLHandler();
            saxHandler.setFixture(fixture);
            // store handler in XMLReader
            xmlReader.setContentHandler(saxHandler);
            // the process starts
            xmlReader.parse(new InputSource(is));
            // get the `Laptop list`
            objects = saxHandler.getObjects();

        } catch (Exception ex) {
            Log.d("XML", "SAXXMLParser: parse() failed");
            ex.printStackTrace();
        }

        // return Laptop list
        return objects;
    }

}