package Model;

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

    public Map<BuildingType, Integer> getNumBuildings() {
        Map<BuildingType, Integer> buildingInventory = new HashMap<>();
        return buildingInventory;
    }

}
