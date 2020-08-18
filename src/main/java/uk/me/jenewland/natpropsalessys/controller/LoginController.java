package uk.me.jenewland.natpropsalessys.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uk.me.jenewland.natpropsalessys.NatPropSalesSys;
import uk.me.jenewland.natpropsalessys.model.Branch;
import uk.me.jenewland.natpropsalessys.model.IModel;
import uk.me.jenewland.natpropsalessys.model.Session;
import uk.me.jenewland.natpropsalessys.model.user.UserAdmin;
import uk.me.jenewland.natpropsalessys.utils.FileHandler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import static uk.me.jenewland.natpropsalessys.Main.dataManager;

public class LoginController
{
    @FXML
    private Tab tabAdminLogin, tabBranchLogin;

    @FXML
    private Label lblBranchUsername, lblBranchPassword, lblAdminUsername, lblAdminPassword;

    @FXML
    private TextField tfBranchUsername, tfAdminUsername;

    @FXML
    private PasswordField pfBranchPassword, pfAdminPassword;

    @FXML
    private Button btnAdminLogin, btnBranchLogin;

    private boolean isLoginValid(TextField usernameField, PasswordField passwordField, boolean isAdmin)
    {
        // Retrieve the username and password typed by the user
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Check if username and password aren't blank
        if (username.isBlank() || password.isBlank()) {
            return false;
        }

        // If the user is an administrator then check their login
        if (isAdmin) {
            // Retrieve the admin's credentials from disk
            Object admin = FileHandler.readObjFromFile(
              NatPropSalesSys.getAdminFile()
            );

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

        // At this stage we know the user isn't an admin so find the branch with matching details
        // and log them in if the correct username/password are supplied.

        Set<IModel> branches = new HashSet<IModel>(dataManager.readAll());

        for (IModel branch : branches) {
            Branch b = (Branch) branch;

            String userBranchUsername = b.getBranchSecretary().getUsername();
            String userBranchPassword = b.getBranchSecretary().getPassword();

            if (userBranchUsername.equalsIgnoreCase(username) && userBranchPassword.equalsIgnoreCase(password)) {
                return true;
            }
        }

        return false;
    }

    public void login() throws IOException
    {
        NatPropSalesSys.LOGGER.log(Level.INFO,"Logging in...");
        if (tabAdminLogin.isSelected() && isLoginValid(tfAdminUsername, pfAdminPassword, true)) {
            NatPropSalesSys.LOGGER.log(Level.INFO, "Login successful. Redirecting to dashboard.");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/adminDashboard.fxml"));
            Parent root = loader.load();
            AdminDashboardController controller = loader.getController();
            controller.setSession(new Session((UserAdmin) FileHandler.readObjFromFile(NatPropSalesSys.getAdminFile())));
            controller.init();

            Stage stage = new Stage();
            stage.setTitle("National Property Sales System - Admin Dashboard");
            stage.setScene(new Scene(root, 1024, 768));
            stage.setResizable(false);
            stage.show();
            btnAdminLogin.getScene().getWindow().hide();

        } else if (tabBranchLogin.isSelected() && isLoginValid(tfBranchUsername, pfBranchPassword, false)) {
            NatPropSalesSys.LOGGER.log(Level.INFO, "Login successful. Redirecting to dashboard.");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/dashboard.fxml"));
            Parent root = loader.load();
            DashboardController controller = loader.getController();
            controller.setSession(new Session((Branch) dataManager.read(tfBranchUsername.getText())));
            controller.init();

            Stage stage = new Stage();
            stage.setTitle("National Property Sales System -  Secretary Dashboard");
            stage.setScene(new Scene(root, 1024, 768));
            stage.setResizable(false);
            stage.show();
            btnAdminLogin.getScene().getWindow().hide();
        } else {
            NatPropSalesSys.LOGGER.log(Level.WARNING, "Login unsuccessful");

            // Clear the text and password fields (For improved UX)
            tfAdminUsername.clear();
            pfAdminPassword.clear();
            tfBranchUsername.clear();
            pfBranchPassword.clear();

            // Display feedback to user with alert message
            new Alert(Alert.AlertType.ERROR, "Invalid login credentials.", ButtonType.OK).showAndWait();
        }
    }
}
