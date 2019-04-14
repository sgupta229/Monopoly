package Controller;

import Model.AbstractSpace;

public class ClassicGame extends AbstractGame {

    private int numHouses;
    private int numHotels;
    private int maxHouses;
    private int passGoAmount;

    public ClassicGame(String filename) {
        super(filename);
        ConfigReader configReader = new ConfigReader(filename);

        numHouses = (int) configReader.getRuleDouble("Houses");
        numHotels = (int) configReader.getRuleDouble("Hotels");
        maxHouses = (int) configReader.getRuleDouble("MaxHouses");
        passGoAmount = (int) configReader.getRuleDouble("PassGo");
    }

    @Override
    public int rollDice() {
        int oldIndex = getCurrPlayer().getCurrentLocation();
        AbstractSpace oldSpace = getBoard().getSpaceAt(oldIndex);
        oldSpace.removeOccupant(getCurrPlayer());
        int roll = rollDice();

        if(!getCurrPlayer().isInJail()) {
            getCurrPlayer().move(roll, getBoardSize());
        }
        else {
            incrementNumRollsinJail();
            if(getNumRollsInJail() == 3) {
                getCurrPlayer().move(roll, getBoardSize());
                resetNumRollsInJail();
            }
        }
        int newIndex = getCurrPlayer().getCurrentLocation();
        checkPassGo(oldIndex, newIndex);
        AbstractSpace newSpace = getBoard().getSpaceAt(newIndex);
        newSpace.addOccupant(getCurrPlayer());

        newSpace.doAction(this);

        return roll;
    }

    public void checkPassGo(int oldIndex, int newIndex) {
        if(0 <= newIndex && 0>= oldIndex) {
            getCurrPlayer().addFunds(passGoAmount);
        }
    }

}
