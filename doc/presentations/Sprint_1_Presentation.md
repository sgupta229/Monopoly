Sprint 1 Presentation
===

### Data File
* Game Type → “Normal”
* Dice info → Number & Sides
* Board size → 40
* Spaces → Type; Index; Name
* Property Info → Name; Color Group; Costs; Rents
* Action Decks → Chance vs. Community Chest
* Action Cards → Type; Action; Deck; Info for action
* Bank starting funds → 1000000
* Token image paths → In data
* General Rules → Player starting funds, jail bail, pass go

### Game Demo Steps
1. Add players
2. Start game
3. Roll dice
4. Move token/player
5. Envoke space’s doAction method
6. At end of space’s doAction method → end turn and roll again for next player

### Our Work and the Demo
* Dez - data
* Ryan - controller/model (board; action decks; cards)
* Dylan - model (spaces)
* Sahil - model/controller (game/player)
* Abby - view 
* Caroline - view

### Goals Completed Sprint 1
* Complete board visible with all spaces
* Some spaces work “Go to Jail”; “Tax” 
    * Tokens with ability to move
* Start game UI
    * Player choses token
    * Player rolls dice
* Player gets 200 for passing go (back end)

### Goals Incomplete for Sprint 1
* Property card displays/Action card displays
    * Display created, handler not ready
* Spaces doActions() not fully working
    * Property spaces
    * Taxes work but no display
* Player assets not displayed but are tracked

### Big Event - creating data
* Big long meeting we all had to be involved in
* Data impacted everyone’s parts
* Parsing too

### What Worked and What Didn't
* What worked
    * Meeting frequently
    * Dividing up work
    * Having a good understanding of each others’ methods and classes
* What didn’t work
    * Integrating our backend and front end too late
    * Lots of time spent on data formatting
* Improve:
    * Display of dice and player tokens (overlay)
    
### Sprint 2 Goals
* Have a fully functional working classic monopoly game with most frontend features and all backend features.
    * Display assets (money and properties)
    * Action cards work
    * Front end displays pop ups for every action/space
* Start implementing basic functionality of other games (data setup etc)







