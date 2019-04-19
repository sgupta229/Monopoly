package Model.Tests;

import Controller.AbstractGame;
import Controller.ClassicGame;
import Model.AbstractPlayer;
import Model.ClassicPlayer;
import Model.actioncards.AbstractActionCard;
import Model.actioncards.ActionDeck;
import Model.actioncards.DeckType;
import Model.actioncards.LoseMoneyAC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoseMoneyACTest {
    AbstractGame game;
    AbstractPlayer p1;
    AbstractPlayer p2;
    AbstractPlayer p3;
    AbstractActionCard ac1;
    AbstractActionCard ac2;

    @BeforeEach
    void setUp() {
        p1 = new ClassicPlayer("boot.png");
        p2 = new ClassicPlayer("battleship.png");
        p3 = new ClassicPlayer("chest.png");
        game = new ClassicGame("Normal_Config_Old.xml");
        game.setPlayers(List.of(p1, p2, p3));
        game.setCurrPlayer(0);

        ac1 = new LoseMoneyAC(DeckType.CHANCE, "Pay $40 per house and $100 per hotel you own", false, "BANK", List.of(40.0, 100.0));
        ac2 = new LoseMoneyAC(DeckType.COMMUNITY_CHEST, "Pay $50 to each player", false, "ALL", List.of(50.0));

        for(ActionDeck d : game.getMyActionDecks()){
            if(d.getMyDeckType() == DeckType.CHANCE){
                ac1.setDeck(d);
            }
            else{
                ac2.setDeck(d);
            }
        }
    }

    @Test
    void playerPaysAllOthers50() {
        AbstractPlayer curr = game.getCurrPlayer();
        double initialFunds = curr.getFunds();

        ac2.doCardAction(game);

        //2 other players in the game, should be $100 less than initial
        var expected = initialFunds-100;
        var actual = curr.getFunds();
        assertEquals(expected, actual);
    }

    @Test
    void allOtherPlayersGain50() {
        AbstractPlayer curr = game.getCurrPlayer();
        List<AbstractPlayer> players = game.getPlayers();
        var expected1 = 2500.00;
        var actualP1 = players.get(1).getFunds();
        var actualP2 = players.get(2).getFunds();

        assertEquals(expected1, actualP1, actualP2);

        ac1.doCardAction(game);

        var expected2 = 2550.00;
        var actual2P1 = players.get(1).getFunds();
        var actual2P2 = players.get(2).getFunds();
        assertEquals(expected2, actual2P1, actual2P2);
    }

    @Test
    void multiPay() {
    }
}