package Model.Tests;

import Controller.ConfigReader;
import Model.AbstractPlayer;
import Model.Bank;
import Model.ClassicPlayer;
import Model.XmlReaderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractPlayerTest {

    ConfigReader cr;

    {
        try {
            cr = new ConfigReader("Normal_Config_Old.xml");
        } catch (XmlReaderException e) {
            e.printStackTrace();
        }
    }

    AbstractPlayer p1;
    AbstractPlayer p2;
    Bank b;
    int boardSize;

    @BeforeEach
    void setup() throws XmlReaderException {
        boardSize = cr.parseBoard();

        b = new Bank();
        p1 = new ClassicPlayer();
        p1.setFunds(500);
        p1.setCurrentLocation(0);

        p2 = new ClassicPlayer();
        p2.setFunds(500);
        p2.setCurrentLocation(39);
    }


    @Test
    void checkPayToAnotherPlayer() {
        p1.makePayment(b, 200, p2);
        double actualp1 = p1.getFunds();
        double expectedp1 = 300;
        assertEquals(actualp1, expectedp1);
    }

    @Test
    void checkReceiveAfterPayment() {
        p1.makePayment(b, 200, p2);
        double actualp2 = p2.getFunds();
        double expectedp2 = 700;
        assertEquals(actualp2, expectedp2);
    }

    //@Test
    //void checkInvalidPayment() {
        //assertThrows(IllegalArgumentException.class, () -> p1.makePayment(b, 700, p2));
    //}

    @Test
    void checkMoveTo() {
        p1.moveTo(5);
        int expected = 5;
        int actual = p1.getCurrentLocation();
        assertEquals(actual, expected);

    }
}