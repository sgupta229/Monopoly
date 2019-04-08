package Model;

public class ClassicProperty implements Property {

    double price;
    String name;
    String color;


    public ClassicProperty(String myName, double myPrice, String myColor){
        name = myName;
        price = myPrice;
        color = myColor;
    }

    /***
     * A getter method that returns the cost to place a house on this property
     * @return the cost to place a house on this property
     */
    public double getCostofHouse(){
        return (double) 0.0;
    }

    /***
     * A getter method that returns the cost to place a hotel on this property
     * @return the cost to place a hotel on this property
     */
    public double getCostofHotel(){
        return (double)0.0;
    }

    /***
     * Allows for users to see the color of a property which is crucial for monopolies
     * @return the color of this property
     */
    public String getPropertyColor(){
        return null;
    }

    /***
     * A getter method that returns the price of this property
     * @return the cost to purchase this property
     */
    public double getPrice(){
        return price;
    }

    /***
     * A method that utilizes the member variables to calculate how
     * much it costs when someone lands on this property
     * @return the total rent value to be paid
     */
    public double calculateRent(){
        return (double) 0.0;
    }
}
