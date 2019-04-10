package Controller;

import java.io.File;
import java.util.*;
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

    public int parseBoard() throws XmlTagException{
        int boardSize = Integer.parseInt(doc.getElementsByTagName("BoardSize").item(0).getTextContent());
        return boardSize;
    }

    public double parseBank() throws XmlTagException{
        double bankFunds = Double.parseDouble(doc.getElementsByTagName("BankFunds").item(0).getTextContent());
        return bankFunds;
    }

    public List<Die> parseDice() throws XmlTagException{
        List<Die> dice = new ArrayList<>();

        int numberOfDice = Integer.parseInt(doc.getElementsByTagName("Number").item(0).getTextContent());
        int numberOfSides = Integer.parseInt(doc.getElementsByTagName("Sides").item(0).getTextContent());

        for(int i=0; i<numberOfDice; i++){
            int[] sideValues = new int[numberOfSides];
            for(int j=0; j<numberOfSides; j++){
                sideValues[j] = j+1;
                if(j == numberOfSides-1){
                    Die newDice = new Die(numberOfSides, sideValues);
                    dice.add(newDice);
                }
            }
        }
        return dice;
/*MIGHT NEED THIS IF DIFFERENT TYPES OF DICE BUT NOT RIGHT NOW
        NodeList diceList = doc.getElementsByTagName("Dice");

        for(int i=0; i<diceList.getLength(); i++){
            Node d = diceList.item(i);
            if(d.getNodeType() == Node.ELEMENT_NODE){
                Element die = (Element) d;
                int numberOfDice = Integer.parseInt(die.getElementsByTagName("Number").item(0).getTextContent());
                for(int j=0; j<numberOfDice; j++){
                    int numSides = Integer.parseInt(die.getElementsByTagName("Sides").item(0).getTextContent());
                    int[] sideValues = new int[numSides]
                    for(int k=0; k<numSides; k++){
                        sideValues[k] = k+1;
                        if(k == numSides-1){
                            Die newDie = new Die(numSides, sideValues);
                            dice.add(newDie);
                        }
                    }
                }
            }
        }*/
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

    public List<List> parseSpaces(){
        List<List> allSpacesAndProps = new ArrayList<>();
        List<AbstractSpace> allSpaces = new ArrayList<>();
        List<Property> allProps = new ArrayList<>();
        NodeList spaceList = doc.getElementsByTagName("Space");
        for(int i = 0; i < spaceList.getLength(); i++) {
            Node s = spaceList.item(i);
            if(s.getNodeType() == Node.ELEMENT_NODE) {
                Element space = (Element) s;
                int index = Integer.parseInt(space.getElementsByTagName("Index").item(0).getTextContent());
                String spaceName = space.getElementsByTagName("SpaceName").item(0).getTextContent().strip();
                //http://www.java67.com/2018/03/java-convert-string-to-boolean.html

                //CHANGE - type = color_property; utility_property; railroad_property
                //if(space.getAttribute("type").split("_")[1].equalsIgnoreCase("property")){}
                if(space.getAttribute("type").equalsIgnoreCase("ACTION")) {
                    AbstractSpace newSpace = new ActionCardSpace(index, spaceName);
                    allSpaces.add(newSpace);

                }
                else if(space.getAttribute("type").equalsIgnoreCase("FREE_PARKING")) {
                    AbstractSpace newSpace = new FreeParkingSpace(index, spaceName);
                    allSpaces.add(newSpace);
                }
                else if(space.getAttribute("type").equalsIgnoreCase("GO")) {
                    AbstractSpace newSpace = new GoSpace(index, spaceName);
                    allSpaces.add(newSpace);
                }
                else if(space.getAttribute("type").equalsIgnoreCase("GO_TO_JAIL")) {
                    String spaceToMoveTo = space.getElementsByTagName("TargetSpace").item(0).getTextContent();
                    AbstractSpace newSpace = new GoToSpace(index, spaceName, spaceToMoveTo);
                    allSpaces.add(newSpace);
                }
                else if(space.getAttribute("type").equalsIgnoreCase("JAIL")) {
                    AbstractSpace newSpace = new JailSpace(index, spaceName);
                    allSpaces.add(newSpace);
                }
                else if(space.getAttribute("type").equalsIgnoreCase("TAX")) {
                    double flatRate = Double.parseDouble(space.getElementsByTagName("FlatRate").item(0).getTextContent());
                    double percentage = Double.parseDouble(space.getElementsByTagName("Percentage").item(0).getTextContent());
                    double newPercent = percentage/100;
                    AbstractSpace newSpace = new TaxSpace(index, spaceName, flatRate, newPercent);
                    allSpaces.add(newSpace);

                }
                else if(space.getAttribute("type").equalsIgnoreCase("COLOR_PROPERTY")) {
                    AbstractSpace newSpace = new PropSpace(index, spaceName);
                    allSpaces.add(newSpace);
                    String colorGroup = space.getElementsByTagName("ColorGroup").item(0).getTextContent();
                    double buyPrice = Double.parseDouble(space.getElementsByTagName("BuyPrice").item(0).getTextContent());
                    double rent = Double.parseDouble(space.getElementsByTagName("Rent").item(0).getTextContent());
                    double rentOneHouse = Double.parseDouble(space.getElementsByTagName("Rent1House").item(0).getTextContent());
                    double rentTwoHouse = Double.parseDouble(space.getElementsByTagName("Rent2House").item(0).getTextContent());
                    double rentThreeHouse = Double.parseDouble(space.getElementsByTagName("Rent3House").item(0).getTextContent());
                    double rentFourHouse = Double.parseDouble(space.getElementsByTagName("Rent4House").item(0).getTextContent());
                    double rentHotel = Double.parseDouble(space.getElementsByTagName("RentHotel").item(0).getTextContent());
                    double pricePerHouse = Double.parseDouble(space.getElementsByTagName("PricePerHouse").item(0).getTextContent());
                    double mortgage = Double.parseDouble(space.getElementsByTagName("Mortgage").item(0).getTextContent());
                    Property newProp = new ColorProperty(buyPrice, spaceName, colorGroup);
                    ((PropSpace) newSpace).linkSpaceToProperty(newProp);
                    allProps.add(newProp);

                }
                else if(space.getAttribute("type").equalsIgnoreCase("RAILROAD_PROPERTY")) {
                    AbstractSpace newSpace = new PropSpace(index, spaceName);
                    allSpaces.add(newSpace);
                    double buyPrice = Double.parseDouble(space.getElementsByTagName("BuyPrice").item(0).getTextContent());
                    double rent = Double.parseDouble(space.getElementsByTagName("Rent").item(0).getTextContent());
                    double rent2 = Double.parseDouble(space.getElementsByTagName("Rent2").item(0).getTextContent());
                    double rent3 = Double.parseDouble(space.getElementsByTagName("Rent3").item(0).getTextContent());
                    double rent4 = Double.parseDouble(space.getElementsByTagName("Rent4").item(0).getTextContent());
                    double mortgage = Double.parseDouble(space.getElementsByTagName("Mortgage").item(0).getTextContent());
                    Property newProp = new RailRoadProperty(buyPrice, spaceName);
                    ((PropSpace) newSpace).linkSpaceToProperty(newProp);
                    allProps.add(newProp);

                }
                else if(space.getAttribute("type").equalsIgnoreCase("UTILITY_PROPERTY")) {
                    AbstractSpace newSpace = new PropSpace(index, spaceName);
                    allSpaces.add(newSpace);
                    double buyPrice = Double.parseDouble(space.getElementsByTagName("BuyPrice").item(0).getTextContent());
                    double rentMult = Double.parseDouble(space.getElementsByTagName("RentMultiplier").item(0).getTextContent());
                    double rentMult2 = Double.parseDouble(space.getElementsByTagName("Rent2Multiplier").item(0).getTextContent());
                    double mortgage = Double.parseDouble(space.getElementsByTagName("Mortgage").item(0).getTextContent());
                    Property newProp = new UtilityProperty(buyPrice, spaceName);
                    ((PropSpace) newSpace).linkSpaceToProperty(newProp);
                    allProps.add(newProp);
                }
//                else{
//                    throw new XmlTagException(space.getAttribute("type"));
//                }
            }
        }
        allSpacesAndProps.add(allSpaces);
        allSpacesAndProps.add(allProps);
        return allSpacesAndProps;
    }

    public List<String> parseTokens() throws XmlTagException{
        List<String> allTokens = new ArrayList<>();

        NodeList tokenList = doc.getElementsByTagName("Token");
        for(int i = 0; i<tokenList.getLength(); i++) {
            Node tk = tokenList.item(i);
            if (tk.getNodeType() == Node.ELEMENT_NODE) {
                Element tok = (Element) tk;
                String tokName = tok.getTextContent();
                allTokens.add(tokName);
            }
        }
        if(!allTokens.isEmpty()){
//            System.out.println(allTokens);
            return allTokens;
        }
        else{
            throw new XmlTagException("BadTag");
        }
    }

    public double getRuleDouble(String attribute) {
        NodeList list = doc.getElementsByTagName(attribute);
        Node node = list.item(0);
        if(node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String stringValue = element.getTextContent();
            return Double.parseDouble(stringValue);
        }
        return -1;
    }

//    public static void main(String[] args) {
//        ConfigReader c = new ConfigReader("Normal_Config.xml");
//        try{
//            c.parseSpaces();
//            c.parseActionCards();
//            c.parseActionDecks();
//            c.parseBank();
//            c.parseBoard();
//            c.parseDice();
//            c.parseTokens();
//        }
//        catch(XmlTagException e){
//
//        }
//    }

}
