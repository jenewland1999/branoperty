package uk.me.jenewland.natpropsalessys.model.property;

import uk.me.jenewland.natpropsalessys.model.IModel;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Property implements Serializable, IModel
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
        this.soldPrice = -1;
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

    public boolean isSold() { return this.soldPrice >= 0; }

    @Override
    public boolean equals(Object o) {
        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (!(o instanceof Property))
            return false;
        Property p = (Property) o;
        // field comparison
        return Objects.equals(address, p.address)
                && Objects.equals(noOfRooms, p.noOfRooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, noOfRooms, sellingPrice, soldPrice);
    }
}
