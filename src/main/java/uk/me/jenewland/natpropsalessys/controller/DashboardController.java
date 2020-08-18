package uk.me.jenewland.natpropsalessys.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uk.me.jenewland.natpropsalessys.utils.DataManager;
import uk.me.jenewland.natpropsalessys.model.Branch;
import uk.me.jenewland.natpropsalessys.model.IModel;
import uk.me.jenewland.natpropsalessys.model.Session;
import uk.me.jenewland.natpropsalessys.model.property.Property;
import uk.me.jenewland.natpropsalessys.model.property.PropertyFlat;
import uk.me.jenewland.natpropsalessys.model.property.PropertyHouse;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static uk.me.jenewland.natpropsalessys.Main.dataManager;

public class DashboardController {

    private Session session;

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return this.session;
    }

    @FXML
    private TabPane tabPaneDashboard;

    @FXML
    private Tab tabBranches, tabProperties;

    @FXML
    private Text txtWelcomeMsg1, txtWelcomeMsg2;

    @FXML
    private TableView<Branch> tableViewBranch;
    @FXML
    private TableView<DisplayProperty> tableViewProperty;

    @FXML
    private TableColumn<Branch, String> tabColBranchName;
    @FXML
    private TableColumn<Branch, String> tabColBranchAddress;
    @FXML
    private TableColumn<Branch, String> tabColBranchEmail;
    @FXML
    private TableColumn<Branch, String> tabColBranchWeb;
    @FXML
    private TableColumn<Branch, Long> tabColBranchTel;
    @FXML
    private TableColumn<DisplayProperty, String> tabColPropertyAddress;
    @FXML
    private TableColumn<DisplayProperty, String> tabColPropertyRooms;
    @FXML
    private TableColumn<DisplayProperty, String> tabColPropertySellPrice;
    @FXML
    private TableColumn<DisplayProperty, String> tabColPropertySoldPrice;
    @FXML
    private TableColumn<DisplayProperty, String> tabColPropertyFloors;
    @FXML
    private TableColumn<DisplayProperty, String> tabColPropertyGarage;
    @FXML
    private TableColumn<DisplayProperty, String> tabColPropertyGarden;
    @FXML
    private TableColumn<DisplayProperty, String> tabColPropertyFloor;
    @FXML
    private TableColumn<DisplayProperty, String> tabColPropertyCharge;

    @FXML
    private Button btnLogout1, btnLogout2, btnBranchSearch, btnBranchCreate, btnBranchEdit, btnBranchDelete, btnPropertySearch, btnPropertyCreate, btnPropertyEdit, btnPropertyDelete;

    @FXML
    private TextField tfBranchSearch, tfPropertySearch;

    private Set<Property> propertyList = new HashSet<>();

    public static class DisplayProperty {
        private PropertyHouse propertyHouse;
        private PropertyFlat propertyFlat;
        private Property property;
        private String address = "";
        private String type = "";
        private String noOfRooms = "";
        private String sellingPrice = "";
        private String soldPrice = "";
        private String noOfFloors = "";
        private String hasGarage = "";
        private String hasGarden = "";
        private String floorNo = "";
        private String monthlyCharge = "";

        public DisplayProperty(Property property) throws IllegalArgumentException {
            this.property = property;
            if (property instanceof PropertyHouse) {
                PropertyHouse propertyHouse = (PropertyHouse) property;

                this.propertyHouse = propertyHouse;

                this.address = propertyHouse.getAddress();
                this.type = property.getType();
                this.noOfRooms = Integer.toString(propertyHouse.getNoOfRooms());
                this.sellingPrice = Integer.toString(propertyHouse.getSellingPrice());
                this.soldPrice = Integer.toString(propertyHouse.getSoldPrice());
                this.noOfFloors = Integer.toString(propertyHouse.getNoOfFloors());
                this.hasGarage = propertyHouse.isHasGarage() ? "Yes" : "No";
                this.hasGarden = propertyHouse.isHasGarden() ? "Yes" : "No";
                this.floorNo = "N/A";
                this.monthlyCharge = "N/A";
            } else if (property instanceof PropertyFlat) {
                PropertyFlat propertyFlat = (PropertyFlat) property;

                this.propertyFlat = propertyFlat;

                this.address = propertyFlat.getAddress();
                this.noOfRooms = Integer.toString(propertyFlat.getNoOfRooms());
                this.sellingPrice = Integer.toString(propertyFlat.getSellingPrice());
                this.soldPrice = Integer.toString(propertyFlat.getSoldPrice());
                this.floorNo = Integer.toString(propertyFlat.getFloorNo());
                this.monthlyCharge = Integer.toString(propertyFlat.getMonthlyCharge());
                this.noOfFloors = "N/A";
                this.hasGarage = "N/A";
                this.hasGarden = "N/A";
            } else {
                throw new IllegalArgumentException();
            }
        }

        public String getAddress() {
            return address;
        }

        public String getNoOfRooms() {
            return noOfRooms;
        }

        public String getSellingPrice() {
            return sellingPrice;
        }

        public String getSoldPrice() {
            return soldPrice;
        }

        public String getNoOfFloors() {
            return noOfFloors;
        }

        public String getHasGarage() {
            return hasGarage;
        }

        public String getHasGarden() {
            return hasGarden;
        }

        public String getFloorNo() {
            return floorNo;
        }

        public String getMonthlyCharge() {
            return monthlyCharge;
        }

        public Property getProperty() {
            return this.property;
        }

        public PropertyHouse getPropertyHouse() {
            if (propertyHouse == null) {
                return null;
            }

            return this.propertyHouse;
        }

        public PropertyFlat getPropertyFlat() {
            if (propertyFlat == null) {
                return null;
            }

            return propertyFlat;
        }
    }

    public void logout() throws IOException {
        // Close the current stage
        Stage stage = (Stage) btnLogout1.getScene().getWindow();
        stage.close();

        // Create and show the login stage
        stage.setTitle("National Property Sales System - Login");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/login.fxml")), 600, 400));
        stage.setResizable(false);
        stage.show();
    }

    public void init() {
        String username = getSession().getUserAdmin().getUsername();
        String welcomeMsg = "Welcome, " + username.substring(0, 1).toUpperCase() + username.substring(1);
        txtWelcomeMsg1.setText(welcomeMsg);
        txtWelcomeMsg2.setText(welcomeMsg);

        if (!getSession().isAdmin()) {
            tabPaneDashboard.getSelectionModel().selectNext();
            tabPaneDashboard.getTabs().remove(0);
        } else {
            populateBranches();
        }

        populateProperties();
    }

    private void populateProperties() {
        populateProperties("");
    }

    private void populateProperties(String query) {

    }

    public void searchProperties() {
        populateProperties(tfPropertySearch.getText());
    }

    public void createProperty() {}

    public void updateProperty() {}

    public void deleteProperty() {}

    private void populateBranches() {
        populateBranches("");
    }

    private void populateBranches(String query) {
        // Only populate branches if user is the administrator
        if (!getSession().isAdmin()) {
            return;
        }

        // Clear the table before populating it (refresh functionality)
        tableViewBranch.getItems().clear();

        ObservableList<Branch> rows = FXCollections.observableArrayList();
        tabColBranchName.setCellValueFactory(new PropertyValueFactory<Branch, String>("name"));
        tabColBranchAddress.setCellValueFactory(new PropertyValueFactory<Branch, String>("address"));
        tabColBranchEmail.setCellValueFactory(new PropertyValueFactory<Branch, String>("email"));
        tabColBranchWeb.setCellValueFactory(new PropertyValueFactory<Branch, String>("website"));
        tabColBranchTel.setCellValueFactory(new PropertyValueFactory<Branch, Long>("tel"));

        for (IModel model : dataManager.readAll()) {
            // Ignore models that aren't branches
            if (!(model instanceof Branch)) {
                continue;
            }

            // Cast the model to Branch
            Branch branch = (Branch) model;

            // If a search query isn't provided add all results otherwise filter respectively
            if (query.trim().length() <= 0) {
                rows.add(branch);
            } else {
                // Search by name or address
                // TODO: Improve search so you can specify which field you want to search by
                if (branch.getName().toLowerCase().contains(query.trim().toLowerCase()) ||
                branch.getAddress().toLowerCase().contains(query.trim().toLowerCase())) {
                    rows.add(branch);
                }
            }
        }

        tableViewBranch.setItems(rows);
        tableViewBranch.setEditable(false);
    }

    public void searchBranches() {
        populateBranches(tfBranchSearch.getText());
    }

    public void createBranch() {}

    public void updateBranch() {}

    public void deleteBranch() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.NO);

        Branch selectedBranch = (Branch) tableViewBranch.getSelectionModel().getSelectedItems().get(0);

        alert.setTitle("Confirm Action");
        alert.setHeaderText("Danger! You are about to remove a branch and all of its data. This is an irreversible action.");
        alert.setContentText("Are you sure you want to delete " + selectedBranch.getName() + "?");
        alert.getButtonTypes().setAll(btnYes, btnNo);

        if (alert.showAndWait().get() == btnYes) {
            dataManager.delete(selectedBranch);
            populateBranches();
        }
    }
}
