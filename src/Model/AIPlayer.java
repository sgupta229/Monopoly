package Model;

import Model.properties.BuildingType;
import Model.properties.Property;

import java.util.Map;

public class AIPlayer extends AbstractPlayer {

    public AIPlayer(String name) {
        super(name);
    }

    public void doSpecialMove() {

    }

    public Map<BuildingType, Integer> getNumBuildings() {
        return null;
    }

    public boolean wantsToBuy(Property property) {
        if(property.getPrice() < getFunds() * 3) {
            return true;
        }
        return false;
    }

    public boolean wantsToBuild() {
        return false;
    }

    public boolean wantsTo() {
        return false;
    }

}
