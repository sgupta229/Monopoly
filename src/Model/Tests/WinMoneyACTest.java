package Model.tests;

import Controller.AbstractGame;
import Controller.ClassicGame;
import Model.AbstractPlayer;
import Model.Bank;
import Model.ClassicPlayer;
import Model.actioncards.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WinMoneyACTest {
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
        game = new ClassicGame("Normal_Config.xml");
        game.setPlayers(List.of(p1, p2, p3));
        game.setCurrPlayer(0);

        ac1 = new WinMoneyAC(DeckType.CHANCE, "Bank pays dividend of $75!", false, "BANK", 75.0);
        ac2 = new WinMoneyAC(DeckType.COMMUNITY_CHEST, "Each player pays you $20!", false, "ALL", 20.0);

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
    void win75FromBank() {
        AbstractPlayer curr = game.getCurrPlayer();
        var expected = curr.getFunds() + 75;

        ac1.doCardAction(game);

        var actual = curr.getFunds();
        assertEquals(expected, actual);
    }

    @Test
    void bankPays75(){
        Bank b = game.getBank();
        var expected = b.getBankBalance() - 75;

        ac1.doCardAction(game);

        var actual = b.getBankBalance();
        assertEquals(expected, actual);
    }

    @Test
    void wins20FromAll(){
        AbstractPlayer curr =game.getCurrPlayer();
        var expected = curr.getFunds() + 40;

        ac2.doCardAction(game);

        var actual = curr.getFunds();
        assertEquals(expected, actual);
    }

    @Test
    void allLose20(){
        List<AbstractPlayer> players = game.getPlayers();
        var expected = 2500 - 20;

        ac2.doCardAction(game);

        var actual1 = players.get(1).getFunds();
        var actual2 = players.get(2).getFunds();
        assertEquals(expected, actual1, actual2);
    }
}