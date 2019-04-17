package Controller;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
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

    public List<Double> parseBank() throws XmlTagException{
        List<Double> bankInfo = new ArrayList<>();
        double bankFunds = Double.parseDouble(doc.getElementsByTagName("BankFunds").item(0).getTextContent());
        double numHouses = Double.parseDouble(doc.getElementsByTagName("Houses").item(0).getTextContent());
        double numHotels = Double.parseDouble(doc.getElementsByTagName("Hotels").item(0).getTextContent());
        double maxNumHouses = Double.parseDouble(doc.getElementsByTagName("MaxHouses").item(0).getTextContent());

        bankInfo.add(bankFunds);
        bankInfo.add(numHouses);
        bankInfo.add(numHotels);
        bankInfo.add(maxNumHouses);
        System.out.println(maxNumHouses);
        return bankInfo;
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

//    public List<AbstractActionCard> parseActionCards() throws XmlTagException{
    public List<AbstractActionCard> parseActionCards(){
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
                String extraString = card.getElementsByTagName("ExtraString").item(0).getTextContent();
                //Get list of doubles
                String[] extraDubTemp = card.getElementsByTagName("ExtraDoubles").item(0).getTextContent().split(",");
                List<Double> extraDubs = new ArrayList<>();
                for(String n:extraDubTemp){
                    extraDubs.add(Double.parseDouble(n));
                }

                String className = card.getAttribute("type");
                //Reflection to create action cards
                try {
                    AbstractActionCard newAC = (AbstractActionCard) Class.forName("Model.actioncards." + className).getConstructor(DeckType.class, String.class, Boolean.class, String.class, List.class).newInstance(dt, msg, holdable, extraString, extraDubs);
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

    public List<List> parseSpaces(){
        List<List> allSpacesAndProps = new ArrayList<>();
        List<AbstractSpace> allSpaces = new ArrayList<>();
        List<Property> allProps = new ArrayList<>();
        Map<String, ArrayList> propInfo = new HashMap<String, ArrayList>();


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
                    newSpace.setMyGroup(SpaceGroup.valueOf(space.getAttribute("type")));
                }
                else if(space.getAttribute("type").equalsIgnoreCase("FREE_PARKING")) {
                    AbstractSpace newSpace = new FreeParkingSpace(index, spaceName);
                    allSpaces.add(newSpace);
                    newSpace.setMyGroup(SpaceGroup.valueOf(space.getAttribute("type")));
                }
                else if(space.getAttribute("type").equalsIgnoreCase("GO")) {
                    AbstractSpace newSpace = new GoSpace(index, spaceName);
                    allSpaces.add(newSpace);
                    newSpace.setMyGroup(SpaceGroup.valueOf(space.getAttribute("type")));
                }
                else if(space.getAttribute("type").equalsIgnoreCase("GO_TO_JAIL")) {
                    String spaceToMoveTo = space.getElementsByTagName("TargetSpace").item(0).getTextContent();
                    AbstractSpace newSpace = new GoToSpace(index, spaceName, spaceToMoveTo);
                    allSpaces.add(newSpace);
                    newSpace.setMyGroup(SpaceGroup.valueOf(space.getAttribute("type")));
                }
                else if(space.getAttribute("type").equalsIgnoreCase("JAIL")) {
                    AbstractSpace newSpace = new JailSpace(index, spaceName);
                    allSpaces.add(newSpace);
                    newSpace.setMyGroup(SpaceGroup.valueOf(space.getAttribute("type")));
                }
                else if(space.getAttribute("type").equalsIgnoreCase("TAX")) {
                    double flatRate = Double.parseDouble(space.getElementsByTagName("FlatRate").item(0).getTextContent());
                    double percentage = Double.parseDouble(space.getElementsByTagName("Percentage").item(0).getTextContent());
                    double newPercent = percentage/100;
                    AbstractSpace newSpace = new TaxSpace(index, spaceName, flatRate, newPercent);
                    allSpaces.add(newSpace);
                    newSpace.setMyGroup(SpaceGroup.valueOf(space.getAttribute("type")));

                }
                else if(space.getAttribute("type").equalsIgnoreCase("COLOR_PROPERTY")) {

                    String colorGroup = space.getElementsByTagName("ColorGroup").item(0).getTextContent();
                    int groupSize = Integer.parseInt(space.getElementsByTagName("GroupSize").item(0).getTextContent());
                    double buyPrice = Double.parseDouble(space.getElementsByTagName("BuyPrice").item(0).getTextContent());
                    double rent = Double.parseDouble(space.getElementsByTagName("Rent").item(0).getTextContent());
                    double rentOneHouse = Double.parseDouble(space.getElementsByTagName("Rent1House").item(0).getTextContent());
                    double rentTwoHouse = Double.parseDouble(space.getElementsByTagName("Rent2House").item(0).getTextContent());
                    double rentThreeHouse = Double.parseDouble(space.getElementsByTagName("Rent3House").item(0).getTextContent());
                    double rentFourHouse = Double.parseDouble(space.getElementsByTagName("Rent4House").item(0).getTextContent());
                    double rentHotel = Double.parseDouble(space.getElementsByTagName("RentHotel").item(0).getTextContent());
                    double pricePerHouse = Double.parseDouble(space.getElementsByTagName("PricePerHouse").item(0).getTextContent());
                    double pricePerHotel = Double.parseDouble(space.getElementsByTagName("PricePerHotel").item(0).getTextContent());
                    double mortgage = Double.parseDouble(space.getElementsByTagName("Mortgage").item(0).getTextContent());
                    ArrayList<Double> rentAmounts = new ArrayList<>();
                    rentAmounts.add(rent);
                    rentAmounts.add(rentOneHouse);
                    rentAmounts.add(rentTwoHouse);
                    rentAmounts.add(rentThreeHouse);
                    rentAmounts.add(rentFourHouse);
                    rentAmounts.add(rentHotel);
                    rentAmounts.add(pricePerHouse);
                    rentAmounts.add(pricePerHotel);
                    rentAmounts.add(mortgage);
                    TreeMap<BuildingType, Double> buildingPriceMap = new TreeMap<>();
                    buildingPriceMap.put(BuildingType.valueOf("HOUSE"), pricePerHouse);
                    buildingPriceMap.put(BuildingType.valueOf("HOTEL"), pricePerHotel);


                    Property newProp = new ColorProperty(buyPrice, spaceName, colorGroup, rentAmounts, groupSize, buildingPriceMap);
                    AbstractSpace newSpace = new PropSpace(index, spaceName, newProp);
                    allSpaces.add(newSpace);
                    //((PropSpace) newSpace).linkSpaceToProperty(newProp);
                    allProps.add(newProp);
                    newSpace.setMyGroup(SpaceGroup.valueOf(space.getAttribute("type").split("_")[0]));

                }
                else if(space.getAttribute("type").equalsIgnoreCase("RAILROAD_PROPERTY")) {

                    int groupSize = Integer.parseInt(space.getElementsByTagName("GroupSize").item(0).getTextContent());
                    double buyPrice = Double.parseDouble(space.getElementsByTagName("BuyPrice").item(0).getTextContent());
                    double rent = Double.parseDouble(space.getElementsByTagName("Rent").item(0).getTextContent());
                    double rent2 = Double.parseDouble(space.getElementsByTagName("Rent2").item(0).getTextContent());
                    double rent3 = Double.parseDouble(space.getElementsByTagName("Rent3").item(0).getTextContent());
                    double rent4 = Double.parseDouble(space.getElementsByTagName("Rent4").item(0).getTextContent());
                    double mortgage = Double.parseDouble(space.getElementsByTagName("Mortgage").item(0).getTextContent());
                    ArrayList<Double> rentAmounts = new ArrayList<>();
                    rentAmounts.add(rent);
                    rentAmounts.add(rent2);
                    rentAmounts.add(rent3);
                    rentAmounts.add(rent4);
                    rentAmounts.add(mortgage);
                    TreeMap<BuildingType, Double> buildingPriceMap = new TreeMap<>();
                    Property newProp = new RailRoadProperty(buyPrice, spaceName, rentAmounts, groupSize, buildingPriceMap);
                    //((PropSpace) newSpace).linkSpaceToProperty(newProp);
                    AbstractSpace newSpace = new PropSpace(index, spaceName, newProp);
                    allSpaces.add(newSpace);
                    allProps.add(newProp);
                    newSpace.setMyGroup(SpaceGroup.valueOf(space.getAttribute("type").split("_")[0]));

                }
                else if(space.getAttribute("type").equalsIgnoreCase("UTILITY_PROPERTY")) {

                    double buyPrice = Double.parseDouble(space.getElementsByTagName("BuyPrice").item(0).getTextContent());
                    double rentMult = Double.parseDouble(space.getElementsByTagName("RentMultiplier").item(0).getTextContent());
                    double rentMult2 = Double.parseDouble(space.getElementsByTagName("Rent2Multiplier").item(0).getTextContent());
                    double mortgage = Double.parseDouble(space.getElementsByTagName("Mortgage").item(0).getTextContent());
                    int groupSize = Integer.parseInt(space.getElementsByTagName("GroupSize").item(0).getTextContent());
                    ArrayList<Double> rentAmounts = new ArrayList<>();
                    rentAmounts.add(rentMult);
                    rentAmounts.add(rentMult2);
                    rentAmounts.add(mortgage);
                    TreeMap<BuildingType, Double> buildingPriceMap = new TreeMap<>();
                    Property newProp = new UtilityProperty(buyPrice, spaceName, rentAmounts, groupSize, buildingPriceMap);
                    //((PropSpace) newSpace).linkSpaceToProperty(newProp);
                    AbstractSpace newSpace = new PropSpace(index, spaceName, newProp);
                    allSpaces.add(newSpace);
                    allProps.add(newProp);
                    newSpace.setMyGroup(SpaceGroup.valueOf(space.getAttribute("type").split("_")[0]));
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

    public Map<Integer, ArrayList> parseColorPropInfo() {
        Map<Integer, ArrayList> propInfo = new HashMap<Integer, ArrayList>();
        NodeList spaceList = doc.getElementsByTagName("Space");
        for (int i = 0; i < spaceList.getLength(); i++) {
            Node s = spaceList.item(i);
            if (s.getNodeType() == Node.ELEMENT_NODE) {
                Element space = (Element) s;
                String spaceName = space.getElementsByTagName("SpaceName").item(0).getTextContent().strip();
                int index = Integer.parseInt(space.getElementsByTagName("Index").item(0).getTextContent());
                if (space.getAttribute("type").equalsIgnoreCase("COLOR_PROPERTY")) {
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
                    ArrayList details = new ArrayList();
                    details.addAll(Arrays.asList(colorGroup, buyPrice, rent, rentOneHouse, rentTwoHouse, rentThreeHouse, rentFourHouse, rentHotel, pricePerHouse, mortgage, spaceName));
                    propInfo.put(index, details);
                } else if (space.getAttribute("type").equalsIgnoreCase("RAILROAD_PROPERTY")) {
                    double buyPrice = Double.parseDouble(space.getElementsByTagName("BuyPrice").item(0).getTextContent());
                    double rent = Double.parseDouble(space.getElementsByTagName("Rent").item(0).getTextContent());
                    double rent2 = Double.parseDouble(space.getElementsByTagName("Rent2").item(0).getTextContent());
                    double rent3 = Double.parseDouble(space.getElementsByTagName("Rent3").item(0).getTextContent());
                    double rent4 = Double.parseDouble(space.getElementsByTagName("Rent4").item(0).getTextContent());
                    double mortgage = Double.parseDouble(space.getElementsByTagName("Mortgage").item(0).getTextContent());
                    ArrayList details = new ArrayList();
                    details.addAll(Arrays.asList(buyPrice, rent, rent2, rent3, rent4, mortgage, spaceName));
                    propInfo.put(index, details);
                } else if (space.getAttribute("type").equalsIgnoreCase("UTILITY_PROPERTY")){
                    double buyPrice = Double.parseDouble(space.getElementsByTagName("BuyPrice").item(0).getTextContent());
                    double rentMult = Double.parseDouble(space.getElementsByTagName("RentMultiplier").item(0).getTextContent());
                    double rentMult2 = Double.parseDouble(space.getElementsByTagName("Rent2Multiplier").item(0).getTextContent());
                    double mortgage = Double.parseDouble(space.getElementsByTagName("Mortgage").item(0).getTextContent());
                    ArrayList details = new ArrayList();
                    details.addAll(Arrays.asList(buyPrice, rentMult, rentMult2, mortgage, spaceName));
                    propInfo.put(index, details);
                } else if (space.getAttribute("type").equalsIgnoreCase("TAX")){
                    double flatRate = Double.parseDouble(space.getElementsByTagName("FlatRate").item(0).getTextContent());
                    double percentage = Double.parseDouble(space.getElementsByTagName("Percentage").item(0).getTextContent());
                    double newPercent = percentage/100;
                    ArrayList details = new ArrayList();
                    details.addAll(Arrays.asList(flatRate, percentage, newPercent));
                    propInfo.put(index, details);
                }
            }
        }
        return propInfo;
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


        NodeList buildingList = doc.getElementsByTagName("BuildingType");
        for (int x = 0; x < buildingList.getLength(); x++) {
            Node bL = buildingList.item(x);
            if (bL.getNodeType() == Node.ELEMENT_NODE) {
                Element building = (Element) bL;
                BuildingType bType = BuildingType.valueOf(building.getAttribute("type"));
                int total = Integer.parseInt(building.getElementsByTagName("TotalAmount").item(0).getTextContent());
                buildingTotalAmount.put(bType, total);
                int max = Integer.parseInt(building.getElementsByTagName("MaxAmount").item(0).getTextContent());
                buildingMaxAmount.put(bType, max);
            }
        }

        buildingProperties.add(buildingTotalAmount);
        buildingProperties.add(buildingMaxAmount);
        return buildingProperties;
    }

    public static void main(String[] args) {
        ConfigReader c = new ConfigReader("Normal_Config.xml");
        try{
            c.parseSpaces();
            c.parseActionCards();
            c.parseActionDecks();
            c.parseBank();
            c.parseBoard();
            c.parseDice();
            c.parseTokens();
            c.getBuildingProperties();
        }
        catch(XmlTagException e){
        }
    }

}


