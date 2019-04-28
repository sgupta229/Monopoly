package Model.Tests;

import Controller.ClassicGame;
import Controller.ConfigReader;
import Model.Bank;
import Model.ClassicPlayer;
import Model.XmlReaderException;
import Model.properties.BuildingType;
import Model.properties.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClassicColorPropertyTest {

    ConfigReader conf;

    {
        try {
            conf = new ConfigReader("Normal_Config_Rework.xml");
        } catch (XmlReaderException e) {
            e.printStackTrace();
        }
    }

    ClassicGame gameClass;
    ClassicPlayer player1;
    ClassicPlayer player2;
    List<Property> propsList;
    Bank gameBank;
    int lastDiceRoll;

    @BeforeEach
    void setUp() throws XmlReaderException {
        gameClass = new ClassicGame("Normal_Config_Rework.xml");
        gameClass.parseXMLFile("Normal_Config_Rework.xml");
        propsList = gameClass.getProperties();
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
        lightBlueProp.addBuilding(BuildingType.valueOf("HOUSE"));
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
        lightBlueProp2.addBuilding(BuildingType.valueOf("HOUSE"));
        lightBlueProp2.addBuilding(BuildingType.valueOf("HOUSE"));
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
        lightBlueProp2.addBuilding(BuildingType.valueOf("HOTEL"));
        var actualRent = lightBlueProp2.calculateRent(player1, lastDiceRoll);
        var expectedRent = 550;
        assertEquals(actualRent, expectedRent);
    }

    @Test
    void addBuildingANDgetNum() {
        Property lightBlueProp = propsList.get(3);
        player1.addProperty(lightBlueProp);
        lightBlueProp.addBuilding(BuildingType.valueOf("HOUSE"));
        var actualNumHouses = lightBlueProp.getNumBuilding(BuildingType.valueOf("HOUSE"));
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
        lightBlueProp2.addBuilding(BuildingType.valueOf("HOUSE"));
        lightBlueProp2.addBuilding(BuildingType.valueOf("HOUSE"));
        lightBlueProp2.removeBuilding(BuildingType.valueOf("HOUSE"), 1);
        var actualNumHouses = lightBlueProp2.getNumBuilding(BuildingType.valueOf("HOUSE"));
        var expectedNumHouses = 1;
        assertEquals(expectedNumHouses, actualNumHouses);
    }

    @Test
    void getNumBuildingNoBuildings() {
        Property lightBlueProp3 = propsList.get(5);
        player1.addProperty(lightBlueProp3);
        var actualNumHouses = lightBlueProp3.getNumBuilding(BuildingType.valueOf("HOUSE"));
        var expectedNumHouses = 0;
        assertEquals(expectedNumHouses, actualNumHouses);
    }
}