package Model.tests;

import Controller.ClassicGame;
import Controller.Token;
import Model.AbstractPlayer;
import Model.AbstractSpace;
import Model.ClassicPlayer;
import Model.XmlTagException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GoToSpaceTest {

    //ConfigReader conf = new ConfigReader("Normal_Config.xml");
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
    void setUp() throws XmlTagException {
        gameClass = new ClassicGame("Normal_Config.xml");
        goToJail = gameClass.getBoard().getSpaceAt(30);
        player1 = new ClassicPlayer();
        player1.setToken(new Token(0));
        List<AbstractPlayer> playerList = new ArrayList<>();
        playerList.add(player1);
        gameClass.setPlayers(playerList);
        player1.moveTo(30, gameClass.getBoardSize());
        player2 = new ClassicPlayer();


    }

    @Test
    void doActionMovesPlayer() {
        goToJail.doAction(gameClass);
        var newLocation = player1.getToken().getCurrentLocation();
        assertEquals(10, newLocation);
    }
    @Test
    void doActionSetsJailToTrue() {
        goToJail.doAction(gameClass);
        var jailbool = player1.isInJail();
        assertEquals(true, jailbool);
    }
}