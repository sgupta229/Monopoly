Monopoly Team 04 Design Document
===

### High Level Design Goals

* One design goal that we had was to be as data driven as possible. We wanted to be able to change layout/rules/colors/etc. just by changing the XML file.
* When implementing a new game with a variety of new rules, we wanted to be able to extend our base game class and only have to implement/override a couple of methods, which is what is done in Junior monopoly implementation.
    * New game configurations with similar rules as monopoly’s classic game can be constructed solely through the creation of new XMLconfig files.
    * To go along with this, we wanted all classes to be extremely general so they are open for extension when creating different game types. This means abstracting classes properly and having several API’s that are easy to understand. This is especially true for Spaces, Properties, and ActionCards
* We also made it a priority to make sure that our code “Smells” and that we were utilizing the 4 core principles of OO coding.
* Separate Model, View, and Controller packages - use no JavaFX in model back end.

### How to Add New Features
Front End:
* Add a key value pair to the properties file GameTypes.properties in the resource folder. The key should be the name of the game type as you want it to appear on the button. The value should be the name of the XML file to be read for that game type.
* Create a subclass of PlayerControl and add whatever additional buttons are necessary for the new version of the game. The abstract PlayerControl includes the player’s icon, name, and balance, the End Turn button, a ListView of the player’s properties, and a ListView of their holdable cards (like the get out of jail free card). For example, the ClassicPlayerControl adds buttons for managing and trading properties.
    * No new buttons needed to be added for Tropical Monopoly
* Create a subclass of BuyPropertyPopup and PayRentPopup if the new game has a property card that has a different layout that is different from the classic game.  This way the image that is shown in the popup reflects the game’s property type.  This step is not needed if the property details align with a classic property (such as in Tropical Monopoly).
* Create an indexToCoordinate.properties file for the game that maps the index specified in the XML file to the gridPane coordinate found on the front end.  
* Create a display.properties file that includes anything specific to the board display such as a base color for the properties, the board logo, the board dimension/height, the action card images, and any space-specific images needed for jail, tax, free parking, go to jail, etc.
* Create a popUpText.properties file that includes any text found on any popup in the game.  For most games, this text will not differ from classic, but include the new file in case there are differences. 

Back End:
* We then might need to write a new subclass that extends our AbstractGame class and create a game class for the specific game we are trying to implement (needed for junior monopoly, not needed for tropical).
* If there are special rules applicable to this game, we would override or implement new methods in this class and call them as necessary. This would include the method that checks whether the game is over and also the method that checks who is the winner (depending on how the game specifies).
* Extend AbstractPlayer and override any methods which need to be changed.
* Implement any new types of action cards if they are not already implemented. This would mean adding a new class with a doCardAction method that handles that type of action. For example, if there’s a new type of action that is not a basic one (move certain number of spaces, pay certain player money, go to certain space) such as switch spots with another player, a new class would need to be made that implements a doAction method that handles that action.
* Similarly, if someone wanted to add new Spaces, they would add a new class that extends AbstractSpace and implements its methods. So if they wanted a space that automatically removes the player from the game when landing on it, add that class, and implement it so that the doAction method performs the necessary action, which would be removing the player from the list of players and handling what to do with all their properties, etc. It is a similar process for adding new properties. One would have to extend the Property class, and implement its methods, most importantly calculateRent, which determines how that property will charge someone if they land on it.  No new spaces were needed for tropical monopoly.
* If there are new building types, add the buildings to the enum BuildingType.
* If there are new action deck types, simply add this to the DeckType enum.  The action deck types in the data files must be the same as those in the enums. (Error handled via XmlReaderException). 
* If a new action card is created that performs a different action than one performed by one of the 8 existing action card concrete subclasses (for example, a holdable action card that hypothetically allows a player to jump to another player’s location on the board at any given time), then one would simply have to write a new class (called JumpToPlayerAC) that implements this new doCardAction method. Then, the game that uses this new action card would have to specify these cards in the xml file too.  

Data:
* When creating a new game, we need to create a new XML file. This XML should include the properties, action card decks, tokens, rules, types of spaces, etc.
* Every tag group in the xml files now should be in the new file, even if some tag groups are empty (Ex: junior config has an empty BuildingPrices tag since building is not allowed, but it still must be present in the file) 
* Data file can specify board size, space names, colors, prices, etc. 

### Design Choices and Trade-Offs
* AbstractGame class vs just having a ClassicGame and overriding methods for new games:
    * Pros
        * If a game is drastically different from classic game, then we don’t have to override all the methods and add a bunch of new ones since AbstractGame only contains methods, abstract methods, and instance variables that all games should have.
        * If a game doesn’t have some of the functionality as ClassicGame, it would inherit all of its methods/instance variables. This wouldn’t really be appropriate then because it was inherit unnecessary variables/methods.
    * Cons
        * There are no obvious cons
* Checking if someone can build in the bank class
    * There are a series of things that would prevent someone from being able to build on their property. These include: if that person doesn’t have a monopoly, if they haven’t built evenly on their other properties of the same color, if they haven’t built enough of the previous structure yet (trying to build a hotel but haven’t built any houses yet). We must check for all of those things before someone builds, which leads to a lot of if statements in the helper method: checkIfCanBuild. The long chain of if statements is a con that we wanted to avoid, but couldn’t because we needed to check all of the different requirements.
* Assets in Player Class:
    * Pros 
        * Players are aware of all properties they own and any holadble action cards they may own
        * Easily accessible to player, thus allows for trading between players and mortgaging/selling to the bank to be easier
    * Cons
        * The bank already knows what properties are owned, it would be easy code to also make it the responsibility of the bank to know what property is owned by what player
* Houses in Property Class:
    * Pros
        * Properties are responsible for knowing any and everything that could affect rent
        * Easy to implement code that checks number of houses for a given property and increases rent accordingly because all in same class
    * Cons
        * Houses should be a part of assets in the player class, because they can be individually sold to the bank or a player 
* CheckMonopoly(Property) in Player Class:
    * Pros
        * Player contains the properties a player owns thus it is simpler to create this method inside that class because the information to answer is located there
    * Cons
        * A property could be responsible for knowing how many properties are in its color group thus when it is owned with the rest of the properties that complete it this method could be set to true if it were a part of the property class
* Deprecated Token Class
    * Pros
        * Simplifies code, because now the Player class can be responsible for one less action
    * Cons
        * Adds a class that could be two methods in the play 
        * Applies to the player class, because a token is a representation of a player, therefore it should be controlled by the player
* While there is a Property class that is abstract, there is also a ColorProperty class that is abstract and extends Property
    * This is because all properties (RailRoad, Utility, a ColorProperty in the classic game) must have similar features such as knowing their name, the type of space they are, and calculatingRent (albeit in different ways.) ColorProperties, however, have all these features but also additional features such as specifying how to add/remove buildings in different types of games, the accessing the different rent numbers (for instance in the classic game, there are several different rent numbers, where as in Junior there are only 2), and more. Due to these differences, we deemed it best to make ColorProperty abstract and have each different game extend this in order to let different games implement their specific color property rules. Doing so also lets us implement specific methods for only ColorProperties 
    * The tradeoff to this is that in order to still be modular, when we are accessing things generally and only calling them Properties, all properties need to have the same method names (if we call Property.addBuilding() but there is no .addBuilding() in Property, there will be an error). To account for this, we added all of the ColorProperty methods to the abstract Property class but gave them empty implementations. In specific scenarios where we know we are only looking at ColorProperties, we wouldn’t have to do this, however, which is why we still needed the extra abstract class. This isn’t the best design, but it helped different games implement different types of color properties.
* Action Card Parameters - all take in extra strings and extra doubles lists
    * Originally, all action cards took in a DeckType, a String message, and a boolean holdable, while some specific subclasses required more parameters.  For example, WinMoneyAC required a extra double (the amount of money to win) and an extra string (who to win that money from), while GetOutOfJailAC did not require any additional information.  Therefore, the constructors all took in differing parameters and didn’t allow us to use reflection in ConfigReader.java upon initializing these action cards.
    * Pros of changing all action cards to take extra strings and extra doubles:
        * Reflection was now used in ConfigReader.java and therefore we did away with a lot of duplicated code and if/else if statement blocks. 
        * Data file tags for all action cards are conformed.  Same tags for all action cards despite card types.
    * Cons of changing all action cards to take extra strings and extra doubles:
        * Some parameters are useless in some action cards.  For example, the GoToJailAC’s extra doubles parameter is empty.
        * This useless code is not the best practice, but it is better than not being able to use reflections.  It is also better to have uniform tags for every action card in the xml file (even if some of those tags are empty) than to not.  
* parseXmlFile method in Abstract Game Called separately from constructor
    * Originally - abstractGame constructor called the parseXmlFile method itself in order to fill all of its variables.  This had to be changed once we added reflection into the Controller.java class in order to initialize abstract games.  Through the use of the reflection, no XmlReaderException was being thrown or caught, and therefore all of the error catching was not being used.  By calling game.parseXmlFile(filename) separately from constructor, we were able to catch all of the XmlReaderExceptions being thrown in the backend again in order to avoid crashes.
    * Pros:
        * Catches XmlReaderException that are thrown by ConfigReader.java. Protects against bad data and bad inputs without crashing the program.
        * Uses reflection as well, thereby doing away with if/else if blocks and allowing other variations of games to be implemented rather easily (by creating new data files and if needed, creating a new game concrete class - junior, not needed for tropical). 
    * Cons:
        * Realistically, a game should not be initialized without parsing its XML therefore logically it makes more sense to call parseXmlFile(fileName) in the game’s constructor.  We now have to add a line of code wherever we initialize a new AbstractGame that calls that newly initialized game.parseXmlFile(filename) otherwise the game itself will have no objects (bank, properties, spaces, etc.) that it gets from the xml file.
        * Requires 2 lines of code to initialize any game: new ClassicGame… and game.parseXmlFile().
* Using a GridPane as the layout for the board on the front end
        * Pros: This design choice made it easy to create boards of varying sizes because all that was needed was a property file that mapped the index to the appropriate coordinate in the grid pane.  By using a grid pane, we assumed that the game types that we were creating would be square. 
        * Cons: This restricts the shape of the board to a square which means if someone was looking to implement a circular board, then a grid pane would not be able to be used and a new type of board class would need to be created.  Luckily, our layout class takes in a board object and player control object so a new board class could be inserted easily into the layout, it would just be a matter of creating a different type of pane for a different shaped board.  
* Creating our own popups
    * Pros:  By creating an abstract popup class and a variety of subclasses, we were able to achieve a level of customization that an Alert simply would not have provided us.  For example, the BuyPropertyPopup and PayRentPopup both have an image of a property card that shows up in the new window alongside the popup message.  This allows the user to see all of the details associated with that property when deciding whether they want to purchase it or not.  It is also helpful with paying rent, because the user can check and see how the rent is calculated according to the rent listed on the card. 
    * Cons: This was a lot of code that could have been reduced if we had decided to use Alerts rather than create our own popups.  From a creative standpoint, our popups are more aesthetically pleasing as much as they are helpful and add a visual aspect that may have been lacking otherwise. Alerts would have been more flexible because a message and alert type are the only things needed whereas our popups create an image as well.
* Saving and Loading - we used JSON objects to store player data and property information
    * This was one of the last features we implemented, and it was done with a time crunch. We should have prioritized this feature in one of our earlier sprints. We originally tried implemented serializable for all of our important classes and then serializing the data. This did not work as expected since serializing frontend objects does not work. We then decided to just write important information to a text file using JSON objects.
    * Pros
        * this method is easy to read, parse, and store data.
        * The file that is stored is a simple text file that takes up very little space. This is important since the player should not have to store very large files to store games.
    * Cons
        * not as flexible as we would’ve liked. If there’s a new game type with different types of properties and things we’ll have to change what we are storing.
        * In retrospect, we should have used Xstream. It seems a lot more flexible and easy to use.

### Assumptions and Simplifications
* All win and lose money action cards are either won from the bank or from all players (no individual players).
* Action card win and lose money values are all fixed amounts, not percentages.
* Action card “move to nearest utility” pays rent based on last roll, not making player roll again (as is done in the real game).
* Discard action cards to separate discard pile instead of to bottom of the deck.
* Users will not simply close out of pop up boxes, they will select one of the provided buttons (buttons trigger other actions therefore closing out will not trigger the action)
* Get out of jail action card - automatically saved when user receives it from an action space (because he/she is not in jail)
* Build method in railroad and utility properties are empty (design decision)
* Users must choose an icon in order to add a new player. However, they are able to a new player with no name, as long as they do choose an icon.
* You must add at least two players to start the game.
* Some parameters in action cards are not used (Extra Strings and Extra Doubles) -- allowed us to give all subclasses the same parameters and therefore we could use reflections and no if/else blocks
* Players can only trade properties for other properties, not money
* Player who passed on buying a property can participate in the auction of that property
* Insufficient funds - automatically triggers the selling of one’s buildings and houses until he/she has enough funds to pay rent or fee
    * The bank will sell houses and hotels first, and then it will mortgage properties until the player has enough money. If all houses and hotels 
* Not separating money into denominations ($1, $5, $20, etc.) - credit card style gameplay
