package uk.me.jenewland.natpropsalessys.model;

import uk.me.jenewland.natpropsalessys.model.property.Property;
import uk.me.jenewland.natpropsalessys.model.user.UserSecretary;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Branch implements Serializable, IModel
{
    private String name = "";
    private String address = "";
    private String email = "";
    private String website = "";
    private long tel = 0;
    private UserSecretary branchSecretary = new UserSecretary();
    private Set<Property> properties = new HashSet<>();

    public Branch()
    {

    }

    public Branch(String name, String password, String address, String email, String website, long tel)
    {
        this.name = name;
        this.address = address;
        this.email = email;
        this.website = website;
        this.tel = tel;
        this.branchSecretary.setUsername(name);
        this.branchSecretary.setPassword(password);
        this.properties = new HashSet<>(properties);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getWebsite()
    {
        return website;
    }

    public void setWebsite(String website)
    {
        this.website = website;
    }

    public long getTel()
    {
        return tel;
    }

    public void setTel(long tel)
    {
        this.tel = tel;
    }

    public UserSecretary getBranchSecretary()
    {
        return branchSecretary;
    }

    public void setBranchSecretary(UserSecretary branchSecretary)
    {
        this.branchSecretary = branchSecretary;
    }

    public Set<Property> getProperties()
    {
        return properties;
    }

    public void setProperties(Set<Property> properties)
    {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return Integer.toString(getName().hashCode());
    }
}
