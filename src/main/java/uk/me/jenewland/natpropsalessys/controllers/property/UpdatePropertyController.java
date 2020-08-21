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

public class UpdatePropertyController {
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
    private Button btnClose;

    private DashboardController dashboardController;
    private Property property;
    private Session session;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public void populateFields() {
        assert session != null;
        assert property != null;

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

        cbBranch.getSelectionModel().select(property.getBranch());
        taAddress.setText(property.getAddress());
        tfNoOfRooms.setText(String.valueOf(property.getNoOfRooms()));
        tfSellingPrice.setText(String.valueOf(property.getSellingPrice()));
        tfSoldPrice.setText(
                property.isSold() ? String.valueOf(property.getSoldPrice()) : ""
        );
        cbType.getItems().add(Property.TYPES.FLAT);
        cbType.getItems().add(Property.TYPES.HOUSE);
        cbType.getSelectionModel().select(property.getType());

        if (property instanceof PropertyHouse) {
            PropertyHouse house = (PropertyHouse) property;
            tfNoOfFloors.setText(String.valueOf(house.getNoOfFloors()));

            if (house.hasGarage()) {
                cbHasGarage.setSelected(true);
            }

            if (house.hasGarden()) {
                cbHasGarden.setSelected(true);
            }
        }

        if (property instanceof PropertyFlat) {
            PropertyFlat flat = (PropertyFlat) property;

            tfFloorNo.setText(String.valueOf(flat.getFloorNo()));
            tfMonthlyCharge.setText(String.valueOf(flat.getMonthlyCharge()));
        }
    }

    public void updateProperty() {
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
                showInvalidFormAlert(errors);
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
                showInvalidFormAlert(errors);
            }
        }

        if (property != null) {
            if (session.isAdmin()) {
                Branch branch = (Branch) dataManager.read(property.getBranch().getName());
                branch.updateProperty(this.property, property);
                dataManager.update(dataManager.read(property.getBranch().getName()), branch);
            } else {
                session.getBranch().updateProperty(this.property, property);
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
