package Model.Tests;

import Controller.AbstractGame;
import Controller.ClassicGame;
import Model.AbstractPlayer;
import Model.ClassicPlayer;
import Model.actioncards.AbstractActionCard;
import Model.actioncards.ActionDeck;
import Model.actioncards.DeckType;
import Model.actioncards.MoveToAC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveToACTest {
    AbstractGame game;
    AbstractPlayer p1;
    AbstractActionCard ac1;
    AbstractActionCard ac2;
    AbstractActionCard ac3;
    AbstractActionCard ac4;
    AbstractActionCard ac5;

    @BeforeEach
    void setUp() {
        p1 = new ClassicPlayer("boot.png");
        game = new ClassicGame("Normal_Config.xml");
        game.setPlayers(List.of(p1));
        game.setCurrPlayer(0);

        ac1 = new MoveToAC(DeckType.CHANCE, "Move to Boardwalk! If unowned you can buy it!", false, "BOARDWALK");
        ac2 = new MoveToAC(DeckType.COMMUNITY_CHEST, "Move to nearest railroad.", false, "RAILROAD");
        ac3 = new MoveToAC(DeckType.COMMUNITY_CHEST, "Move to nearest utility.", false, "UTILITY");
        ac4 = new MoveToAC(DeckType.CHANCE, "Move back 3 spaces.", false, "-3");
        ac5 = new MoveToAC(DeckType.CHANCE, "Move forward 5 spaces.", false, "5");

        for(ActionDeck d : game.getMyActionDecks()){
            if(d.getMyDeckType() == DeckType.CHANCE){
                ac1.setDeck(d);
                ac4.setDeck(d);
                ac5.setDeck(d);
            }
            else{
                ac2.setDeck(d);
                ac3.setDeck(d);
            }
        }
    }

    @Test
    void moveToSpecificBoardwalk() {
        AbstractPlayer curr = game.getCurrPlayer();
        System.out.println(curr.getCurrentLocation());
        var expected = game.getBoard().getLocationOfSpace("BOARDWALK");
        ac1.doCardAction(game);
        var actual = curr.getCurrentLocation();
        assertEquals(expected, actual);
    }

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
    }

}