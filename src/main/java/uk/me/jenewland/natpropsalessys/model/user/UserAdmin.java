package uk.me.jenewland.natpropsalessys.model.user;

import java.io.Serializable;

public class UserAdmin extends User implements Serializable
{
    public UserAdmin()
    {
        super("", "");
    }

    public UserAdmin(String username, String password)
    {
        super(username, password);
    }
}
