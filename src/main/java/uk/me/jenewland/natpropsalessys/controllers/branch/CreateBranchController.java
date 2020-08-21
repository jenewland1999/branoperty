package uk.me.jenewland.natpropsalessys.controllers.branch;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uk.me.jenewland.natpropsalessys.controllers.DashboardController;
import uk.me.jenewland.natpropsalessys.models.Branch;

import java.util.ArrayList;
import java.util.List;

import static uk.me.jenewland.natpropsalessys.Main.dataManager;
import static uk.me.jenewland.natpropsalessys.Main.showInvalidFormAlert;

public class CreateBranchController {
    @FXML
    private TextField tfUsername, tfWebsite, tfEmail, tfTelNo;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextArea taAddress;

    @FXML
    private Button btnCancel, btnCreate;

    private DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public void createBranch() {
        List<String> errors = new ArrayList<>();

        if (tfUsername.getText().isBlank()) {
            errors.add("Username cannot be empty.");
        }

        if (tfUsername.getText().trim().length() > 127) {
            errors.add("Username cannot exceed 127 characters.");
        }

        if (pfPassword.getText().isBlank()) {
            errors.add("Password cannot be empty.");
        }

        if (taAddress.getText().isBlank()) {
            errors.add("Address cannot be empty.");
        }

        if (tfWebsite.getText().isBlank()) {
            errors.add("Website cannot be empty.");
        }

        if (tfEmail.getText().isBlank()) {
            errors.add("Email cannot be empty.");
        }

        if (tfTelNo.getText().isBlank()) {
            errors.add("Telephone No. cannot be empty.");
        }

        if (tfTelNo.getText().trim().length() > 11) {
            errors.add("Telephone No. cannot exceed 11 digits.");
        }

        if (errors.size() == 0) {
            dataManager.create(new Branch(
                    tfUsername.getText().toLowerCase(),
                    pfPassword.getText(),
                    taAddress.getText(),
                    tfWebsite.getText(), tfEmail.getText(),
                    tfTelNo.getText()
            ));
            close();
        } else {
            showInvalidFormAlert(errors);
        }
    }

    public void close() {
        ((Stage) btnCancel.getScene().getWindow()).close();
        dashboardController.refresh();
    }
}
