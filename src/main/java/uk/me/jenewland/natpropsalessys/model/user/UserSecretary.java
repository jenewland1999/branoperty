package uk.me.jenewland.natpropsalessys.model.user;

import java.io.Serializable;

/**
 * Serializable model class to represent estate agency admin users.
 */
public class UserSecretary extends User implements Serializable {
    /**
     * Default empty constructor.
     */
    public UserSecretary() {
        super("", "");
    }

    /**
     * UserSecretary constructor.
     *
     * @param username the user's username.
     * @param password the user's password.
     */
    public UserSecretary(String username, String password) {
        super(username, password);
    }
}
