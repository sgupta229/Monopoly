Sprint 2 Presentation
===

### Program Demo
* ~~Rule changes?~~ 
* Add players - with name showing
* Test dice roll input
* Display funds
* Property Space
    * Buy (deduct money, show in hand) or Pass
    * Pay Rent (deduct money and pay to player - if building can work)
* Tax Space
    * Choose fixed or %
* Action Space
    * Displays card and does action on click
* Go To Jail
* ~~Pass Go -- collect 200???~~
* ~~Forfeit?????~~
* Building on monopolized properties
* ~~Roll/pay to get out of jail??~~

### Data Files
* Map of name to color???
* Same XML file as before (added tags for property group)
    * New Tags Property Group
    * New Rules (EvenBuildings, FreeParking, JailRolls)
* Property files to write to for saved/loaded games

### Work Related to Demo
* Front end -- all the popups we decided to show after landing on each space before doing each action
* ActionCard methods are set up and working (holdable or not)
* Building and buying properties 

### As Flexible/Open as Thought
* Not as flexible with respect to...
    * Housing types - been struggling to figure out best way to make this flexible if other game types dont use hotels and houses
        * Led to the use of enums for buildings
        * In data file - list which building types are for this game - more closed 
    * Right now game is abstract so adding new game would need to add a whole new game class
        * Could change because through discussion we found most game methods are going to be common amongst all games
    * Colors mapped to property names from data file to fill front end
        * Not very flexible because need new map with new property names -- thinking about how to just get the color straight from the file  
* Is as flexible as expected with respect to...
    * Added more rules to data file in the "rules" tag
        * Starting funds; jail bail; pass go winnings; even building?; Free parking?
        * All rules can be edited by the user on the start screen (turn on/off, change amount of money)
    * Basically tried to map out all of the information needed in the data file and read it all into game
    * Thinking about loading and saving games -- will write info on players and their current states to more data files (property files)
    * Action cards/decks are flexible
        * New deck for new game, simply add a new enum to DeckTypes.java (then the decks are initialized from data file)
        * New action cards
            * Most likely fall under one of the five existing categories
            * If there is a new one simply create one new subclass
       

### 2 APIs 
1. API from first presentation - Transfer.java
    1. implemented by any class that makes transfers (players, banks) 
    2. Allows transactions to be made without having to specify bank or player in method parameter
    3. Extension -- if another type of instance can make transactions (CPU player, old west version = barterer)
2. New API - AbstractActionCard.java
    1. One main method other than getters and setters - doCardAction()
    2. Executes the action of each card when called (linked to front end popup display)
    3. 5 concrete cards so far (open for extension) - MoveTo, GoToJail, GetOutJail, WinMoney, LoseMoney
    4. One of the concrete classes underneath it has changed in taking in a list of doubles instead of just a double because some payments take multiple things (deprecated that).
    5. added some getters and setters 
        1. getIsHoldable; getMyDeck; getMyDeckType; setMyDeck -- more closed     
    7. Supports teammates - getters for all methods and works with front end to simply call one method every time a card is drawn or an action card space is landed on 

### Use Cases Use Above APIs
1. Player lands on chance and draws chance card that reads "Property tax! Pay $40 for every house that you own and $115 for every Hotel that you own."
```java
    //Player starts by rolling dice 
    int diceRoll = game.rollDice();
    AbstractPlayer currPlayer = game.getCurrentPlayer();
    int prevLocation = currPlayer.getCurrentLocation();
    int newLocation = game.getNewIndex(prevLocation, diceRoll);
    
    //Moves player on the board; sets occupants of spaces; invokes space's "doAction()" 
    game.movePlayer(prevLocation, newLocation);
    
    //Back end of ActionSpace
    ActionCardSpace.doAction(game){
        AbstractActionCard cardDrawn;
        List<ActionDeck> tempDecks = game.getMyActionDecks();
        for(ActionDeck d : tempDecks) {
            //MyDeckType set in the constructor from data file (based on chance or commChest space)
            if (d.getMyDeckType() == myDeckType) {
                cardDrawn = d.drawCard();
                //cardDrawn.doCardAction(game);
                game.setCurrentActionCard(cardDrawn);
            }
        }
        game.endTurn();
    };
    
    //Card is sent to game
    AbstractActionCard cd = game.currentActionCard
    //Card message is taken by front end to display to user --> user clicks "OK" button
    cd.doCardAction(game){
        if(amountLose.size() == 1){
            singlePay(game);
        }
        else{
            multiPay(game);
        }
        ActionDeck d = this.getMyDeck();
        d.discardCard(this);
    }
    //Since card's amount variable will be [40, 115] as discussed the change before -- leads to
    cd.multiPay(game){
        AbstractPlayer currP = game.getCurrPlayer();
        double payment = 0;
        //getNumBuildings returns a map of BuildingType to Number -- used to find total payment
        //Building type enums are associated with values (0 for cheaper; 1 for second level - align with indices) 
        Map<BuildingType, Integer> buildingsMap = currP.getNumBuildings();
        //Flexible for game types 
        for(BuildingType bt : buildingsMap.keySet()){
            int numB = buildingsMap.get(bt);
            //Building level = 0 for house; 1 for hotel
            //Price per house at index 0; pp hotel at index 1
            payment += numB * amountLose.get(bt.getBuildingLevel());
        }
        currP.makePayment(payment, game.getBank());
    }
    //Then returns card the deck and completes the action -- ends the turn
```
The above use case spans so many different classes and APIs all working together (including the transfer api)

2. Player rolls and lands on an owned property, must pay the rent to that property's owner.
```java
    //Player starts by rolling dice 
    int diceRoll = game.rollDice();
    AbstractPlayer currPlayer = game.getCurrentPlayer();
    int prevLocation = currPlayer.getCurrentLocation();
    int newLocation = game.getNewIndex(prevLocation, diceRoll);
    
    //Moves player on the board; sets occupants of spaces; invokes space's "doAction()" 
    game.movePlayer(prevLocation, newLocation);
    
    //Back end of ActionSpace
    ActionCardSpace.doAction(game){
        AbstractPlayer propOwner = game.getBank().propertyOwnedBy(myProperty);
        Bank bank = game.getBank();
        AbstractPlayer currPlayer = game.getCurrPlayer();
        double propertyPrice = myProperty.getPrice();
        int lastDiceRoll = game.getLastDiceRoll();
        if (propOwner == null) {
            if (userChoice == 0) { //aka buy property
                bank.setPropertyOwner(myProperty, currPlayer);
                currPlayer.makePayment(propertyPrice, bank);
                currPlayer.addProperty(myProperty); //have to update the players assets
                //front end updates this somewhere?
            } else {
                //start auction process.
                //game.startAuction();
            }
        } else { //property is owned by someone, what are the choices?
            //pay rent or...
            //Uses Transfer interface with is implemented by player (currPlayer)
            //Property API calculates own rent -- more overlap 
            currPlayer.makePayment(myProperty.calculateRent(propOwner, lastDiceRoll), propOwner);
        }
    }
```

### Design Change
* Space's doAction methods are less active now than they originally were
* Realized that we want the design to be more user driven where methods are called when a user perform's an action or clicks a button 
* Previously - ActionCardSpace drew the card and invoked card's action --> now it just draws the card and sends it to front end to display
* Action not invoked until user reads the message and clicks "ok"
* Same with taxes -- user selects which tax method to pay and then action is invoked  
