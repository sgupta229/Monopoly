package Controller;

public class ClassicGame extends AbstractGame {

    private int rollsInJail;
    private int passGoAmount;

    public ClassicGame(String filename) {
        super(filename);
        ConfigReader configReader = new ConfigReader(filename);

        passGoAmount = (int) configReader.getRuleDouble("PassGo");
        rollsInJail = (int) configReader.getRuleDouble("RollsInJail");
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
            getCurrPlayer().incrementNumRollsinJail();
            if(getCurrPlayer().getNumRollsInJail() == rollsInJail) {
                this.movePlayer(oldIndex, newIndex);
                getCurrPlayer().resetNumRollsInJail();
            }
        }
        checkPassGo(oldIndex, newIndex);
        return roll;
    }

    public void checkPassGo(int oldIndex, int newIndex) {
        if(newIndex < oldIndex) {
//        if(0 <= newIndex && 0>= oldIndex) {
            getCurrPlayer().addFunds(passGoAmount);
        }
    }
}
