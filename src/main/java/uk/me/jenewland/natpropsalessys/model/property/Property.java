package uk.me.jenewland.natpropsalessys.model.property;

import uk.me.jenewland.natpropsalessys.model.Branch;
import uk.me.jenewland.natpropsalessys.model.IModel;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Property implements Serializable, IModel
{
    protected Branch branch;
    protected String address;
    protected int noOfRooms;
    protected long sellingPrice;
    protected long soldPrice;
    protected TYPES type;


    public enum TYPES {
        HOUSE, FLAT, NULL
    }

    public Property()
    {
        this.branch = null;
        this.address = "";
        this.noOfRooms = 0;
        this.sellingPrice = 0;
        this.soldPrice = -1;
        this.type = TYPES.NULL;
    }

    public Property(Branch branch, String address, int noOfRooms, long sellingPrice, long soldPrice, TYPES type)
    {
        this.branch = branch;
        this.address = address;
        this.noOfRooms = noOfRooms;
        this.sellingPrice = sellingPrice;
        this.soldPrice = soldPrice;
        this.type = type;
    }

    public Branch getBranch()
    {
        return branch;
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

    public long getSellingPrice()
    {
        return sellingPrice;
    }

    public void setSellingPrice(long sellingPrice)
    {
        this.sellingPrice = sellingPrice;
    }

    public long getSoldPrice()
    {
        return soldPrice;
    }

    public void setSoldPrice(long soldPrice)
    {
        this.soldPrice = soldPrice;
    }

    public boolean isSold() { return this.soldPrice >= 0; }

    public TYPES getType() { return type; }

    public void setType(TYPES type) {
        this.type = type;
    }

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
