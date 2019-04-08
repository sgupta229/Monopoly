package Controller;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class ConfigReader {

    DocumentBuilder dBuilder;
    Document doc;

    public ConfigReader(String filename) {
        File inputFile = new File(filename);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseActionCards() {

    }

    public void parseSpaces() {
        NodeList spaceList = doc.getElementsByTagName("Space");
        for(int i = 0; i < spaceList.getLength(); i++) {
            Node s = spaceList.item(i);
            if(s.getNodeType() == Node.ELEMENT_NODE) {
                Element space = (Element) s;
                if(space.getAttribute("type").equals("Property")) {
                    String name = space.getElementsByTagName("SpaceName").item(0).getTextContent();
                    int index = Integer.parseInt(space.getElementsByTagName("Index").item(0).getTextContent());
                    System.out.println(name);
                    System.out.println(index);
                }
                else if(space.getAttribute("type").equals("parking")) {

                }
            }
        }
    }

    public static void main(String[] args) {
        ConfigReader c = new ConfigReader("/Users/SGGS1234/Desktop/workspace307/monopoly_team04/data/Normal_Config.xml");
        c.parseSpaces();
    }
}
