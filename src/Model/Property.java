package Model;
import java.util.*;

/***
 * This class holds all the information regarding a property such as its color, price, rent amount,
 * rent amount with monopoly, cost of placing a house, cost of placing a hotel, cost of rent
 * with every specific  number of houses, cost of rent with a hotel, and the mortgage cost
 */
public abstract class Property {

    double myPrice;
    String propertyName;

    public Property(double price, String propName){
        myPrice=price;
        propertyName = propName;
    }


    /***
     * A getter method that returns the price of this property
     * @return the cost to purchase this property
     */
    public double getPrice(){
        return myPrice;
    }

    public String getPropertyName(){
        return propertyName;
    }

    /***
     * A method that utilizes the member variables to calculate how
     * much it costs when someone lands on this property
     * @return the total rent value to be paid
     */
    public abstract double calculateRent();


}
