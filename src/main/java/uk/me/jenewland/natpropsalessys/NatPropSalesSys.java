package uk.me.jenewland.natpropsalessys;

import uk.me.jenewland.natpropsalessys.model.user.UserAdmin;
import uk.me.jenewland.natpropsalessys.utils.FileHandler;

import java.util.logging.Logger;

public final class NatPropSalesSys
{
    // Declare the admin account details
    // and the location where they are stored.
    static final String ADMIN_USERNAME = "admin";
    static final String ADMIN_PASSWORD = "password";
    static final String ADMIN_FILENAME = "admin.dat";

    // Declare logger instance
    public static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void generateAdminFile() {
        FileHandler.writeObjsToFile(ADMIN_FILENAME, new UserAdmin(ADMIN_USERNAME, ADMIN_PASSWORD));
    }

    public static String getAdminUsername()
    {
        return ADMIN_USERNAME;
    }

    public static String getAdminPassword()
    {
        return ADMIN_PASSWORD;
    }

    public static String getAdminFilename()
    {
        return ADMIN_FILENAME;
    }

    public static String getAdminFile() {return getResource(ADMIN_FILENAME); }

    public static String getResource(String filename)
    {
        return Main.class.getClassLoader().getResource(filename).getFile();
    }
}
