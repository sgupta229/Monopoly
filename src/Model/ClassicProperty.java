package Model;

public class ClassicProperty implements Property {

    float price;

    public ClassicProperty(float myPrice){
        price = myPrice;
    }

    /***
     * A getter method that returns the cost to place a house on this property
     * @return the cost to place a house on this property
     */
    public float getCostofHouse(){
        return (float)0.0;
    }

    /***
     * A getter method that returns the cost to place a hotel on this property
     * @return the cost to place a hotel on this property
     */
    public float getCostofHotel(){
        return (float)0.0;
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
    public float getPrice(){
        return price;
    }

    /***
     * A method that utilizes the member variables to calculate how
     * much it costs when someone lands on this property
     * @return the total rent value to be paid
     */
    public float calculateRent(){
        return (float)0.0;
    }
}
