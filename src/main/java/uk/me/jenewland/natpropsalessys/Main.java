package uk.me.jenewland.natpropsalessys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.me.jenewland.natpropsalessys.controller.BranchDataController;
import uk.me.jenewland.natpropsalessys.model.Branch;
import uk.me.jenewland.natpropsalessys.model.IModel;

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

        BranchDataController branchDataController = new BranchDataController(Paths.get("repositories" + File.separator + "branches"));
        branchDataController.create(new Branch("weymouth", "password", "", "", "", 7802684152L));

        Branch model = (Branch) branchDataController.read("weymouth");
//        System.out.println(model.getTel());

        branchDataController.delete(model);

        // Launch the application
//        launch(args);
    }
}
