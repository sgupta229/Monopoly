package Model;
import Model.properties.Property;

public class AIPlayer extends AbstractPlayer {

    public AIPlayer(String name, String image) {
        super(name,image);
    }

    public boolean wantsToBuy(Property property) {
        if(property.getPrice() < getFunds()) {
            return true;
        }
        return false;
    }

    public boolean wantsToBuildHouse() {
        return true;
    }

}
