package branoperty.controller.branch;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import branoperty.controller.Dashboard;
import branoperty.model.Branch;

public class ViewBranch {
    @FXML
    private TextField tfUsername, tfWebsite, tfEmail, tfTelNo;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextArea taAddress;

    @FXML
    private Button btnClose;

    private Dashboard dashboard;
    private Branch branch;

    public void setDashboardController(Dashboard dashboard) {
        this.dashboard = dashboard;
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
    }
}
