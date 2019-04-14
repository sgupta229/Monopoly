package Model;

import Model.properties.BuildingTypes;

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

    public Map<BuildingTypes, Integer> getNumBuildings() {
        Map<BuildingTypes, Integer> buildingInventory = new HashMap<>();
        return buildingInventory;
    }

}
