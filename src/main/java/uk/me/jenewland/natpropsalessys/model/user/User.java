package uk.me.jenewland.natpropsalessys.model.user;

import uk.me.jenewland.natpropsalessys.model.IModel;

import java.io.Serializable;
import java.util.Date;

/**
 * Serializable model class to represent estate agency users.
 */
public class User implements Serializable, IModel {
    protected String username = "";
    protected String password = ""; // TODO: SALT & HASH ME
    protected Date dateCreated = new Date();

    /**
     * Default empty constructor.
     */
    public User() {
    }

    /**
     * User constructor.
     *
     * @param username the user's username.
     * @param password the user's password.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Getter for username field.
     *
     * @return the username field.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for username field.
     *
     * @param username the new username to set the field to.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for password field.
     *
     * @return the password field.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for password field.
     *
     * @param password the new password to set the field to.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for dateCreated field.
     *
     * @return the dateCreated field.
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Setter for dateCreated field.
     *
     * @param dateCreated the new dateCreated to set the field to.
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
