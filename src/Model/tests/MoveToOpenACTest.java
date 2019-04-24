package Model.tests;

import Controller.AbstractGame;
import Controller.ClassicGame;
import Model.AbstractPlayer;
import Model.ClassicPlayer;
import Model.XmlReaderException;
import Model.actioncards.*;
import Model.properties.Property;
import Model.spaces.ClassicPropSpace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveToOpenACTest {
    AbstractGame game;
    AbstractPlayer p1;
    AbstractPlayer p2;
    AbstractActionCard ac1;
    AbstractActionCard ac2;
    AbstractActionCard ac3;

    @BeforeEach
    void setUp() {
        p1 = new ClassicPlayer();
        p2 = new ClassicPlayer();
        try {
            game = new ClassicGame("Junior_Config.xml");
        } catch (XmlReaderException e) {
            e.printStackTrace();
        }
        game.setPlayers(List.of(p1, p2));
        game.setCurrPlayer(0);

        ac1 = new MoveToOpenAC(DeckType.CHANCE, "ADVANCE TO A BROWN OR YELLOW SPACE. IF ONE IS AVAILABLE, GET IT FOR FREE! OTHERWISE PAY OWNER.", false, List.of("BROWN","YELLOW"), List.of(0.0));
        ac2 = new MoveToOpenAC(DeckType.CHANCE, "ADVANCE TO A LIGHT BLUE SPACE. IF ONE IS AVAILABLE, GET IT FOR FREE! OTHERWISE PAY OWNER.", false, List.of("LIGHTBLUE"), List.of(0.0));

        for(ActionDeck d : game.getMyActionDecks()){
            if(d.getMyDeckType() == DeckType.CHANCE){
                ac1.setDeck(d);
                ac2.setDeck(d);
            }
        }
    }

    @Test
    void moveToBrownAllOpen() {
        AbstractPlayer curr = game.getCurrPlayer();
        var expected = game.getBoard().getLocationOfSpace("BURGER_JOINT");
        System.out.println(expected);
        ac1.doCardAction(game);
        var actual = curr.getCurrentLocation();
        assertEquals(expected, actual);
    }

    @Test
    void moveToBrownFirstOwned(){
        AbstractPlayer curr = game.getCurrPlayer();
        ClassicPropSpace sp = (ClassicPropSpace) game.getBoard().getSpaceAt(1);
        game.getBank().setPropertyOwner(sp.getMyProperty(), p2);
        var expected = game.getBoard().getLocationOfSpace("PIZZA_HOUSE");
        System.out.println(expected);
        ac1.doCardAction(game);
        var actual = curr.getCurrentLocation();
        assertEquals(expected, actual);
    }

    @Test
    void moveToYellowBrownOwnedOwnYellow(){
        AbstractPlayer curr = game.getCurrPlayer();
        ClassicPropSpace sp1 = (ClassicPropSpace) game.getBoard().getSpaceAt(1);
        ClassicPropSpace sp2 = (ClassicPropSpace) game.getBoard().getSpaceAt(2);
        game.getBank().setPropertyOwner(sp1.getMyProperty(), p2);
        game.getBank().setPropertyOwner(sp2.getMyProperty(), p2);
        var expected1 = game.getBoard().getLocationOfSpace("TOY_STORE");
        ac1.doCardAction(game);
        var actual1 = curr.getCurrentLocation();
        assertEquals(expected1, actual1);

        var expected2 = curr;
        ClassicPropSpace sp3 = (ClassicPropSpace) game.getBoard().getSpaceAt(curr.getCurrentLocation());
        Property prop = sp3.getMyProperty();
        var actual2 = game.getBank().propertyOwnedBy(prop);
        assertEquals(expected2, actual2);
    }

    @Test
    void moveToLightBlueAndOwn(){
        AbstractPlayer curr = game.getCurrPlayer();
        //ClassicPropSpace sp = (ClassicPropSpace) game.getBoard().getSpaceAt(4);
        //game.getBank().setPropertyOwner(sp.getMyProperty(), p2);
        var expected = game.getBoard().getLocationOfSpace("CANDY_STORE");

        ac2.doCardAction(game);
        var actual = curr.getCurrentLocation();
        assertEquals(expected, actual);

        ClassicPropSpace sp = (ClassicPropSpace) game.getBoard().getSpaceAt(4);
        var expected2 = curr;
        var actual2 = game.getBank().propertyOwnedBy(sp.getMyProperty());
        assertEquals(expected2, actual2);
    }
}