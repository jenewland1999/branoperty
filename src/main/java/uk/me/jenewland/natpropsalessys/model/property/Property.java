package uk.me.jenewland.natpropsalessys.model.property;

import uk.me.jenewland.natpropsalessys.model.Branch;
import uk.me.jenewland.natpropsalessys.model.IModel;

import java.io.Serializable;
import java.util.Objects;

/**
 * Serializable model class to represent estate agency properties.
 */
public class Property implements Serializable, IModel {
    // enum for the different property types
    public enum TYPES {
        HOUSE, FLAT, NULL
    }

    protected Branch branch; // the associated branch
    protected String address;
    protected int noOfRooms;
    protected long sellingPrice;
    protected long soldPrice;
    protected TYPES type;

    /**
     * Default empty constructor.
     */
    public Property() {
        this.branch = null;
        this.address = "";
        this.noOfRooms = 0;
        this.sellingPrice = 0;
        this.soldPrice = -1;
        this.type = TYPES.NULL;
    }

    /**
     * Property constructor.
     *
     * @param branch       the associated branch.
     * @param address      the property's address.
     * @param noOfRooms    the number of rooms the property has.
     * @param sellingPrice the price the house is being marketed at.
     * @param soldPrice    the price the house sold for (-1 means the property hasn't sold).
     * @param type         the type of property it is.
     */
    public Property(Branch branch, String address, int noOfRooms, long sellingPrice, long soldPrice, TYPES type) {
        this.branch = branch;
        this.address = address;
        this.noOfRooms = noOfRooms;
        this.sellingPrice = sellingPrice;
        this.soldPrice = soldPrice;
        this.type = type;
    }

    /**
     * Getter for branch field.
     *
     * @return the branch field.
     */
    public Branch getBranch() {
        return branch;
    }

    /**
     * Getter for address field.
     *
     * @return the address field.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter for address field.
     *
     * @param address the new address to set the field to.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter for noOfRooms field.
     *
     * @return the noOfRooms field.
     */
    public int getNoOfRooms() {
        return noOfRooms;
    }

    /**
     * Setter for noOfRooms field.
     *
     * @param noOfRooms the new noOfRooms to set the field to.
     */
    public void setNoOfRooms(int noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    /**
     * Getter for sellingPrice field.
     *
     * @return the sellingPrice field.
     */
    public long getSellingPrice() {
        return sellingPrice;
    }

    /**
     * Setter for sellingPrice field.
     *
     * @param sellingPrice the new sellingPrice to set the field to.
     */
    public void setSellingPrice(long sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    /**
     * Getter for soldPrice field.
     *
     * @return the soldPrice field.
     */
    public long getSoldPrice() {
        return soldPrice;
    }

    /**
     * Setter for soldPrice field.
     *
     * @param soldPrice the new soldPrice to set the field to.
     */
    public void setSoldPrice(long soldPrice) {
        this.soldPrice = soldPrice;
    }

    /**
     * Getter for type field.
     *
     * @return the type field.
     */
    public TYPES getType() {
        return type;
    }

    /**
     * Setter for type field.
     *
     * @param type the new type to set the field to.
     */
    public void setType(TYPES type) {
        this.type = type;
    }

    /**
     * Returns true or false depending on if the property is sold. This is
     * determined by the {@code soldPrice} field being greater than or equal to
     * 0.
     *
     * @return true/false depending on if the property is sold.
     */
    public boolean isSold() {
        return this.soldPrice >= 0;
    }

    /**
     * Override the {@code Object.equals()} method to allow for accurate object
     * comparison.
     *
     * @param object the object to compare it to.
     * @return true/false depending on if the two objects are equal.
     */
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

    /**
     * Override the {@code Object.hashCode()} method which is used for object
     * comparisons. It does this by hashing the object's fields.
     *
     * @return an integer-based hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(address, noOfRooms, sellingPrice, soldPrice);
    }
}
