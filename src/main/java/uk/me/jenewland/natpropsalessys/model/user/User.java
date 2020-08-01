package uk.me.jenewland.natpropsalessys.model.user;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable
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
}
