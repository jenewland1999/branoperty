package uk.me.jenewland.natpropsalessys.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uk.me.jenewland.natpropsalessys.model.Branch;
import uk.me.jenewland.natpropsalessys.model.IModel;
import uk.me.jenewland.natpropsalessys.model.Session;
import uk.me.jenewland.natpropsalessys.model.property.Property;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static uk.me.jenewland.natpropsalessys.Main.dataManager;
import static uk.me.jenewland.natpropsalessys.NatPropSalesSys.LOGGER;

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
    private Button btnLogout1, btnLogout2, btnBranchSearch, btnBranchCreate, btnBranchEdit, btnBranchDelete, btnPropertySearch, btnPropertyCreate, btnPropertyEdit, btnPropertyDelete;

    @FXML
    private TextField tfBranchSearch, tfPropertySearch;

    private Set<Property> properties = new HashSet<>();

    private final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(
            new Locale("en", "GB")
    );

    public void logout() throws IOException {
        // Close the current stage
        Stage stage;

        // If the user isn't an admin...
        if (!getSession().isAdmin()) {
            // Retrieve the existing branch on disk
            Branch b = (Branch) dataManager.read(getSession().getBranch().getName());
            // we must update the branch on disk with changes to its properties
            dataManager.update(b, getSession().getBranch());
        }

        try {
            stage = (Stage) btnLogout1.getScene().getWindow();
        } catch (NullPointerException e) {
            stage = (Stage) btnLogout2.getScene().getWindow();
        }

        if (stage == null) {
            LOGGER.log(Level.SEVERE, "Unable to logout... Please close the application.");
            return;
        }

        stage.close();

        // Create and show the login stage
        stage.setTitle("National Property Sales System - Login");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/login.fxml")), 600, 400));
        stage.setResizable(false);
        stage.show();
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
        List<Property> results = new ArrayList<>();

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

        for (TableColumn<Property, Long> col : cols) {
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
        }
    }

    public void searchProperties() {
        populateProperties(tfPropertySearch.getText(), Property.TYPES.NULL, -2);
    }

    public void createProperty() throws IOException {
        openModal(
                "../views/createProperty.fxml",
                String.format("National Property Sales System - %s Dashboard - Create Property",
                        getSession().isAdmin() ? "Admin" : "Secretary"
                        ));
    }

    public void updateProperty() throws IOException {
        openModal(
                "../views/updateProperty.fxml",
                String.format("National Property Sales System - %s Dashboard - Update Property",
                        getSession().isAdmin() ? "Admin" : "Secretary"
                ));
    }

    public void deleteProperty() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.NO);

        Property selectedProperty = (Property) tableViewProperty.getSelectionModel().getSelectedItems().get(0);

        alert.setTitle("Confirm Action");
        alert.setHeaderText("Danger! You are about to remove a property and all of its data. This is an irreversible action.");
        alert.setContentText("Are you sure you want to delete " + selectedProperty.getAddress() + "?");
        alert.getButtonTypes().setAll(btnYes, btnNo);

        if (alert.showAndWait().get() == btnYes) {
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
        FXMLLoader loader = openModal(
                "../view/createBranch.fxml",
                "National Property Sales System - Admin Dashboard - Create Branch",
                400,
                600
        );

        ((CreateBranchController) loader.getController()).setDashboardController(this);
    }

    public void updateBranch() throws IOException {
        FXMLLoader loader = openModal(
                "../view/updateBranch.fxml",
                "National Property Sales System - Admin Dashboard - Update Branch",
                400,
                600
        );
        UpdateBranchController controller = (UpdateBranchController) loader.getController();

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

        if (alert.showAndWait().get() == btnYes) {
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

    private FXMLLoader openModal(String viewPath, String title) throws IOException {
        return openModal(viewPath, title, 1024, 768);
    }

    private FXMLLoader openModal(String viewPath, String title, double width, double height) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load(), width, height));
        stage.setTitle(title);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        return loader;
    }
}
