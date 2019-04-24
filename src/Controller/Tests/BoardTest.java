package Controller.Tests;

import Model.*;
import Controller.Board;
import Controller.ConfigReader;
import Controller.GameBoardException;

import Model.spaces.AbstractSpace;
import Model.spaces.GoSpace;
import Model.spaces.JailSpace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    ConfigReader cr;

    {
        try {
            cr = new ConfigReader("Normal_Config_Old.xml");
        } catch (XmlReaderException e) {
            e.printStackTrace();
        }
    }

    Board board;

    @BeforeEach
    void setup() throws XmlReaderException {
        List<AbstractSpace> spaces = cr.parseSpaces().get(0);
        int numSpaces = cr.parseBoard();
        board = new Board(numSpaces, spaces);
    }

    @Test
    void getSpaceAtGoLocationZero() {
        AbstractSpace expected = new GoSpace(0, "GO");
        AbstractSpace actual = board.getSpaceAt(0);

        assertEquals(expected, actual);
        assertEquals(expected.getMyName(), actual.getMyName());
    }

    @Test
    void getSpaceAtJailLocation(){
        AbstractSpace expected = new JailSpace(10, "JAIL");
        AbstractSpace actual = board.getSpaceAt(10);

        assertEquals(expected, actual);
        assertEquals(expected.getMyName(), expected.getMyName());
    }

    @Test
    void getLocationOfGoSpace() {
        int expected = 0;
        int actual = board.getLocationOfSpace("GO");

        assertEquals(expected, actual);
    }

    @Test
    void getLocationOfJailSpace(){
        int expected = 10;
        int actual = board.getLocationOfSpace("JAIL");

        assertEquals(expected, actual);
    }

    @Test
    void getLocationToGetSpaceAt(){
        int location = board.getLocationOfSpace("BOARDWALK");
        String actual = board.getSpaceAt(location).getMyName();
        String expected = "BOARDWALK";

        assertEquals(expected, actual);
    }

    @Test
    void getSpaceAtErrorThrowsException1(){
        //AbstractSpace space = board.getSpaceAt(50);
        assertThrows(GameBoardException.class, () -> board.getSpaceAt(50));
    }

    @Test
    void getSpaceAtErrorThrowsException2(){
        assertThrows(GameBoardException.class, () -> board.getSpaceAt(-4));
    }

    @Test
    void getLocationOfSpaceErrorThrowsException(){
        assertThrows(GameBoardException.class, () -> board.getLocationOfSpace("RYAN"));
    }
}