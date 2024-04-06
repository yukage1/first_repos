import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;


public class DOM_parser {
    private Document m_doc;

    public DOM_parser(String xmlfile) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        m_doc = builder.parse(new FileInputStream(new File(xmlfile)));
    }

    public DOM_parser() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        m_doc = builder.newDocument();
    }

    public int getChildCount(String parentTag, int parentIndex, String childTag) {
        NodeList list = m_doc.getElementsByTagName(parentTag);
        Element parent = (Element) list.item(parentIndex);
        NodeList childList = parent.getElementsByTagName(childTag);
        return childList.getLength();
    }

    public String getChildValue(String parentTag, int parentIndex, String childTag, int childIndex) {
        NodeList list = m_doc.getElementsByTagName(parentTag);
        Element parent = (Element) list.item(parentIndex);
        NodeList childList = parent.getElementsByTagName(childTag);
        Element element = (Element) childList.item(childIndex);
        Node child = element.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }

    public String getChildAttribute(String parentTag, int parentIndex, String childTag, int childIndex,
                                    String attributeTag) {
        NodeList list = m_doc.getElementsByTagName(parentTag);
        Element parent = (Element) list.item(parentIndex);
        NodeList childList = parent.getElementsByTagName(childTag);
        Element element = (Element) childList.item(childIndex);
        return element.getAttribute(attributeTag);
    }

    public int getRootElementCount(String rootElementTag) {
        NodeList list = m_doc.getElementsByTagName(rootElementTag);
        return list.getLength();
    }

    public void addChildElement(String parentTag, int parentIndex, String childTag, String childValue) {
        NodeList list = m_doc.getElementsByTagName(parentTag);
        Element parent = (Element) list.item(parentIndex);
        Element child = m_doc.createElement(childTag);
        if (childValue != null) {
            child.appendChild(m_doc.createTextNode(childValue));
        }
        if (parent == null) {
            m_doc.appendChild(child);
        }
        else {
            parent.appendChild(child);
        }
    }

    public void setAttributeValue(String elementTag, int elementIndex, String attributeTag,
                                  String attributeValue) {
        NodeList list = m_doc.getElementsByTagName(elementTag);
        Element element = (Element) list.item(elementIndex);
        element.setAttribute(attributeTag, attributeValue);
    }

    public String renderXml() throws Exception {
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        tf.transform(new DOMSource(m_doc), new StreamResult(out));
        return new String(out.toString().getBytes(), StandardCharsets.UTF_8);
    }
    public static void main(String[] args) throws Exception {
        DOM_parser domParser = new DOM_parser("Popular_Baby_NAMES_NY.xml");
        FileWriter fileWriter = new FileWriter("new.xml");
        fileWriter.write(domParser.renderXml());
        fileWriter.close();
    }
}


