1) Game Class
    * Holds chance/community chest decks
    * Holds properties available
    * Holds Bank stuff
        * Get bank method 
    * All players involved
        * Get Players Method
        * Current turn player (who is up)
        
2) Abstract Space Class (Command Design Pattern) --- Concrete class for each type of space
    * Knows when player lands on it and --> initializes its own doAction() method
    * Property space
    * Chance space
        * Draw from chance deck (held in Game) and give card to player
    * Community Chest Space
        * Draw from community chest deck (held in Game) and give to player
    * Jail Space
    * Go Space
    * Tax Space


2) public abstract class Deck (In play vs. Discard could be compositions within other class
    * Method reshuffle (takes discared, shuffels into inPlayDeck)
    * public class CommunityChestDeck
        * Class inPlayDeck -- drawCard()
        * Class discardDeck -- returnCard()
    * Public class ChanceDeck (Queue of Chance Cards)
        * Class inPlayDeck -- drawCard()
        * Class discardDeck -- returnCard()

3) Public abstract ActionCard
    * public abstract doAction(Game game)
    * public class CommunityChestCard (concrete sub)
    * Public class ChanceCard (concrete sub)
    
4) Player class
    * actionCardHand
    * propertyCardHand
    * PlayerName
    * PlayerToken
