package uk.me.jenewland.natpropsalessys.model.property;

import java.io.Serializable;

public class Property implements Serializable
{
    protected String address;
    protected int noOfRooms;
    protected int sellingPrice;
    protected int soldPrice;

    public Property()
    {
        this.address = "";
        this.noOfRooms = 0;
        this.sellingPrice = 0;
        this.soldPrice = 0;
    }

    public Property(String address, int noOfRooms, int sellingPrice, int soldPrice)
    {
        this.address = address;
        this.noOfRooms = noOfRooms;
        this.sellingPrice = sellingPrice;
        this.soldPrice = soldPrice;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public int getNoOfRooms()
    {
        return noOfRooms;
    }

    public void setNoOfRooms(int noOfRooms)
    {
        this.noOfRooms = noOfRooms;
    }

    public int getSellingPrice()
    {
        return sellingPrice;
    }

    public void setSellingPrice(int sellingPrice)
    {
        this.sellingPrice = sellingPrice;
    }

    public int getSoldPrice()
    {
        return soldPrice;
    }

    public void setSoldPrice(int soldPrice)
    {
        this.soldPrice = soldPrice;
    }
}
