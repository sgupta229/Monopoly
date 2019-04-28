package Model.tests;

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

class MoveToNearestACTest {
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

        ac1 = new MoveToNearestAC(DeckType.CHANCE, "Move to nearest utility. If pass go, collect $200.", false, List.of("UTILITY"), List.of(200.0));
        ac2 = new MoveToNearestAC(DeckType.CHANCE, "Move to nearest railroad. If pass go, collect $200.", false, List.of("RAILROAD"), List.of(200.0));

        for(ActionDeck d : game.getMyActionDecks()){
            if(d.getMyDeckType() == DeckType.CHANCE){
                ac1.setDeck(d);
                ac2.setDeck(d);
            }
        }
    }

    @Test
    void moveNearestUtilityElectric() {
        AbstractPlayer curr = game.getCurrPlayer();
        var expected = game.getBoard().getLocationOfSpace("ELECTRIC_COMPANY");
        ac1.doCardAction(game);
        var actual = curr.getCurrentLocation();
        assertEquals(expected, actual);
    }

    @Test
    void moveNearestUtilityWater(){
        AbstractPlayer curr = game.getCurrPlayer();
        curr.setCurrentLocation(20);
        var expected = game.getBoard().getLocationOfSpace("WATER_WORKS");
        ac1.doCardAction(game);
        var actual = curr.getCurrentLocation();
        assertEquals(expected, actual);
    }

    @Test
    void moveNearestRRPennsylvania(){
        AbstractPlayer curr = game.getCurrPlayer();
        curr.setCurrentLocation(11);
        var expected = game.getBoard().getLocationOfSpace("PENNSYLVANIA_RAILROAD");
        ac2.doCardAction(game);
        var actual = curr.getCurrentLocation();
        assertEquals(expected, actual);
    }

    @Test
    void moveNearestRRReadingPassGo(){
        AbstractPlayer curr = game.getCurrPlayer();
        curr.setCurrentLocation(36);
        var expected1 = game.getBoard().getLocationOfSpace("READING_RAILROAD");
        var expected2 = curr.getFunds() + 200;
        ac2.doCardAction(game);
        var actual1 = curr.getCurrentLocation();
        var actual2 = curr.getFunds();
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }
}