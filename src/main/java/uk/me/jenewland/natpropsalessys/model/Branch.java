package uk.me.jenewland.natpropsalessys.model;

import uk.me.jenewland.natpropsalessys.model.property.Property;
import uk.me.jenewland.natpropsalessys.model.user.UserSecretary;

import java.io.Serializable;
import java.util.ArrayList;

public class Branch implements Serializable, IModel
{
    private String name = "";
    private String address = "";
    private String email = "";
    private String website = "";
    private int tel = 0;
    private UserSecretary branchSecretary = new UserSecretary(this.name, "password");
    private ArrayList<Property> properties = new ArrayList<>();

    public Branch()
    {

    }

    public Branch(String name, String address, String email, String website, int tel, UserSecretary branchSecretary, ArrayList<Property> properties)
    {
        this.name = name;
        this.address = address;
        this.email = email;
        this.website = website;
        this.tel = tel;
        this.branchSecretary = branchSecretary;
        this.properties = new ArrayList<>(properties);
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

    public int getTel()
    {
        return tel;
    }

    public void setTel(int tel)
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

    public ArrayList<Property> getProperties()
    {
        return properties;
    }

    public void setProperties(ArrayList<Property> properties)
    {
        this.properties = properties;
    }
}
