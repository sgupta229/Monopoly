package Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import Model.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class ConfigReader {

    DocumentBuilder dBuilder;
    Document doc;

    //https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
    public ConfigReader(String filename) {
        //File inputFile = new File(filename);
        try {
            File inputFile = new File(this.getClass().getClassLoader().getResource(filename).toURI());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ActionDeck> parseActionDecks() throws XmlTagException{
        List<ActionDeck> decks = new ArrayList<>();

        NodeList actionDeckList = doc.getElementsByTagName("ActionDeck");

        for(int i=0; i<actionDeckList.getLength(); i++){
            Node ad = actionDeckList.item(i);
            if(ad.getNodeType() == Node.ELEMENT_NODE){
                Element deck = (Element) ad;
                //String deckName = deck.getElementsByTagName("ActionDeck").item(0).getTextContent();
                String deckName = deck.getTextContent();
                DeckType dt = DeckType.valueOf(deckName);
                ActionDeck tempDeck = new ActionDeck(dt);
                decks.add(tempDeck);
            }
        }
        return decks;
    }

    public List<AbstractActionCard> parseActionCards() throws XmlTagException{
        //Feed this allActionCards list into fillLiveDeck() in deck class after initializing empty decks
        List<AbstractActionCard> allActionCards = new ArrayList<>();

        NodeList actionCardList = doc.getElementsByTagName("ActionCardType");
        for(int i = 0; i<actionCardList.getLength(); i++){
            Node ac = actionCardList.item(i);
            if(ac.getNodeType() == Node.ELEMENT_NODE){
                Element card = (Element) ac;
                //All types of action cards have these fields
                DeckType dt = DeckType.valueOf(card.getElementsByTagName("DeckType").item(0).getTextContent());
                String msg = card.getElementsByTagName("Message").item(0).getTextContent();
                //http://www.java67.com/2018/03/java-convert-string-to-boolean.html
                Boolean holdable = Boolean.parseBoolean(card.getElementsByTagName("Holdable").item(0).getTextContent());

                //Specialized fields below
                if(card.getAttribute("type").equalsIgnoreCase("MOVE_TO")){
                    String targetSpace = card.getElementsByTagName("TargetSpace").item(0).getTextContent();
                    AbstractActionCard newCard = new MoveToAC(dt, msg, holdable, targetSpace);
                    allActionCards.add(newCard);
                }
                else if(card.getAttribute("type").equalsIgnoreCase("GO_TO_JAIL")){
                    AbstractActionCard newCard = new GoToJailAC(dt, msg, holdable);
                    allActionCards.add(newCard);
                }
                else if(card.getAttribute("type").equalsIgnoreCase("GET_OUT_OF_JAIL")){
                    AbstractActionCard newCard = new GetOutJailAC(dt, msg, holdable);
                    allActionCards.add(newCard);
                }
                else if(card.getAttribute("type").equalsIgnoreCase("WIN_MONEY")){
                    String winFrom = card.getElementsByTagName("From").item(0).getTextContent();
                    double amnt = Double.parseDouble(card.getElementsByTagName("Amount").item(0).getTextContent());
                    AbstractActionCard newCard = new WinMoneyAC(dt, msg, holdable, winFrom, amnt);
                    allActionCards.add(newCard);
                }
                else if(card.getAttribute("type").equalsIgnoreCase("LOSE_MONEY")){
                    String loseTo = card.getElementsByTagName("To").item(0).getTextContent();
                    double amnt = Double.parseDouble(card.getElementsByTagName("Amount").item(0).getTextContent());
                    AbstractActionCard newCard = new LoseMoneyAC(dt, msg, holdable, loseTo, amnt);
                    allActionCards.add(newCard);
                }
                else{
                    throw new XmlTagException(card.getAttribute("type"));
                }
            }
        }
        return allActionCards;

    }

    public void parseSpaces() {
        NodeList spaceList = doc.getElementsByTagName("Space");
        for(int i = 0; i < spaceList.getLength(); i++) {
            Node s = spaceList.item(i);
            if(s.getNodeType() == Node.ELEMENT_NODE) {
                Element space = (Element) s;
                //CHANGE - type = color_property; utility_property; railroad_property
                //if(space.getAttribute("type").split("_")[1].equalsIgnoreCase("property")){}
                if(space.getAttribute("type").equalsIgnoreCase("Property")) {
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
}
