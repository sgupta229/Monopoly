package Model.properties;

import java.util.List;

public class ColorProperty extends Property {

    private double numHouse;
    private double numHotel;
    private String myColor;
    private String myGroup;
    private double rent;
    private double rentOneHouse;
    private double rentTwoHouse;
    private double rentThreeHouse;
    private double rentFourHouse;
    private double rentHotel;
    private double pricePerHouse;
    private double mortgage;
    private final double INFO_NUM = 8;


    public ColorProperty(double price, String propName, String color, List<Double> paymentInfo){
        super(price, propName,color, paymentInfo);
        myColor=color;
        myGroup=color;
    }

    protected void initializePaymentInfo(List<Double> paymentInformation) throws IndexOutOfBoundsException{
        if(paymentInformation.size()>=INFO_NUM)   {
            rent = paymentInformation.get(0);
            rentOneHouse = paymentInformation.get(1);
            rentTwoHouse = paymentInformation.get(2);
            rentThreeHouse = paymentInformation.get(3);
            rentFourHouse = paymentInformation.get(4);
            rentHotel = paymentInformation.get(5);
            pricePerHouse = paymentInformation.get(6);
            mortgage = paymentInformation.get(7);
        }
        else{
            throw new IndexOutOfBoundsException("Bad data") ;
        }


    }

    /***
     * A getter method that returns the cost to place a house on this property
     * @return the cost to place a house on this property
     */
    public double getCostofHouse(){
        return pricePerHouse;
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
        if(this.getIsMortgaged()){
            return 0.0;
        }

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
    public double getNumHouse(){
        return numHouse;
    }
    public double getNumHotel(){
        return numHotel;
    }


}
