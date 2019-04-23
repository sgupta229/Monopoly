package Controller;

import Model.actioncards.DeckType;
import Model.properties.BuildingType;
import Model.properties.Property;
import Model.spaces.SpaceGroup;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigReaderErrorHandling {
    Document myDoc;

    public ConfigReaderErrorHandling(Document doc){
        myDoc = doc;
    }

    //Helper method to check if deck type found is a legal deck type enum -- for error handling
    public boolean checkDeckType(String deckName){
        for(DeckType dtype : DeckType.values()) {
            if (dtype.name().equalsIgnoreCase(deckName)) {
                return true;
            }
        }
        return false;
    }

    //Helper method to check if building type found is a legal building type enum -- for error handling
    public boolean checkBuildingType(String buildingType){
        for(BuildingType btype : BuildingType.values()) {
            if (btype.name().equalsIgnoreCase(buildingType)) {
                return true;
            }
        }
        return false;
    }

    //Helper method to check if tag name being searched for is in the xml documnet -- for error handling
    public boolean checkTagName(String tagName){
        NodeList listCheck = myDoc.getElementsByTagName(tagName);
        //System.out.println(listCheck.getLength());
        //If length == 0, return false because tagName not in xml
        return (listCheck.getLength() != 0);
    }

    public String checkTagName(List<String> tagNames){
        for(String s: tagNames){
            NodeList listCheck = myDoc.getElementsByTagName(s);
            if(listCheck.getLength() == 0){
                return s;
            }
        }
        return "VALID";
    }

    public Property checkLinkedProperty(Map<String, Property> allPropsAndNames, String name){
        if(allPropsAndNames.containsKey(name)){
            return allPropsAndNames.get(name);
        }
        else{
            return null;
        }
    }

    public boolean checkValuesAreNumbers(String val){
        try{
            Double.parseDouble(val);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

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

    public boolean checkSpaceGroup(String spaceName){
        for(SpaceGroup sg : SpaceGroup.values()) {
            if (sg.name().equalsIgnoreCase(spaceName)) {
                return true;
            }
        }
        return false;
    }





}
