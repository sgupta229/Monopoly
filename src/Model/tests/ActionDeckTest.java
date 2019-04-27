package Model.Tests;

import Controller.ConfigReader;
import Model.XmlReaderException;
import Model.actioncards.AbstractActionCard;
import Model.actioncards.ActionDeck;
import Model.actioncards.DeckType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActionDeckTest {
    ConfigReader cr;

    {
        try {
            cr = new ConfigReader("Normal_Config_Rework.xml");
        } catch (XmlReaderException e) {
            e.printStackTrace();
        }
    }

    ActionDeck chanceDeck;
    ActionDeck communityChestDeck;
    List<AbstractActionCard> actionCardList;

    @BeforeEach
    void setUp() throws XmlReaderException {
        chanceDeck = new ActionDeck(DeckType.CHANCE);
        communityChestDeck = new ActionDeck(DeckType.COMMUNITY_CHEST);
        actionCardList = cr.parseActionCards();
    }

    @Test
    void fillLiveDeckEmptyToFull() {
        assertTrue(chanceDeck.getMyLiveCards().isEmpty());

        chanceDeck.fillLiveDeck(actionCardList);

        assertFalse(chanceDeck.getMyLiveCards().isEmpty());
    }

    @Test
    void fillLiveDeckCommunityChestCardTypes() {
        communityChestDeck.fillLiveDeck(actionCardList);
        for(AbstractActionCard ac : communityChestDeck.getMyLiveCards()){
            var expected = DeckType.COMMUNITY_CHEST;
            var actual = ac.getMyDeckType();
            assertEquals(expected,actual);
        }
    }

    @Test
    void fillLiveDeckChanceCardTypes(){
        chanceDeck.fillLiveDeck(actionCardList);
        for(AbstractActionCard ac : chanceDeck.getMyLiveCards()){
            var expected = DeckType.CHANCE;
            var actual = ac.getMyDeckType();
            assertEquals(expected,actual);
        }
    }

    @Test
    void drawCardTypeAndLiveSizeCheck(){
        chanceDeck.fillLiveDeck(actionCardList);
        int originalSize = chanceDeck.getMyLiveCards().size();

        chanceDeck.drawCard();
        int newSize1 = chanceDeck.getMyLiveCards().size();
        var actual1 = newSize1;
        var expected1 = originalSize - 1;
        assertEquals(expected1, actual1);

        chanceDeck.drawCard();
        int newSize2 = chanceDeck.getMyLiveCards().size();
        var actual2 = newSize2;
        var expected2 = originalSize - 2;

        assertEquals(expected2, actual2);
    }

    @Test
    void discardCardCheck() {
        communityChestDeck.fillLiveDeck(actionCardList);

        var expected1 = 0;
        var actual1 = communityChestDeck.getMyDeadCards().size();
        assertEquals(expected1, actual1);

        var ac1 = communityChestDeck.drawCard();
        var ac2 = communityChestDeck.drawCard();
        ac1.getMyDeck().discardCard(ac1);
        ac2.getMyDeck().discardCard(ac2);

        var expected2 = 2;
        var actual2 = communityChestDeck.getMyDeadCards().size();
        assertEquals(expected2, actual2);
    }

    @Test
    void shuffleDeckRefillsLiveCards() {
        chanceDeck.fillLiveDeck(actionCardList);
        int size = chanceDeck.getMyLiveCards().size();

        //Draw all but last card from myLiveCards
        for(int i=0; i<size-1; i++){
            AbstractActionCard ac = chanceDeck.drawCard();
            ac.getMyDeck().discardCard(ac);
        }

        //Should be 1 card left
        var expected1 = 1;
        var actual1 = chanceDeck.getMyLiveCards().size();
        assertEquals(expected1, actual1);

        //draw the last card from myLiveCards --> shuffle myDeadCards into myLiveCards (not including most recently drawn card)
        AbstractActionCard ac2 = chanceDeck.drawCard();
        var expected2 = size-1;
        var actual2 = chanceDeck.getMyLiveCards().size();
        assertEquals(expected2, actual2);
    }

    @Test
    void shuffleDeckChangesCardOrder(){
        chanceDeck.fillLiveDeck(actionCardList);
        int size = chanceDeck.getMyLiveCards().size();
        //Top card of live deck before shuffling
        AbstractActionCard card1 = chanceDeck.getMyLiveCards().peek();

        //draw all cards, discard them and reshuffle
        for(int i=0; i<size; i++){
            var ac = chanceDeck.drawCard();
            ac.getMyDeck().discardCard(ac);
        }

        //Top card live deck after shuffling (by chance it might be the same but usually not)
        AbstractActionCard card2 = chanceDeck.getMyLiveCards().peek();

        assertFalse(card1.equals(card2));
    }
}