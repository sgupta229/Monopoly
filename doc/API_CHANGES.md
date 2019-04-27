__Changes Made__

* Dylan changed the bank to take in
a list of doubles rather than one double.
This is because in our first sprint, we didn't
include buying properties and building on them,
but we are now implementing these abilities in 
Sprint 2. This means the bank needs more information, such
as how many houses there are, etc.

* Ryan changed the "amount" parameter that LoseMoneyAC takes in from a double to a List<double>.  This is because some of the action cards require players to pay $$ for each house they own and $$$ for each hotel that they own, therefore providing two different values.  Now, these action cards will take in a list of either 1 or 2 values and be able to accurately deduct the correct amount of money from the player that draws the card.

* Dylan changed the Property (color, utility, and railroad) constructor so that 
it would also take in the size of its group and a map that contains
information about buildings and their prices. Before doing this, we still
hadn't implemented building yet, so the constructor was more primitive.  