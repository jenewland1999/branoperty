package uk.me.jenewland.natpropsalessys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("./view/login.fxml"));
        primaryStage.setTitle("National Property Sales System");
        primaryStage.setScene(new Scene(root, 600, 400));
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
        Files.createDirectories(Paths.get("repositories" + File.separator + "branches"));
        Files.createDirectories(Paths.get("repositories" + File.separator + "properties"));

        DataController branchDC = new DataController(Paths.get("repositories" + File.separator + "branches"));
        DataController propertyDC = new DataController(Paths.get("repositories" + File.separator + "properties"));
        branchDC.create(new Branch("weymouth", "password", "", "", "", 7802684152L));
        propertyDC.create(new PropertyHouse("14 Gentian Way, Weymouth, Dorset, DT3 6FH", 3, 250000, -1, 2, true, true));

        Branch model = (Branch) branchDC.read("weymouth");
        Property model2 = (PropertyHouse) propertyDC.read("14 Gentian Way, Weymouth, Dorset, DT3 6FH");
        System.out.println(model2.getAddress());
        System.out.println(model.getName());

        branchDC.delete(model);
        propertyDC.update(model2, new PropertyFlat("4 Radley Court", 2, 120000, 100000, 0, 750));
        model2 = (PropertyFlat) propertyDC.read("4 Radley Court");
        System.out.println(((PropertyFlat) model2).getMonthlyCharge());


        // Launch the application
        launch(args);
    }
}
