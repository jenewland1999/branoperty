package branoperty.model;

import branoperty.model.property.Property;
import branoperty.model.user.UserSecretary;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Serializable model class to represent estate agency branches.
 */
public class Branch implements Serializable, IModel {
    private String name = "";
    private String address = "";
    private String website = "";
    private String email = "";
    private String tel = "";
    private UserSecretary branchSecretary = new UserSecretary();
    // Use Set as apposed to List because properties should be unique!
    private Set<Property> properties = new HashSet<>();

    /**
     * Default empty constructor.
     */
    public Branch() {
    }

    /**
     * Branch constructor.
     *  @param name     the name of the branch (doubles as username for secretary).
     * @param password the branch secretary's password.
     * @param address  the address of the branch.
     * @param website  the web address associated with the branch.
     * @param email    the email address associated with the branch.
     * @param tel      the telephone number associated with the branch.
     */
    public Branch(String name, String password, String address, String website, String email, String tel) {
        this.name = name;
        this.address = address;
        this.website = website;
        this.email = email;
        this.tel = tel;
        this.branchSecretary.setUsername(name);
        this.branchSecretary.setPassword(password);
        this.properties = new HashSet<>(properties);
    }

    /**
     * Getter for name field.
     *
     * @return the name field.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name field.
     *
     * @param name the new name to set the field to.
     */
    public void setName(String name) {
        this.name = name;
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
     * Getter for email field.
     *
     * @return the email field.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for email field.
     *
     * @param email the new email to set the field to.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for website field.
     *
     * @return the website field.
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Setter for website field.
     *
     * @param website the new website to set the field to.
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * Getter for tel (telephone no.) field.
     *
     * @return the tel (telephone no.) field.
     */
    public String getTel() {
        return tel;
    }

    /**
     * Setter for tel (telephone no.) field.
     *
     * @param tel the new tel (telephone no.) to set the field to.
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * Getter for branch secretary field.
     *
     * @return the branch secretary field.
     */
    public UserSecretary getBranchSecretary() {
        return branchSecretary;
    }

    /**
     * Setter for branch secretary field.
     *
     * @param branchSecretary the new branch secretary to set the field to.
     */
    public void setBranchSecretary(UserSecretary branchSecretary) {
        this.branchSecretary = branchSecretary;
    }

    /**
     * Getter for properties field.
     *
     * @return the properties field.
     */
    public Set<Property> getProperties() {
        return properties;
    }

    /**
     * Setter for properties field.
     *
     * @param properties the new properties to set the field to.
     */
    public void setProperties(Set<Property> properties) {
        this.properties = new HashSet<>(properties);
    }

    /**
     * Print the address and associated branch of each property stored for the
     * branch.
     */
    public void printProperties() {
        properties.forEach(property -> {
            System.out.printf("Associated Branch: %s%nAddress: %s%n", getName(), property.getAddress());
        });
    }

    /**
     * Add a single property to {@code properties} field.
     *
     * @param property the property to add to the set of properties.
     */
    public void addProperty(Property property) {
        properties.add(property);
    }

    /**
     * Find and remove the given property from the {@code properties} field.
     *
     * @param property the property to remove from the set of properties.
     */
    public void deleteProperty(Property property) {
        properties.remove(property);
    }

    /**
     * Find and remove the {@code oldProperty} from the properties field then
     * add the {@code newProperty} to the properties field.
     *
     * @param oldProperty the old state of the property to be removed.
     * @param newProperty the new state of the property to be added.
     */
    public void updateProperty(Property oldProperty, Property newProperty) {
        properties.remove(oldProperty);
        properties.add(newProperty);
    }

    /**
     * Overridden {@code toString()} method which returns the branch's name
     * field.
     *
     * @return the branch's name field.
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     * Override the {@code Object.equals()} method to allow for accurate object
     * comparison.
     *
     * @param object the object to compare it to.
     * @return true/false depending on if the two objects are equal.
     */
    @Override
    public boolean equals(Object object) {
        // First, check if the object is itself.
        if (this == object) return true;

        // Next, check if the object is null.
        if (object == null) return false;

        // Then, check if the object is of the same type.
        if (!(object instanceof Branch)) return false;

        // The objects are of the same type so cast the provided object for
        // deeper comparison (field comparisons).
        Branch branch = (Branch) object;

        // Finally, return the result of objects' field comparisons.
        // NB: The comparison disregards the branch field as it's not required.
        return Objects.equals(name, branch.name)
                && Objects.equals(address, branch.address)
                && Objects.equals(website, branch.website)
                && Objects.equals(email, branch.email)
                && Objects.equals(tel, branch.tel);
    }

    /**
     * Override the {@code Object.hashCode()} method which is used for object
     * comparisons. It does this by hashing the object's fields.
     *
     * @return an integer-based hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, address, website, email, tel);
    }
}
