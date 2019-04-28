package Model.tests;

import Controller.ClassicGame;
import Controller.ConfigReader;
import Model.Bank;
import Model.ClassicPlayer;
import Model.XmlReaderException;
import Model.properties.Property;
import Model.spaces.AbstractSpace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UtilityPropertyTest {

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
    List<AbstractSpace> spaceList;
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
    void calculateRentOnly1Utility() {
        lastDiceRoll = 2;
        Property utilProp = propsList.get(7);
        player1.addProperty(propsList.get(7));
        var actualRent = utilProp.calculateRent(player1, lastDiceRoll);
        var expectedRent = 8;
        assertEquals(actualRent, expectedRent);
    }

    @Test
    void calculateRent2Utilities() {
        lastDiceRoll = 3;
        Property utilProp = propsList.get(7);
        Property utilProp2 = propsList.get(20);
        player1.addProperty(propsList.get(7));
        player1.addProperty(utilProp2);
        var actualRent = utilProp.calculateRent(player1, lastDiceRoll);
        var expectedRent = 30;
        assertEquals(actualRent, expectedRent);
    }

    @Test
    void calculateRentMortgaged() {
        lastDiceRoll = 2;
        Property utilProp = propsList.get(7);
        player1.addProperty(propsList.get(7));
        utilProp.setIsMortgaged(true);
        var actualRent = utilProp.calculateRent(player1, lastDiceRoll);
        var expectedRent = 0;
        assertEquals(actualRent, expectedRent);
    }
}