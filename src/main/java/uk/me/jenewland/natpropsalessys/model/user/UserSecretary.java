package main.java.uk.me.jenewland.natpropsalessys.model.user;

import java.io.Serializable;

public class UserSecretary extends User implements Serializable
{
    public UserSecretary()
    {
        super("", "");
    }

    public UserSecretary(String username, String password)
    {
        super(username, password);
    }
}
