package uk.me.jenewland.natpropsalessys.model.user;

import uk.me.jenewland.natpropsalessys.model.IModel;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class User implements Serializable, IModel
{
    protected String username = "";
    protected String password = ""; // TODO: HASH+SALT ME LATER
    protected Date dateCreated = new Date();

    public User()
    {

    }

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Date getDateCreated()
    {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return UUID.fromString(getUsername()).toString();
    }
}
