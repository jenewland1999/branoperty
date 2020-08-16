package uk.me.jenewland.natpropsalessys.model.property;

import uk.me.jenewland.natpropsalessys.model.Branch;

import java.io.Serializable;

public class PropertyFlat extends Property implements Serializable
{
    private int floorNo = 0;
    private int monthlyCharge = 0;

    public PropertyFlat()
    {
        super();
    }

    public PropertyFlat(Branch branch, String address, int noOfRooms, int sellingPrice, int soldPrice, int floorNo, int monthlyCharge, TYPES type)
    {
        super(branch, address, noOfRooms, sellingPrice, soldPrice, type);
        this.floorNo = floorNo;
        this.monthlyCharge = monthlyCharge;
    }

    public int getFloorNo()
    {
        return floorNo;
    }

    public void setFloorNo(int floorNo)
    {
        this.floorNo = floorNo;
    }

    public int getMonthlyCharge()
    {
        return monthlyCharge;
    }

    public void setMonthlyCharge(int monthlyCharge)
    {
        this.monthlyCharge = monthlyCharge;
    }
}
