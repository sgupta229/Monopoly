package Model.properties;

public interface Buildable {

    void addBuilding(BuildingType b);

    void removeBuilding(BuildingType b);

    int getNumBuilding(BuildingType b);

    double getBuildingPrice(BuildingType b);
}
