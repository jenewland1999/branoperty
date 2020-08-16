package uk.me.jenewland.natpropsalessys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.me.jenewland.natpropsalessys.utils.DataManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
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

    public static DataManager dataManager;

    public static void main(String[] args) throws IOException {
        // Create the branches directory if it doesn't already exist
        Files.createDirectories(Paths.get("branches"));

        // Instantiate the data controller for the app
        dataManager = new DataManager("branches");

        // Launch the application
        launch(args);
    }
}
