package Model;
import java.util.*;

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
    public double getCostofHouse();

    /***
     * A getter method that returns the cost to place a hotel on this property
     * @return the cost to place a hotel on this property
     */
    public double getCostofHotel();

    /***
     * Allows for users to see the color of a property which is crucial for monopolies
     * @return the color of this property
     */
    public String getPropertyColor();

    /***
     * A getter method that returns the price of this property
     * @return the cost to purchase this property
     */
    public double getPrice();

    /***
     * A method that utilizes the member variables to calculate how
     * much it costs when someone lands on this property
     * @return the total rent value to be paid
     */
    public double calculateRent();


}
