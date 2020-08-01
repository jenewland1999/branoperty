package uk.me.jenewland.natpropsalessys.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import uk.me.jenewland.natpropsalessys.Main;
import uk.me.jenewland.natpropsalessys.NatPropSalesSys;
import uk.me.jenewland.natpropsalessys.model.user.UserAdmin;
import uk.me.jenewland.natpropsalessys.utils.FileHandler;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerMain
{
    @FXML
    private Tab tabAdminLogin;

    @FXML
    private Tab tabBranchLogin;

    @FXML
    private Label lblBranchUsername;

    @FXML
    private TextField tfBranchUsername;

    @FXML
    private Label lblBranchPassword;

    @FXML
    private PasswordField pfBranchPassword;

    @FXML
    private TextField tfAdminUsername;

    @FXML
    private PasswordField pfAdminPassword;

    public boolean isValidAdminLogin()
    {
        // Retrieve the username and password typed by the user
        String username = tfAdminUsername.getText();
        String password = pfAdminPassword.getText();

        // Check if username and password aren't blank
        if (username.isBlank() || password.isBlank()) {
            return false;
        }

        // Retrieve the list of admins from disk
        ArrayList<Object> admins = FileHandler.readObjsFromFile(
                NatPropSalesSys.getAdminFile()
        );

        // Loop through list of admins and see if any of them match the entered credentials
        for (Object admin : admins)
        {
            // Check if admin is instance of UserAdmin
            if (!(admin instanceof UserAdmin)) {
                return false;
            }

            // Cast the object to UserAdmin and retrieve username and password field
            UserAdmin userAdmin = (UserAdmin) admin;
            String userAdminUsername = userAdmin.getUsername();
            String userAdminPassword = userAdmin.getPassword();

            // Login credentials are correct;
            return userAdminUsername.equalsIgnoreCase(username) && userAdminPassword.equals(password);
        }

        return false;
    }

    public void login()
    {
        NatPropSalesSys.LOGGER.log(Level.INFO,"Logging in...");
        if (tabAdminLogin.isSelected()) {
            if (isValidAdminLogin()) {
                NatPropSalesSys.LOGGER.log(Level.INFO, "Login successful. Redirecting to dashboard.");
            }
        }
    }
}
