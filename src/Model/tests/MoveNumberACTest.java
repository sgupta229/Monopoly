package Model.Tests;

import Controller.AbstractGame;
import Controller.ClassicGame;
import Model.AbstractPlayer;
import Model.ClassicPlayer;
import Model.XmlReaderException;
import Model.actioncards.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveNumberACTest {
    AbstractGame game;
    AbstractPlayer p1;
    AbstractActionCard ac1;
    AbstractActionCard ac2;

    @BeforeEach
    void setUp() {
        p1 = new ClassicPlayer();
        try {
            game = new ClassicGame("Normal_Config_Rework.xml");
            game.parseXMLFile("Normal_Config_Rework.xml");

        } catch (XmlReaderException e) {
            e.printStackTrace();
        }
        ObservableList<AbstractPlayer> playerList = FXCollections.observableList(new ArrayList<>());
        playerList.addAll(p1);
        game.setPlayers(playerList);
        game.setCurrPlayer(0);

        ac1 = new MoveNumberAC(DeckType.CHANCE, "Move back 3 spaces", false, List.of("-3"), List.of(0.0));
        ac2 = new MoveNumberAC(DeckType.CHANCE, "Move forward 5 spaces.", false, List.of("5"), List.of(0.0));

        for(ActionDeck d : game.getMyActionDecks()){
            if(d.getMyDeckType() == DeckType.CHANCE){
                ac1.setDeck(d);
                ac2.setDeck(d);
            }
        }
    }

    @Test
    void moveBack3Spaces() {
        AbstractPlayer curr = game.getCurrPlayer();
        var expected = 37;
        ac1.doCardAction(game);
        var actual = curr.getCurrentLocation();
        assertEquals(expected, actual);
    }

    @Test
    void moveForward5Spaces(){
        AbstractPlayer curr = game.getCurrPlayer();
        var expected = 5;
        ac2.doCardAction(game);
        var actual = curr.getCurrentLocation();
        assertEquals(expected, actual);
    }
}