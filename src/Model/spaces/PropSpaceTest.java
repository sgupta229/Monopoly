package Model.spaces;

import Controller.ClassicGame;
import Controller.Token;
import Model.AbstractPlayer;
import Model.ClassicPlayer;
import Model.Bank;
import Model.properties.Property;
import Model.XmlTagException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PropSpaceTest {

    ClassicGame gameClass;
    ClassicPlayer player1;
    ClassicPlayer player2;
    PropSpace propSpace;
    //List<AbstractSpace> spaceList;
    //List<Property> propsList;
    Bank gameBank;
    //Property prop1;

    double startBalance = 10000;




    @BeforeEach
    void setUp() throws XmlTagException {
        gameClass = new ClassicGame("Normal_Config.xml");
        propSpace = (PropSpace) gameClass.getBoard().getSpaceAt(1);
        player1 = new ClassicPlayer();
        player2 = new ClassicPlayer();
        List<AbstractPlayer> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);
        gameClass.setPlayers(playerList);
        player1.moveTo(1);
        gameBank = gameClass.getBank();
        //player1.moveTo(30, gameClass.getBoardSize());



    }

    AbstractPlayer buyProperty(){
        propSpace.doAction(gameClass, 0);
        AbstractPlayer propOwner = gameBank.propertyOwnedBy(propSpace.getMyProperty());
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
        propSpace.doAction(gameClass, 2);
        double player2NewFunds = player2.getFunds();
        double player1NewFunds = player1.getFunds();
        assertTrue(player2OldFunds>player2NewFunds);
        assertTrue(player1OldFunds<player1NewFunds);
    }

    @Test
    void getMyProperty(){
        buyProperty();
        Property linkedProperty = propSpace.getMyProperty();
        Property expectedProp = player1.getProperties().get(0);
        assertEquals(linkedProperty, expectedProp);
    }
}