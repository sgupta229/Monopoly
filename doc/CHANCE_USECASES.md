#####A player lands on Chance, draws "Bank pays you a dividend of $50", your funds are updated appropriately, and the card is returned to the bottom of the deck.
1. The Game rolls the dice - Dice.roll()
2. Game moves the Player?s token. - Player.move()
3. Game checks space based on Player?s location - Board.getSpaceType()
4. Game calls space action on Chance space - Space.doAction()
5. Chance space draws card from ChanceDeck - ChanceDeck.drawCard()
6. When card is drawn, Card executes the action - Card.doAction(Game game) 
7. Game.withdrawFromBank(50)
8. Game.deposit(currentPlayer, 50)
9. Card ChanceDeck.discard()
#####A player lands on Chance, draws "Advance to Go, collect $200", your token is moved and your funds are updated appropriately, and the card is returned to the bottom of the deck.
1. Game rolls the dice - dice.roll()
2. Game moves the player - player.move()
3. Game checks the space the player lands on - Board.getSpaceType()
4. Game calls space action - Space.doAction()
5. Space draws the card from the chance deck - ChanceDeck.draw(Card)
6. Card executes the action when drawn - Card.doAction(Game game)
7. Card knows when it is drawn
8. Card moves the player to Go and pays player (Player.move(); Game.deposit(200). 
9. ChanceDeck.discard(card)
#####A player lands on Chance, draws "Get Out of Jail Free", and it is saved with your inventory, and the card is returned to the bottom of the deck.
This is similar to the above question except that the action of the card will store the card in the player?s cards (that is what its action will be)
#####A player lands on Chance, draws "Go to Jail, Go directly to Jail", your token is moved (passing Go, but not collecting any money) and placed in jail, and the card is returned to the bottom of the deck.
#####A new rule is added that lets a player draw a Chance card when landing on Free Parking space.
#####A new theme for the game is designed, creating cards in a different language.
