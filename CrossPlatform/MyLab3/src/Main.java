import org.xml.sax.Attributes;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;



public class Main {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        SAXParser saxParser = SAXParserFactory.newDefaultInstance().newSAXParser();
        DataHandler handler = new DataHandler();
        saxParser.parse(new File("Popular_Baby_Names_NY.xml"),handler);

        System.out.println("-----------------------------\nTags:");
        for (String str: handler.getTags()) {
            if(str!=null) System.out.println(str);
        }
    }
}

class DataHandler extends DefaultHandler{
    public String[] getTags() {
        return tags;
    }

    private String[] tags;
    private int cnt = 0;
    public DataHandler(){
        tags = new String[10];

    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        System.out.println(qName);
        for (int i = 0; i < cnt; i++) {
            if(tags[i].compareTo(qName) == 0){return;}
        }
        tags[cnt++] = qName;

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.out.println(qName);

    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String str = new String(ch, start, length);
        str = str.replace("\n","").trim();
        if (!str.isEmpty()) {
            System.out.println("\t" + str.trim());
        }
    }
}