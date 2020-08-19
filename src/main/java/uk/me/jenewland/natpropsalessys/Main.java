package uk.me.jenewland.natpropsalessys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uk.me.jenewland.natpropsalessys.model.Branch;
import uk.me.jenewland.natpropsalessys.model.property.Property;
import uk.me.jenewland.natpropsalessys.model.user.UserAdmin;
import uk.me.jenewland.natpropsalessys.utils.DataManager;
import uk.me.jenewland.natpropsalessys.utils.FileHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class Main extends Application {
    // Declare the admin account details and the file they're stored in through
    // serialization.
    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "password";
    public static final String ADMIN_FILENAME = "admin.dat";

    // Application data manager for managing branches/properties
    public static DataManager dataManager;

    // Declare and instantiate logger for logging purposes
    public static Logger logger = Logger.getLogger(Main.class.getName());

    /*
        This method is called by JavaFX library which initialises the
        application and it's GUI. Here we're setting the window size,
        title and view (FXML file) then displaying it.

        In addition to this there is some developer-only code used to
        generate the admin's login details which is bundled in the
        jar file when the application is built.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("National Property Sales System - Login");
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/login.fxml")), 600, 400));
        primaryStage.setResizable(false);
        primaryStage.show();

        // DEVELOPER CODE ONLY
        // ===================
        // Used in development to generate admin.dat file that stores the default admin
        // account details for the application.
        // NatPropSalesSys.generateAdminFile();
    }

    // Main method - runs when the JVM is started
    public static void main(String[] args) throws IOException {
        // Create the branches directory if it doesn't already exist
        Files.createDirectories(Paths.get("branches"));

        // Instantiate the data controller for the app
        dataManager = new DataManager("branches");

        // Seed data (for demo purposes)
        List<Branch> branches = new ArrayList<>();

        branches.add(new Branch("weymouth", "password", "Weymouth, Dorset, GB", "weymouth@domain.tld", "domain.tld/branches/weymouth", "07700900461"));
        branches.add(new Branch("dorchester", "password", "Dorchester, Dorset, GB", "dorchester@domain.tld", "domain.tld/branches/dorchester", "07700900461"));
        branches.add(new Branch("poole", "password", "Poole, Dorset, GB", "poole@domain.tld", "domain.tld/branches/poole", "07700900461"));
        branches.add(new Branch("bournemouth", "password", "Bournemouth, Dorset, GB", "bournemouth@domain.tld", "domain.tld/branches/bournemouth", "07700900461"));

        List<Property> properties = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            for (int ii = 0; ii < 4; ii++) {
                properties.add(new Property(branches.get(ii), "B: " + ii + " P: " + i, 0, 0L, 0L, ii % 2 == 0 ? Property.TYPES.HOUSE : Property.TYPES.FLAT));
            }
        }

        for (Branch b : branches) {
            for (Property p : properties) {
                if (p.getBranch() == b) {
                    b.addProperty(p);
                }
            }
            dataManager.create(b);
        }

        // Launch the application
        launch(args);
    }

    /**
     * Generate the admin login file using the FileHandler utility class.
     */
    public static void setAdminFile() {
        FileHandler.writeObjsToFile(ADMIN_FILENAME, new UserAdmin(ADMIN_USERNAME, ADMIN_PASSWORD));
    }

    /**
     * Retrieve the admin login file from resources.
     *
     * @return the URL of the admin login file.
     */
    public static String getAdminFile() {
        return Objects.requireNonNull(Main.class.getClassLoader().getResource(ADMIN_FILENAME)).getFile();
    }

    /**
     * Creates and displays a new fixed-size view (window) with the dimensions
     * of 1024x768 (wxh).
     *
     * @param viewPath the relative filepath (inc. extension) to fxml view.
     * @param title the window title to be displayed.
     * @param isModal whether or not to display the window as a modal.
     * @return returns the {@code FXMLLoader} instance for the newly opened gui.
     * @throws IOException
     */
    public static FXMLLoader openGui(String viewPath, String title, boolean isModal) throws IOException {
        return openGui(viewPath, title, 1024, 768, isModal);
    }

    /**
     * Creates and displays a new fixed-size view (window) with the specified
     * dimensions.
     *
     * @param viewPath the relative filepath (inc. extension) to fxml view.
     * @param title the window title to be displayed.
     * @param width the width (px) of the window.
     * @param height the height (px) of the window.
     * @param isModal whether or not to display the window as a modal.
     * @return returns the {@code FXMLLoader} instance for the newly opened gui.
     * @throws IOException
     */
    public static FXMLLoader openGui(String viewPath, String title, double width, double height, boolean isModal) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(viewPath));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load(), width, height));
        stage.setTitle(title);
        stage.setResizable(false);
        if (isModal) {
            stage.initModality(Modality.APPLICATION_MODAL);
        }
        stage.show();

        return loader;
    }
}
