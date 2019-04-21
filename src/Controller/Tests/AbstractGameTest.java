package Controller.Tests;

import Controller.*;
import Model.AbstractPlayer;
import Model.ClassicPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbstractGameTest {

    ClassicGame game;
    ClassicPlayer p1;
    ClassicPlayer p2;
    ClassicPlayer p3;
    ClassicPlayer p4;

    @BeforeEach
    void setup() {
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
    }

    @Test
    void leftPlayer() {
        AbstractPlayer actual = game.getLeftPlayer();
        AbstractPlayer expected = p2;
        assertEquals(expected, actual);
    }

    @Test
    void nextPlayer() {
        game.getLeftPlayer();
        game.startNextTurn();
    }

}