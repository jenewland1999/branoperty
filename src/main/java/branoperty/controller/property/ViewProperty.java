package branoperty.controller.property;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import branoperty.controller.Dashboard;
import branoperty.model.property.Property;
import branoperty.model.property.PropertyFlat;
import branoperty.model.property.PropertyHouse;

import java.text.NumberFormat;
import java.util.*;

public class ViewProperty {
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

    private Dashboard dashboard;
    private Property property;

    private final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("en", "GB"));

    public void setDashboardController(Dashboard dashboard) {
        this.dashboard = dashboard;
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
