package uk.me.jenewland.natpropsalessys.controllers.property;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uk.me.jenewland.natpropsalessys.controllers.DashboardController;
import uk.me.jenewland.natpropsalessys.models.Branch;
import uk.me.jenewland.natpropsalessys.models.Session;
import uk.me.jenewland.natpropsalessys.models.property.Property;
import uk.me.jenewland.natpropsalessys.models.property.PropertyFlat;
import uk.me.jenewland.natpropsalessys.models.property.PropertyHouse;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static uk.me.jenewland.natpropsalessys.Main.*;

public class CreatePropertyController {
    @FXML
    private ChoiceBox<Branch> cbBranch;

    @FXML
    private TextField tfNoOfRooms, tfSellingPrice, tfSoldPrice, tfFloorNo, tfMonthlyCharge, tfNoOfFloors;

    @FXML
    private TextArea taAddress;

    @FXML
    private ChoiceBox<Property.TYPES> cbType;

    @FXML
    private CheckBox cbHasGarage, cbHasGarden;

    @FXML
    private Button btnClose, btnCreate;

    private DashboardController dashboardController;
    private Session session;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public void setSession(Session session) {
        this.session = session;

        assert session != null;
    }

    public void populateFields() {
        assert session != null;

        if (session.isAdmin()) {
            dataManager.readAll().forEach(model -> {
                if (model instanceof Branch) {
                    cbBranch.getItems().add((Branch) model);
                }
            });
        } else {
            cbBranch.getItems().add(session.getBranch());
            cbBranch.getSelectionModel().selectFirst();
        }

        cbType.getItems().add(Property.TYPES.FLAT);
        cbType.getItems().add(Property.TYPES.HOUSE);
    }

    public void createProperty() {
        // First check the type ChoiceBox
        if (cbType.getSelectionModel().isEmpty()) {
            showInvalidFormAlert("You must select a type.");
            return;
        }

        // Create a list to store potential form validation errors
        List<String> errors = new ArrayList<>();

        // Collect the numeric text fields together in an array
        TextField[] textFields = {tfNoOfRooms, tfSellingPrice, tfSoldPrice, tfFloorNo, tfMonthlyCharge, tfNoOfFloors};

        // Remove any non-digit characters from the number-only text fields
        for (TextField textField : textFields) {
            if (textField == null) {
                continue;
            }

            if (textField.getText().isBlank()) {
                continue;
            }

            if (!textField.getText().trim().matches("\\d*")) {
                textField.setText(textField.getText().replaceAll("[^\\d]", ""));
            }
        }

        if (cbBranch.getSelectionModel().isEmpty()) {
            errors.add("You must select a branch.");
        }

        if (taAddress.getText().isBlank()) {
            errors.add("Address cannot be empty.");
        }

        if (tfNoOfRooms.getText().isBlank()) {
            errors.add("Number of Rooms cannot be empty.");
        }

        if (tfSellingPrice.getText().isBlank()) {
            errors.add("Selling price cannot be empty.");
        }

        Property property = null;

        if (cbType.getValue() == Property.TYPES.FLAT) {
            if (tfFloorNo.getText().isBlank()) {
                errors.add("Floor Number cannot be empty.");
            }

            if (tfMonthlyCharge.getText().isBlank()) {
                errors.add("Monthly Charge cannot be empty.");
            }

            if (errors.size() == 0) {
                property = new PropertyFlat(
                        cbBranch.getValue(),
                        taAddress.getText(),
                        Integer.parseInt(tfNoOfRooms.getText()),
                        Long.parseLong(tfSellingPrice.getText()),
                        tfSoldPrice.getText().isBlank() ? -1 : Long.parseLong(tfSoldPrice.getText()),
                        Integer.parseInt(tfFloorNo.getText()),
                        Integer.parseInt(tfMonthlyCharge.getText())
                );
            } else {
                showInvalidFormAlert(errors.toArray(new String[0]));
            }

        } else if (cbType.getSelectionModel().getSelectedItem() == Property.TYPES.HOUSE) {
            if (tfNoOfFloors.getText().isBlank()) {
                errors.add("Number of Floors cannot be empty.");
            }

            if (errors.size() == 0) {
                property = new PropertyHouse(
                        cbBranch.getValue(),
                        taAddress.getText(),
                        Integer.parseInt(tfNoOfRooms.getText()),
                        Long.parseLong(tfSellingPrice.getText()),
                        tfSoldPrice.getText().isBlank() ? -1 : Long.parseLong(tfSoldPrice.getText()),
                        Integer.parseInt(tfNoOfFloors.getText()),
                        cbHasGarage.isSelected(),
                        cbHasGarden.isSelected()
                );
            } else {
                showInvalidFormAlert(errors.toArray(new String[0]));
            }
        }

        if (property != null) {
            if (session.isAdmin()) {
                Branch branch = (Branch) dataManager.read(property.getBranch().getName());
                branch.addProperty(property);
                dataManager.update(dataManager.read(property.getBranch().getName()), branch);
            } else {
                session.getBranch().addProperty(property);
                dataManager.update(dataManager.read(session.getBranch().getName()), session.getBranch());
            }

            close();
        } else {
            logger.log(Level.SEVERE, "Unable to submit form; property is null.");
        }
    }

    public void close() {
        ((Stage) btnClose.getScene().getWindow()).close();
        dashboardController.refresh();
    }
}
