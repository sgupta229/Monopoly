package View;

import Controller.AbstractGame;
import Model.spaces.AbstractSpace;
import Model.properties.Property;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class BoardConfigReader {

    private List<AbstractSpace> spaces;
    private List<Property> properties;
    private List myFiles;
    Map<Point2D.Double, AbstractSpace> indexToName;
    Map<String, Integer> nameToPrice;
    Map<String, String> nameToColor;
    Map<Integer, List> colorPropInfo;
    Map<Integer, Point2D.Double> indexToCoord;
    private ResourceBundle myResourceBundle;


    public BoardConfigReader(AbstractGame game) {
        myFiles = game.getFrontEndFiles();
        myResourceBundle = ResourceBundle.getBundle(myFiles.get(0).toString());
        spaces = game.getSpaces();
        properties = game.getProperties();


        colorPropInfo = new HashMap<>();
        for(AbstractSpace sp:spaces){
            if(sp.getInfo().size()>=1){
                colorPropInfo.put(sp.getMyLocation(), sp.getInfo());
            }
        }
        indexToName = new HashMap<>();
        indexToCoord= new HashMap<>();
        setUpMapping();
        setIndexToName();
    }

    private void setUpMapping(){
        nameToPrice = new HashMap<>();
        nameToColor = new HashMap<>();
        for (Property p: properties){
            nameToPrice.put(p.getName().replace("_"," "),(int)p.getPrice());
            nameToColor.put(p.getName().replace("_"," "),p.getColor());
        }
    }

    private Map setIndexToName(){
        for(AbstractSpace sp : spaces) {
            String[] coord = myResourceBundle.getString(String.valueOf(sp.getMyLocation())).split(",");
            Point2D.Double myPoint = new Point2D.Double(Double.valueOf(coord[0]),Double.valueOf(coord[1]));
            indexToName.put(myPoint,sp);
            indexToCoord.put(sp.getMyLocation(),myPoint);
        }
        return indexToName;
    }

    public Map<String, Integer> getNameToPrice() { return nameToPrice; }

    public Map<String, String> getNameToColor() { return nameToColor; }

    public Map<Point2D.Double, AbstractSpace> getIndexToName() { return indexToName; }

    public Map<Integer,Point2D.Double> getIndexToCoord() { return indexToCoord; }

    public List<AbstractSpace> getSpaces() { return spaces; }

    public List<Property> getProperties() { return properties; }

    public List getFiles(){
        return myFiles;
    }

}
















