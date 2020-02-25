package Desktop;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Controller {

    public GridPane topdownViewPane;
    public GridPane sideViewPane;
    public MenuItem importMenuButton;
    public MenuItem exportMenuButton;
    public MenuItem settingsMenuButton;
    public MenuItem quitMenuButton;
    public VBox root;

    public void importMenuButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        System.out.println("Opened " + file);
    }

    public void exportMenuButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(root.getScene().getWindow());

        if (file != null) {
            try {
                PrintWriter writer;
                writer = new PrintWriter(file);
                writer.println("Testing.."); // Eventually there will be some meaningful stuff being written...
                System.out.println("Saving to " + file + "...");
                writer.close();
            } catch (IOException ex) { }
        }

    }

    public void settingsMenuButtonClicked() throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("settings.fxml"));
        Stage primaryStage = (Stage)root.getScene().getWindow();
        primaryStage.setTitle("Settings");
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(parent));
        primaryStage.showAndWait();
    }

    public void quitMenuButtonClicked() {
        ((Stage)root.getScene().getWindow()).close();
    }

    public void switchViewsButtonClicked() {
        System.out.println("Switching views...");

        if(topdownViewPane.isVisible()) {
            topdownViewPane.setVisible(false);
            sideViewPane.setVisible(true);
        } else {
            sideViewPane.setVisible(false);
            topdownViewPane.setVisible(true);
        }

    }

    public void addObstacleButtonClicked() {

    }

    public void changeRunwayButtonClicked() {

    }

    public void showDetailsButtonClicked() {

    }

    public void showCalculationsButtonClicked() {

    }
}
