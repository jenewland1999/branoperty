package branoperty.model.user;

import java.io.Serializable;

/**
 * Serializable model class to represent estate agency admin users.
 */
public class UserAdmin extends User implements Serializable {
    /**
     * Default empty constructor.
     */
    public UserAdmin() {
        super("", "");
    }

    /**
     * UserAdmin constructor.
     *
     * @param username the user's username.
     * @param password the user's password.
     */
    public UserAdmin(String username, String password) {
        super(username, password);
    }
}
