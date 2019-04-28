package Model.Tests;

import Controller.ClassicGame;
import Model.AbstractPlayer;
import Model.spaces.AbstractSpace;
import Model.ClassicPlayer;
import Model.XmlReaderException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GoToSpaceTest {

    //ConfigReader conf = new ConfigReader("Normal_Config_Old.xml");
    ClassicGame gameClass;
    ClassicPlayer player1;
    ClassicPlayer player2;
    AbstractSpace goToJail;
    //List<AbstractSpace> spaceList;
    //List<Property> propsList;
    //Bank gameBank;
    //Property prop1;

    double startBalance = 10000;




    @BeforeEach
    void setUp() throws XmlReaderException {
        gameClass = new ClassicGame("Normal_Config_Rework.xml");
        gameClass.parseXMLFile("Normal_Config_Rework.xml");
        goToJail = gameClass.getBoard().getSpaceAt(30);
        player1 = new ClassicPlayer();
        ObservableList<AbstractPlayer> playerList = FXCollections.observableList(new ArrayList<>());
        playerList.add(player1);
        gameClass.setPlayers(playerList);
        player1.moveTo(30);
        //player1.moveTo(30, gameClass.getBoardSize());
        player2 = new ClassicPlayer();


    }

    @Test
    void doActionMovesPlayer() {
        goToJail.doAction(gameClass,0);
        var newLocation = player1.getCurrentLocation();
        assertEquals(10, newLocation);
    }
    @Test
    void doActionSetsJailToTrue() {
        goToJail.doAction(gameClass, 0);
        var jailbool = player1.isInJail();
        assertEquals(true, jailbool);
    }
}
