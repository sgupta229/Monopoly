package Model;

import Model.properties.Buildable;
import Model.properties.BuildingType;
import Model.properties.Property;

import java.util.HashMap;
import java.util.Map;

public class ClassicPlayer extends AbstractPlayer {

    public ClassicPlayer(String name) {
        super(name);
    }

    public ClassicPlayer() {
        super();
    }

    public void doSpecialMove() {

    }

//    public int getNumHouses() {
//        Map<String, ObservableList<Property>> properties = getProperties();
//
//    }
//
//    public int getNumHotels() {
//
//    }

    public Map<BuildingType, Integer> getNumBuildings() {
        Map<BuildingType, Integer> buildingInventory = new HashMap<>();
        return buildingInventory;
    }

}
