package Controller;

import Model.properties.Property;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.*;

public class ConfigReaderErrorHandling {
    Document myDoc;

    public ConfigReaderErrorHandling(Document doc){
        myDoc = doc;
    }


    /**
     * Combines checkDeckType, checkSpaceGroup, and checkBuildingType into one method
     * @param arg string to check
     * @param checkList list of enum values to check against
     * @return true if string is valid enum; false otherwise
     */
    public boolean checkEnums(String arg, Enum[] checkList){
        for(Enum en : checkList){
            if(en.name().equalsIgnoreCase(arg)){
                return true;
            }
        }
        return false;
    }

    /**
     * Used to check that tag names being searched for return some values from xml
     * @param tagName tagName string to check
     * @return true if valid; false if not
     */
    //Helper method to check if tag name being searched for is in the xml documnet -- for error handling
    public boolean checkTagName(String tagName){
        NodeList listCheck = myDoc.getElementsByTagName(tagName);
        return (listCheck.getLength() != 0);
    }

    /**
     * Used to check that list of tag names all return some values from xml
     * @param tagNames list of tag name strings to check
     * @return VALID if valid; invalid string if not
     */
    public String checkTagName(List<String> tagNames){
        for(String s: tagNames){
            NodeList listCheck = myDoc.getElementsByTagName(s);
            if(listCheck.getLength() == 0){
                return s;
            }
        }
        return "VALID";
    }

    /**
     * Used to ensure that property names and space names have matches (no typos)
     * @param allPropsAndNames map of Names: properties
     * @param name name to check for link
     * @return property if exists; null if not (null is used in gameplay)
     */
    public Property checkLinkedProperty(Map<String, Property> allPropsAndNames, String name){
        if(allPropsAndNames.containsKey(name)){
            return allPropsAndNames.get(name);
        }
        else{
            return null;
        }
    }

    /**
     * Used to ensure that an input value in the xml is a number valid for parsing
     * @param val string value from xml to check if numerics
     * @return true if valid; false if not
     */
    public boolean checkValuesAreNumbers(String val){
        try{
            Double.parseDouble(val);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * Used to ensure that a list of input values are all numbers suitable for parsing
     * @param vals list of string values to check if numerics
     * @return true if valid; false if not
     */
    public boolean checkValuesAreNumbers(List<String> vals){
        try{
            for(String s: vals){
                Double.parseDouble(s);
            }
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }


    /**
     * Ensure that number of spaces listed matches board size listed
     * @param boardSize int - board size from xml
     * @param spaces int - length of spacesList from xml
     * @return true if valid; false if not
     */
    public boolean checkBoardSizeAndSpaces(int boardSize, int spaces){
        return (boardSize == spaces);
    }

    /**
     * Check that target spaces in move to action cards are valid and have matches
     * @param cardType string to limit checks
     * @param targetSpaces potential target spaces to match
     * @return true if valid; false if not
     */
    //"MoveToAC", "MoveToNearestAC", "GoToJailAC", "MoveToOpenAC" checks
    public boolean checkMoveToTargets(String cardType, List<String> targetSpaces){
        Set<String> classSet = Set.of("MoveToAC", "MoveToNearestAC", "GoToJailAC", "MoveToOpenAC");
        if(!classSet.contains(cardType)){
            return true;
        }
        Set<String> propColorSet = getSetFromXml(myDoc.getElementsByTagName("ColorGroup"));
        Set<String> spaceNameSet = getSetFromXml(myDoc.getElementsByTagName("SpaceName"));
        Set<String> spaceGroupSet = getSetFromXml(myDoc.getElementsByTagName("SpaceGroup"));

        for(String s : targetSpaces){
            if(propColorSet.contains(s) || spaceNameSet.contains(s) || spaceGroupSet.contains(s)){
                return true;
            }
        }
        return false;
    }

    //Helper method to get sets for checking valid target space names
    private Set<String> getSetFromXml(NodeList nl){
        Set<String> res = new HashSet<>();
        for(int i=0; i<nl.getLength(); i++){
            res.add(nl.item(i).getTextContent());
        }
        return res;
    }
}
