package uk.me.jenewland.natpropsalessys.model.property;

import uk.me.jenewland.natpropsalessys.model.Branch;

import java.io.Serializable;

/**
 * Serializable model class to represent estate agency flats (properties).
 */
public class PropertyFlat extends Property implements Serializable {
    private int floorNo = 0;
    private int monthlyCharge = 0;

    /**
     * Default empty constructor.
     */
    public PropertyFlat() {
        super();
    }

    /**
     * PropertyFlat constructor.
     *
     * @param branch        the associated branch.
     * @param address       the property's address.
     * @param noOfRooms     the number of rooms the property has.
     * @param sellingPrice  the price the house is being marketed at.
     * @param soldPrice     the price the house sold for (-1 means the property hasn't sold).
     * @param type          the type of property it is.
     * @param floorNo       the floor in which the flat is situated on. E.g. 0 for ground floor.
     * @param monthlyCharge the monthly charge associated with the flat.
     */
    public PropertyFlat(Branch branch, String address, int noOfRooms, int sellingPrice, int soldPrice, TYPES type, int floorNo, int monthlyCharge) {
        super(branch, address, noOfRooms, sellingPrice, soldPrice, type);
        this.floorNo = floorNo;
        this.monthlyCharge = monthlyCharge;
    }

    /**
     * Getter for floorNo field.
     *
     * @return the floorNo field.
     */
    public int getFloorNo() {
        return floorNo;
    }

    /**
     * Setter for floorNo field.
     *
     * @param floorNo the new floorNo to set the field to.
     */
    public void setFloorNo(int floorNo) {
        this.floorNo = floorNo;
    }

    /**
     * Getter for monthlyCharge field.
     *
     * @return the monthlyCharge field.
     */
    public int getMonthlyCharge() {
        return monthlyCharge;
    }

    /**
     * Setter for monthlyCharge field.
     *
     * @param monthlyCharge the new monthlyCharge to set the field to.
     */
    public void setMonthlyCharge(int monthlyCharge) {
        this.monthlyCharge = monthlyCharge;
    }
}
