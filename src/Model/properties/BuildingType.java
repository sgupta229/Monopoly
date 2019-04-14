package Model.properties;

public enum BuildingType {
    HOUSE (0),
    HOTEL (1);

    private final int buildingLevel;
    BuildingType(int buildingLevel){
        this.buildingLevel = buildingLevel;
    }

    public int getBuildingLevel(){return this.buildingLevel;}
}
