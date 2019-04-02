package Model;

/***
 * This class holds all the information regarding a property such as its color, price, rent amount,
 * rent amount with monopoly, cost of placing a house, cost of placing a hotel, cost of rent
 * with every specific  number of houses, cost of rent with a hotel, and the mortgage cost
 */
public interface Property {
    /***
     * A getter method that returns the cost to place a house on this property
     * @return the cost to place a house on this property
     */
    public float getCostofHouse();

    /***
     * A getter method that returns the cost to place a hotel on this property
     * @return the cost to place a hotel on this property
     */
    public float getCostofHotel();

    /***
     * Allows for users to see the color of a property which is crucial for monopolies
     * @return the color of this property
     */
    public Color getPropertyColor();

    /***
     * A getter method that returns the price of this property
     * @return the cost to purchase this property
     */
    public float getPrice();

    /***
     * A method that utilizes the member variables to calculate how
     * much it costs when someone lands on this property
     * @param numHouses the number of houses on the property, numHotels the number of
     *                  hotels on the property, mortgageExists whether there is a mortgage,
     *                  monopolyAchieved whether the player has monopoly
     * @return the total rent value to be paid
     */
    public float calculateRent(int numHouses, int numHotels, boolean mortgageExists, boolean monopolyAcheived);


}
