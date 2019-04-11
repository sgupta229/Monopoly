package View;

import Controller.ConfigReader;
import Model.spaces.AbstractSpace;
import Model.properties.Property;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardConfigReader {

    private static final String CONFIG_PATH = "Normal_Config.xml";
    private ConfigReader mySpaceConfigs;
    private List<AbstractSpace> spaces;
    private List<Property> properties;
    Map<Point2D.Double, AbstractSpace> indexToName;
    Map<String, Integer> nameToPrice;
    Map<String, String> nameToColor;


    public BoardConfigReader(){
        mySpaceConfigs = new ConfigReader(CONFIG_PATH);
        List<List> allSpacesAndProps = mySpaceConfigs.parseSpaces();
        spaces = allSpacesAndProps.get(0);
        properties = allSpacesAndProps.get(1);
        indexToName = new HashMap<Point2D.Double, AbstractSpace>();
        setUpMapping();
        setIndexToName();
    }

    private void setUpMapping(){
        nameToPrice = new HashMap<String, Integer>();
        nameToColor = new HashMap<String, String>();
        for (Property p: properties){
            nameToPrice.put(p.getName().replace("_"," "),(int)p.getPrice());
            nameToColor.put(p.getName().replace("_"," "),p.getColor());
        }
    }

    private Map setIndexToName(){
        for(AbstractSpace sp : spaces){
            int x = 0;
            if (sp.getMyLocation()==0 || sp.getMyLocation()==30){
                x = 10;
            }
            if (sp.getMyLocation()==1 || sp.getMyLocation()==11 || sp.getMyLocation()==29 || sp.getMyLocation()==39){
                x = 9;
            }
            if (sp.getMyLocation()==2 || sp.getMyLocation()==12 || sp.getMyLocation()==28 || sp.getMyLocation()==38){
                x = 8;
            }
            if (sp.getMyLocation()==3 || sp.getMyLocation()==13 || sp.getMyLocation()==27 || sp.getMyLocation()==37){
                x = 7;
            }
            if (sp.getMyLocation()==4 || sp.getMyLocation()==14 || sp.getMyLocation()==26 || sp.getMyLocation()==36){
                x = 6;
            }
            if (sp.getMyLocation()==5 || sp.getMyLocation()==15 || sp.getMyLocation()==25 || sp.getMyLocation()==35){
                x = 5;
            }
            if (sp.getMyLocation()==6 || sp.getMyLocation()==16 || sp.getMyLocation()==24 || sp.getMyLocation()==34){
                x = 4;
            }
            if (sp.getMyLocation()==7 || sp.getMyLocation()==17 || sp.getMyLocation()==23 || sp.getMyLocation()==33){
                x = 3;
            }
            if (sp.getMyLocation()==8 || sp.getMyLocation()==18 || sp.getMyLocation()==22 || sp.getMyLocation()==32){
                x = 2;
            }
            if (sp.getMyLocation()==9 || sp.getMyLocation()==19 || sp.getMyLocation()==21 || sp.getMyLocation()==31){
                x = 1;
            }
            if (sp.getMyLocation()==10 || sp.getMyLocation()==20){
                x = 0;
            }
            if (sp.getMyLocation()>=0 && sp.getMyLocation()<=10) {
                indexToName.put(new Point2D.Double(x, 10),sp);
            }
            else if(sp.getMyLocation()>=11 && sp.getMyLocation()<=20){
                indexToName.put(new Point2D.Double(0, x),sp);
            }
            else if(sp.getMyLocation()>=21 && sp.getMyLocation()<=30){
                indexToName.put(new Point2D.Double(x, 0),sp);
            }
            else if(sp.getMyLocation()>=31 && sp.getMyLocation()<=39){
                indexToName.put(new Point2D.Double(10, x),sp);
            }
        }
        return indexToName;
    }

    public Map<String, Integer> getNameToPrice() {
        return nameToPrice;
    }

    public Map<String, String> getNameToColor() {
        return nameToColor;
    }

    public Map<Point2D.Double, AbstractSpace> getIndexToName() {
        return indexToName;
    }
}
















