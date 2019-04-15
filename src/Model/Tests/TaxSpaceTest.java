package Model.tests;

import Controller.ClassicGame;
import Model.*;
import Model.spaces.AbstractSpace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaxSpaceTest {

    //ConfigReader conf = new ConfigReader("Normal_Config.xml");
    ClassicGame gameClass;
    ClassicPlayer player1;
    ClassicPlayer player2;
    AbstractSpace taxSpace;
    Bank gameBank;

    double startBalance = 10000;




    @BeforeEach
    void setUp() throws XmlTagException {
        gameClass = new ClassicGame("Normal_Config.xml");
        taxSpace = gameClass.getBoard().getSpaceAt(38);
        player1 = new ClassicPlayer();
        List<AbstractPlayer> playerList = new ArrayList<>();
        playerList.add(player1);
        player1.receivePayment(100);
        gameClass.setPlayers(playerList);
        gameBank = gameClass.getBank();
    }

    @Test
    void doActionRemovesPlayerFunds() {
        var newFunds =player1.getFunds()-10;
        taxSpace.doAction(gameClass);
        assertEquals(newFunds, player1.getFunds());
    }

    @Test
    void doActionAddsBankFunds() {
        var newFunds =gameBank.getBankBalance()+10.0;
        taxSpace.doAction(gameClass);
        assertEquals(newFunds, gameBank.getBankBalance());
    }
}