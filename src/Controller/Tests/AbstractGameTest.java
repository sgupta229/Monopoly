package Controller.Tests;

import Controller.*;
import Model.ClassicPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractGameTest {

    ClassicGame game;

    @BeforeEach
    void setup() {
        game = new ClassicGame("Normal_Config.xml");
        ClassicPlayer p1 = new ClassicPlayer();
        ClassicPlayer p2 = new ClassicPlayer();
        ClassicPlayer p3 = new ClassicPlayer();
        ClassicPlayer p4 = new ClassicPlayer();
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);
        game.addPlayer(p4);
    }

    @Test


}