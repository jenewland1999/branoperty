package uk.me.jenewland.natpropsalessys.model.property;

import uk.me.jenewland.natpropsalessys.model.Branch;

import java.io.Serializable;

/**
 * Serializable model class to represent estate agency houses (properties).
 */
public class PropertyHouse extends Property implements Serializable {
    private int noOfFloors = 0;
    private boolean hasGarage = false;
    private boolean hasGarden = false;

    /**
     * Default empty constructor.
     */
    public PropertyHouse() {
        super();
    }

    /**
     * PropertyHouse constructor.
     *
     * @param branch       the associated branch.
     * @param address      the property's address.
     * @param noOfRooms    the number of rooms the property has.
     * @param sellingPrice the price the house is being marketed at.
     * @param soldPrice    the price the house sold for (-1 means the property hasn't sold).
     * @param type         the type of property it is.
     * @param noOfFloors   the number of floors the property has.
     * @param hasGarage    whether or not the property has a garage.
     * @param hasGarden    whether or not the property has a garden.w
     */
    public PropertyHouse(Branch branch, String address, int noOfRooms, int sellingPrice, int soldPrice, TYPES type, int noOfFloors, boolean hasGarage, boolean hasGarden) {
        super(branch, address, noOfRooms, sellingPrice, soldPrice, type);
        this.noOfFloors = noOfFloors;
        this.hasGarage = hasGarage;
        this.hasGarden = hasGarden;
    }

    /**
     * Getter for noOfFloors field.
     *
     * @return the noOfFloors field.
     */
    public int getNoOfFloors() {
        return noOfFloors;
    }

    /**
     * Setter for noOfFloors field.
     *
     * @param noOfFloors the new noOfFloors to set the field to.
     */
    public void setNoOfFloors(int noOfFloors) {
        this.noOfFloors = noOfFloors;
    }

    /**
     * Getter for hasGarage field.
     *
     * @return the hasGarage field.
     */
    public boolean hasGarage() {
        return hasGarage;
    }

    /**
     * Setter for hasGarage field.
     *
     * @param hasGarage the new hasGarage to set the field to.
     */
    public void setHasGarage(boolean hasGarage) {
        this.hasGarage = hasGarage;
    }

    /**
     * Getter for hasGarden field.
     *
     * @return the hasGarden field.
     */
    public boolean hasGarden() {
        return hasGarden;
    }

    /**
     * Setter for hasGarden field.
     *
     * @param hasGarden the new hasGarden to set the field to.
     */
    public void setHasGarden(boolean hasGarden) {
        this.hasGarden = hasGarden;
    }
}
