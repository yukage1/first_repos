import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ethnicityFinder {
        public static ArrayList<String> ethnicity = new ArrayList<>();

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        DefaultHandler defaultHandler = new DefaultHandler(){
            String currentTag;
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
               currentTag = qName;
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                super.endElement(uri, localName, qName);
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                if(currentTag.contains("ethcty")){
                    ethnicityFinder.ethnicity.add(new String(ch,start,length));
                }
            }
        };


        SAXParser saxParser = SAXParserFactory.newDefaultInstance().newSAXParser();

        saxParser.parse(new File("Popular_Baby_Names_NY.xml"),defaultHandler);

        Set<String> set = new HashSet<>(ethnicity);

        set.stream().forEach(System.out::println);

    }
}
