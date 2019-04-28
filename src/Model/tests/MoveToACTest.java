package Model.tests;

import Controller.AbstractGame;
import Controller.ClassicGame;
import Model.AbstractPlayer;
import Model.ClassicPlayer;
import Model.XmlReaderException;
import Model.actioncards.AbstractActionCard;
import Model.actioncards.ActionDeck;
import Model.actioncards.DeckType;
import Model.actioncards.MoveToAC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveToACTest {
    AbstractGame tropical_game;
    AbstractGame normal_game;
    AbstractPlayer p1;
    AbstractActionCard ac1;
    AbstractActionCard ac2;
    AbstractActionCard ac3;
    AbstractActionCard ac4;
    AbstractActionCard ac5;

    @BeforeEach
    void setUp() {
        p1 = new ClassicPlayer();
        try {
            tropical_game = new ClassicGame("Tropical_Config.xml");
            tropical_game.parseXMLFile("Tropical_Config.xml");

            normal_game = new ClassicGame("Normal_Config_Rework.xml");
            normal_game.parseXMLFile("Normal_Config_Rework.xml");

        } catch (XmlReaderException e) {
            e.printStackTrace();
        }
        ObservableList<AbstractPlayer> playerList = FXCollections.observableList(new ArrayList<>());
        playerList.addAll(p1);
        tropical_game.setPlayers(playerList);
        normal_game.setPlayers(playerList);
        tropical_game.setCurrPlayer(0);
        normal_game.setCurrPlayer(0);

        ac1 = new MoveToAC(DeckType.CHANCE, "Move to Boardwalk! If unowned you can buy it!", false, "BOARDWALK");
        ac2 = new MoveToAC(DeckType.CHANCE, "Take a ride on the reading railroad! If you pass go, collect $200.", false, List.of("READING_RAILROAD"), List.of(200.0));
        ac3 = new MoveToAC(DeckType.TREASURE_CHEST, "GO TO MYKONOS WITH A VISION OF A GENTAL COAST. ADVANCE TOKEN TO MYKONOS.", false, "MYKONOS");
        ac4 = new MoveToAC(DeckType.LUAU_GIFT_BAG, "TAKE A TRIP TO HAWAII, HOP ON HAWAIIAN AIR. IF YOU PASS GO, COLLECT $200!", false, List.of("HAWAIIAN_AIR"), List.of(200.0));

        for(ActionDeck d : tropical_game.getMyActionDecks()){
            if(d.getMyDeckType() == DeckType.LUAU_GIFT_BAG){
                ac4.setDeck(d);
            }
            if(d.getMyDeckType() == DeckType.TREASURE_CHEST){
                ac3.setDeck(d);
            }
        }
        for(ActionDeck d : normal_game.getMyActionDecks()){
            if(d.getMyDeckType() == DeckType.CHANCE){
                ac1.setDeck(d);
                ac2.setDeck(d);
            }
        }
    }

    @Test
    void moveToSpecificMykonos() {
        AbstractPlayer curr = tropical_game.getCurrPlayer();

        //var expected = game.getBoard().getLocationOfSpace("BOARDWALK");
        var expected = tropical_game.getBoard().getLocationOfSpace("MYKONOS");
        //ac1.doCardAction(game);
        ac3.doCardAction(tropical_game);
        var actual = curr.getCurrentLocation();
        assertEquals(expected, actual);
    }

    @Test
    void moveToSpecificHawaiianAir(){
        AbstractPlayer curr = tropical_game.getCurrPlayer();

        //var expected = game.getBoard().getLocationOfSpace("READING_RAILROAD");
        var expected = tropical_game.getBoard().getLocationOfSpace("HAWAIIAN_AIR");
        //ac2.doCardAction(game);
        ac4.doCardAction(tropical_game);
        var actual = curr.getCurrentLocation();
        assertEquals(expected, actual);
    }

    @Test
    void moveToSpecificPassGo(){
        AbstractPlayer curr = normal_game.getCurrPlayer();
        //36 is the chance action space before go
        curr.setCurrentLocation(36);
        var expected = curr.getFunds() + 200;
        ac2.doCardAction(normal_game);
        var actual = curr.getFunds();
        assertEquals(expected, actual);
    }

    @Test
    void moveToSpecificBoardwalk() {
        AbstractPlayer curr = normal_game.getCurrPlayer();

        var expected = normal_game.getBoard().getLocationOfSpace("BOARDWALK");
        ac1.doCardAction(normal_game);

        var actual = curr.getCurrentLocation();
        assertEquals(expected, actual);
    }

    @Test
    void moveToSpecificReadingRailroad(){
        AbstractPlayer curr = tropical_game.getCurrPlayer();

        var expected = normal_game.getBoard().getLocationOfSpace("READING_RAILROAD");
        ac2.doCardAction(normal_game);
        var actual = curr.getCurrentLocation();
        assertEquals(expected, actual);
    }

/*
    @Test
    void moveToGeneralRailroad(){
        AbstractPlayer curr = game.getCurrPlayer();
        var expected = game.getBoard().getLocationOfSpace("READING_RAILROAD");
        ac2.doCardAction(game);
        var actual = curr.getCurrentLocation();
        assertEquals(expected, actual);
    }

    @Test
    void moveToGeneralRailroadFromDiffStart(){
        AbstractPlayer curr = game.getCurrPlayer();
        curr.setCurrentLocation(31);
        var expected = 35;
        ac2.doCardAction(game);
        var actual = curr.getCurrentLocation();
        assertEquals(expected, actual);
    }

    @Test
    void moveToGeneralPassingGo(){
        AbstractPlayer curr = game.getCurrPlayer();
        curr.setCurrentLocation(38);
        //index = 12;
        var expected = game.getBoard().getLocationOfSpace("ELECTRIC_COMPANY");
        ac3.doCardAction(game);
        var actual = curr.getCurrentLocation();
        assertEquals(expected, actual);
    }

    @Test
    void moveNumberOfSpacesBack(){
        AbstractPlayer curr = game.getCurrPlayer();
        var expected = 37;
        ac4.doCardAction(game);
        var actual = curr.getCurrentLocation();

        assertEquals(expected, actual);
    }

    @Test void moveNumSpacesForward(){
        AbstractPlayer curr = game.getCurrPlayer();
        var expected = 5;
        ac5.doCardAction(game);
        var actual = curr.getCurrentLocation();

        assertEquals(expected, actual);
    }*/

}