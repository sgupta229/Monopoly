package Model.properties;

import Model.AbstractPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColorProperty extends Property implements Buildable {

    private int numHouse;
    private int numHotel;
    private String myColor;

    private List<Double> rentNumbers;
    private double pricePerHouse;
    private double pricePerHotel;
    private double mortgage;
    private final double INFO_NUM = 8;
    protected Map<BuildingType, Integer> buildingMap;
    protected Map<BuildingType, Double> buildingPrices;

    public ColorProperty(double price, String propName, String color, List<Double> paymentInfo, int groupSize, Map<BuildingType, Double> buildingPricesMap){
        super(price, propName, paymentInfo, groupSize);
        buildingPrices = buildingPricesMap;
        buildingMap = new HashMap<>();
        myColor=color;
        setMyColor(color);
        setGroup(color);

        for(BuildingType buildingType : buildingPrices.keySet()){
            buildingMap.put(buildingType, 0);
        }

    }

    protected void initializePaymentInfo(List<Double> paymentInformation) throws IndexOutOfBoundsException{
        if(paymentInformation.size()>=INFO_NUM)   {
            pricePerHouse = paymentInformation.get(6);
            pricePerHotel = paymentInformation.get(7);
            setMortgageAmount(paymentInformation.get(8));
            rentNumbers = paymentInformation.subList(0, 6);
            buildingPrices = new HashMap<>();
            /////buildingPrices.put(something);
        }
        else{
            throw new IndexOutOfBoundsException("Bad data") ;
        }
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
    @Override
    public double calculateRent(AbstractPlayer propOwner, int lastDiceRoll){
        if(this.getIsMortgaged()){
            return 0.0;
        }
        double rentTotal = 0.0;
        numHotel = getNumBuilding(BuildingType.valueOf("HOTEL"));
        if(numHotel>0){
            rentTotal+= numHotel*rentNumbers.get(5);
        }
        else{
            rentTotal+= rentNumbers.get(getNumBuilding(BuildingType.valueOf("HOUSE")));
        }
        return rentTotal;
    }

    public void addBuilding(BuildingType building){
        if(!buildingMap.containsKey(building)){
            buildingMap.put(building, 0);
        }
        buildingMap.put(building, buildingMap.get(building)+1);
        System.out.println(building );
        System.out.println(buildingMap.get(building));
    }

    public void removeBuilding(BuildingType building){
        if(buildingMap.get(building)>0){
            buildingMap.put(building, buildingMap.get(building)-1);
        }
    }

    public int getNumBuilding(BuildingType building){
        if(!buildingMap.containsKey(building)){
            buildingMap.put(building, 0);
        }
        return buildingMap.get(building);
    }

    public double getBuildingPrice(BuildingType building){
        return buildingPrices.get(building);
    }

}
