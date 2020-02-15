package main.java.uk.me.jenewland.natpropsalessys.model.property;

import java.io.Serializable;

public class PropertyFlat extends Property implements Serializable
{
    private int floorNo = 0;
    private int monthlyCharge = 0;

    public PropertyFlat()
    {
        super("", 0, 0, 0);
    }

    public PropertyFlat(String address, int noOfRooms, int sellingPrice, int soldPrice, int floorNo, int monthlyCharge)
    {
        super(address, noOfRooms, sellingPrice, soldPrice);
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
