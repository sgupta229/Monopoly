package Model.tests;

import Controller.ClassicGame;
import Model.*;
import Model.spaces.AbstractSpace;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaxSpaceTest {

    //ConfigReader conf = new ConfigReader("Normal_Config_Old.xml");
    ClassicGame gameClass;
    ClassicPlayer player1;
    ClassicPlayer player2;
    AbstractSpace taxSpace;
    Bank gameBank;

    double startBalance = 10000;




    @BeforeEach
    void setUp() throws XmlReaderException {
        gameClass = new ClassicGame("Normal_Config_Rework.xml");
        gameClass.parseXMLFile("Normal_Config_Rework.xml");

        taxSpace = gameClass.getBoard().getSpaceAt(38);
        player1 = new ClassicPlayer();
        ObservableList<AbstractPlayer> playerList = FXCollections.observableList(new ArrayList<>());
        playerList.add(player1);
        //player1.receivePayment(100);
        gameClass.setPlayers(playerList);
        gameBank = gameClass.getBank();
    }

    @Test
    void doActionRemovesPlayerFunds() {
        var newFunds =player1.getFunds()-200;
        taxSpace.doAction(gameClass,0);
        assertEquals(newFunds, player1.getFunds());
    }

    @Test
    void doActionAddsBankFunds() {
        var newFunds =gameBank.getBankBalance()+200.0;
        taxSpace.doAction(gameClass,0);
        assertEquals(newFunds, gameBank.getBankBalance());
    }

    @Test
    void doActionRemovesPlayerFundsPercent() {
        var percentage = player1.getFunds()*.1;
        var newFunds =player1.getFunds()-percentage;
        taxSpace.doAction(gameClass,1);
        assertEquals(newFunds, player1.getFunds());
    }

    @Test
    void doActionAddsBankFundsPercent() {
        var percentage = player1.getFunds()*.1;
        var newFunds =gameBank.getBankBalance()+percentage;
        taxSpace.doAction(gameClass,1);
        assertEquals(newFunds, gameBank.getBankBalance());
    }
}