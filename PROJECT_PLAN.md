Monopoly Team 4 Project Plan
===

### First Week Goals
Throughout this first week, we attempted to determine all of the different classes that we might need to implement in order to build our program.  We also discussed and mapped out the methods that will fall under each class and how the classes will  interact with one another.  We decided that our project will follow the MVC programming design pattern as well as the Command design pattern, where one class (Game.java) holds all of the necessary information for other classes to operate, such as players, the bank, the dice, etc..  In addition to determining our classes and methods, we came up with an initial plan as to how to organize these classes and responsibilities into their respective packages (monopoly_team04.Model, monopoly_team04.View, monopoly_team04.Model.Controller).  Our plan is as follows:

monopoly_team04.View:
* SceneSetUp.java
* View.Main.java → start
* SplashScreens/User greeting/player Setup
* Button handling 
* Error handling
* Layout.java
* PlayerActionPanel.java 
* BoardDisplay.java
* PropertyDisplay.java
* CSS Files
* Properties files for all GUI text

monopoly_team04.Model.Controller:
* DataReader.java
* SetUp.java (based on data reader, initializes game components -- passes to game)
* Game.java (holds stuff from setUP; communicates b/w front and back ends)
* Board.java (organizes spaces)
* Token.java (link between player back end and front end player display)
* Dice.java

monopoly_team04.Model (Game Logic):
* ActionCard.java
* Deck.java
* Bank.java
* Player.java
* Property.java
* Space.java
* Transfer.java

### Division of Responsibilities
After talking through all of  our classes and methods (which are described further in DESIGN_PLAN.md and in our interfaces), we broke down what each team will focus on completing throughout the remainder of this project.  After determining who wanted to work on front end and who wanted to work on back end, we tried to group various classes together based on how much those classes interact with one another.  For example, we decided that whoever is to work on the Player.java class should also work on the Token.java class because tokens are held by each player and represent a player on the game board.  Our responsibilities break down as follows:

Abby Zhang:
1. Primary: PlayerActionPanel.java, BoardDisplay.java, PropertyDisplay.java, CSS Files, Properties files for all GUI text
2. Secondary: Data/config files, Game

Caroline Breaux:
1. Primary: SceneSetUp.java, View.Main.java → start, SplashScreens/User greeting/player Setup, Button handling, Error handling, Layout.java
2. Secondary: data/config files, interaction between monopoly_team04.Model.Controller and monopoly_team04.View (Game.java)

Dez Martin: 
1. Primary: data reading/parsing; data/config file creation
2. Secondary: Data interaction with backend/Game class

Sahil Gupta:
1. Primary: Token.java, Player.java, Game.java, Dice.java
2. Secondary: Game.java interaction with and passing of information to front end 

Ryan Bloom:
1. Primary: Deck, ActionCard, Board
2. Secondary: Other backend classes interactions with primary classes above (Bank.java/ Property.java/ Space.java/ Transfer.java/ Game.java/ Dice.java/ Player.java/ Token.java)

Dylan Karul:
1. Primary: Bank, Property, Space, Transfers
2. Secondary: Other backend classes interactions with primary classes above (Deck.java/ ActionCard.java/ Board.java/ Game.java/ Dice.java/ Player.java/ Token.java)

### Sprint Goals

After we mapped out all of our responsibilities, we began to discuss potential goals to set for each sprint that we have coming up.  We attempted to create goals that would be ambitious enough to move our project forward at a consistent rate, while still being reasonable enough to complete in a week span.  After discussion, we decided on the following sprint goals:

Goals for Sprint 1 (Tuesday April 9)
* Read in data and initialize all game pieces
    * Players, tokens, spaces, board with spaces, bank, properties
* Complete front end board visualization with all spaces
* Player is  able to roll and move token -> landing on spaces, the following spaces’ doAction method work 
    * Go to Jail
    * Pay Income Tax
    * Chance/Community Chest (card is drawn from deck, card’s action will not yet be completed)
    * Property Space (player can buy, no auction yet)
    * Passing Go (player gets $200)
* Start game UI
    * Player chooses token/name
    * Player starts turn and rolls dice

Goals for Sprint 2 (Tuesday April 16)
* Bank auctions properties
* Players pay rent when landing on owned property
    * Player’s check for their monopoly (rent adjusted accordingly)
* Players improve properties (builds houses/hotels)
    * Rent adjusted accordingly
* ActionCard doAction() methods can be completed
* Game recognizes amount of doubles thrown 
    * If three, sends player directly to jail

Goals for Complete (Friday April 26)
* Implement different game rules (Laser battles; running out of gas)
* Allow players to trade assets with one another
* Complete game once bankrupt; one player reaches $1mill; other game ending rules…



