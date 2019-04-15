package Model.properties;

import Model.AbstractPlayer;

import java.util.List;

public class ColorProperty extends Property {

    private int numHouse;
    private int numHotel;
    private String myColor;
    private String myGroup;
    private double rent;
    private double rentOneHouse;
    private double rentTwoHouse;
    private double rentThreeHouse;
    private double rentFourHouse;
    private double rentHotel;
    private List<Double> rentNumbers;
    private double pricePerHouse;
    private double pricePerHotel;
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
            pricePerHotel = paymentInformation.get(7);
            mortgage = paymentInformation.get(8);
            rentNumbers = paymentInformation.subList(0, 5);
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
        return pricePerHotel;
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
    public double calculateRent(AbstractPlayer propOwner, int lastDiceRoll){
        if(this.getIsMortgaged()){
            return 0.0;
        }
        double rentTotal = 0.0;
        if(numHotel>0){
            rentTotal+= numHotel*pricePerHotel;
        }
        else{
            rentTotal+= rentNumbers.get(numHouse);
        }
        return rentTotal;
    }

    public void build(){

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
    public int getNumHouse(){
        return numHouse;
    }
    public int getNumHotel(){
        return numHotel;
    }


}
