package Model;

import Controller.Board;
import Controller.ConfigReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractPlayerTest {

    ConfigReader cr = new ConfigReader("Normal_Config.xml");
    AbstractPlayer player;
    Board board;


    @Test
    void checkMoveBeyondBoardLength() {

    }

}