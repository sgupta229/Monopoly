package Model.tests;

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

    @BeforeEach
    void setUp() {
        p1 = new ClassicPlayer("boot.png");
        game = new ClassicGame("Normal_Config.xml");
        game.setPlayers(List.of(p1));
        game.setCurrPlayer(0);

        ac1 = new MoveToAC(DeckType.CHANCE, "Move to Boardwalk! If unowned you can buy it!", false, "BOARDWALK");
        ac2 = new MoveToAC(DeckType.COMMUNITY_CHEST, "Move to nearest railroad.", false, "RAILROAD");
        ac3 = new MoveToAC(DeckType.CHANCE, "Move back 3 spaces.", false, "-3");

        for(ActionDeck d : game.getMyActionDecks()){
            if(d.getMyDeckType() == DeckType.CHANCE){
                ac1.setDeck(d);
                ac3.setDeck(d);
            }
            else{
                ac2.setDeck(d);
            }
        }
    }

    @Test
    void moveToSpecificBoardwalk() {
        AbstractPlayer curr = game.getCurrPlayer();
        var expected = game.getBoard().getLocationOfSpace("BOARDWALK");
        ac1.doCardAction(game);
        var actual = curr.getCurrentLocation();
        assertEquals(expected, actual);
    }

    @Test
    void moveToGeneralRailroad(){

    }
}