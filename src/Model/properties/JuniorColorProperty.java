package Model.properties;

import Model.AbstractPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/***
 * Author: Dylan Karul
 * Purpose: This class is used for all color properties in the junior game
 * Assumptions: This is under the assumption that it gets its data in the right form
 * but we make this assumption because we check for this in config reader when reading in
 * the data, so it always should be getting proper data.
 * Dependencies: It is dependent on AbstractPlayer,
 * the BuildingType enum class, ColorProperty, and Property.
 * How to use it: You would use this class by calling a new JuniorColorProperty to represent a
 * color property in the junior rules game. Then, to get the rent info and other metadata,
 * call getInfo. To see how much someone pays when landing on the space, call calculateRent.
 */
public class JuniorColorProperty extends ColorProperty {

    //private int numHouse;
    //private List<Double> rentNumbers;
    private ArrayList ret;


    public JuniorColorProperty(double price, String propName, String color, List<Double> paymentInfo, int groupSize, Map<BuildingType, Double> buildingPriceMap){
        super(price, propName, color, paymentInfo, groupSize, buildingPriceMap);
        setGroup(color);
        getInfo();
    }


    public List getInfo(){
        ret = new ArrayList();
        ret.addAll(Arrays.asList(getColor(), this.getPrice()));
//        ret.add(getRentNumbers().get(0));
        ret.add(this.getMortgageAmount());
        ret.add(this.getName());
        return ret;
    }


    /***
     * A method that utilizes the member variables to calculate how
     * much it costs when someone lands on this property
     * @return the total rent value to be paid
     */
    @Override
    public double calculateRent(AbstractPlayer propOwner, int lastDiceRoll){
        double rentNums=0.0;
        rentNums = (double)ret.get(1);
        if(propOwner.checkMonopoly(this)){
            rentNums = rentNums*2;
        }
        return rentNums;

    }

    /***
     * Junior properties cannot build
     * @param building the type of structure to build
     * @throws UnsupportedOperationException
     */
    public void addBuilding(BuildingType building) throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Junior Doesn't Build");
    }

    /***
     * Junior properties cannot build
     * @param building the type of structure to build
     * @throws UnsupportedOperationException
     */
    public void removeBuilding(BuildingType building, int amount) throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Junior Doesn't Build");

    }

    /***
     * Junior properties cannot build
     * @param building the type of structure to build
     * @throws UnsupportedOperationException
     */
    public int getNumBuilding(BuildingType building)throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Junior Doesn't Build");
        //return 0;
    }
}
