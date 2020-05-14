package Desktop;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Pos;


import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

import Model.Data.*;
import Model.Objects.*;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import javax.print.DocFlavor;
import javax.swing.text.html.ImageView;

public class Controller {

    private BorderPane topdownViewPane;
    private BorderPane sideViewPane;

    @FXML
    private TextField rightThreshInput;
    @FXML
    private TextField leftThreshInput;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label airportName;
    @FXML
    private Label airportRunways;
    @FXML
    private ComboBox changeRunwaysMenu;
    @FXML
    private ComboBox addObstacleButton;
    @FXML
    private VBox root;
    @FXML
    private Label runwayNumber;
    @FXML
    private Label runwayPos;
    @FXML
    private Label runwayTora;
    @FXML
    private Label runwayToda;
    @FXML
    private Label runwayAsda;
    @FXML
    private Label runwaySld;
    @FXML
    private Label runwayClearway;
    @FXML
    private Label runwayStopway;
    @FXML
    private Label runwayStripEnd;
    @FXML
    private Label runwayALS;
    @FXML
    private Label runwayTCS;
    @FXML
    private Label runwayEnd;
    @FXML
    private TextField obstacleLengthInput;
    @FXML
    private TextField obstacleNameInput;
    @FXML
    private TextField obstacleHeightInput;
    @FXML
    private Button redeclareButton;
    @FXML
    private Button resetCalcButton;
    @FXML
    private Label calculationsLabel;
    @FXML
    private Label runwayObstacleLabel;
    @FXML
    private Button createObstacleButton;
    @FXML
    private ListView<BorderPane> notificationPanel;

    private TopDownView topDown = new TopDownView();
    private SideOnView sideOn = new SideOnView();
    private NotificationPanel notificationSystem = new NotificationPanel();
    private boolean darkMode = false;
    public static Scene scene;

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
        setUpRunwayTab();
    }

    public void setCurrentAirport(Airport airport) {
        this.currentAirport = airport;
        setUpAirportTab();
    }

    public void importAirportButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        System.out.println("Opened " + file);
        try {
            setCurrentAirport(XMLImport.importAirportXML(file));

            if (currentAirport != null) {
                System.out.println("Airport " + currentAirport.getAirportName() + " loaded successfully");
                notificationSystem.notify(notificationPanel, "Airport imported succesfully!");

            }

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
            List<Obstacle> importResult = XMLImport.importObstaclesXML(file);

            List<Obstacle> newList = new ArrayList<>();
            Stream.of(obstacles, importResult).forEach(newList::addAll);

            obstacles = newList;

            if (importResult != null) {
                System.out.println(importResult.size() + " obstacles loaded successfully");
                notificationSystem.notify(notificationPanel, "Obstacles imported succesfully!");

            }
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
            List<Aircraft> importResult = XMLImport.importAircraftXML(file);

            List<Aircraft> newList = new ArrayList<>();
            Stream.of(aircrafts, importResult).forEach(newList::addAll);

            aircrafts = newList;

            if (aircrafts != null) {
                System.out.println(aircrafts.size() + " aircraft loaded successfully");
                notificationSystem.notify(notificationPanel, "Aircrafts imported succesfully!");

            }
        }  catch (Exception e ) {
            e.printStackTrace();
            if(file != null)
                showPopupMessage(file + " is not a valid aircraft xml file", Alert.AlertType.ERROR);
        }
    }

    /*
    public void exportMenuButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(root.getScene().getWindow());

        if(file != null && currentAirport != null)
            if(Model.Data.DataPrint.exportData(file, currentAirport))
                System.out.println("Saving to " + file + "...");
    }
    */


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

    public void aboutButtonClicked() {
        Stage popupwindow=new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("About");
        TextArea aboutTextArea = new TextArea();

        try {
            Scanner s = new Scanner(new File("./src/Desktop/UserGuide.txt")).useDelimiter("\\s+");
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (s.hasNextInt()) {
                    aboutTextArea.appendText(s.nextInt() + " ");
                } else {
                    aboutTextArea.appendText(line + "\n");
                }
            }
            aboutTextArea.setWrapText(true);
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }

        Button button1= new Button("Close");
        button1.setOnAction(e -> popupwindow.close());
        VBox layout= new VBox(10);
        layout.getChildren().addAll(aboutTextArea, button1);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 600, 250);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
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

    public void addObstacleToRunwayButtonClicked() {

        int obstIndex = addObstacleButton.getSelectionModel().getSelectedIndex();
        Obstacle selectedObstacle = obstacles.get(obstIndex);

        try {
            resetCalcButton.setDisable(false);
            int leftThresh = Integer.parseInt(leftThreshInput.getText());
            int runwayIndex = changeRunwaysMenu.getSelectionModel().getSelectedIndex();
            Runway currentRunway = this.currentAirport.getAirportRunways().get(runwayIndex);
            selectedObstacle.setRightThreshold(currentRunway.getTakeOffRunAvail() - selectedObstacle.getObstacleLength() - leftThresh);
            selectedObstacle.setLeftThreshold(leftThresh);

            Calculator calc = new Calculator();
            if(!currentRunway.getAlreadyCalculated()) {
                resetCalcButton.setDisable(false);
                calc.calculate(selectedObstacle, currentRunway);
                calculationsLabel.setText(calc.getCalculationBreakdown());
                resetView();
                setUpRunwayTab();
                currentRunway.calculationsMade();
            }

            notificationSystem.notify(notificationPanel, "Object " + selectedObstacle.getName() + " added!");
        } catch (Exception e) {
            showPopupMessage("Invalid input", Alert.AlertType.ERROR);
            return;
        }



        System.out.println("Adding " + selectedObstacle.getName()+ " to " + currentRunway.getRunwayName());
        currentRunway.setObstacle(selectedObstacle);
        resetView();
        topDown.setObstacleVisibility(true);topDown.arrowVisibility(true);
        sideOn.setObstacleVisibility(true);sideOn.arrowVisibility(true);

    }

    public void remObstacleButtonClicked() {
        if(currentRunway.getObstacle() != null){
            currentRunway.setObstacle(null);
            resetCalcButton.setDisable(true);

            currentRunway.resetRunwayValues();
            currentRunway.calculationsReverted();
            calculationsLabel.setText("");
            resetView();
            setUpRunwayTab();
            topDown.setObstacleVisibility(false); topDown.arrowVisibility(false);
            sideOn.setObstacleVisibility(false); sideOn.arrowVisibility(false);
            try{
                notificationSystem.notify(notificationPanel, "Object removed from the Runway!");
            }catch (Exception e) {
                showPopupMessage("Notification Error!", Alert.AlertType.ERROR);
                return;
            }
        }
    }

    public void changeRunwayButtonClicked() {


        System.out.println("Selected runway " + changeRunwaysMenu.getSelectionModel().getSelectedItem());

        int runwayIndex = changeRunwaysMenu.getSelectionModel().getSelectedIndex();

        Runway runwayChosen = this.currentAirport.getAirportRunways().get(runwayIndex);
        this.setCurrentRunway(runwayChosen);
        resetView();
        if(runwayChosen.getObstacle() == null){
            topDown.setObstacleVisibility(false); topDown.arrowVisibility(false);
            sideOn.setObstacleVisibility(false); sideOn.arrowVisibility(false);
        }
        try{
            notificationSystem.notify(notificationPanel, "Runway " + runwayChosen.getRunwayName() + " selected!");
        }catch (Exception e) {
            showPopupMessage("Notification Error!", Alert.AlertType.ERROR);
            return;
        }

    }



    public void redeclareButtonClicked() {
        Calculator calc = new Calculator();
        /*
        if(currentRunway == null || currentRunway.getObstacle() == null) {
            showPopupMessage("No obstacle has been placed onto the runway", Alert.AlertType.ERROR);
            return;
        }
        */
        if(!currentRunway.getAlreadyCalculated()) {
            //redeclareButton.setDisable(true);
            resetCalcButton.setDisable(false);
            //topDown.arrowVisibility(true);
            //sideOn.arrowVisibility(true);
            calc.calculate(currentRunway.getObstacle(), currentRunway);
            calculationsLabel.setText(calc.getCalculationBreakdown());
            resetView();
            setUpRunwayTab();
            currentRunway.calculationsMade();
        }
    }

    public void resetCalcButtonClicked() {
        if(currentRunway.getObstacle() != null){
            currentRunway.setObstacle(null);
            resetCalcButton.setDisable(true);

            currentRunway.resetRunwayValues();
            currentRunway.calculationsReverted();
            calculationsLabel.setText("");
            resetView();
            setUpRunwayTab();
            topDown.setObstacleVisibility(false); topDown.arrowVisibility(false);
            sideOn.setObstacleVisibility(false); sideOn.arrowVisibility(false);
            try{
                notificationSystem.notify(notificationPanel, "Calculations reset!");
            }catch (Exception e) {
                showPopupMessage("Notification Error!", Alert.AlertType.ERROR);
                return;
            }
        }

    }

    public void showCalculationsButtonClicked() {
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
            resetView();
            currentRunway.calculationsMade();

        }
        else {
            Stage popupwindow = new Stage();
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.setTitle("Already re-declared!");
            Label label1 = new Label("Would you like to revert to previous values?");
            Button button1 = new Button("Yes!");
            button1.setOnAction(e -> {
                currentRunway.resetRunwayValues();
                currentRunway.calculationsReverted();
                resetView();
                popupwindow.close();

            });
            VBox layout = new VBox(10);
            layout.getChildren().addAll(label1, button1);
            layout.setAlignment(Pos.CENTER);
            Scene scene1 = new Scene(layout, 600, 250);
            popupwindow.setScene(scene1);
            popupwindow.showAndWait();
        }
    } // not used anymore

    private void showPopupMessage(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    void setupRunwayComboBox() {

        changeRunwaysMenu.getItems().clear();

        if(this.currentAirport != null) {
            for(int i = 0; i < currentAirport.getAirportRunways().size(); i++) {
                Runway runway = currentAirport.getAirportRunways().get(i);
                //int runwayNum = runway.getRunwayNumber();
                changeRunwaysMenu.getItems().add(runway.getRunwayName());
            }
        }

    }

    void setupObstaclesComboBox() {

        addObstacleButton.getItems().clear();

        if(this.obstacles != null) {
            for(int i = 0; i < obstacles.size(); i++) {
                Obstacle obstacle = obstacles.get(i);
                addObstacleButton.getItems().add(obstacle.getName() + " " +
                        String.valueOf(obstacle.getObstacleHeight()) + " x " +
                        String.valueOf(obstacle.getObstacleLength()));
                System.out.println(obstacle.getName());
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

    private void resetView(){
        setupRunwayViews();
    }

    void setupPredefinedObstacles() {

        /*File predefinedObstacles = new File("src/obstacle.xml");

        try {

            List<Obstacle> importResult = XMLImport.importObstaclesXML(predefinedObstacles);

            List<Obstacle> newList = new ArrayList<>();
            Stream.of(obstacles, importResult).forEach(newList::addAll);

            obstacles = newList;

            setupObstaclesComboBox();
        }  catch (Exception e ) {
            e.printStackTrace();
        }*/

        List<Obstacle> predefinedObstacles = new ArrayList<>();

        Obstacle obstacle1 = new Obstacle(15, 12, 11, 125);
        Obstacle obstacle2 = new Obstacle(15, 12, 350, 1000);
        Obstacle obstacle3 = new Obstacle(8,12,123,247);
        Obstacle obstacle4= new Obstacle(20, 20, 13, 150);

        obstacle1.setName("Obstacle1");
        obstacle2.setName("Obstacle2");
        obstacle3.setName("Obstacle3");
        obstacle4.setName("Obstacle4");


        predefinedObstacles.add(obstacle1);
        predefinedObstacles.add(obstacle2);
        predefinedObstacles.add(obstacle3);
        predefinedObstacles.add(obstacle4);

        obstacles = predefinedObstacles;
        setupObstaclesComboBox();
    }

    private void setupRunwayViews() {
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

            try {
                topdownViewPane = topDown.setUpTopDownView(currentRunway);
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

    private void setUpAirportTab() {
        if(currentAirport != null) {
            airportName.setText(this.currentAirport.getAirportName());
            airportRunways.setText(String.valueOf(currentAirport.getAirportRunways().size()));
        }
    }

    private void setUpRunwayTab() {
        if(currentRunway != null) {
            runwayNumber.setText(String.valueOf(currentRunway.getRunwayNumber()));
            runwayClearway.setText(String.valueOf(currentRunway.getClearwayLength()));
            runwayTora.setText(String.valueOf(currentRunway.getTakeOffRunAvail()));
            runwayAsda.setText(String.valueOf(currentRunway.getAccStopDistAvail()));
            runwaySld.setText(String.valueOf(currentRunway.getLandDistAvail()));
            runwayTCS.setText(String.valueOf(currentRunway.getTakeoffClimbSurf()));
            runwayStopway.setText(String.valueOf(currentRunway.getStopwayLength()));
            runwayStripEnd.setText(String.valueOf(currentRunway.getStripEnd()));
            runwayToda.setText(String.valueOf(currentRunway.getTakeOffDistAvail()));
            runwayEnd.setText(String.valueOf(currentRunway.getRunwayEndSafeArea()));
            runwayALS.setText(String.valueOf(currentRunway.getApprochLandSurf()));
            runwayPos.setText(String.valueOf(currentRunway.getRunwayPos()));
            if(currentRunway.getObstacle() != null) {
                runwayObstacleLabel.setText(currentRunway.getObstacle().getName());
            }
        }
    }
    public void exportMenuButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extTxtFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        FileChooser.ExtensionFilter extJpegFilter = new FileChooser.ExtensionFilter("JPEG files (*.jpeg)", "*.jpeg");
        FileChooser.ExtensionFilter extPngFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");

        fileChooser.getExtensionFilters().addAll(extTxtFilter, extJpegFilter, extPngFilter);

        File file = fileChooser.showSaveDialog(root.getScene().getWindow());

        FileChooser.ExtensionFilter selectedExtensionFilter = fileChooser.getSelectedExtensionFilter();
        if(file != null && currentAirport != null) {
            if (selectedExtensionFilter.equals(extTxtFilter)) try{
                Model.Data.DataPrint.exportTxtData(file, currentAirport);
                System.out.println("Saving to " + file + "...");
                notificationSystem.notify(notificationPanel, "Txt exported successfully!");
                }catch (Exception e) {
                    showPopupMessage("Notification Error!", Alert.AlertType.ERROR);
                    return;
            } else if (selectedExtensionFilter.equals(extJpegFilter)) try{
                Model.Data.DataPrint.exportJpegData(file);
                System.out.println("Saving to " + file + "...");
                notificationSystem.notify(notificationPanel, "Jpeg exported successfully!");
            }catch (Exception e) {
                showPopupMessage("Notification Error!", Alert.AlertType.ERROR);
                return;
            } else if (selectedExtensionFilter.equals(extPngFilter)) try{
                Model.Data.DataPrint.exportPngData(file);
                System.out.println("Saving to " + file + "...");
                notificationSystem.notify(notificationPanel, "Png exported successfully!");
            }catch (Exception e) {
                showPopupMessage("Notification Error!", Alert.AlertType.ERROR);
                return;
            }
        }

    }

    public void createObstacleButtonClicked() {

        if (obstacleNameInput.getText() == null
                || obstacleLengthInput.getText() == null || obstacleHeightInput.getText() == null) {
            showPopupMessage("Please enter all values for the obstacle", Alert.AlertType.ERROR);
            return;
        }


        try {
            String obstacleName = obstacleNameInput.getText();
            int height = Integer.parseInt(obstacleHeightInput.getText());
            int length = Integer.parseInt(obstacleLengthInput.getText());

            if (length <= 0 || height <= 0)
                throw new Exception();

            Obstacle obstacle = new Obstacle(height, length, -1, -1);
            obstacle.setName(obstacleName);
            obstacles.add(obstacle);
            setupObstaclesComboBox();


        } catch (Exception e) {
            showPopupMessage("Invalid input", Alert.AlertType.ERROR);
            return;
        }
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void darkModeToggle() {
        System.out.println("Toggle");

        if (!darkMode) {
            scene.getStylesheets().add("darktheme.css");
            darkMode = true;
        } else {
            scene.getStylesheets().remove("darktheme.css");
            darkMode = false;
        }
    }

}
