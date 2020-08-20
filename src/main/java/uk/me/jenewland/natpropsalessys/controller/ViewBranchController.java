package uk.me.jenewland.natpropsalessys.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uk.me.jenewland.natpropsalessys.model.Branch;

public class ViewBranchController {
    @FXML
    private TextField tfUsername, tfWebsite, tfEmail, tfTelNo;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextArea taAddress;

    @FXML
    private Button btnClose;

    private DashboardController dashboardController;
    private Branch branch;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;

        tfUsername.setText(branch.getName());
        pfPassword.setText(branch.getBranchSecretary().getPassword());
        taAddress.setText(branch.getAddress());
        tfWebsite.setText(branch.getWebsite());
        tfEmail.setText(branch.getEmail());
        tfTelNo.setText(branch.getTel());
    }

    public void close() {
        ((Stage) btnClose.getScene().getWindow()).close();
        dashboardController.searchBranches();
    }
}
