package Model.properties;

import Model.properties.Property;

public class ColorProperty extends Property {

    double numHouse;
    double numHotel;
    String myColor;


    public ColorProperty(double price, String propName, String color){
        super(price, propName,color);
        myColor=color;
        myGroup=color;
    }
    /***
     * A getter method that returns the cost to place a house on this property
     * @return the cost to place a house on this property
     */
    public double getCostofHouse(){
        return 0.0;
    }

    /***
     * A getter method that returns the cost to place a hotel on this property
     * @return the cost to place a hotel on this property
     */
    public double getCostofHotel(){
        return 0.0;
    }

    /***
     * Allows for users to see the color of a property which is crucial for monopolies
     * @return the color of this property
     */
    public String getPropertyColor(){
        return myColor;
    }

    /***
     * A method that utilizes the member variables to calculate how
     * much it costs when someone lands on this property
     * @return the total rent value to be paid
     */
    public double calculateRent(){
        return 0.0;
    }

    public void addHouse(){
        numHouse++;
    }
    public void addHotel(){
        numHotel++;
    }

    public void removeHouse(){
        numHouse--;
    }
    public void removeHotel(){
        numHotel--;
    }
}
