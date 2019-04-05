package monopoly_team04.Model.Controller;

/**
 * Dice interface simply rolls the dice
 */
public interface Dice {
    /**
     * Returns random number from 1 to sides simulating the roll of a die
     * @param sides
     * @return
     */
    public int rollDice(int sides);
}
