package Desktop;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = fxmlLoader.load();

        Controller controller = fxmlLoader.getController();
        controller.setupObstaclesComboBox();
        controller.setupRunwayComboBox();
        controller.setupPredefinedObstacles();

        primaryStage.setTitle("Runway Re-Declaration Tool");
        primaryStage.setResizable(false);
        Scene scene = new Scene(root);
        //scene.getStylesheets().add("darktheme.css");
        controller.setScene(scene);
        primaryStage.setScene(scene);
        primaryStage.show();


        /*try {
            Thread.sleep(10000);
        } catch (Exception e) {}

        scene.getStylesheets().remove("darktheme.css");*/

    }


    public static void main(String[] args) {
        launch(args);
    }
}


/*
 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Settings.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
 */