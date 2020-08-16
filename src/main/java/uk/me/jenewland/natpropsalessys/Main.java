package uk.me.jenewland.natpropsalessys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import uk.me.jenewland.natpropsalessys.controller.DataController;
import uk.me.jenewland.natpropsalessys.model.Branch;
import uk.me.jenewland.natpropsalessys.model.property.Property;
import uk.me.jenewland.natpropsalessys.model.property.PropertyFlat;
import uk.me.jenewland.natpropsalessys.model.property.PropertyHouse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
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

    public static void main(String[] args) throws IOException {
        // Create the branches directory if it doesn't already exist
        Files.createDirectories(Paths.get("repositories"));

        DataController dataController = new DataController("repositories");

        dataController.create(new Branch("weymouth", "password", "Bond Street, Weymouth, DT3 5A2", "weymouth@npss.com", "https://npss.com/branches/weymouth", 7802684152L, new HashSet<Property>()));
        dataController.create(new Branch("dorchester", "password", "Weymouth Way, Dorchester, DT1 2CT", "dorchester@npss.com", "https://npss.com/branches/dorchester", 7795623641L, new HashSet<Property>()));
        dataController.create(new Branch("bridport", "password", "Cockell Close, Bridport, BP2 2DE", "bridport@npss.com", "https://npss.com/branches/bridport", 7962351458L, new HashSet<Property>()));
        dataController.create(new Branch("poole", "password", "Ball Lane, Poole, BH1 2TF", "poole@npss.com", "https://npss.com/branches/poole", 4125698547L, new HashSet<Property>()));
        dataController.create(new Branch("bournemouth", "password", "Oxford Close, Bournemouth, BH12 1AR", "bournemouth@npss.com", "https://npss.com/branches/bournemouth", 7745569851L, new HashSet<Property>()));

        Branch model = (Branch) dataController.read("weymouth");
        Branch model2 = (Branch) dataController.read("dorchester");

        Set<Property> properties = new HashSet<>();
        Set<Property> properties1 = new HashSet<>();

        properties.add(new PropertyHouse("14 Gentian Way, Weymouth, Dorset, DT3 6FH", 3, 250000, -1, 2, true, true));
        properties.add(new PropertyHouse("35 Pugmill Lane, Weymouth, Dorset, DT3 4PB", 3, 200000, -1, 2, true, true));
        properties1.add(new PropertyFlat("2 Denver Close, Dorchester, Dorset, DT1 5AY", 2, 230000, -1, 5, 600));

        model.setProperties(properties);
        model2.setProperties(properties1);

        model.printProperties();
        model2.printProperties();

        // save changes
        dataController.update(model, model);

        // Launch the application
        launch(args);
    }
}
