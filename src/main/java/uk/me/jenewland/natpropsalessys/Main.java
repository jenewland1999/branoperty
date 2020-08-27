package uk.me.jenewland.natpropsalessys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uk.me.jenewland.natpropsalessys.models.user.UserAdmin;
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
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("views/login.fxml")), 600, 400));
        primaryStage.setResizable(false);
        primaryStage.show();

        // DEVELOPER CODE ONLY
        // ===================
        // Used in development to generate admin.dat file that stores the default admin
        // account details for the application.
        // setAdminFile();
    }

    // Main method - runs when the JVM is started
    public static void main(String[] args) throws IOException {
        // Create the branches directory if it doesn't already exist
        Files.createDirectories(Paths.get("branches"));

        // Instantiate the data controller for the app
        dataManager = new DataManager("branches");

        // Seed data for demo purposes
        new Seed();

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
     * @param title    the window title to be displayed.
     * @param isModal  whether or not to display the window as a modal.
     * @return returns the {@code FXMLLoader} instance for the newly opened gui.
     * @throws IOException if getResource fails.
     */
    public static FXMLLoader openGui(String viewPath, String title, boolean isModal) throws IOException {
        return openGui(viewPath, title, 1024, 768, isModal);
    }

    /**
     * Creates and displays a new fixed-size view (window) with the specified
     * dimensions.
     *
     * @param viewPath the relative filepath (inc. extension) to fxml view.
     * @param title    the window title to be displayed.
     * @param width    the width (px) of the window.
     * @param height   the height (px) of the window.
     * @param isModal  whether or not to display the window as a modal.
     * @return returns the {@code FXMLLoader} instance for the newly opened gui.
     * @throws IOException if getResource fails.
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

    /**
     * Generate an alert for failed form submissions that accepts a list of the
     * errors to show to the user.
     *
     * @param errors the list of issues with the form.
     */
    public static void showInvalidFormAlert(List<String> errors) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        ButtonType btnOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

        StringBuilder stringBuilder = new StringBuilder();
        errors.forEach(error -> {
            stringBuilder.append(String.format("\t- %s%n", error));
        });

        alert.setTitle("Invalid Form Submission");
        alert.setHeaderText("Whoops! There appear to be some problems with your form.");
        alert.setContentText(String.format(
                "Please correct the following issue and re-attempt form submission: %n%n%s",
                stringBuilder.toString()
        ));
        alert.getButtonTypes().setAll(btnOk);
        alert.showAndWait();
    }

    public static void showInvalidFormAlert(String error) {
        List<String> errors = new ArrayList<>();
        errors.add(error);
        showInvalidFormAlert(errors);
    }
}
