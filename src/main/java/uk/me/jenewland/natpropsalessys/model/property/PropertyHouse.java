package uk.me.jenewland.natpropsalessys.model.property;

import uk.me.jenewland.natpropsalessys.model.Branch;

import java.io.Serializable;

public class PropertyHouse extends Property implements Serializable
{
    private int noOfFloors = 0;
    private boolean hasGarage = false;
    private boolean hasGarden = false;

    public PropertyHouse()
    {
        super();
    }

    public PropertyHouse(Branch branch, String address, int noOfRooms, int sellingPrice, int soldPrice, int noOfFloors, boolean hasGarage, boolean hasGarden, TYPES type)
    {
        super(branch, address, noOfRooms, sellingPrice, soldPrice, type);
        this.noOfFloors = noOfFloors;
        this.hasGarage = hasGarage;
        this.hasGarden = hasGarden;
    }

    public int getNoOfFloors()
    {
        return noOfFloors;
    }

    public void setNoOfFloors(int noOfFloors)
    {
        this.noOfFloors = noOfFloors;
    }

    public boolean isHasGarage()
    {
        return hasGarage;
    }

    public void setHasGarage(boolean hasGarage)
    {
        this.hasGarage = hasGarage;
    }

    public boolean isHasGarden()
    {
        return hasGarden;
    }

    public void setHasGarden(boolean hasGarden)
    {
        this.hasGarden = hasGarden;
    }
}
