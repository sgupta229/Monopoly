package Model.properties;

import Controller.ClassicGame;
import Controller.ConfigReader;
import Model.Bank;
import Model.ClassicPlayer;
import Model.XmlTagException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ColorPropertyTest {

    ConfigReader conf = new ConfigReader("Normal_Config.xml");
    ClassicGame gameClass;
    ClassicPlayer player1;
    ClassicPlayer player2;
    List<Property> propsList;
    Bank gameBank;
    int lastDiceRoll;

    @BeforeEach
    void setUp() throws XmlTagException {
        gameClass = new ClassicGame("Normal_Config.xml");
        propsList = conf.parseSpaces().get(1);
        player1 = new ClassicPlayer();
        gameBank = gameClass.getBank();
    }

    @Test
    void calculateRent1of3Owned() {
        Property lightBlueProp = propsList.get(4);
        player1.addProperty(lightBlueProp);
        var actualRent = lightBlueProp.calculateRent(player1, lastDiceRoll);
        var expectedRent = 6;
        assertEquals(actualRent, expectedRent);
    }

    @Test
    void calculateRent2of3Owned() {
        Property lightBlueProp = propsList.get(4);
        Property lightBlueProp2 = propsList.get(5);
        player1.addProperty(lightBlueProp);
        player1.addProperty(lightBlueProp2);
        var actualRent = lightBlueProp.calculateRent(player1, lastDiceRoll);
        var expectedRent = 6;
        assertEquals(actualRent, expectedRent);
    }

    @Test
    void calculateRent1House() {
        Property lightBlueProp = propsList.get(3);
        Property lightBlueProp2 = propsList.get(4);
        Property lightBlueProp3 = propsList.get(5);
        player1.addProperty(lightBlueProp);
        player1.addProperty(lightBlueProp2);
        player1.addProperty(lightBlueProp3);
        ((Buildable) lightBlueProp).addBuilding(BuildingType.valueOf("HOUSE"));
        var actualRent = lightBlueProp.calculateRent(player1, lastDiceRoll);
        var expectedRent = 30;
        assertEquals(actualRent, expectedRent);
    }

    @Test
    void calculateRent2House() {
        Property lightBlueProp = propsList.get(3);
        Property lightBlueProp2 = propsList.get(4);
        Property lightBlueProp3 = propsList.get(5);
        player1.addProperty(lightBlueProp);
        player1.addProperty(lightBlueProp2);
        player1.addProperty(lightBlueProp3);
        ((Buildable) lightBlueProp2).addBuilding(BuildingType.valueOf("HOUSE"));
        ((Buildable) lightBlueProp2).addBuilding(BuildingType.valueOf("HOUSE"));
        var actualRent = lightBlueProp2.calculateRent(player1, lastDiceRoll);
        var expectedRent = 90;
        assertEquals(actualRent, expectedRent);
    }

    @Test
    void calculateRentHotel() {
        Property lightBlueProp = propsList.get(3);
        Property lightBlueProp2 = propsList.get(4);
        Property lightBlueProp3 = propsList.get(5);
        player1.addProperty(lightBlueProp);
        player1.addProperty(lightBlueProp2);
        player1.addProperty(lightBlueProp3);
        ((Buildable) lightBlueProp2).addBuilding(BuildingType.valueOf("HOTEL"));
        var actualRent = lightBlueProp2.calculateRent(player1, lastDiceRoll);
        var expectedRent = 550;
        assertEquals(actualRent, expectedRent);
    }

    @Test
    void addBuildingANDgetNum() {
        Property lightBlueProp = propsList.get(3);
        player1.addProperty(lightBlueProp);
        ((Buildable) lightBlueProp).addBuilding(BuildingType.valueOf("HOUSE"));
        var actualNumHouses = ((Buildable) lightBlueProp).getNumBuilding(BuildingType.valueOf("HOUSE"));
        var expectedNumHouses = 1;
        assertEquals(expectedNumHouses, actualNumHouses);
    }

    @Test
    void removeBuildingANDgetNum() {
        Property lightBlueProp = propsList.get(3);
        Property lightBlueProp2 = propsList.get(4);
        Property lightBlueProp3 = propsList.get(5);
        player1.addProperty(lightBlueProp);
        player1.addProperty(lightBlueProp2);
        player1.addProperty(lightBlueProp3);
        ((Buildable) lightBlueProp2).addBuilding(BuildingType.valueOf("HOUSE"));
        ((Buildable) lightBlueProp2).addBuilding(BuildingType.valueOf("HOUSE"));
        ((Buildable) lightBlueProp2).removeBuilding(BuildingType.valueOf("HOUSE"));
        var actualNumHouses = ((Buildable) lightBlueProp2).getNumBuilding(BuildingType.valueOf("HOUSE"));
        var expectedNumHouses = 1;
        assertEquals(expectedNumHouses, actualNumHouses);
    }

    @Test
    void getNumBuildingNoBuildings() {
        Property lightBlueProp3 = propsList.get(5);
        player1.addProperty(lightBlueProp3);
        var actualNumHouses = ((Buildable) lightBlueProp3).getNumBuilding(BuildingType.valueOf("HOUSE"));
        var expectedNumHouses = 0;
        assertEquals(expectedNumHouses, actualNumHouses);
    }
}