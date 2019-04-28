package Model.Tests;

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

class RailRoadPropertyTest {

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
    Property prop1;


    @BeforeEach
    void setUp() throws XmlReaderException {
        gameClass = new ClassicGame("Normal_Config_Rework.xml");
        gameClass.parseXMLFile("Normal_Config_Rework.xml");

        //gameClass.get
        spaceList = gameClass.getSpaces();
        propsList = gameClass.getProperties();
        player1 = new ClassicPlayer();
        //player2 = new ClassicPlayer();
        gameBank = gameClass.getBank();
        prop1 = propsList.get(0);

    }



    @Test
    void calculateRentMortgaged() {
        Property roadProp = propsList.get(3);
        player1.addProperty(roadProp);
        roadProp.setIsMortgaged(true);
        var actualRent = roadProp.calculateRent(player1, 0);
        var expectedRent = 0;
        assertEquals(actualRent, expectedRent);
    }

    @Test
    void calculateRent1RailRoad() {
        Property roadProp = propsList.get(2);
        player1.addProperty(roadProp);
        var actualRent = roadProp.calculateRent(player1, 12);
        var expectedRent = 25;
        assertEquals(expectedRent, actualRent);
    }

    @Test
    void calculateRent2RailRoads() {
        Property roadProp = propsList.get(2);
        Property roadProp2 = propsList.get(10);
        player1.addProperty(roadProp2);
        player1.addProperty(roadProp);
        var actualRent = roadProp.calculateRent(player1, 3);
        var expectedRent = 50;
        assertEquals(expectedRent, actualRent);
    }

    @Test
    void calculateRent3RailRoads() {
        Property roadProp = propsList.get(2);
        Property roadProp2 = propsList.get(10);
        Property roadProp3 = propsList.get(17);
        player1.addProperty(roadProp2);
        player1.addProperty(roadProp3);
        player1.addProperty(roadProp);
        var actualRent = roadProp.calculateRent(player1, 5);
        var expectedRent = 100;
        assertEquals(expectedRent, actualRent);
    }
}