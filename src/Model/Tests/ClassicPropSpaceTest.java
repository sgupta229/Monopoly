package Model.Tests;

import Controller.ClassicGame;
import Model.AbstractPlayer;
import Model.ClassicPlayer;
import Model.Bank;
import Model.properties.Property;
import Model.XmlReaderException;
import Model.spaces.ClassicPropSpace;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClassicPropSpaceTest {

    ClassicGame gameClass;
    ClassicPlayer player1;
    ClassicPlayer player2;
    ClassicPropSpace classicPropSpace;
    //List<AbstractSpace> spaceList;
    //List<Property> propsList;
    Bank gameBank;
    //Property prop1;

    double startBalance = 10000;




    @BeforeEach
    void setUp() throws XmlReaderException {
        gameClass = new ClassicGame("Normal_Config_Rework.xml");
        gameClass.parseXMLFile("Normal_Config_Rework.xml");
        classicPropSpace = (ClassicPropSpace) gameClass.getBoard().getSpaceAt(1);
        player1 = new ClassicPlayer();
        player2 = new ClassicPlayer();
        ObservableList<AbstractPlayer> playerList = FXCollections.observableList(new ArrayList<>());
        playerList.add(player1);
        playerList.add(player2);
        gameClass.setPlayers(playerList);
        player1.moveTo(1);
        gameBank = gameClass.getBank();
        gameClass.rollAndCheck();
        //player1.moveTo(30, gameClass.getBoardSize());
    }

    AbstractPlayer buyProperty(){
        classicPropSpace.doAction(gameClass, 0);
        AbstractPlayer propOwner = gameBank.propertyOwnedBy(classicPropSpace.getMyProperty());
        return propOwner;
    }

    @Test
    void doActionBuyProp() {
        AbstractPlayer propOwner= buyProperty();
        assertEquals(player1, propOwner);
    }

    @Test
    void doActionHaveToPayRent() {
        buyProperty();
        gameClass.setCurrPlayer(1);
        double player2OldFunds= player2.getFunds();
        double player1OldFunds= player1.getFunds();
        classicPropSpace.doAction(gameClass, 2);
        double player2NewFunds = player2.getFunds();
        double player1NewFunds = player1.getFunds();
        assertTrue(player2OldFunds>player2NewFunds);
        assertTrue(player1OldFunds<player1NewFunds);
    }

    @Test
    void getMyProperty(){
        buyProperty();
        Property linkedProperty = classicPropSpace.getMyProperty();
        Property expectedProp = player1.getProperties().get(0);
        assertEquals(linkedProperty, expectedProp);
    }
}