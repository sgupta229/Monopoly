package Controller.Tests;

import Controller.*;
import Model.AbstractPlayer;
import Model.ClassicPlayer;
import Model.XmlReaderException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AbstractGameTest {

    AbstractGame game;
    ClassicPlayer p1;
    ClassicPlayer p2;
    ClassicPlayer p3;
    ClassicPlayer p4;

    @BeforeEach
    void setup() throws XmlReaderException {
        game = new ClassicGame("Normal_Config_Rework.xml");
        p1 = new ClassicPlayer();
        p1.setName("Sahil");
        p2 = new ClassicPlayer();
        p2.setName("Ryan");
        p3 = new ClassicPlayer();
        p3.setName("Dylan");
        p4 = new ClassicPlayer();
        p4.setName("Abby");
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);
        game.addPlayer(p4);
        p1.moveTo(1);
        p2.moveTo(2);
        p3.moveTo(3);
        p4.moveTo(4);
        ObservableList<AbstractPlayer> playerList = FXCollections.observableList(new ArrayList<>());
        playerList.addAll(p1,p2,p3,p4);
        game.setPlayers(playerList);
    }

    @Test
    void leftPlayer() {
        AbstractPlayer actual = game.getLeftPlayer();
        AbstractPlayer expected = p2;
        assertEquals(expected, actual);
    }

    @Test
    void rightPlayer() {
        AbstractPlayer actual = game.getRightPlayer();
        AbstractPlayer expected = p4;
        assertEquals(actual, expected);
    }

    @Test
    void nextPlayer() {
        game.startNextTurn();
        AbstractPlayer actual = game.getCurrPlayer();
        AbstractPlayer expected = game.getPlayers().get(1);
        assertEquals(expected, actual);
    }

    @Test
    void rollDice() {
        List<Integer> values = game.rollAndCheck();
        assertTrue(0 < values.get(0));
        assertTrue(values.get(0) < 7);
        assertTrue(0 < values.get(1));
        assertTrue(values.get(1) < 7);
    }

    @Test
    void checkDoubles() {
        game.getDiceHistory().get(0).add(5);
        game.getDiceHistory().get(1).add(4);
        game.getDiceHistory().get(0).add(2);
        game.getDiceHistory().get(1).add(1);
        game.getDiceHistory().get(0).add(3);
        game.getDiceHistory().get(1).add(3);
        boolean actual = game.checkDoubles();
        boolean expected = true;
        assertEquals(actual, expected);
    }

    @Test
    void checkThreeDoublesInRow() {
        game.getDiceHistory().get(0).add(2);
        game.getDiceHistory().get(1).add(3);
        game.getDiceHistory().get(1).add(1);
        game.getDiceHistory().get(0).add(1);
        game.getDiceHistory().get(1).add(1);
        game.getDiceHistory().get(0).add(1);
        game.getDiceHistory().get(1).add(1);
        game.getDiceHistory().get(0).add(1);
        boolean actual = game.checkDoublesForJail();
        boolean expected = true;
        assertEquals(actual, expected);
    }

    @Test
    void getBoardIndexAfterRoll() {
        int oldIndex = 39;
        int roll = 5;
        int actual = game.getNewIndex(oldIndex, roll);
        int expected = 4;
        assertEquals(actual, expected);
    }

    @Test
    void checkMovePlayer() {
        p1.moveTo(39);
        game.movePlayer(39, 5);
        int actual = p1.getCurrentLocation();
        int expected = 5;
    }

    @Test
    void getLastDiceRoll() {
        List<Integer> rolls = game.rollAndCheck();
        int val = 0;
        for(int i = 0; i < rolls.size(); i++) {
            val += rolls.get(i);
        }
        int actual = game.getLastDiceRoll();
        int expected = val;
        assertEquals(actual, expected);
    }

}