package Desktop;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Pos;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Model.Data.*;
import Model.Objects.*;
import javafx.util.Pair;

import javax.swing.text.html.ImageView;

public class Controller {

    private BorderPane topdownViewPane;
    private BorderPane sideViewPane;

    @FXML
    private AnchorPane anchorPane;
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
    private ComboBox addObstacleButton;
    @FXML
    private VBox root;

    /**
     * @currentAirport the currently imported airport
     * @obstacles list of currently available (predefined + imported) obstacles
     * @aircrafts list of currently available (predefined + imported) aircraft
     * @currentRunway currently viewed runway
     */

    private Airport currentAirport;
    private List<Obstacle> obstacles = new ArrayList<>();
    private List<Aircraft> aircrafts = new ArrayList<>();
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
            currentAirport = XMLImport.importAirportXML(file);

            if (currentAirport != null)
                System.out.println("Airport " + currentAirport.getAirportName() + " loaded successfully");
            setupRunwayComboBox();
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
            obstacles = XMLImport.importObstaclesXML(file);

            if (obstacles != null)
                System.out.println(obstacles.size() + " obstacles loaded successfully");
            setupObstaclesComboBox();
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
            aircrafts = XMLImport.importAircraftXML(file);

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

        if(currentRunway == null) {
            showPopupMessage("No runway is currently chosen", Alert.AlertType.ERROR);
            return;
        }

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
        /*
        if(currentRunway == null) {
            showPopupMessage("Please select a runway first", Alert.AlertType.ERROR);
            return;
        }

        int obstacleIndex = addObstacleButton.getSelectionModel().getSelectedIndex();

        Obstacle obstacle = obstacles.get(obstacleIndex);

        try {
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Add an obstacle");

            ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField leftThreshold = new TextField();
            leftThreshold.setPromptText("Left Threshold");
            TextField rightThreshold  = new TextField();
            rightThreshold.setPromptText("Right threshold");

            grid.add(new Label("Left threshold :"), 0, 0);
            grid.add(leftThreshold, 1, 0);
            grid.add(new Label("Right threshold :"), 0, 1);
            grid.add(rightThreshold, 1, 1);

            Node okayButton = dialog.getDialogPane().lookupButton(okButtonType);
            okayButton.setDisable(true);

            leftThreshold.textProperty().addListener((observable, oldValue, newValue) -> {
                okayButton.setDisable(newValue.trim().isEmpty());
            });

            dialog.getDialogPane().setContent(grid);

            Platform.runLater(() -> leftThreshold.requestFocus());

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == okButtonType) {
                    return new Pair<>(leftThreshold.getText(), rightThreshold.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = dialog.showAndWait();

            result.ifPresent(e -> {

                int leftThresholdVal = Integer.parseInt(e.getKey());
                int rightThresholdVal = Integer.parseInt(e.getValue());

                obstacle.setLeftThreshold(leftThresholdVal);
                obstacle.setRightThreshold(rightThresholdVal);
                obstacle.setObstacleRunway(currentRunway);

                currentRunway.addObstacle(obstacle);

            });
        } catch (Exception e) {

        }

        System.out.println("Added obstacle " + obstacle.getName() + " to " + currentRunway.getRunwayName());
        System.out.println("Obstacles on " + currentRunway.getRunwayName() + " " + currentRunway.getObstacles().size());


*/
    }

    public void changeRunwayButtonClicked() {


        System.out.println("Selected runway " + changeRunwaysMenu.getSelectionModel().getSelectedItem());

        int runwayIndex = changeRunwaysMenu.getSelectionModel().getSelectedIndex();

        this.setCurrentRunway(this.currentAirport.getAirportRunways().get(runwayIndex));

        boolean sideViewVis = false;
        boolean topDownVis = true;

        if(currentRunway != null) {

            if(topdownViewPane != null) {
                topDownVis = topdownViewPane.isVisible();
                anchorPane.getChildren().remove(topdownViewPane);
            }

            if(sideViewPane != null) {
                sideViewVis = sideViewPane.isVisible();
                anchorPane.getChildren().remove(sideViewPane);
            }

            TopDownView topDown = new TopDownView();
            SideOnView sideOn = new SideOnView();

            try {
                topdownViewPane = topDown.setUpSideOnView(currentRunway);
                sideViewPane = sideOn.setUpSideOnView(currentRunway);

                topdownViewPane.setVisible(topDownVis);
                sideViewPane.setVisible(sideViewVis);

                anchorPane.getChildren().add(topdownViewPane);
                anchorPane.getChildren().add(sideViewPane);

                System.out.println("Runway GUI set up");
            } catch (Exception e) {
                showPopupMessage("Error setting up the runway visualisation ", Alert.AlertType.ERROR);
            }

        }
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

    public void showCalculationsButtonClicked() throws Exception {
        Calculator calc = new Calculator();

        if(!currentRunway.getAlreadyCalculated())
        {
            calc.calculate(obstacles.get(changeRunwaysMenu.getSelectionModel().getSelectedIndex()), currentRunway);

            Stage popupwindow=new Stage();
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.setTitle("Calculation Breakdown");
            Label label1= new Label(calc.getCalculationBreakdown());
            Button button1= new Button("It matches!");
            button1.setOnAction(e -> popupwindow.close());
            VBox layout= new VBox(10);
            layout.getChildren().addAll(label1, button1);
            layout.setAlignment(Pos.CENTER);
            Scene scene1= new Scene(layout, 600, 250);
            popupwindow.setScene(scene1);
            popupwindow.showAndWait();

            boolean sideViewVis = false;
            boolean topDownVis = true;

            if(currentRunway != null) {

                if (topdownViewPane != null) {
                    topDownVis = topdownViewPane.isVisible();
                    anchorPane.getChildren().remove(topdownViewPane);
                }

                if (sideViewPane != null) {
                    sideViewVis = sideViewPane.isVisible();
                    anchorPane.getChildren().remove(sideViewPane);
                }

                TopDownView topDown = new TopDownView();
                SideOnView sideOn = new SideOnView();

                try {
                    topdownViewPane = topDown.setUpSideOnView(currentRunway);
                    sideViewPane = sideOn.setUpSideOnView(currentRunway);

                    topdownViewPane.setVisible(topDownVis);
                    sideViewPane.setVisible(sideViewVis);

                    anchorPane.getChildren().add(topdownViewPane);
                    anchorPane.getChildren().add(sideViewPane);

                    System.out.println("Runway GUI set up");
                } catch (Exception e) {
                    showPopupMessage("Error setting up the runway visualisation ", Alert.AlertType.ERROR);
                }
                currentRunway.calculationsMade();
            }
        }
        else {
            Stage popupwindow=new Stage();
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.setTitle("Already re-declared!");
            Label label1= new Label("Would you like to revert to previous values?");
            Button button1= new Button("Yes!");
            button1.setOnAction(e -> {
                popupwindow.close();
                calc.setOGValues(currentRunway);
                currentRunway.calculationsReverted();
                /////////////
                /////////////
                /////////////
            });
            VBox layout= new VBox(10);
            layout.getChildren().addAll(label1, button1);
            layout.setAlignment(Pos.CENTER);
            Scene scene1= new Scene(layout, 600, 250);
            popupwindow.setScene(scene1);
            popupwindow.showAndWait();
        }


        //showPopupMessage(calc.getCalculationBreakdown(), Alert.AlertType.ERROR);

        /*

        TopDownView topDown = new TopDownView();
        SideOnView sideOn = new SideOnView();

        try {
            topdownViewPane = topDown.setUpSideOnView(currentRunway);
            sideViewPane = sideOn.setUpSideOnView(currentRunway);
            System.out.println("Runway Re-declared");
        } catch (Exception e) {
            showPopupMessage("Error setting up the runway re-declaration ", Alert.AlertType.ERROR);
        }

         */
    }

    private void showPopupMessage(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setupRunwayComboBox() {

        changeRunwaysMenu.getItems().clear();

        if(this.currentAirport != null) {
            for(int i = 0; i < currentAirport.getAirportRunways().size(); i++) {
                Runway runway = currentAirport.getAirportRunways().get(i);
                //int runwayNum = runway.getRunwayNumber();
                changeRunwaysMenu.getItems().add(runway.getRunwayName());
            }
        }

    }

    public void setupObstaclesComboBox() {

        addObstacleButton.getItems().clear();

        if(this.obstacles != null) {
            for(int i = 0; i < obstacles.size(); i++) {
                Obstacle obstacle = obstacles.get(i);
                addObstacleButton.getItems().add(obstacle.getName() + " " +
                        String.valueOf(obstacle.getObstacleHeight()) + " x " +
                        String.valueOf(obstacle.getObstacleLength()));
            }
        }

    }

    public void reset() {

        changeRunwaysMenu.getItems().clear();
        addObstacleButton.getItems().clear();

        this.currentRunway = null;
        this.currentAirport = null;
        this.obstacles = null;
        this.aircrafts = null;

        if(topdownViewPane != null)
            anchorPane.getChildren().remove(topdownViewPane);

        if(sideViewPane != null)
            anchorPane.getChildren().remove(sideViewPane);

    }
}
