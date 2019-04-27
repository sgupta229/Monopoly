Monopoly Team 4
===

### Team Members

Ryan Bloom, Dylan Karul, Caroline Breaux, Abby Zhang, Dez Martin, Sahil Gupta

### Project Timeline
Start Date: Wednesday, March 27
Finish Date: Friday, April 26
Hours Spent: 200+

### Team Member Roles
Ryan Bloom:
Model - AbstractActionCard (and subclasses), ActionDeck, Board, tests for these classes
Controller - ConfigReader, ConfigReaderErrorHandling
Exception Handling - GameBoardException; XmlReaderException; exception throwing and catching in back end and front end
XML files - wrote House_Rules_Config.xml; Junior_Config.xml
Dylan Karul:
Model - Transfer interface, AbstractSpace (and all subclasses), AbstractPropSpace (and all subclasses), Property (and all subclasses), ColorProperty (and all subclasses), Bank, JuniorGame, JuniorPlayer, tests for all of these
Controller - ConfigReader
Caroline Breaux: 
View - PropertyDisplay (abstract) and all subclasses, Popup (abstract) and all subclasses apart from Auction and Trade, Dice (spinning image), Board, BoardConfigReader, ChooseGameScreen, EndGameScreen, Layout, classic vs junior player control as well as helped with other aspects in View.
Properties files - wrote IndexToCoordinate.properties, boardDisplay.properties
and PopUpText.properties for each type of game (these contain the board images and any colors that contribute to the board?s theme.
Connected front and back end in terms of popUps with the space?s associated doAction.  
Abby Zhang:
View - AddPlayersScreen, DiceRoller, PlayerControl, PlayerTabs, Editing rules, Trading pop up - view and integration, Auctioning pop up - view and integration
Controller - Integrating front end and back end
Sahil Gupta:
Model - worked with the player hierararchy and functionality. Made AbstractPlayer and ClassicPlayer.
Controller - developed AbstractGame class and set appropriate abstractions to easily extend other games (also made ClassicGame).
Made the dice class and the GameSaver class (which is responsible for saving and loading the game). 
Initially wrote methods in ConfigReader to parse data, which was later redone with better design by Dylan and Ryan.
View - added some basic tweaks/error checking and added a few alerts when certain actions occur in the backend and
the player should be notified.
Dezmanique Martin:
Data - Wrote Normal_Config.xml
Tests - GUI Testing
Controller - ConfigReader, connecting front end and back end, started SaveandLoadData and SaveData

### Resources Used
Parse Boolean: http://www.java67.com/2018/03/java-convert-string-to-boolean.html
Shuffle deck: https://www.geeksforgeeks.org/collections-shuffle-java-examples/
String to enum: https://www.baeldung.com/java-string-to-enum
Reading/using xml: https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
Enum ints: http://tutorials.jenkov.com/java/enums.html
Junior Monopoly Rules and Spaces: https://en.wikipedia.org/wiki/Monopoly_Junior
Using propertyChangeListeners: https://docs.oracle.com/javase/tutorial/uiswing/events/propertychangelistener.html
ListView with delete buttons: https://stackoverflow.com/questions/42529782/listview-with-delete-button-on-every-row-in-javafx

### Files Used to Start Project
Run Main.java

### Files Used to Test
Found in Controller.Tests
AbstractGameTest.java
BoardTest.java
Found in Model.tests
AbstractPlayerTest.java
ActionCardSpaceTest.java
ActionDeckTest.java
BankTest.java
ClassicColorPropertyTest.java
ClassicPropSpaceTest.java
GetOutJailACTest.java
GoToJailACTest.java
GoToSpaceTest.java
LoseMoneyACTest.java
MoveNumberACTest.java
MoveToACTest.java
MoveToNearestACTest.java
MoveToOpenACTest.java
RailRoadPropertyTest.java
TaxSpaceTest.java
UtilityPropertyTest.java
WinMoneyACTest.java
Found in View.Tests
AddPlayersScreenTest.java

### Errors We Handle
Action deck type inputs found in ?ActionDeck? tags in xml config files are valid instances of DeckType enums
Action deck type inputs found in each action card?s ?DeckType? tag in xml config files are valid instances of DeckType enums
Building type inputs found in both ?BuildingType? tags and ?BuildingPrice? tags in xml config files are valid instances of BuildingType enums.
Space group inputs found in each space?s ?SpaceGroup? tag in xml config files are valid instances of SpaceGroup enums.
ConfigReader.java?s final string variables are valid tags in the xml document.
Inputs in xml in xml config files that are supposed to be numbers (ints or doubles) are actually parsable numbers (not strings or characters).
Class names being reflected from xml in xml config files are real class names in the src folder of the project.
File names in the xml config files? ?OtherFiles? tag are valid files found in the data folder and are ?.properties? files.
File names in the xml config file?s ?Tokens? tag are valid files found in the data folder and are ?.png? files.
FileName taken into ConfigReader() as a parameter is an existing file and is a ?.xml? file.
Number of spaces listed in xml config file?s ?BoardSize? tag matches the number of space items in the config file overall.
Action card?s target spaces for MoveTo, MoveToNearest, MoveToOpen, and GoToJail (found in the ?ExtraString? tag in xml config files) match a space on the board.



### Data/Resource Files Required by Project
Normal_Config_Rework.xml
IndexToCoordinate.properties
classicBoardDisplay.properties
PopUpText.properties
Tropical_Config.xml
IndexToCoordinateHouseRules.properties
houseRulesDisplay.properties
HouseRulesPopUpText.properties
Junior_Config.xml
IndexToCoordinateJunior.properties
juniorDisplay.properties
juniorPopUpText.properties
Token Image Files:
Battleship.png; boot.png; cage.png; car_junior.png; cat_junior.png; dog_junior.png; hat_junior.png; iron.png; racecar.png; sheep_dog.png; thimble.png; top_hat.png; wheelbarrow.png
Game Board/Card Image Files:
chance.png; chest.png; classic.jpg; freeParking.png; giftBag.png; go.png; goToJail.png; jail.png; logo.png; openOcean.png; plane.png; railroad.png; sharkDiving.png; tax.png; taxCard.png; treasure.png; tropical.jpg; welcome.jpg; winner.jpg; 
Files front end uses regardless of game type:
GameTypes.properties
 Messages.properties
Rules.properties
GUI.css
number_spinner.css


### Information About Using the Program
To start the program, simply select one of the three game options that are displayed on the start screen, ?Classic,? ?Junior,? or ?Tropical.?  Or, if a game had previously been saved on the user?s computer, he/she can click the ?Load Saved Game? button to continue playing this saved game. Then, type in player names and select a tokens for players, and click the ?add? button in order to add this player to the game (rolling order will be the order in which players are added).  After adding players, you can change some house rules by clicking the ?Edit Rules? button on the same screen.  This will prompt a rules popup on which you can edit some rules (make sure to apply these changes before exiting from this screen).  Once all players and rules are finalized, click start game to begin.

Players click the ?Roll? button to roll the dice, and follow subsequent popup instructions, choices, and buttons that present themselves depending on the space on which the user lands.  After going through one?s roll and subsequent action as decided by the space that a user lands on, users can click the ?Manage Properties? button on the right screen in order to manage their properties (build houses, mortgage properties, etc.).  They can also propose trades to other players via the ?Trade? button.  Once the player is done managing properties and proposing trades, he/she clicks the ?End Turn? button in order to progress the game to the next player?s turn.  This player then rolls and continues the process.  At any point during the game, the current player can click the ?Forfeit? button, which would return all of his/her properties to the bank and remove the player from the game.  If there is only one player remaining (all others forfeited), then that player wins! 

### Assumptions and Simplifications
All win and lose money action cards are either won from the bank or from all players (no individual players).
Action card win and lose money values are all fixed amounts, not percentages.
Action card ?move to nearest utility? pays rent based on last roll, not making player roll again (as is done in the real game).
Discard action cards to separate discard pile instead of to bottom of the deck.
Users will not simply close out of pop up boxes, they will select one of the provided buttons (buttons trigger other actions therefore closing out will not trigger the action)
Get out of jail action card - automatically saved when user receives it from an action space (because he/she is not in jail)
Build method in railroad and utility properties are empty (design decision)
Some parameters in action cards are not used (Extra Strings and Extra Doubles) -- allowed us to give all subclasses the same parameters and therefore we could use reflections and no if/else blocks
Player who passed on buying a property can participate in the auction of that property
Insufficient funds - automatically triggers the selling of one?s buildings and houses until he/she has enough funds to pay rent or fee



### Known bugs, problems, crashes
When other players forfeit and one player remains, if that last player forfeits, an IndexOutOfBoundsException is thrown.
When back funds are updated on the add player screen, it updates on the backend but not on the rules tab on the front end.

### Extra Features?
Rules that one can edit - getting money from free parking; if there is a reward for landing exactly on Go, whether players have to build evenly on their properties, start funds, banks starting funds, amount for passing go, reward for rolling snake eyes, amount to pay bail, and how many rolls it takes to get out of jail
Loading and saving games????????????????
Trading: players can trade their properties
Tropicalopoly- a fun spin on Monopoly, same rules but different images, spaces, properties, types of action cards, etc
Junior Monopoly- a different type of Monopoly. Players cannot build or mortgage properties, they must buy a property that they land on, there is only 1 dice, and once 1 player goes bankrupt, the game ends and the player with the most money wins (player with the most properties if there is a tie) 

### Impression of the Assignment
Overall, this assignment was one of, if not the largest computer science project that anyone in our team had taken on.  It was overwhelming at first sight, but overall I think that it really helped teach us more about working with a team and how best to attack projects of this size.  Having a week before starting actual work to just plan our attack was extremely helpful, and we probably should?ve planned it out even more.  The size of this project really showed us how important it is to communicate our progress with our teammates as well as demonstrated the usefulness of having many many classes working together.  Furthermore, I think that the use of ?issues? on github was a helpful skill to learn and helped to organize all of our tasks as well.  

We agree that allowing us to set our own sprint goals was a new and interesting project structure and gave us the freedom and control that we had never had before.  We understand that this was done in order to simulate the real world, but we also think that, had we been given a few guidelines and deadlines (they didn?t have to be strict or very specific) by the project and the professors, we would have been able to accomplish more with respect to adding more functionality and completing our program earlier in a more relaxed manner, therefore providing us more time at the end to refactor and improve our design.    

