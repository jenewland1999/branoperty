package uk.me.jenewland.natpropsalessys.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uk.me.jenewland.natpropsalessys.model.Branch;

import java.util.ArrayList;
import java.util.List;

import static uk.me.jenewland.natpropsalessys.Main.dataManager;

public class UpdateBranchController {
    @FXML
    private TextField tfUsername, tfWebsite, tfEmail, tfTelNo;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextArea taAddress;

    @FXML
    private Button btnCancel, btnCreate;

    private DashboardController dashboardController;
    private Branch branch;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public void populateFields() {
        tfUsername.setText(branch.getName());
        pfPassword.setText(branch.getBranchSecretary().getPassword());
        taAddress.setText(branch.getAddress());
        tfWebsite.setText(branch.getWebsite());
        tfEmail.setText(branch.getEmail());
        tfTelNo.setText(branch.getTel());
    }

    public void updateBranch() {
        List<String> errors = new ArrayList<>();

        if (tfUsername.getText().trim().length() <= 0) {
            errors.add("Username cannot be empty");
        }

        if (tfUsername.getText().trim().length() > 127) {
            errors.add("Username cannot exceed 127 characters");
        }

        if (pfPassword.getText().trim().length() <= 0) {
            errors.add("Password cannot be empty");
        }

        if (taAddress.getText().trim().length() <= 0) {
            errors.add("Address cannot be empty");
        }

        if (tfWebsite.getText().trim().length() <= 0) {
            errors.add("Website cannot be empty");
        }

        if (tfWebsite.getText().trim().length() > 255) {
            errors.add("Website cannot exceed 255 characters");
        }

        if (tfEmail.getText().trim().length() <= 0) {
            errors.add("Email cannot be empty");
        }

        if (tfEmail.getText().trim().length() > 255) {
            errors.add("Email cannot exceed 255 characters");
        }

        if (tfTelNo.getText().trim().length() <= 0) {
            errors.add("Telephone No. cannot be empty");
        }

        if (tfTelNo.getText().trim().length() > 11) {
            errors.add("Telephone No. cannot exceed 10 digits");
        }

        if (errors.size() == 0) {
            Branch b = new Branch(
                    tfUsername.getText(),
                    pfPassword.getText(),
                    taAddress.getText(),
                    tfEmail.getText(),
                    tfWebsite.getText(),
                    tfTelNo.getText()
            );
            b.setProperties(branch.getProperties());
            dataManager.update(branch, b);
            close();
        }
    }

    public void close() {
        ((Stage) btnCancel.getScene().getWindow()).close();
        dashboardController.searchBranches();
    }
}
