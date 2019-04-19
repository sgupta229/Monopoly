package Model.tests;

import Controller.AbstractGame;
import Controller.ClassicGame;
import Model.AbstractPlayer;
import Model.ClassicPlayer;
import Model.actioncards.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveToOpenACTest {
    AbstractGame game;
    AbstractPlayer p1;
    AbstractActionCard ac1;
    AbstractActionCard ac2;
    AbstractActionCard ac3;

    @BeforeEach
    void setUp() {
        p1 = new ClassicPlayer("boot.png");
        game = new ClassicGame("Junior_Config.xml");
        game.setPlayers(List.of(p1));
        game.setCurrPlayer(0);

        ac1 = new MoveToOpenAC(DeckType.CHANCE, "ADVANCE TO A BROWN OR YELLOW SPACE. IF ONE IS AVAILABLE, GET IT FOR FREE! OTHERWISE PAY OWNER.", false, List.of("BROWN,YELLOW"), List.of(0.0));
        ac2 = new MoveToOpenAC(DeckType.CHANCE, "ADVANCE TO A LIGHT BLUE SPACE. IF ONE IS AVAILABLE, GET IT FOR FREE! OTHERWISE PAY OWNER.", false, List.of("LIGHTBLUE"), List.of(0.0));

        for(ActionDeck d : game.getMyActionDecks()){
            if(d.getMyDeckType() == DeckType.CHANCE){
                ac1.setDeck(d);
                ac2.setDeck(d);
            }
        }
    }

    @Test
    void moveToOpenBrown() {
        AbstractPlayer curr = game.getCurrPlayer();
        var expected = game.getBoard().getLocationOfSpace("BURGER_JOINT");
        System.out.println(expected);
        ac1.doCardAction(game);
        var actual = curr.getCurrentLocation();
        assertEquals(expected, actual);
    }
}