package uk.me.jenewland.natpropsalessys.model;

import uk.me.jenewland.natpropsalessys.model.user.UserAdmin;

/**
 * Session class to store the currently logged in user and their data. Once
 * instantiated the method becomes read-only.
 */
public final class Session {
    private UserAdmin admin;
    private Branch branch;

    /**
     * Session constructor for administrators.
     *
     * @param admin the instance of {@code UserAdmin} to be held in the session.
     */
    public Session(UserAdmin admin) {
        this.admin = admin;
    }

    /**
     * Session constructor for branch secretaries.
     *
     * @param branch the instance of {@code Branch} to be held in the session.
     */
    public Session(Branch branch) {
        this.branch = branch;
    }

    /**
     * Getter for admin field.
     *
     * @return the admin field.
     */
    public final UserAdmin getAdmin() {
        return admin;
    }

    /**
     * Getter for branch field.
     *
     * @return the branch field.
     */
    public final Branch getBranch() {
        return branch;
    }

    /**
     * Helper method to return the username of either the administrator or
     * branch secretary.
     *
     * @return the username of the admin or branch secretary.
     */
    public final String getUsername() {
        if (branch != null) return branch.getName();
        if (admin != null) return admin.getUsername();
        return "invalid session";
    }

    /**
     * Check whether or not the session contains an administrative user.
     *
     * @return true if admin; false otherwise.
     */
    public final boolean isAdmin() {
        return branch == null && admin != null;
    }
}
