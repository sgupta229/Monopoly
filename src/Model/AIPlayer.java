package Model;

import Model.properties.BuildingType;
import Model.properties.Property;
import javafx.scene.Node;

import java.util.Map;

public class AIPlayer extends AbstractPlayer {

    public AIPlayer(String name, String image) {
        super(name,image);
    }

    public Map<BuildingType, Integer> getNumBuildings() {
        return null;
    }

    public boolean wantsToBuy(Property property) {
        if(property.getPrice() < getFunds()) {
            return true;
        }
        return false;
    }

    public boolean wantsToBuild() {
        return true;
    }

}
