package Model.Tests;

import Controller.AbstractGame;
import Controller.ClassicGame;
import Model.AbstractPlayer;
import Model.ClassicPlayer;
import Model.XmlReaderException;
import Model.actioncards.AbstractActionCard;
import Model.actioncards.ActionDeck;
import Model.actioncards.DeckType;
import Model.actioncards.GoToJailAC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GoToJailACTest {
    AbstractGame game;
    AbstractPlayer p1;
    AbstractPlayer p2;
    AbstractActionCard ac;

    @BeforeEach
    void setUp(){
        p1 = new ClassicPlayer();
        p2 = new ClassicPlayer();
        try {
            game = new ClassicGame("Normal_Config_Rework.xml");
            game.parseXMLFile("Normal_Config_Rework.xml");
        } catch (XmlReaderException e) {
            e.printStackTrace();
        }
        ObservableList<AbstractPlayer> playerList = FXCollections.observableList(new ArrayList<>());
        playerList.addAll(p1,p2);
        game.setPlayers(playerList);
        game.setCurrPlayer(1);
        ArrayList<String> exStrings = new ArrayList<>();
        ArrayList<Double> extraDouble = new ArrayList<>();
        exStrings.add("JAIL");
        ac = new GoToJailAC(DeckType.CHANCE, "Go to jail!", false, exStrings, extraDouble);
        for(ActionDeck d : game.getMyActionDecks()){
            if(d.getMyDeckType() == DeckType.CHANCE){
                ac.setDeck(d);
            }
        }

    }

    @Test
    void playerLocationMoveCheck() {
        AbstractPlayer curr = game.getCurrPlayer();
        curr.setCurrentLocation(0);
        int prevLocation = curr.getCurrentLocation();
        var expected = game.getBoard().getLocationOfSpace("JAIL");
        assertNotEquals(prevLocation, expected);

        ac.doCardAction(game);
        var actual = curr.getCurrentLocation();
        assertEquals(expected, actual);
    }

    @Test
    void playerJailSetChanges(){
        AbstractPlayer curr = game.getCurrPlayer();
        assertFalse(curr.isInJail());

        ac.doCardAction(game);
        assertTrue(curr.isInJail());
    }

    @Test
    void cardReturnedToDiscard(){
        AbstractPlayer curr = game.getCurrPlayer();
        int prevSize = ac.getMyDeck().getMyDeadCards().size();
        assertEquals(0, prevSize);

        ac.doCardAction(game);

        int newSize = ac.getMyDeck().getMyDeadCards().size();
        assertEquals(1, newSize);
    }
}