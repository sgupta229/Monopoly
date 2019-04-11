package Model.tests;

import Controller.ClassicGame;
import Controller.ConfigReader;
import Controller.Token;
import Model.AbstractPlayer;
import Model.ClassicPlayer;
import Model.XmlTagException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractPlayerTest {

    ConfigReader cr = new ConfigReader("Normal_Config.xml");
    AbstractPlayer p1;
    AbstractPlayer p2;
    int boardSize;
    ClassicGame game;
    private Token t1 = new Token(3);
    private Token t2 = new Token(38);

    @BeforeEach
    void setup() throws XmlTagException {
        boardSize = cr.parseBoard();

        p1 = new ClassicPlayer();
        p1.setToken(t1);
        p1.setFunds(500);

        p2 = new ClassicPlayer();
        p2.setToken(t2);
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
        p1.move(5, boardSize);
        int expected = 8;
        int actual = p1.getCurrentLocation();
        assertEquals(actual, expected);

    }

    @Test
    void checkMoveTo() {
        p2.moveTo(10, boardSize);
        int expected = 10;
        int actual = p2.getCurrentLocation();
        assertEquals(actual, expected);
    }

    @Test
    void checkMoveBeyondBoardSize() {
        p2.move(3, boardSize);
        int expected = 1;
        int actual = p2.getCurrentLocation();
        assertEquals(actual, expected);
    }

    @Test
    void checkInvalidMoveTo() {
        assertThrows(IllegalArgumentException.class, () -> p1.moveTo(41, boardSize));
    }


}