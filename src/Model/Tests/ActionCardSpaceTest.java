package Model.Tests;

import Controller.ConfigReader;
import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controller.AbstractGame;
import Controller.ClassicGame;

import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class ActionCardSpaceTest {
    ConfigReader conf = new ConfigReader("Normal_Config.xml");
    List<AbstractActionCard> actionCardList;
    ActionDeck chanceDeck = new ActionDeck(DeckType.CHANCE);
    ActionDeck communityChestDeck = new ActionDeck(DeckType.COMMUNITY_CHEST);
    List<AbstractSpace> spaceList;
    ClassicGame gameClass;
    ClassicPlayer player1;

    @BeforeEach
    void setUp() throws XmlTagException {
        gameClass = new ClassicGame("Normal_Config.xml");
        actionCardList = conf.parseActionCards();
        spaceList = conf.parseSpaces().get(0);
        chanceDeck.fillLiveDeck(actionCardList);
        communityChestDeck.fillLiveDeck(actionCardList);
        player1 = new ClassicPlayer();
    }



    @Test
    void doActionChanceSpace() {
        gameClass.getMyActionDecks();
        AbstractActionCard topCard = chanceDeck.getMyLiveCards().peek();
        AbstractSpace chanceSpace = spaceList.get(7);
        System.out.println(chanceDeck.getMyLiveCards().size());
        chanceSpace.doAction(gameClass);
        System.out.println(chanceDeck.getMyLiveCards().size());
        var deadCardList =  chanceDeck.getMyDeadCards();
        var mostRecentlyDeadCard = deadCardList.get(deadCardList.size()-1);
        assertEquals(topCard, mostRecentlyDeadCard);
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
