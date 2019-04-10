package Controller;

import Model.AbstractSpace;

public class ClassicGame extends AbstractGame {

    public ClassicGame(String filename) {
        super(filename);
    }

    @Override
    public int rollDice() {
        int oldIndex = getCurrPlayer().getCurrentLocation();
        AbstractSpace oldSpace = getBoard().getSpaceAt(oldIndex);
        oldSpace.removeOccupant(getCurrPlayer());
        int roll = super.rollDice();

        if(!getCurrPlayer().isInJail()) {
            getCurrPlayer().move(roll, getBoardSize());
        }

        int newIndex = getCurrPlayer().getCurrentLocation();
        AbstractSpace newSpace = getBoard().getSpaceAt(newIndex);
        newSpace.addOccupant(getCurrPlayer());

        newSpace.doAction(this);

        return roll;
    }

    public static void main(String[] args) {
        ClassicGame c = new ClassicGame("Normal_Config.xml");
        System.out.println(c.getPossibleTokens());
    }

}
