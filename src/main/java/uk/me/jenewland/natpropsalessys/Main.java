package uk.me.jenewland.natpropsalessys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("./view/main.fxml"));
        primaryStage.setTitle("National Property Sales System");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        // DEVELOPER CODE ONLY
        // ===================
        // Used in development to generate admin.dat file that stores the default admin
        // account details for the application.
        // NatPropSalesSys.generateAdminFile();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
