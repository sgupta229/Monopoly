package Model.Tests;

import Controller.AbstractGame;
import Controller.ClassicGame;
import Model.AbstractPlayer;
import Model.ClassicPlayer;
import Model.actioncards.AbstractActionCard;
import Model.actioncards.ActionDeck;
import Model.actioncards.DeckType;
import Model.actioncards.GoToJailAC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GoToJailACTest {
    AbstractGame game;
    AbstractPlayer p1;
    AbstractPlayer p2;
    AbstractActionCard ac;

    @BeforeEach
    void setUp(){
        p1 = new ClassicPlayer("boot.png");
        p2 = new ClassicPlayer("battleship.png");
        game = new ClassicGame("Normal_Config_Old.xml");
        game.setPlayers(List.of(p1, p2));
        game.setCurrPlayer(1);

        ac = new GoToJailAC(DeckType.CHANCE, "Go to jail!", false);
        for(ActionDeck d : game.getMyActionDecks()){
            if(d.getMyDeckType() == DeckType.CHANCE){
                ac.setDeck(d);
            }
        }

    }

    @Test
    void playerLocationMoveCheck() {
        AbstractPlayer curr = game.getCurrPlayer();
        curr.setCurrentLocation(0);
        int prevLocation = curr.getCurrentLocation();
        var expected = game.getBoard().getLocationOfSpace("JAIL");
        assertNotEquals(prevLocation, expected);

        ac.doCardAction(game);
        var actual = curr.getCurrentLocation();
        assertEquals(expected, actual);
    }

    @Test
    void playerJailSetChanges(){
        AbstractPlayer curr = game.getCurrPlayer();
        assertFalse(curr.isInJail());

        ac.doCardAction(game);
        assertTrue(curr.isInJail());
    }

    @Test
    void cardReturnedToDiscard(){
        AbstractPlayer curr = game.getCurrPlayer();
        int prevSize = ac.getMyDeck().getMyDeadCards().size();
        assertEquals(0, prevSize);

        ac.doCardAction(game);

        int newSize = ac.getMyDeck().getMyDeadCards().size();
        assertEquals(1, newSize);
    }
}