package Model.Tests;

import Controller.JuniorGame;
import Controller.ConfigReader;
import Model.Bank;
import Model.JuniorPlayer;
import Model.XmlReaderException;
import Model.properties.BuildingType;
import Model.properties.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JuniorColorPropertyTest {



    JuniorGame gameClass;
    JuniorPlayer player1;
    JuniorPlayer player2;
    List<Property> propsList;
    Bank gameBank;
    Property prop1;
    Property prop2;
    int lastDiceRoll;

    @BeforeEach
    void setUp() throws XmlReaderException {
        gameClass = new JuniorGame("Junior_Config.xml");
        gameClass.parseXMLFile("Junior_Config.xml");
        propsList = gameClass.getProperties();
        prop1 = propsList.get(0);
        prop2 = propsList.get(1);
        player1 = new JuniorPlayer();
        gameBank = gameClass.getBank();
    }

    @Test
    void calculateRentNoMonopolyTest(){
        player1.addProperty(propsList.get(0));
        gameBank.setPropertyOwner(prop1, player1);
        var actualRent = prop1.calculateRent(player1, 1);
        var expectedRent = 1;
        assertEquals(expectedRent, actualRent);
    }

    @Test
    void calculateRentMonopolyTest(){
        player1.addProperty(prop1);
        player1.addProperty(prop2);
        gameBank.setPropertyOwner(prop1, player1);
        var actualRent = prop1.calculateRent(player1, 1);
        var expectedRent = 2;
        assertEquals(expectedRent, actualRent);
    }

    @Test
    void checkNoBuilding() {
        assertThrows(UnsupportedOperationException.class, () -> prop1.addBuilding(BuildingType.valueOf("HOUSE")));
        assertThrows(UnsupportedOperationException.class, () -> prop1.removeBuilding(BuildingType.valueOf("HOUSE"), 4));
        assertThrows(UnsupportedOperationException.class, () -> prop1.addBuilding(BuildingType.valueOf("HOUSE")));

    }

}