package uk.me.jenewland.natpropsalessys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.me.jenewland.natpropsalessys.model.Branch;
import uk.me.jenewland.natpropsalessys.model.property.Property;
import uk.me.jenewland.natpropsalessys.utils.DataManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
}
