package Model.properties;

import Model.AbstractPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public abstract void addBuilding(BuildingType building);

    public abstract void removeBuilding(BuildingType building, int amount);

    public abstract int getNumBuilding(BuildingType building);
}
