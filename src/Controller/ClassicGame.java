package Controller;

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
        int roll = super.rollDice();
        int newIndex = getNewIndex(oldIndex, roll);
        if(!getCurrPlayer().isInJail()) {
            this.movePlayer(oldIndex, newIndex);
        }
        else {
            incrementNumRollsinJail();
            if(getNumRollsInJail() == 3) {
                this.movePlayer(oldIndex, newIndex);
                resetNumRollsInJail();
            }
        }
        checkPassGo(oldIndex, newIndex);
        return roll;
    }

    public void checkPassGo(int oldIndex, int newIndex) {
        if(0 <= newIndex && 0>= oldIndex) {
            getCurrPlayer().addFunds(passGoAmount);
        }
    }
}
