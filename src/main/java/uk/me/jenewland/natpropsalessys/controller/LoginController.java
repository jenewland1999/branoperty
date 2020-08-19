package uk.me.jenewland.natpropsalessys.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import uk.me.jenewland.natpropsalessys.Main;
import uk.me.jenewland.natpropsalessys.model.Branch;
import uk.me.jenewland.natpropsalessys.model.IModel;
import uk.me.jenewland.natpropsalessys.model.Session;
import uk.me.jenewland.natpropsalessys.model.user.UserAdmin;
import uk.me.jenewland.natpropsalessys.utils.FileHandler;

import java.io.IOException;
import java.util.logging.Level;

import static uk.me.jenewland.natpropsalessys.Main.*;

public class LoginController
{
    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabAdminLogin;

    @FXML
    private TextField tfBranchUsername, tfAdminUsername;

    @FXML
    private PasswordField pfBranchPassword, pfAdminPassword;

    /**
     * Checks the passed in username and password against the stored branches
     * or the stored admin then returns either null or a new instance of
     * {@code Session} class based on whether the credentials match.
     *
     * @param username the username that the user has typed.
     * @param password the password that the user has typed.
     * @param isAdmin whether or not we're checking for admin login.
     * @return either null or a new instance of {@code Session} class.
     */
    private Session getSession(String username, String password, boolean isAdmin) {
        // Check if username and password aren't blank
        if (username.isBlank() || password.isBlank()) {
            return null;
        }

        // If the user is an administrator then check their login
        if (isAdmin) {
            // Retrieve the admin's credentials from disk
            Object admin = FileHandler.readObjFromFile(
              Main.getAdminFile()
            );

            // Check if admin is instance of UserAdmin
            if (!(admin instanceof UserAdmin)) {
                return null;
            }

            // Cast the object to UserAdmin and retrieve username and password
            // field from said object
            UserAdmin userAdmin = (UserAdmin) admin;
            String userAdminUsername = userAdmin.getUsername();
            String userAdminPassword = userAdmin.getPassword();

            // Check login credentials and if they're valid return the new
            // session otherwise return null as login failed
            if (userAdminUsername.equalsIgnoreCase(username) && userAdminPassword.equals(password)) {
                return new Session((UserAdmin) FileHandler.readObjFromFile(Main.getAdminFile()));
            } else {
                return null;
            }
        }

        /*
            At this stage we know the user isn't an admin so find the branch
            with matching details and log them in if the correct
            username/password are supplied.
         */

        // Retrieve all the branches as IModels and iterate over them
        for (IModel model : dataManager.readAll()) {
            // If the model isn't an instance of Branch then skip it
            if (!(model instanceof Branch)) {
                continue;
            }

            // Cast the model to branch and store in local field
            Branch branch = (Branch) model;

            // Check the login credentials and store them in a boolean
            boolean isValid = branch.getBranchSecretary().getUsername().equalsIgnoreCase(username)
                    && branch.getBranchSecretary().getPassword().equalsIgnoreCase(password);

            // Check login credentials and if they're valid return the new
            // session otherwise return null as login failed
            if (isValid) {
                return new Session((Branch) dataManager.read(tfBranchUsername.getText()));
            }
        }

        // If all else fails return null :(
        return null;
    }

    /**
     * GUI login button event handler which handles login.
     * @throws IOException
     */
    public void login() throws IOException {
        // Store whether we're checking for an administrator or not
        boolean isAdmin = tabAdminLogin.isSelected();

        // Log information to the console on what's happening
        logger.log(Level.INFO,"Logging in...");

        // Declare a local session field
        Session session;

        // Retrieve either the admin or branch secretaries session based on
        // which login tab we're on getSession checks user's login
        // credentials and returns either a new instance of session or null if
        // login failed.
        if (isAdmin) {
            session = getSession(tfAdminUsername.getText(), pfAdminPassword.getText(), true);
        } else {
            session = getSession(tfBranchUsername.getText(), pfBranchPassword.getText(), false);
        }

        // If login didn't fail open the dashboard otherwise present the user
        // with an error dialog
        if (session != null) {
            // Log information to the console on what's happening
            logger.log(Level.INFO, "Login successful. Redirecting to dashboard.");

            // Format the title string based of being an admin or not
            String title = String.format(
                    "National Property Sales System - %s Dashboard",
                    isAdmin ? "Admin" : "Secretary"
            );

            // Open the GUI and initialise the dashboard controller
            FXMLLoader loader = openGui("./view/dashboard.fxml", title, false);
            DashboardController controller = loader.getController();
            controller.setSession(session);
            controller.init();

            // Then hide the login screen
            tabPane.getScene().getWindow().hide();
        } else {
            // Log information to the console on what's happening
            logger.log(Level.INFO, "Login unsuccessful. Please try again.");

            // Display feedback to user with alert message
            new Alert(Alert.AlertType.ERROR, "Invalid login credentials.", ButtonType.OK).showAndWait();
        }

        // Clear the text and password fields (For improved UX)
        tfAdminUsername.clear();
        pfAdminPassword.clear();
        tfBranchUsername.clear();
        pfBranchPassword.clear();
    }
}
