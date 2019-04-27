package Controller;

import Model.XmlReaderException;
import Model.actioncards.AbstractActionCard;
import Model.actioncards.ActionDeck;
import Model.actioncards.DeckType;
import Model.properties.BuildingType;
import Model.properties.Property;
import Model.spaces.AbstractSpace;
import Model.spaces.SpaceGroup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.*;

/*
import java.util.logging;
*/

//imports

public class ConfigReader {
    private static final String BOARD_SIZE_TAG = "BoardSize";
    private static final String GAME_TYPE_TAG = "GameType";
    private static final String INDEX_COORD_TAG = "IndexToCoord";
    private static final String DISPLAY_FILE_TAG = "Display";
    private static final String POPUP_FILE_TAG = "PopUpText";
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

    //private DocumentBuilder dBuilder;
    private Document doc;
    private ConfigReaderErrorHandling errorChecker;
    private int BoardSize;
    private Map<Exception, List<String>> ExceptionLog;

    //https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
    public ConfigReader(String filename) throws XmlReaderException{
        //File inputFile = new File(filename);
        if(checkFileExists(filename, ".xml")){
            try {
                File inputFile = new File(this.getClass().getClassLoader().getResource(filename).toURI());
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                doc = dBuilder.parse(inputFile);
                errorChecker = new ConfigReaderErrorHandling(doc);
                ExceptionLog = new HashMap<>();
                //errorChecker.checkFileExists(filename);
            }
            catch (ParserConfigurationException | IOException | URISyntaxException  | SAXException e ) {
                logException(e, e.getMessage());
                throw new XmlReaderException(e.getMessage());
            }
/*            catch(IOException e) {
                logException(e, e.getMessage());
                throw new XmlReaderException(e.getMessage());*/
/*            } catch (URISyntaxException e) {
                logException(e, e.getMessage());
                throw new XmlReaderException(e.getMessage());
            } *//*catch (SAXException e) {
                logException(e, e.getMessage());
                throw new XmlReaderException(e.getMessage());
            }*/
        }
        else{
            throw new XmlReaderException(filename + " is not a valid name for a config file.");
        }
    }

    public String parseGameType(){
        String gameType = doc.getElementsByTagName(GAME_TYPE_TAG).item(0).getTextContent();
        return gameType;
    }

    /**
     * Get board size while checking for valid inputs
     * @return return board size
     * @throws XmlReaderException check valid tag name and values are numerics
     */
    public int parseBoard() throws XmlReaderException {
        if(errorChecker.checkTagName(BOARD_SIZE_TAG)){
            String boardSize = doc.getElementsByTagName(BOARD_SIZE_TAG).item(0).getTextContent();
            if(errorChecker.checkValuesAreNumbers(boardSize)){
                BoardSize = Integer.parseInt(boardSize);
                return BoardSize;
            }
            else{
                throw getXmlReadNumberException(BOARD_SIZE_TAG);
            }
        }
        else{
            throw getXmlReaderException(BOARD_SIZE_TAG);
        }
    }

    /**
     * Get property files for front end use (check valid inputs)
     * @return list of file names
     * @throws XmlReaderException check valid tag name and file exists
     */
    public List<String> parseOtherFiles() throws XmlReaderException {
        if(errorChecker.checkTagName(List.of(INDEX_COORD_TAG, DISPLAY_FILE_TAG, POPUP_FILE_TAG)).equalsIgnoreCase("VALID")){
            List<String> frontEndFiles = new ArrayList<>();
            frontEndFiles.add(doc.getElementsByTagName(INDEX_COORD_TAG).item(0).getTextContent());
            frontEndFiles.add(doc.getElementsByTagName(DISPLAY_FILE_TAG).item(0).getTextContent());
            frontEndFiles.add(doc.getElementsByTagName(POPUP_FILE_TAG).item(0).getTextContent());
            for(String filename: frontEndFiles){
                if(!checkFileExists(filename + ".properties", ".properties")){
                    throw new XmlReaderException(filename + " is not a valid file found in doc directory. Check xml config file");
                }
            }
            return frontEndFiles;
        }
        else{
            throw getXmlReaderException(INDEX_COORD_TAG + " and/or " + DISPLAY_FILE_TAG + " and/or " + POPUP_FILE_TAG);
        }
    }

    /**
     * Get bank info (starting funds) while checking for valid inputs
     * @return List of bank information
     * @throws XmlReaderException check valid tag names and values are numerics
     */
    public List<Double> parseBank() throws XmlReaderException {
        if(errorChecker.checkTagName(BANK_FUNDS_TAG)){
            List<Double> bankInfo = new ArrayList<>();
            String bankFunds = doc.getElementsByTagName(BANK_FUNDS_TAG).item(0).getTextContent();
            if(errorChecker.checkValuesAreNumbers(bankFunds)){
                double bankFundsInt = Double.parseDouble(bankFunds);
                bankInfo.add(bankFundsInt);
                return bankInfo;
            }
            else{
                throw getXmlReadNumberException(BANK_FUNDS_TAG);
            }
        }
        else{
            throw getXmlReaderException(BANK_FUNDS_TAG);
        }
    }

    /**
     * Get dice information (number of dice and number of sides) while checking for valid inputs
     * @return list of die inormation
     * @throws XmlReaderException check valid tag names and inputs are numerics
     */
    public List<Die> parseDice() throws XmlReaderException {
        if(!errorChecker.checkTagName(DICE_SIDES_TAG)){
            throw getXmlReaderException(DICE_SIDES_TAG);
        }
        if(!errorChecker.checkTagName(DICE_NUMBER_TAG)){
            throw getXmlReaderException(DICE_NUMBER_TAG);
        }
        List<Die> dice = new ArrayList<>();

        //Check valid input
        String numDice = doc.getElementsByTagName(DICE_NUMBER_TAG).item(0).getTextContent();
        String numSides = doc.getElementsByTagName(DICE_SIDES_TAG).item(0).getTextContent();
        List<String> checkList = List.of(numDice, numSides);
        for(String s: checkList){
            if(!errorChecker.checkValuesAreNumbers(s)){
                throw getXmlReadNumberException(DICE_NUMBER_TAG + " and/or " + DICE_SIDES_TAG);
            }
        }

        int numberOfDice = Integer.parseInt(numDice);
        int numberOfSides = Integer.parseInt(numSides);

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

    /**
     * Get the types of decks from xml while checking they are valid enums
     * @return list of action decks
     * @throws XmlReaderException check valid tag name and deck type
     */
    public List<ActionDeck> parseActionDecks() throws XmlReaderException {
        if(!errorChecker.checkTagName(ACTION_DECK_TAG)){
            throw getXmlReaderException(ACTION_DECK_TAG);
        }
        List<ActionDeck> decks = new ArrayList<>();

        NodeList actionDeckList = doc.getElementsByTagName(ACTION_DECK_TAG);

        for(int i=0; i<actionDeckList.getLength(); i++){
            Node ad = actionDeckList.item(i);
            if(ad.getNodeType() == Node.ELEMENT_NODE){
                Element deck = (Element) ad;
                //String deckName = deck.getElementsByTagName("ActionDeck").item(0).getTextContent();
                String deckName = deck.getTextContent();
                //Check if deckName in DeckType enum before getting enum
                //if(errorChecker.checkDeckType(deckName)){
                if(errorChecker.checkEnums(deckName, DeckType.values())){
                    DeckType dt = DeckType.valueOf(deckName);
                    ActionDeck tempDeck = new ActionDeck(dt);
                    decks.add(tempDeck);
                }
                else{
                    throw new XmlReaderException(deckName + " is not a valid DeckType enum. Please check the data file.");
                }
            }
        }
        return decks;
    }


    /**
     * Get list of all action cards from xml checking for valid inputs
     * Fill action decks later on
     * @return list of abstract action cards
     * @throws XmlReaderException checks valid tag name; valid deck types; move to targets are valid
     */
    public List<AbstractActionCard> parseActionCards() throws XmlReaderException {
        String errorCheck = errorChecker.checkTagName(List.of(ACTION_CARD_TAG, DECK_TYPE_TAG, MESSAGE_TAG, HOLDABLE_TAG, EXTRA_STRINGS_TAG, EXTRA_DOUBLES_TAG));
        if(errorCheck.equalsIgnoreCase("VALID")){
            //Feed this allActionCards list into fillLiveDeck() in deck class after initializing empty decks
            List<AbstractActionCard> allActionCards = new ArrayList<>();
            NodeList actionCardList = doc.getElementsByTagName(ACTION_CARD_TAG);
            for(int i = 0; i<actionCardList.getLength(); i++){
                Node ac = actionCardList.item(i);
                if(ac.getNodeType() == Node.ELEMENT_NODE){
                    Element card = (Element) ac;
                    String deckName = card.getElementsByTagName(DECK_TYPE_TAG).item(0).getTextContent();
                    //if(errorChecker.checkDeckType(deckName)){
                    if(errorChecker.checkEnums(deckName, DeckType.values())){
                        //All types of action cards have these fields
                       // DeckType dt = DeckType.valueOf(card.getElementsByTagName(DECK_TYPE_TAG).item(0).getTextContent());
                        String msg = card.getElementsByTagName(MESSAGE_TAG).item(0).getTextContent();
                        //http://www.java67.com/2018/03/java-convert-string-to-boolean.html
                        Boolean holdable = Boolean.parseBoolean(card.getElementsByTagName(HOLDABLE_TAG).item(0).getTextContent());
                        List<String> extraStrings = List.of(card.getElementsByTagName(EXTRA_STRINGS_TAG).item(0).getTextContent().split(","));
                        //System.out.println(extraStrings.get(0) + " AND " + extraStrings.get(1));
                        //Get list of doubles
                        String[] extraDubTemp = card.getElementsByTagName(EXTRA_DOUBLES_TAG).item(0).getTextContent().split(",");
                        helpCheckDoublesTag(List.of(extraDubTemp));
                        List<Double> extraDubs = listDoubleConverter(extraDubTemp);
                        String className = card.getAttribute(TYPE_TAG);
                        DeckType dt = DeckType.valueOf(card.getElementsByTagName(DECK_TYPE_TAG).item(0).getTextContent());
                        if(!errorChecker.checkMoveToTargets(className, extraStrings)){
                            throw new XmlReaderException(extraStrings + ": is not a valid target move to space group, color, or name.");
                        }
                        //Reflection to create action cards
                        try {
                            AbstractActionCard newAC = (AbstractActionCard) Class.forName(ACTION_CARD_PATH + className).getConstructor(DeckType.class, String.class, Boolean.class, List.class, List.class).newInstance(dt, msg, holdable, extraStrings, extraDubs);
                            allActionCards.add(newAC);
                        }
                        catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException  | ClassNotFoundException e ) {
                            logException(e, e.getMessage());
                            throw new XmlReaderException(e.getMessage());
                        }/*
                        catch (InstantiationException e) {
                            throw new XmlReaderException("Instantiation error");
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            throw new XmlReaderException("Method reflection not found");
                        } catch (ClassNotFoundException e) {
                            throw new XmlReaderException(className + " was not a valid class name... please check the data file's ActionCard 'type' attributes to ensure they match the class names");
                            //e.printStackTrace();
                        }*/
                    }
                    else{
                        throw new XmlReaderException(deckName + "not a valid action deck. Please check the data file.");
                    }
                }
            }
            return allActionCards;
        }
        else{
            throw getXmlReaderException(errorCheck);
        }
    }

    public List<Property> parseAllProps()throws XmlReaderException{
        //BuildingPrices might be empty list so don't check that
        String errorCheck = errorChecker.checkTagName(List.of(PROPERTY_TAG, PROPERTY_NAME_TAG, COLOR_GROUP_TAG, GROUP_SIZE_TAG, BUY_PRICE_TAG, RENT_TAG));
        if(!errorCheck.equalsIgnoreCase("VALID")){
            throw new XmlReaderException(errorCheck + " is not a valid tag. Please check the xml config file.");
        }
        List<Property> propsList = new ArrayList<>();
        NodeList propTypeList = doc.getElementsByTagName(PROPERTY_TAG);
        for(int i = 0; i<propTypeList.getLength(); i++){
            Node ac = propTypeList.item(i);
            if(ac.getNodeType() == Node.ELEMENT_NODE){
                Element prop = (Element) ac;
                String name = prop.getElementsByTagName(PROPERTY_NAME_TAG).item(0).getTextContent();
                //http://www.java67.com/2018/03/java-convert-string-to-boolean.html
                String color = prop.getElementsByTagName(COLOR_GROUP_TAG).item(0).getTextContent();
                String groupSizeString = prop.getElementsByTagName(GROUP_SIZE_TAG).item(0).getTextContent();
                String buyPriceString = prop.getElementsByTagName(BUY_PRICE_TAG).item(0).getTextContent();
                if(!errorChecker.checkValuesAreNumbers(List.of(groupSizeString, buyPriceString))){
                    throw getXmlReadNumberException(GROUP_SIZE_TAG + " and/or " + BUY_PRICE_TAG);
                }
                int groupSize = Integer.parseInt(groupSizeString);
                double buyPrice = Double.parseDouble(buyPriceString);

                //get all rent numbers
                ArrayList<Double> rentNumbers = new ArrayList<>();
                NodeList rentList = doc.getElementsByTagName(RENT_TAG);

                Node thisRent1 = rentList.item(i);
                Element thisRent = (Element) thisRent1;
                NodeList rentNums = thisRent.getChildNodes();

                for(int x=1; x<rentNums.getLength(); x=x+2){
                    Node currRentNum = rentNums.item(x);
                    if(errorChecker.checkValuesAreNumbers(currRentNum.getTextContent())){
                        Double rentVal = Double.parseDouble(currRentNum.getTextContent());
                        rentNumbers.add(rentVal);
                    }
                    else{
                        throw getXmlReadNumberException(RENT_TAG + " Children");
                    }
                }

                //get all building numbers
                Map<BuildingType, Double> BuildingPrices = new TreeMap<>();
                NodeList priceList = doc.getElementsByTagName(BUILDING_PRICES_TAG);
                Node thisPrice = priceList.item(i);
                NodeList priceNums = thisPrice.getChildNodes();
                for(int x=1; x<priceNums.getLength(); x=x+2){
                    Node currPriceNum = priceNums.item(x);
                    Element c = (Element) currPriceNum;
                    String buildingType = c.getAttribute(TYPE_TAG);
                    //if(errorChecker.checkBuildingType(buildingType)){
                    if(errorChecker.checkEnums(buildingType, BuildingType.values())){
                        BuildingType bType = (BuildingType.valueOf(c.getAttribute(TYPE_TAG)));
                        if(errorChecker.checkValuesAreNumbers(currPriceNum.getTextContent())){
                            double priceVal = Double.parseDouble(currPriceNum.getTextContent());
                            BuildingPrices.put(bType, priceVal);
                        }
                        else{
                            throw getXmlReadNumberException(BUILDING_PRICES_TAG);
                        }
                    }
                    else{
                        throw getXmlReaderException(buildingType);
                    }
                }

                String className = prop.getAttribute(TYPE_TAG);
                //Reflection to create properties
                try {
                    Property newProp = (Property) Class.forName(PROPERTIES_PATH_TAG + className).getConstructor(double.class, String.class, String.class, List.class, int.class, Map.class).newInstance(buyPrice,
                            name, color, rentNumbers, groupSize, BuildingPrices);
                    propsList.add(newProp);
                    newProp.setIsMortgaged(false);
                }catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException  | ClassNotFoundException e ) {
                    logException(e, e.getMessage());
                    throw new XmlReaderException(e.getMessage());
                }

                /*catch (InstantiationException e) {
                    throw new XmlReaderException("Instantiation error");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    throw new XmlReaderException("Method reflection not found");
                } catch (ClassNotFoundException e) {
                    throw new XmlReaderException(className + " was not a valid class name... please check the data file's Property 'type' attributes to ensure they match the class names");
                    //e.printStackTrace();
                }*/
            }
        }
        return propsList;
    }

    public List<AbstractSpace> parseNewSpaces(List<Property> propsList) throws XmlReaderException{
        String errorString = errorChecker.checkTagName(List.of(SPACE_TAG, INDEX_TAG, SPACE_GROUP_TAG, SPACE_NAME_TAG, EXTRA_STRINGS_TAG, EXTRA_DOUBLES_TAG));
        if(!errorString.equalsIgnoreCase("VALID")){
            throw getXmlReaderException(errorString);
        }
        List<AbstractSpace> allSpaces = new ArrayList<>();
        NodeList spaceList = doc.getElementsByTagName(SPACE_TAG);
        if(!errorChecker.checkBoardSizeAndSpaces(BoardSize, spaceList.getLength())){
            throw new XmlReaderException("Board Size and Number of Spaces listed in the xml config file to not match.");
        }

        for(int i = 0; i < spaceList.getLength(); i++) {
            Node s = spaceList.item(i);
            if (s.getNodeType() == Node.ELEMENT_NODE) {
                Element space = (Element) s;
                String dexString = space.getElementsByTagName(INDEX_TAG).item(0).getTextContent();
                if(!errorChecker.checkValuesAreNumbers(dexString)){
                    throw getXmlReadNumberException(INDEX_TAG);
                }
                int index = Integer.parseInt(dexString);
                String spaceGroupString = space.getElementsByTagName(SPACE_GROUP_TAG).item(0).getTextContent().strip();
                //if(!errorChecker.checkSpaceGroup(spaceGroupString)){
                if(!errorChecker.checkEnums(spaceGroupString, SpaceGroup.values())){
                    throw new XmlReaderException(spaceGroupString + " is not a valid SpaceGroup enum. Please check the config file");
                }
                SpaceGroup sg = SpaceGroup.valueOf(spaceGroupString);
                String spaceName = space.getElementsByTagName(SPACE_NAME_TAG).item(0).getTextContent().strip();
                String extraString = space.getElementsByTagName(EXTRA_STRINGS_TAG).item(0).getTextContent().strip();
                String[] extraDubTemp = space.getElementsByTagName(EXTRA_DOUBLES_TAG).item(0).getTextContent().split(",");
                //Help check if values in EXTRA_DOUBLES_TAG are numbers separated by a comma
                helpCheckDoublesTag(List.of(extraDubTemp));
                List<Double> extraDubs = listDoubleConverter(extraDubTemp);

                Property myProp = findLinkedProperty(propsList, spaceName, sg);

                String className = space.getAttribute(TYPE_TAG);

                //Reflection to create properties
                try {
                    AbstractSpace newSpace = (AbstractSpace) Class.forName(SPACES_PATH_TAG + className).getConstructor(int.class, String.class, String.class, String.class, List.class, Property.class).newInstance(index,
                            spaceName, spaceGroupString, extraString, extraDubs, myProp);
                    //newSpace.setMyProp(myProp);
                    allSpaces.add(newSpace);
                }catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException  | ClassNotFoundException e ) {
                    logException(e, e.getMessage());
                    throw new XmlReaderException(e.getMessage());
                }

                /*catch (InstantiationException e) {
                    throw new XmlReaderException("Instantiation error");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    throw new XmlReaderException("Method reflection not found");
                } catch (ClassNotFoundException e) {
                    throw new XmlReaderException(className + " was not a valid class name... please check the data file's Space 'type' attributes to ensure they match the class names");
                }*/

            }
        }
        return allSpaces;
    }

    //Helper to find linked property; checks that no typos between links
    private Property findLinkedProperty(List<Property> allProperties, String name, SpaceGroup sg) throws XmlReaderException{
        Map<String, Property> allPropsAndNames = new HashMap<>();
        for(Property prop:allProperties){
            allPropsAndNames.put(prop.getName(), prop);
        }
        if(sg != SpaceGroup.RAILROAD && sg != SpaceGroup.UTILITY && sg != SpaceGroup.COLOR){
            return null;
        }
        else if(errorChecker.checkLinkedProperty(allPropsAndNames, name) != null){
            return errorChecker.checkLinkedProperty(allPropsAndNames, name);
        }
        else{
            throw new XmlReaderException(name + " does not match a property name, check for typos in xml between space names and property names.");
        }
    }

    public List<List> parseSpaces() throws XmlReaderException{
        List<List> allSpacesAndProps = new ArrayList<>();
        List<Property> allProps = parseAllProps();
        List<AbstractSpace> allSpaces = parseNewSpaces(allProps);

        allSpacesAndProps.add(allSpaces);
        allSpacesAndProps.add(allProps);

        return allSpacesAndProps;
    }

    /**
     * Get list of token image names to use in front end
     * @return List of token file names
     * @throws XmlReaderException checks tag name; file exists
     */
    public List<String> parseTokens() throws XmlReaderException {
        if(errorChecker.checkTagName(TOKEN_TAG)){
            List<String> allTokens = new ArrayList<>();

            NodeList tokenList = doc.getElementsByTagName(TOKEN_TAG);
            for(int i = 0; i<tokenList.getLength(); i++) {
                Node tk = tokenList.item(i);
                if (tk.getNodeType() == Node.ELEMENT_NODE) {
                    Element tok = (Element) tk;
                    String tokName = tok.getTextContent();
                    if(checkFileExists(tokName, ".png")){
                        allTokens.add(tokName);
                    }
                    else{
                        throw new XmlReaderException(tokName + " is not a token image found in doc directory or has the incorrect suffix. Fix data file");
                    }
                }
            }
            return allTokens;
        }
        else{
            throw getXmlReaderException(TOKEN_TAG);
        }
    }

    /**
     * Method used in abstract game to get numeric rules from xml
     * @param attribute tag in xml to get rule from
     * @return double representing a rule
     * @throws XmlReaderException checks tag name and values are numbers
     */
    public double getRuleDouble(String attribute) throws XmlReaderException{
        if(errorChecker.checkTagName(attribute)){
            NodeList list = doc.getElementsByTagName(attribute);
            Node node = list.item(0);
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String stringValue = element.getTextContent();
                if(errorChecker.checkValuesAreNumbers(stringValue)){
                    return Double.parseDouble(stringValue);
                }
                else{
                    throw getXmlReadNumberException(attribute);
                }
            }
            return -1;
        }
        else{
            throw getXmlReaderException(attribute);
        }
    }

    /**
     * Method used in abstract game to get true/false rules from xml
     * @param attribute tag in xml to get info from
     * @return boolean representing a rule
     * @throws XmlReaderException checks tag name
     */
    public boolean getRuleBool(String attribute) throws XmlReaderException{
        if(errorChecker.checkTagName(attribute)){
            NodeList list = doc.getElementsByTagName(attribute);
            Node node = list.item(0);
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String stringValue = element.getTextContent();
                System.out.println("rule bool was true "+attribute);
                return Boolean.parseBoolean(stringValue);
            }
            return false;
        }
        else{
            throw getXmlReaderException(attribute);
        }
    }

    public List<Map<BuildingType, Integer>> getBuildingProperties() throws XmlReaderException{
        List<Map<BuildingType, Integer>> buildingProperties = new ArrayList<>();
        Map<BuildingType, Integer> buildingTotalAmount = new TreeMap<>();
        Map<BuildingType, Integer> buildingMaxAmount = new TreeMap<>();


        NodeList buildingList = doc.getElementsByTagName(BUILDING_TYPE_TAG);

        for (int x = 0; x < buildingList.getLength(); x++) {
            Node bL = buildingList.item(x);
            if (bL.getNodeType() == Node.ELEMENT_NODE) {
                Element building = (Element) bL;
                BuildingType bType = BuildingType.valueOf(building.getAttribute(TYPE_TAG));
                String totalString = building.getElementsByTagName(TOTAL_AMOUNT_TAG).item(0).getTextContent();
                String maxString = building.getElementsByTagName(MAX_AMOUNT_TAG).item(0).getTextContent();
                if(errorChecker.checkValuesAreNumbers(List.of(totalString, maxString))){
                    int total = Integer.parseInt(totalString);
                    buildingTotalAmount.put(bType, total);
                    int max = Integer.parseInt(maxString);
                    buildingMaxAmount.put(bType, max);
                }
                else{
                    throw getXmlReadNumberException(TOTAL_AMOUNT_TAG + " and/or " + MAX_AMOUNT_TAG);
                }
            }
        }

        buildingProperties.add(buildingTotalAmount);
        buildingProperties.add(buildingMaxAmount);
        return buildingProperties;
    }

    //Helper method converts list of strings to list of doubles for parsing
    private List<Double> listDoubleConverter(String[] stringList) throws XmlReaderException{
        for(String s: stringList){
            if(!errorChecker.checkValuesAreNumbers(s)){
                throw new XmlReaderException(s + " - not a valid inputs.  Must be number value, not character or string");
            }
        }
        List<Double> doubleList = new ArrayList<>();
        for(String n:stringList){
            doubleList.add(Double.parseDouble(n));
        }
        return doubleList;
    }

    //Helper method for checking EXTRA_DOUBLES check
    private void helpCheckDoublesTag(List<String> args) throws XmlReaderException{
        if(errorChecker.checkValuesAreNumbers(args)){
            return;
        }
        else{
            throw getXmlReadNumberException(EXTRA_DOUBLES_TAG);
        }
    }

    //Helper method to check files being found exist in the module
    private boolean checkFileExists(String fileName, String fileEnding){
        File[] files = new File("data").listFiles();
        for(File file : files){
            if(file.getName().equals(fileName) && file.getName().toLowerCase().endsWith(fileEnding.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    //Helper to reduce duplication
    private XmlReaderException getXmlReaderException(String errorCheck) {
        return new XmlReaderException(errorCheck + " is not a valid tag. Please check the xml config file");
    }
    //Helper to reduce duplication
    private XmlReaderException getXmlReadNumberException(String errorCheck) {
        return new XmlReaderException(errorCheck + " is not a valid input. Value must be a number, not a string or character.");
    }

    private void logException(Exception e, String msg){
        if(ExceptionLog.keySet().contains(e)){
            List<String> temp = ExceptionLog.get(e);
            temp.add(msg);
            ExceptionLog.put(e, temp);
        }
        else{
            ExceptionLog.put(e, List.of(msg));
        }
    }

/*    public static void main(String[] args) {
        try{
            ConfigReader c = new ConfigReader("Junior_Config.xml");
            c.parseSpaces();
            c.parseActionCards();
            c.parseActionDecks();
        }
        catch(XmlReaderException e){

        }
    }*/
}


