package Controller;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import Model.*;

import Model.properties.*;
import Model.spaces.*;

import Model.actioncards.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import static javax.xml.datatype.DatatypeFactory.newInstance;

public class ConfigReader {
    private static final String BOARD_SIZE_TAG = "BoardSize";
    private static final String BANK_FUNDS_TAG = "BankFunds";
    private static final String DICE_NUMBER_TAG = "Number";
    private static final String DICE_SIDES_TAG = "Sides";
    private static final String ACTION_DECK_TAG = "ActionDeck";
    private static final String ACTION_CARD_TAG = "ActionCardType";
    private static final String DECK_TYPE_TAG = "DeckType";
    private static final String MESSAGE_TAG = "Message";
    private static final String HOLDABLE_TAG = "Holdable";
    private static final String EXTRA_STRINGS_TAG = "ExtraString";
    private static final String EXTRA_DOUBLES_TAG = "ExtraDoubles";
    private static final String ACTION_CARD_PATH = "Model.actioncards.";
    private static final String PROPERTY_TAG = "Property";
    private static final String PROPERTY_NAME_TAG = "PropertyName";
    private static final String COLOR_GROUP_TAG = "ColorGroup";
    private static final String BUY_PRICE_TAG = "BuyPrice";
    private static final String RENT_TAG = "Rent";
    private static final String BUILDING_PRICES_TAG = "BuildingPrices";
    private static final String PROPERTIES_PATH_TAG = "Model.properties.";
    private static final String TYPE_TAG = "type";
    private static final String GROUP_SIZE_TAG = "GroupSize";
    private static final String SPACE_TAG = "Space";
    private static final String INDEX_TAG = "Index";
    private static final String SPACE_GROUP_TAG = "SpaceGroup";
    private static final String SPACE_NAME_TAG = "SpaceName";
    private static final String SPACES_PATH_TAG = "Model.spaces.";
    private static final String TOKEN_TAG = "Token";
    private static final String BUILDING_TYPE_TAG = "BuildingType";
    private static final String TOTAL_AMOUNT_TAG = "TotalAmount";
    private static final String MAX_AMOUNT_TAG = "MaxAmount";



    DocumentBuilder dBuilder;
    Document doc;
    ConfigReaderErrorHandling errorChecker;

    //https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
    public ConfigReader(String filename) {
        //File inputFile = new File(filename);
        try {
            File inputFile = new File(this.getClass().getClassLoader().getResource(filename).toURI());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
            errorChecker = new ConfigReaderErrorHandling(doc);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int parseBoard() throws XmlReaderException {
        int boardSize = Integer.parseInt(doc.getElementsByTagName(BOARD_SIZE_TAG).item(0).getTextContent());
        return boardSize;
    }

    public List<Double> parseBank() throws XmlReaderException {
        List<Double> bankInfo = new ArrayList<>();
        double bankFunds = Double.parseDouble(doc.getElementsByTagName(BANK_FUNDS_TAG).item(0).getTextContent());
        bankInfo.add(bankFunds);
        return bankInfo;
    }

    public List<Die> parseDice() throws XmlReaderException {
        List<Die> dice = new ArrayList<>();

        int numberOfDice = Integer.parseInt(doc.getElementsByTagName(DICE_NUMBER_TAG).item(0).getTextContent());
        int numberOfSides = Integer.parseInt(doc.getElementsByTagName(DICE_SIDES_TAG).item(0).getTextContent());

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
    }

    public List<ActionDeck> parseActionDecks() throws XmlReaderException {
        List<ActionDeck> decks = new ArrayList<>();

        NodeList actionDeckList = doc.getElementsByTagName(ACTION_DECK_TAG);

        for(int i=0; i<actionDeckList.getLength(); i++){
            Node ad = actionDeckList.item(i);
            if(ad.getNodeType() == Node.ELEMENT_NODE){
                Element deck = (Element) ad;
                //String deckName = deck.getElementsByTagName("ActionDeck").item(0).getTextContent();
                String deckName = deck.getTextContent();
                //Check if deckName in DeckType enum before getting enum
                if(!checkDeckType(deckName)){
                    throw new XmlReaderException(deckName + " is not a valid action deck. Please check the data file.");
                }
                else{
                    DeckType dt = DeckType.valueOf(deckName);
                    ActionDeck tempDeck = new ActionDeck(dt);
                    decks.add(tempDeck);
                }
            }
        }
        return decks;
    }

//    public List<AbstractActionCard> parseActionCards() throws XmlReaderException{
    public List<AbstractActionCard> parseActionCards() throws XmlReaderException {
        //Feed this allActionCards list into fillLiveDeck() in deck class after initializing empty decks
        List<AbstractActionCard> allActionCards = new ArrayList<>();

        NodeList actionCardList = doc.getElementsByTagName(ACTION_CARD_TAG);
        for(int i = 0; i<actionCardList.getLength(); i++){
            Node ac = actionCardList.item(i);
            if(ac.getNodeType() == Node.ELEMENT_NODE){
                Element card = (Element) ac;
                String deckName = card.getElementsByTagName(DECK_TYPE_TAG).item(0).getTextContent();
                if(!checkDeckType(deckName)){
                    throw new XmlReaderException(deckName + "not a valid action deck. Please check the data file.");
                }

                //All types of action cards have these fields
                DeckType dt = DeckType.valueOf(card.getElementsByTagName(DECK_TYPE_TAG).item(0).getTextContent());
                String msg = card.getElementsByTagName(MESSAGE_TAG).item(0).getTextContent();
                //http://www.java67.com/2018/03/java-convert-string-to-boolean.html
                Boolean holdable = Boolean.parseBoolean(card.getElementsByTagName(HOLDABLE_TAG).item(0).getTextContent());
                List<String> extraStrings = List.of(card.getElementsByTagName(EXTRA_STRINGS_TAG).item(0).getTextContent().split(","));
                //System.out.println(extraStrings.get(0) + " AND " + extraStrings.get(1));
                //Get list of doubles
                String[] extraDubTemp = card.getElementsByTagName(EXTRA_DOUBLES_TAG).item(0).getTextContent().split(",");

                List<Double> extraDubs = listDoubleConverter(extraDubTemp);

                String className = card.getAttribute(TYPE_TAG);
                //Reflection to create action cards
                try {
                    AbstractActionCard newAC = (AbstractActionCard) Class.forName(ACTION_CARD_PATH + className).getConstructor(DeckType.class, String.class, Boolean.class, List.class, List.class).newInstance(dt, msg, holdable, extraStrings, extraDubs);
                    allActionCards.add(newAC);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return allActionCards;
    }

    public List<Property> parseAllProps(){
        List<Property> propsList = new ArrayList<>();
        NodeList propTypeList = doc.getElementsByTagName(PROPERTY_TAG);
        for(int i = 0; i<propTypeList.getLength(); i++){
            Node ac = propTypeList.item(i);
            if(ac.getNodeType() == Node.ELEMENT_NODE){
                Element prop = (Element) ac;
                String name = prop.getElementsByTagName(PROPERTY_NAME_TAG).item(0).getTextContent();
                //http://www.java67.com/2018/03/java-convert-string-to-boolean.html
                String color = prop.getElementsByTagName(COLOR_GROUP_TAG).item(0).getTextContent();
                int groupSize = Integer.parseInt(prop.getElementsByTagName(GROUP_SIZE_TAG).item(0).getTextContent());
                double buyPrice = Double.parseDouble(prop.getElementsByTagName(BUY_PRICE_TAG).item(0).getTextContent());

                //get all rent numbers
                ArrayList<Double> rentNumbers = new ArrayList<>();
                NodeList rentList = doc.getElementsByTagName(RENT_TAG);

                Node thisRent1 = rentList.item(i);
                Element thisRent = (Element) thisRent1;
                NodeList rentNums = thisRent.getChildNodes();

                for(int x=1; x<rentNums.getLength(); x=x+2){
                    Node currRentNum = rentNums.item(x);
                    Double rentVal = Double.parseDouble(currRentNum.getTextContent());
                    rentNumbers.add(rentVal);
                }

                //get all building numbers
                Map<BuildingType, Double> BuildingPrices = new TreeMap<>();
                NodeList priceList = doc.getElementsByTagName(BUILDING_PRICES_TAG);
                Node thisPrice = priceList.item(i);
                NodeList priceNums = thisPrice.getChildNodes();
                for(int x=1; x<priceNums.getLength(); x=x+2){
                    Node currPriceNum = priceNums.item(x);
                    Element c = (Element) currPriceNum;
                    BuildingType bType = (BuildingType.valueOf(c.getAttribute(TYPE_TAG)));
                    double priceVal = Double.parseDouble(currPriceNum.getTextContent());
                    BuildingPrices.put(bType, priceVal);
                }

                String className = prop.getAttribute(TYPE_TAG);
                //Reflection to create properties
                try {
                    Property newProp = (Property) Class.forName(PROPERTIES_PATH_TAG + className).getConstructor(double.class, String.class, String.class, List.class, int.class, Map.class).newInstance(buyPrice,
                            name, color, rentNumbers, groupSize, BuildingPrices);
                    propsList.add(newProp);
                    newProp.setIsMortgaged(false);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return propsList;
    }

    public List<AbstractSpace> parseNewSpaces(List<Property> propsList){
        List<AbstractSpace> allSpaces = new ArrayList<>();

        NodeList spaceList = doc.getElementsByTagName(SPACE_TAG);
        for(int i = 0; i < spaceList.getLength(); i++) {
            Node s = spaceList.item(i);
            if (s.getNodeType() == Node.ELEMENT_NODE) {
                Element space = (Element) s;
                int index = Integer.parseInt(space.getElementsByTagName(INDEX_TAG).item(0).getTextContent());
                String spaceGroupString = space.getElementsByTagName(SPACE_GROUP_TAG).item(0).getTextContent().strip();
                String spaceName = space.getElementsByTagName(SPACE_NAME_TAG).item(0).getTextContent().strip();
                String extraString = space.getElementsByTagName(EXTRA_STRINGS_TAG).item(0).getTextContent().strip();
                String[] extraDubTemp = space.getElementsByTagName(EXTRA_DOUBLES_TAG).item(0).getTextContent().split(",");

                List<Double> extraDubs = listDoubleConverter(extraDubTemp);

                Property myProp = findLinkedProperty(propsList, spaceName);

                String className = space.getAttribute(TYPE_TAG);

                //Reflection to create properties
                try {
                    AbstractSpace newSpace = (AbstractSpace) Class.forName(SPACES_PATH_TAG + className).getConstructor(int.class, String.class, String.class, String.class, List.class, Property.class).newInstance(index,
                            spaceName, spaceGroupString, extraString, extraDubs, myProp);
                    //newSpace.setMyProp(myProp);
                    allSpaces.add(newSpace);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
        return allSpaces;
    }

    private Property findLinkedProperty(List<Property> allProperties, String name){
        HashMap<String, Property> allPropsAndNames = new HashMap<>();
        for(Property prop:allProperties){
            allPropsAndNames.put(prop.getName(), prop);
        }
        if(allPropsAndNames.containsKey(name)){
            return allPropsAndNames.get(name);
        }
        else{
            return null;
        }
    }

    public List<List> parseSpaces(){
        List<List> allSpacesAndProps = new ArrayList<>();
        List<Property> allProps = parseAllProps();
        List<AbstractSpace> allSpaces = parseNewSpaces(allProps);

        allSpacesAndProps.add(allSpaces);
        allSpacesAndProps.add(allProps);


        return allSpacesAndProps;
    }


    public List<String> parseTokens() throws XmlReaderException {
        List<String> allTokens = new ArrayList<>();

        NodeList tokenList = doc.getElementsByTagName(TOKEN_TAG);
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
            throw new XmlReaderException("BadTag");
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

    public boolean getRuleBool(String attribute){
        NodeList list = doc.getElementsByTagName(attribute);
        Node node = list.item(0);
        if(node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String stringValue = element.getTextContent();
            return Boolean.parseBoolean(stringValue);
        }
        return false;
    }

    public List<Map<BuildingType, Integer>> getBuildingProperties() {
        List<Map<BuildingType, Integer>> buildingProperties = new ArrayList<>();
        Map<BuildingType, Integer> buildingTotalAmount = new TreeMap<>();
        Map<BuildingType, Integer> buildingMaxAmount = new TreeMap<>();


        NodeList buildingList = doc.getElementsByTagName(BUILDING_TYPE_TAG);
        for (int x = 0; x < buildingList.getLength(); x++) {
            Node bL = buildingList.item(x);
            if (bL.getNodeType() == Node.ELEMENT_NODE) {
                Element building = (Element) bL;
                BuildingType bType = BuildingType.valueOf(building.getAttribute("type"));
                int total = Integer.parseInt(building.getElementsByTagName(TOTAL_AMOUNT_TAG).item(0).getTextContent());
                buildingTotalAmount.put(bType, total);
                int max = Integer.parseInt(building.getElementsByTagName(MAX_AMOUNT_TAG).item(0).getTextContent());
                buildingMaxAmount.put(bType, max);
            }
        }

        buildingProperties.add(buildingTotalAmount);
        buildingProperties.add(buildingMaxAmount);
        return buildingProperties;
    }

    //Helper method converts list of strings to list of doubles for parsing
    private List<Double> listDoubleConverter(String[] stringList){
        List<Double> doubleList = new ArrayList<>();
        for(String n:stringList){
            doubleList.add(Double.parseDouble(n));
        }
        return doubleList;
    }

    //Helper method to check if deck type found is a legal deck type enum -- for error handling
    private boolean checkDeckType(String deckName){
        for(DeckType dtype : DeckType.values()) {
            if (dtype.name().equalsIgnoreCase(deckName)) {
                return true;
            }
        }
        return false;
    }

    //Helper method to check if building type found is a legal building type enum -- for error handling
    private boolean checkBuildingType(String buildingType){
        for(BuildingType btype : BuildingType.values()) {
            if (btype.name().equalsIgnoreCase(buildingType)) {
                return true;
            }
        }
        return false;
    }

    //Helper method to check if tag name being searched for is in the xml documnet -- for error handling
    private boolean checkTagName(String tagName){
        NodeList listCheck = doc.getElementsByTagName(tagName);
        System.out.println(listCheck.getLength());
        //If length == 0, return false because tagName not in xml
        return (listCheck.getLength() != 0);
    }

    public static void main(String[] args) {
        ConfigReader c = new ConfigReader("Junior_Config.xml");
        try{
            c.parseSpaces();
            c.parseActionCards();
            c.parseActionDecks();
            /*c.parseBank();
            c.parseBoard();
            c.parseDice();
            c.parseTokens();
            c.getBuildingProperties();*/
        }
        catch(XmlReaderException e){
        }
    }

}


