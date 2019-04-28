package Model.properties;

import Model.AbstractPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***
 * Author: Dylan Karul
 * Purpose: This abstract class is used for all color properties in any monopoly game
 * Assumptions: This is under the assumption that it will be extended by a more concrete subclass
 * Dependencies: It is dependent on AbstractPlayer,
 * the BuildingType enum class, ColorProperty, and Property.
 * How to use it: You would use this class by extending it and creating a more specific concrete subclass
 * such as JuniorColorProperty, which implements the specific methods the way that the game specifies,
 * such as how to calculate rent when someone lands on it and how building works in the game.
 */

public abstract class ColorProperty extends Property {

    //private int numHouse;
    private List<Double> rentNumbers;



    public ColorProperty(double price, String propName, String color, List<Double> paymentInfo, int groupSize, Map<BuildingType, Double> buildingPriceMap){
        super(price, propName, color, paymentInfo, groupSize, buildingPriceMap);
        setGroup(color);
    }

    protected void initializePaymentInfo(List<Double> paymentInformation) throws IndexOutOfBoundsException{
        List<Double> paymentInformationCopy = List.copyOf(paymentInformation);

            //pricePerHouse = paymentInformation.get(6);
            //pricePerHotel = paymentInformation.get(7);
            setMortgageAmount(paymentInformationCopy.get(paymentInformationCopy.size()-1));
            rentNumbers = new ArrayList<>(paymentInformationCopy.subList(0, paymentInformationCopy.size()-1));

    }

    /***
     * Method that returns a list of metadata about the space
     * such as its color, price, mortgage, and rent info
     * @return see above
     */
    public abstract List getInfo();

    public List<Double> getRentNumbers(){
        return List.copyOf(rentNumbers);
    }



    /***
     * A method that utilizes the member variables to calculate how
     * much it costs when someone lands on this property
     * @return the total rent value to be paid
     */
    @Override
    public abstract double calculateRent(AbstractPlayer propOwner, int lastDiceRoll);

    /***
     * method for building structures on a color property
     * @param building the type of structure to build
     */
    public abstract void addBuilding(BuildingType building);

    /***
     * method for removing structures on a color property
     * @param building the type of structure to build,
     * @param amount the amount you are removing
     */
    public abstract void removeBuilding(BuildingType building, int amount);

    public abstract int getNumBuilding(BuildingType building);
}
