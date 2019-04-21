package Model.Tests;

import Controller.AbstractGame;
import Controller.ClassicGame;
import Model.AbstractPlayer;
import Model.ClassicPlayer;
import Model.actioncards.AbstractActionCard;
import Model.actioncards.ActionDeck;
import Model.actioncards.DeckType;
import Model.actioncards.GetOutJailAC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GetOutJailACTest {
    AbstractGame game;
    AbstractPlayer p1;
    AbstractPlayer p2;
    AbstractPlayer p3;
    AbstractActionCard ac;

    @BeforeEach
    void setUp() {
        p1 = new ClassicPlayer();
        p2 = new ClassicPlayer();
        p3 = new ClassicPlayer();
        game = new ClassicGame("Normal_Config_Rework.xml");
        game.setPlayers(List.of(p1, p2, p3));
        game.setCurrPlayer(0);

        ac = new GetOutJailAC(DeckType.CHANCE, "Get out of jail free!", true);
        for(ActionDeck d : game.getMyActionDecks()){
            if(d.getMyDeckType() == DeckType.CHANCE){
                ac.setDeck(d);
            }
        }
    }

    @Test
    void playerInJailSetFree() {
        game.getCurrPlayer().setJail(true);
        assertTrue(game.getCurrPlayer().isInJail());

        ac.doCardAction(game);
        assertFalse(game.getCurrPlayer().isInJail());
    }

    @Test
    void playerInJailCardReturned(){
        ActionDeck deck = ac.getMyDeck();
        int discardSize = deck.getMyDeadCards().size();
        game.getCurrPlayer().setJail(true);

        ac.doCardAction(game);

        var expected = discardSize + 1;
        var actual = deck.getMyDeadCards().size();
        assertEquals(expected, actual);
    }

    @Test
    void playerNotInJailCardSaved(){
        AbstractPlayer curr = game.getCurrPlayer();
        assertTrue(curr.getActionCards().isEmpty());
        curr.setJail(false);

        ac.doCardAction(game);
        var expected = 1;
        var actual = curr.getActionCards().size();
        assertEquals(expected, actual);
    }

    @Test
    void playerNotInJailCardNotReturned(){
        AbstractPlayer curr = game.getCurrPlayer();
        assertTrue(ac.getMyDeck().getMyDeadCards().isEmpty());
        curr.setJail(false);

        ac.doCardAction(game);
        assertTrue(ac.getMyDeck().getMyDeadCards().isEmpty());
    }
}