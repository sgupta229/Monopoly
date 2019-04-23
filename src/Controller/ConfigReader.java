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

    public int parseBoard() throws XmlReaderException {
        int boardSize = Integer.parseInt(doc.getElementsByTagName("BoardSize").item(0).getTextContent());
        return boardSize;
    }

    public List<Double> parseBank() throws XmlReaderException {
        List<Double> bankInfo = new ArrayList<>();
        double bankFunds = Double.parseDouble(doc.getElementsByTagName("BankFunds").item(0).getTextContent());
        //double numHouses = Double.parseDouble(doc.getElementsByTagName("Houses").item(0).getTextContent());
        //double numHotels = Double.parseDouble(doc.getElementsByTagName("Hotels").item(0).getTextContent());
        //double maxNumHouses = Double.parseDouble(doc.getElementsByTagName("MaxHouses").item(0).getTextContent());

        bankInfo.add(bankFunds);
        //bankInfo.add(numHouses);
        //bankInfo.add(numHotels);
        //bankInfo.add(maxNumHouses);
        //System.out.println(maxNumHouses);
        return bankInfo;
    }

    public List<Die> parseDice() throws XmlReaderException {
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

    public List<ActionDeck> parseActionDecks() throws XmlReaderException {
        List<ActionDeck> decks = new ArrayList<>();

        NodeList actionDeckList = doc.getElementsByTagName("ActionDeck");

        for(int i=0; i<actionDeckList.getLength(); i++){
            Node ad = actionDeckList.item(i);
            if(ad.getNodeType() == Node.ELEMENT_NODE){
                Element deck = (Element) ad;
                //String deckName = deck.getElementsByTagName("ActionDeck").item(0).getTextContent();
                String deckName = deck.getTextContent();
                //Check if deckName in DeckType enum before getting enum
                if(!checkDeckType(deckName)){
                    throw new XmlReaderException(deckName + "not a valid action deck. Please check the data file.");
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

        NodeList actionCardList = doc.getElementsByTagName("ActionCardType");
        for(int i = 0; i<actionCardList.getLength(); i++){
            Node ac = actionCardList.item(i);
            if(ac.getNodeType() == Node.ELEMENT_NODE){
                Element card = (Element) ac;
                String deckName = card.getElementsByTagName("DeckType").item(0).getTextContent();
                if(!checkDeckType(deckName)){
                    throw new XmlReaderException(deckName + "not a valid action deck. Please check the data file.");
                }

                //All types of action cards have these fields
                DeckType dt = DeckType.valueOf(card.getElementsByTagName("DeckType").item(0).getTextContent());
                String msg = card.getElementsByTagName("Message").item(0).getTextContent();
                //http://www.java67.com/2018/03/java-convert-string-to-boolean.html
                Boolean holdable = Boolean.parseBoolean(card.getElementsByTagName("Holdable").item(0).getTextContent());
                List<String> extraStrings = List.of(card.getElementsByTagName("ExtraString").item(0).getTextContent().split(","));
                //System.out.println(extraStrings.get(0) + " AND " + extraStrings.get(1));
                //Get list of doubles
                String[] extraDubTemp = card.getElementsByTagName("ExtraDoubles").item(0).getTextContent().split(",");
/*                List<Double> extraDubs = new ArrayList<>();
                for(String n:extraDubTemp){
                    extraDubs.add(Double.parseDouble(n));
                }*/
                List<Double> extraDubs = listDoubleConverter(extraDubTemp);


                String className = card.getAttribute("type");
                //Reflection to create action cards
                try {
                    AbstractActionCard newAC = (AbstractActionCard) Class.forName("Model.actioncards." + className).getConstructor(DeckType.class, String.class, Boolean.class, List.class, List.class).newInstance(dt, msg, holdable, extraStrings, extraDubs);
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

/*    private List<Integer> loopThroughChildren(String tag, int index){
        ArrayList<Integer> numbers = new ArrayList<>();
        NodeList rentList = doc.getElementsByTagName(tag);
        Node thisValue = rentList.item(index);
        NodeList nodeNums = thisValue.getChildNodes();
        for(int x=0; x<nodeNums.getLength(); x++){
            Node currNum = nodeNums.item(x);
            int rentVal = Integer.parseInt(currNum.getTextContent());
            numbers.add(rentVal);
        }
        return numbers;
    }*/

    public List<Property> parseAllProps(){
        List<Property> propsList = new ArrayList<>();
        NodeList propTypeList = doc.getElementsByTagName("Property");
        for(int i = 0; i<propTypeList.getLength(); i++){
            Node ac = propTypeList.item(i);
            if(ac.getNodeType() == Node.ELEMENT_NODE){
                Element prop = (Element) ac;
                String name = prop.getElementsByTagName("PropertyName").item(0).getTextContent();
                //http://www.java67.com/2018/03/java-convert-string-to-boolean.html
                String color = prop.getElementsByTagName("ColorGroup").item(0).getTextContent();
                int groupSize = Integer.parseInt(prop.getElementsByTagName("GroupSize").item(0).getTextContent());
                double buyPrice = Double.parseDouble(prop.getElementsByTagName("BuyPrice").item(0).getTextContent());

                //get all rent numbers
                //Node rentNode = doc.getDocumentElement();
                ArrayList<Double> rentNumbers = new ArrayList<>();
                NodeList rentList = doc.getElementsByTagName("Rent");
                //System.out.println(rentList.getLength());
                Node thisRent1 = rentList.item(i);
                Element thisRent = (Element) thisRent1;
                NodeList rentNums = thisRent.getChildNodes();
                //System.out.println(rentNums.getLength());
                //System.out.println(name);
                for(int x=1; x<rentNums.getLength(); x=x+2){
                    Node currRentNum = rentNums.item(x);
                    //System.out.println(currRentNum.getNodeName());
                    //System.out.println(currRentNum.getTextContent());
                    Double rentVal = Double.parseDouble(currRentNum.getTextContent());
                    //System.out.println("RentVal is " + rentVal);
                    rentNumbers.add(rentVal);
                }

                //get all building numbers
                Map<BuildingType, Double> BuildingPrices = new TreeMap<>();
                NodeList priceList = doc.getElementsByTagName("BuildingPrices");
                Node thisPrice = priceList.item(i);
                NodeList priceNums = thisPrice.getChildNodes();
                for(int x=1; x<priceNums.getLength(); x=x+2){
                    Node currPriceNum = priceNums.item(x);
                    Element c = (Element) currPriceNum;
                    BuildingType bType = (BuildingType.valueOf(c.getAttribute("type")));
                    double priceVal = Double.parseDouble(currPriceNum.getTextContent());
                    BuildingPrices.put(bType, priceVal);
                }

                String className = prop.getAttribute("type");
                //Reflection to create properties
                try {
                    Property newProp = (Property) Class.forName("Model.properties." + className).getConstructor(double.class, String.class, String.class, List.class, int.class, Map.class).newInstance(buyPrice,
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

        NodeList spaceList = doc.getElementsByTagName("Space");
        for(int i = 0; i < spaceList.getLength(); i++) {
            Node s = spaceList.item(i);
            if (s.getNodeType() == Node.ELEMENT_NODE) {
                Element space = (Element) s;
                int index = Integer.parseInt(space.getElementsByTagName("Index").item(0).getTextContent());
                String spaceGroupString = space.getElementsByTagName("SpaceGroup").item(0).getTextContent().strip();
                SpaceGroup spaceGroup = SpaceGroup.valueOf(spaceGroupString);
                String spaceName = space.getElementsByTagName("SpaceName").item(0).getTextContent().strip();
                String extraString = space.getElementsByTagName("ExtraString").item(0).getTextContent().strip();
                String[] extraDubTemp = space.getElementsByTagName("ExtraDoubles").item(0).getTextContent().split(",");
/*                List<Double> extraDubs = new ArrayList<>();
                for(String n:extraDubTemp){
                    extraDubs.add(Double.parseDouble(n));
                }*/
                List<Double> extraDubs = listDoubleConverter(extraDubTemp);

                Property myProp = findLinkedProperty(propsList, spaceName);

                String className = space.getAttribute("type");
                //System.out.println(className);
                //Reflection to create properties
                try {
                    AbstractSpace newSpace = (AbstractSpace) Class.forName("Model.spaces." + className).getConstructor(int.class, String.class, String.class, String.class, List.class, Property.class).newInstance(index,
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

    private boolean checkDeckType(String deckName){
        int counter = DeckType.values().length;
        for(DeckType dtype : DeckType.values()) {
            if (dtype.name().equalsIgnoreCase(deckName)) {
                return true;
            }
        }
        return false;
    }

    public List<List> parseSpaces(){
        List<List> allSpacesAndProps = new ArrayList<>();
        List<Property> allProps = parseAllProps();
        List<AbstractSpace> allSpaces = parseNewSpaces(allProps);

        /*Map<String, ArrayList> propInfo = new HashMap<String, ArrayList>();


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


                    Property newProp = new ClassicColorProperty(buyPrice, spaceName, colorGroup, rentAmounts, groupSize, buildingPriceMap);
                    AbstractSpace newSpace = new ClassicPropSpace(index, spaceName, newProp);
                    allSpaces.add(newSpace);
                    //((ClassicPropSpace) newSpace).linkSpaceToProperty(newProp);
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
                    Property newProp = new RailRoadProperty(buyPrice, spaceName, rentAmounts, groupSize);
                    //((ClassicPropSpace) newSpace).linkSpaceToProperty(newProp);
                    AbstractSpace newSpace = new ClassicPropSpace(index, spaceName, newProp);
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
                    Property newProp = new UtilityProperty(buyPrice, spaceName, rentAmounts, groupSize);
                    //((ClassicPropSpace) newSpace).linkSpaceToProperty(newProp);
                    AbstractSpace newSpace = new ClassicPropSpace(index, spaceName, newProp);
                    allSpaces.add(newSpace);
                    allProps.add(newProp);
                    newSpace.setMyGroup(SpaceGroup.valueOf(space.getAttribute("type").split("_")[0]));
                }
//                else{
//                    throw new XmlReaderException(space.getAttribute("type"));
//                }
            }
        }*/
        allSpacesAndProps.add(allSpaces);
        allSpacesAndProps.add(allProps);


        return allSpacesAndProps;
    }

    /*public Map<Integer, ArrayList> parseColorPropInfo() {
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
    }*/


    public List<String> parseTokens() throws XmlReaderException {
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

    private List<Double> listDoubleConverter(String[] stringList){
        List<Double> doubleList = new ArrayList<>();
        for(String n:stringList){
            doubleList.add(Double.parseDouble(n));
        }
        return doubleList;
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


