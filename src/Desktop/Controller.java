package Desktop;
import javafx.fxml.FXML;
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
import java.util.List;

import Model.Data.*;
import Model.Objects.*;
import javafx.stage.StageStyle;

public class Controller {

    @FXML
    private GridPane topdownViewPane;
    @FXML
    private GridPane sideViewPane;
    @FXML
    private MenuItem importAirportButton;
    @FXML
    private MenuItem importObstaclesButton;
    @FXML
    private MenuItem importAircraftButton;
    @FXML
    private MenuItem exportMenuButton;
    @FXML
    private MenuItem settingsMenuButton;
    @FXML
    private MenuItem quitMenuButton;
    @FXML
    private ComboBox changeRunwaysMenu;
    @FXML
    private VBox root;

    /**
     * @currentAirport the currently imported airport
     * @obstacles list of currently available (predefined + imported) obstacles
     * @aircrafts list of currently available (predefined + imported) aircraft
     * @currentRunway currently viewed runway
     */

    private Airport currentAirport;
    private List<Obstacle> obstacles;
    private List<Aircraft> aircrafts;
    private Runway currentRunway;


    public void setCurrentRunway(Runway currentRunway) {
        this.currentRunway = currentRunway;
    }

    public void importAirportButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        System.out.println("Opened " + file);
        try {
            currentAirport = Model.Data.DataPersistance.importAirportXML(file);

            if (currentAirport != null)
                System.out.println("Airport " + currentAirport.getAirportName() + " loaded successfully");
            setupComboBox();
        } catch (Exception e ) {
            e.printStackTrace();
            if(file != null)
                showPopupMessage(file + " is not a valid airport xml file", Alert.AlertType.ERROR);
        }
    }

    public void importObstaclesButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        System.out.println("Opened " + file);
        try {
            obstacles = Model.Data.DataPersistance.importObstaclesXML(file);

            if (obstacles != null)
                System.out.println(obstacles.size() + " obstacles loaded successfully");
        }  catch (Exception e ) {
            e.printStackTrace();
            if(file != null)
                showPopupMessage(file + " is not a valid obstacles xml file", Alert.AlertType.ERROR);
        }

    }

    public void importAircraftButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        System.out.println("Opened " + file);
        try {
            aircrafts = Model.Data.DataPersistance.importAircraftXML(file);

            if (aircrafts != null)
                System.out.println(aircrafts.size() + " aircraft loaded successfully");
        }  catch (Exception e ) {
            e.printStackTrace();
            if(file != null)
                showPopupMessage(file + " is not a valid aircraft xml file", Alert.AlertType.ERROR);
        }
    }

    public void exportMenuButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(root.getScene().getWindow());

        if(file != null && currentAirport != null)
            if(Model.Data.DataPrint.exportData(file, currentAirport))
                System.out.println("Saving to " + file + "...");
    }

    public void settingsMenuButtonClicked()  throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Settings.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Settings");
        stage.setScene(new Scene(root1));
        stage.show();
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
        System.out.println("Selected runway " + changeRunwaysMenu.getSelectionModel().getSelectedItem());

        int runwayIndex = changeRunwaysMenu.getSelectionModel().getSelectedIndex();

        this.setCurrentRunway(this.currentAirport.getAirportRunways().get(runwayIndex));
    }

    public void showDetailsButtonClicked() throws Exception {

        if(currentRunway == null)
            showPopupMessage("No runway chosen currently", Alert.AlertType.ERROR);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RunwayDetails.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        RunwayDetails controller = (RunwayDetails)fxmlLoader.getController();
        controller.initData(currentRunway);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Current Runway");
        stage.setScene(new Scene(root1));
        stage.show();

    }

    public void showAirportDetailsButtonClicked()  {

        try {
            System.out.println("Current airport is " + currentAirport.getAirportName());
            System.out.println("Runways : " + currentAirport.getAirportRunways().size());

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AirportDetails.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            AirportDetails controller = (AirportDetails)fxmlLoader.getController();
            controller.initData(currentAirport.getAirportName(), currentAirport.getAirportRunways().size());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("Airport Details");
            stage.setScene(new Scene(root1));

            stage.showAndWait();
        } catch (Exception e) {
            if(currentAirport == null)
                showPopupMessage("No airport currently imported ", Alert.AlertType.ERROR);
            else
                showPopupMessage("There was an error ", Alert.AlertType.ERROR);

        }

    }

    public void showCalculationsButtonClicked() {

    }

    private void showPopupMessage(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setupComboBox() {

        if(this.currentAirport == null) {
            return;
        }

        changeRunwaysMenu.getItems().clear();

        for(int i = 0; i < currentAirport.getAirportRunways().size(); i++) {
            Runway runway = currentAirport.getAirportRunways().get(i);
            int runwayNum = runway.getRunwayNumber();
            changeRunwaysMenu.getItems().add(String.valueOf(runwayNum));
        }


    }
}
