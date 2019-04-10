package Model.Tests;

import Controller.Board;
import Controller.ClassicGame;
import Controller.ConfigReader;
import Model.AbstractPlayer;
import Model.AbstractSpace;
import Model.ClassicPlayer;
import Model.XmlTagException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AbstractPlayerTest {

    ConfigReader cr = new ConfigReader("Normal_Config.xml");
    AbstractPlayer p1;
    AbstractPlayer p2;
    Board board;
    ClassicGame game;

    @BeforeEach
    void setup() throws XmlTagException {
        p1 = new ClassicPlayer();
        p1.setFunds(500);
        p2 = new ClassicPlayer();
        p2.setFunds(500);
    }


    @Test
    void checkPayToAnotherPlayer() {
        p1.makePayment(200, p2);
        double actualp1 = p1.getFunds();
        double expectedp1 = 300;
        assertEquals(actualp1, expectedp1);
    }

    @Test
    void checkReceiveAfterPayment() {
        p1.makePayment(200, p2);
        double actualp2 = p2.getFunds();
        double expectedp2 = 700;
        assertEquals(actualp2, expectedp2);
    }

    @Test
    void checkInvalidPayment() {
        assertThrows(IllegalArgumentException.class, () -> p1.makePayment(700, p2));
    }

    @Test
    void checkMove() {

    }

    @Test
    void checkMoveTo() {

    }


}