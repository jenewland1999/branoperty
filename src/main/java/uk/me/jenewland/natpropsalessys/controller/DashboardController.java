package uk.me.jenewland.natpropsalessys.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import uk.me.jenewland.natpropsalessys.model.Branch;
import uk.me.jenewland.natpropsalessys.model.IModel;
import uk.me.jenewland.natpropsalessys.model.Session;
import uk.me.jenewland.natpropsalessys.model.property.Property;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static uk.me.jenewland.natpropsalessys.Main.dataManager;
import static uk.me.jenewland.natpropsalessys.Main.openGui;

public class DashboardController {
    private Session session;
    private final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("en", "GB"));

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return this.session;
    }

    @FXML
    private TabPane tabPaneDashboard;

    @FXML
    private Text txtWelcomeMsg1, txtWelcomeMsg2;

    @FXML
    private TableView<Branch> tableViewBranch;

    @FXML
    private TableView<Property> tableViewProperty;

    @FXML
    private TableColumn<Branch, String> tabColBranchName;

    @FXML
    private TableColumn<Branch, String> tabColBranchAddress;

    @FXML
    private TableColumn<Branch, String> tabColBranchEmail;

    @FXML
    private TableColumn<Branch, String> tabColBranchWeb;

    @FXML
    private TableColumn<Branch, String> tabColBranchTel;

    @FXML
    private TableColumn<Property, Branch> tabColPropertyBranch;

    @FXML
    private TableColumn<Property, String> tabColPropertyAddress;

    @FXML
    private TableColumn<Property, Integer> tabColPropertyRooms;

    @FXML
    private TableColumn<Property, Long> tabColPropertySellPrice;

    @FXML
    private TableColumn<Property, Long> tabColPropertySoldPrice;

    @FXML
    private TableColumn<Property, Enum<Property.TYPES>> tabColPropertyType;

    @FXML
    private TextField tfBranchSearch, tfPropertySearch;

    public void logout() throws IOException {
        // If the user isn't an admin we need to save the changes made to disk
        if (!getSession().isAdmin()) {
            dataManager.update(dataManager.read(getSession().getBranch().getName()), getSession().getBranch());
        }

        // Open the login GUI
        openGui(
                "./view/login.fxml",
                "National Property Sales System - Login",
                600, 400, false
        );

        // Then close the dashboard
        tabPaneDashboard.getScene().getWindow().hide();
    }

    public void init() {
        String username = getSession().getUsername();
        String welcomeMsg = "Currently logged in as: " + username.substring(0, 1).toUpperCase() + username.substring(1);
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
        populateProperties("", Property.TYPES.NULL, -2);
    }

    private void populateProperties(String query, Property.TYPES type, long soldPrice) {
        // Clear the table before populating it (refresh functionality)
        tableViewProperty.getItems().clear();

        ObservableList<Property> rows = FXCollections.observableArrayList();
        tabColPropertyBranch.setCellValueFactory(new PropertyValueFactory<Property, Branch>("branch"));
        tabColPropertyAddress.setCellValueFactory(new PropertyValueFactory<Property, String>("address"));
        tabColPropertyRooms.setCellValueFactory(new PropertyValueFactory<Property, Integer>("noOfRooms"));
        tabColPropertySellPrice.setCellValueFactory(new PropertyValueFactory<Property, Long>("sellingPrice"));
        tabColPropertySoldPrice.setCellValueFactory(new PropertyValueFactory<Property, Long>("soldPrice"));
        tabColPropertyType.setCellValueFactory(new PropertyValueFactory<Property, Enum<Property.TYPES>>("type"));

        List<Property> properties = new ArrayList<>();

        if (getSession().isAdmin()) {
            for (IModel model : dataManager.readAll()) {
                if (!(model instanceof Branch)) {
                    continue;
                }

                properties.addAll(((Branch) model).getProperties());
            }
        } else {
            properties.addAll(getSession().getBranch().getProperties());
        }

        if (!query.isEmpty()) {
            properties = properties
                    .stream()
                    .filter(row -> row.getAddress().toLowerCase().contains(query.trim().toLowerCase()) ||
                            row.getBranch().getName().toLowerCase().contains(query.trim().toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (type != Property.TYPES.NULL) {
            properties = properties
                    .stream()
                    .filter(row -> row.getType() == type)
                    .collect(Collectors.toList());
        }

        if (soldPrice == -1) {
            properties = properties
                    .stream()
                    .filter(row -> !row.isSold())
                    .collect(Collectors.toList());
        }

        if (soldPrice >= 0) {
            properties = properties
                    .stream()
                    .filter(row -> row.getSoldPrice() == soldPrice)
                    .collect(Collectors.toList());
        }

        rows.addAll(properties);

        tableViewProperty.setItems(rows);
        tableViewProperty.setEditable(false);

        List<TableColumn<Property, Long>> cols = new ArrayList<>();
        cols.add(tabColPropertySellPrice);
        cols.add(tabColPropertySoldPrice);
        cols.forEach(col -> {
            col.setCellFactory(c -> new TableCell<>() {
                @Override
                protected void updateItem(Long item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(numberFormat.format(item));
                    }
                }
            });
        });
    }

    public void searchProperties() {
        populateProperties(tfPropertySearch.getText(), Property.TYPES.NULL, -2);
    }

    public void createProperty() throws IOException {
        openGui(
                "./views/createProperty.fxml",
                String.format("National Property Sales System - %s Dashboard - Create Property",
                        getSession().isAdmin() ? "Admin" : "Secretary"
                ), true);
    }

    public void updateProperty() throws IOException {
        openGui(
                "./views/updateProperty.fxml",
                String.format("National Property Sales System - %s Dashboard - Update Property",
                        getSession().isAdmin() ? "Admin" : "Secretary"
                ), true);
    }

    public void deleteProperty() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.NO);

        Property selectedProperty = (Property) tableViewProperty.getSelectionModel().getSelectedItem();

        alert.setTitle("Confirm Action");
        alert.setHeaderText("Danger! You are about to remove a property and all of its data. This is an irreversible action.");
        alert.setContentText("Are you sure you want to delete " + selectedProperty.getAddress() + "?");
        alert.getButtonTypes().setAll(btnYes, btnNo);

        if (alert.showAndWait().isPresent() && alert.showAndWait().get() == btnYes) {
            // If user is not an admin...
            if (!getSession().isAdmin()) {
                // Remove the branch from the session
                getSession().getBranch().deleteProperty(selectedProperty);
            } else {
                Branch b = selectedProperty.getBranch();

                // remove the property in question
                b.deleteProperty(selectedProperty);

                // update the branch on disk
                dataManager.update((Branch) dataManager.read(b.getName()), b);
            }

            // Repopulate the table view after deleting the property
            populateProperties();
        }
    }

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
        tabColBranchTel.setCellValueFactory(new PropertyValueFactory<Branch, String>("tel"));

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
        tableViewBranch.getSortOrder().add(tabColBranchName);

        tabColBranchName.setCellFactory(c -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item.substring(0, 1).toUpperCase() + item.substring(1));
                }
            }
        });

        // TODO: Format phone number column (Ask Chris/Nick for RegEx solution)
    }

    public void searchBranches() {
        populateBranches(tfBranchSearch.getText());
    }

    public void createBranch() throws IOException {
        FXMLLoader loader = openGui(
                "./view/createBranch.fxml",
                "National Property Sales System - Admin Dashboard - Create Branch",
                400,
                600,
                true
        );

        ((CreateBranchController) loader.getController()).setDashboardController(this);
    }

    public void updateBranch() throws IOException {
        FXMLLoader loader = openGui(
                "./view/updateBranch.fxml",
                "National Property Sales System - Admin Dashboard - Update Branch",
                400,
                600,
                true
        );


        UpdateBranchController controller = loader.getController();

        controller.setDashboardController(this);
        controller.setBranch((Branch) tableViewBranch.getSelectionModel().getSelectedItem());
        controller.populateFields();

    }

    public void deleteBranch() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.NO);

        Branch selectedBranch = (Branch) tableViewBranch.getSelectionModel().getSelectedItems().get(0);

        alert.setTitle("Confirm Action");
        alert.setHeaderText("Danger! You are about to remove a branch and all of its data. This is an irreversible action.");
        alert.setContentText("Are you sure you want to delete " + selectedBranch.getName() + "?");
        alert.getButtonTypes().setAll(btnYes, btnNo);

        if (alert.showAndWait().isPresent() && alert.showAndWait().get() == btnYes) {
            dataManager.delete(selectedBranch);
            populateBranches();
        }
    }

    public void refresh() {
        if (getSession() != null) {
            populateProperties();
            populateBranches();
        }
    }


}
