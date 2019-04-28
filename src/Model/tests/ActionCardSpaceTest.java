package Model.Tests;

import Controller.ConfigReader;
import Model.*;
import Model.actioncards.DeckType;
import Model.spaces.AbstractSpace;
import Model.actioncards.AbstractActionCard;
import Model.actioncards.ActionDeck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controller.ClassicGame;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ActionCardSpaceTest {
    ConfigReader conf;

    {
        try {
            conf = new ConfigReader("Normal_Config_Rework.xml");
        } catch (XmlReaderException e) {
            e.printStackTrace();
        }
    }

    List<AbstractActionCard> actionCardList;
    ActionDeck chanceDeck = new ActionDeck(DeckType.CHANCE);
    ActionDeck communityChestDeck = new ActionDeck(DeckType.COMMUNITY_CHEST);
    List<AbstractSpace> spaceList;
    ClassicGame gameClass;
    ClassicPlayer player1;

    @BeforeEach
    void setUp() throws XmlReaderException {
        gameClass = new ClassicGame("Normal_Config_Rework.xml");
        gameClass.parseXMLFile("Normal_Config_Rework.xml");
        actionCardList = conf.parseActionCards();
        spaceList = gameClass.getSpaces();
        chanceDeck.fillLiveDeck(actionCardList);
        communityChestDeck.fillLiveDeck(actionCardList);
        player1 = new ClassicPlayer();
    }



    @Test
    void doActionChanceSpace() {
        List<ActionDeck> tempDecks = gameClass.getMyActionDecks();
        for(ActionDeck d : tempDecks) {
            if (d.getMyDeckType() == DeckType.valueOf("CHANCE")) {
                chanceDeck = d;
            }
        }
        AbstractActionCard topCard = chanceDeck.getMyLiveCards().peek();
        AbstractSpace chanceSpace = spaceList.get(7);
        chanceSpace.doAction(gameClass, 0);
        //var deadCardList =  chanceDeck.getMyDeadCards();
        //var mostRecentlyDeadCard = deadCardList.get(deadCardList.size()-1);
        var mostRecentCard = gameClass.getCurrentActionCard();
        assertEquals(topCard, mostRecentCard);
    }

    @Test
    void testLocation(){
        AbstractSpace spaceAt10 = spaceList.get(10);
        var correctIndex = 10;
        var testIndex = spaceAt10.getMyLocation();
        assertEquals(correctIndex, testIndex);
    }

    @Test
    void testGetMyName(){
        AbstractSpace spaceAt7 = spaceList.get(7);
        var correctName = "CHANCE";
        var testName = spaceAt7.getMyName();
        assertEquals(correctName, testName);
    }

    @Test
    void testGetOccupantsEmpty(){
        AbstractSpace spaceAt7 = spaceList.get(7);
        var correctNumOfOccupants = 0;
        var occList = spaceAt7.getOccupants();
        assertEquals(correctNumOfOccupants, occList.size());
    }

    @Test
    void testGetOccupantsOneOccupant(){
        AbstractSpace spaceAt7 = spaceList.get(7);
        var correctNumOfOccupants = 1;
        spaceAt7.addOccupant(player1);
        var occList = spaceAt7.getOccupants();
        assertEquals(correctNumOfOccupants, occList.size());
        assertEquals(player1, occList.get(0));
    }

    @Test
    void testAddOccupants(){
        AbstractSpace space = spaceList.get(5);
        var startingNumOfOccupants = 0;
        var occList = space.getOccupants();
        assertEquals(startingNumOfOccupants, occList.size());
        space.addOccupant(player1);
        var newNumOccupants = 1;
        assertEquals(newNumOccupants, occList.size());
    }

    @Test
    void testRemoveOccupantsEmpty(){
        AbstractSpace space = spaceList.get(5);
        var startingNumOfOccupants = 0;
        var occList = space.getOccupants();
        assertEquals(startingNumOfOccupants, occList.size());
        space.removeOccupant(player1);
        assertEquals(startingNumOfOccupants, occList.size());
    }

    @Test
    void testRemoveOccupantsOneOccupant(){
        AbstractSpace space = spaceList.get(5);
        space.addOccupant(player1);
        assertEquals(1, space.getOccupants().size());
        space.removeOccupant(player1);
        assertEquals(0, space.getOccupants().size());
    }
}
