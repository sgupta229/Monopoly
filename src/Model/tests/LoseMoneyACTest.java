package Model.tests;

import Controller.AbstractGame;
import Controller.ClassicGame;
import Model.AbstractPlayer;
import Model.ClassicPlayer;
import Model.XmlReaderException;
import Model.actioncards.AbstractActionCard;
import Model.actioncards.ActionDeck;
import Model.actioncards.DeckType;
import Model.actioncards.LoseMoneyAC;
import Model.properties.BuildingType;
import Model.properties.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        p1 = new ClassicPlayer();
        p2 = new ClassicPlayer();
        p3 = new ClassicPlayer();
        try {
            game = new ClassicGame("Normal_Config_Rework.xml");
            game.parseXMLFile("Normal_Config_Rework.xml");
        } catch (XmlReaderException e) {
            e.printStackTrace();
        }
        ObservableList<AbstractPlayer> playerList = FXCollections.observableList(new ArrayList<>());
        playerList.addAll(p1,p2,p3);
        game.setPlayers(playerList);
        game.setCurrPlayer(0);

        ac1 = new LoseMoneyAC(DeckType.CHANCE, "Pay $40 per house and $100 per hotel you own", false, List.of("BANK"), List.of(40.0, 100.0));
        ac2 = new LoseMoneyAC(DeckType.COMMUNITY_CHEST, "Pay $50 to each player", false, List.of("ALL"), List.of(50.0));

        for (ActionDeck d : game.getMyActionDecks()) {
            if (d.getMyDeckType() == DeckType.CHANCE) {
                ac1.setDeck(d);
            } else {
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
        var expected = initialFunds - 100;
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
    void multiPayPerHouse() {
        AbstractPlayer curr = game.getCurrPlayer();
        List<Property> props = new ArrayList<>(game.getBank().getUnOwnedProps());
        for (Property p : props) {
            if (p.getColor().equalsIgnoreCase("DARKBLUE")) {
                curr.addProperty(p);
                game.getBank().setPropertyOwner(p, curr);
                p.setIsOwned(true);
            }
        }
        //1 house on each property
        for (Property p : curr.getProperties()) {
            game.getBank().build(p, BuildingType.HOUSE);
        }

        var expected = curr.getFunds() - 80;
        ac1.doCardAction(game);
        var actual = curr.getFunds();
        assertEquals(expected, actual);
    }

    @Test
    void multiPayPerHotel() {
        AbstractPlayer curr = game.getCurrPlayer();
        List<Property> props = new ArrayList<>(game.getBank().getUnOwnedProps());
        for (Property p : props) {
            if (p.getColor().equalsIgnoreCase("BROWN")) {
                curr.addProperty(p);
                game.getBank().setPropertyOwner(p, curr);
                p.setIsOwned(true);
            }
        }

        //1 hotel on one property, 4 houses first -- then hotel
        for(int i=0; i<4; i++){
            game.getBank().build(curr.getProperties().get(0), BuildingType.HOUSE);
        }
        game.getBank().build(curr.getProperties().get(0), BuildingType.HOTEL);

        //4 houses on other property
        for(int i =0; i<4; i++){
            game.getBank().build(curr.getProperties().get(1), BuildingType.HOUSE);
        }

        var expected = curr.getFunds() - 120;
        ac1.doCardAction(game);
        var actual = curr.getFunds();
        assertEquals(expected, actual);
    }


}