package uk.me.jenewland.natpropsalessys.controllers.property;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uk.me.jenewland.natpropsalessys.controllers.DashboardController;
import uk.me.jenewland.natpropsalessys.models.property.Property;
import uk.me.jenewland.natpropsalessys.models.property.PropertyFlat;
import uk.me.jenewland.natpropsalessys.models.property.PropertyHouse;

import java.text.NumberFormat;
import java.util.*;

public class ViewPropertyController {
    @FXML
    private TextField tfBranch, tfNoOfRooms, tfSellingPrice, tfSoldPrice, tfFloorNo, tfMonthlyCharge, tfNoOfFloors;

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

    private final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("en", "GB"));

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public void setProperty(Property property) {
        this.property = property;

        tfBranch.setText(property.getBranch().getName());
        taAddress.setText(property.getAddress());
        tfNoOfRooms.setText(String.valueOf(property.getNoOfRooms()));
        tfSellingPrice.setText(numberFormat.format(((double) property.getSellingPrice() / 100)));
        tfSoldPrice.setText(
                property.isSold() ? numberFormat.format(((double) property.getSoldPrice()) / 100) : "-"
        );
        cbType.getItems().add(property.getType());
        cbType.getSelectionModel().selectFirst();

        if (property instanceof PropertyHouse) {
            PropertyHouse house = (PropertyHouse) property;
            tfNoOfFloors.setText(String.valueOf(house.getNoOfFloors()));

            if (house.hasGarage()) {
                cbHasGarage.setSelected(true);
            }

            if (house.hasGarden()) {
                cbHasGarden.setSelected(true);
            }

            tfFloorNo.setText("N/A");
            tfMonthlyCharge.setText("N/A");
            return;
        }

        if (property instanceof PropertyFlat) {
            PropertyFlat flat = (PropertyFlat) property;

            tfFloorNo.setText(String.valueOf(flat.getFloorNo()));
            tfMonthlyCharge.setText(numberFormat.format(((double) flat.getMonthlyCharge()) / 100));

            tfNoOfFloors.setText("N/A");
            cbHasGarden.setVisible(false);
            cbHasGarage.setVisible(false);
        }
    }

    public void close() {
        ((Stage) btnClose.getScene().getWindow()).close();
    }
}
