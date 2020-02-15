package main.java.uk.me.jenewland.natpropsalessys.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ControllerMain
{
  @FXML
  private Label lblBranchUsername;

  @FXML
  private TextField tfBranchUsername;

  @FXML
  private Label lblBranchPassword;

  @FXML
  private PasswordField pfBranchPassword;

  @FXML
  private Button btnBranchLogin;

  public void login() {
    String username = tfBranchUsername.getText();
    String password = pfBranchPassword.getText();

    System.out.println("Username: " + username + "\n" + "Password: " + password);
  }
}
