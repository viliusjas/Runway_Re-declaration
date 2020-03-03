package Desktop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import Model.Data.*;
import Model.Objects.*;

import static javafx.scene.paint.Color.*;

public class Controller {

    @FXML
    private BorderPane topdownViewPane;
    @FXML
    private BorderPane sideViewPane;
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
            currentAirport = XMLImport.importAirportXML(file);

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
            obstacles = XMLImport.importObstaclesXML(file);

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
            //int runwayNum = runway.getRunwayNumber();
            changeRunwaysMenu.getItems().add(runway.getRunwayName());
        }


    }

    public void setupSideOnView () throws Exception {

        int obstacleLength = 60;
        int obstacleHeight = 25;
        int obstaclePosition = 2500;
        int displacedThreshold = 300;
        int threshold = 0;
        int startRESA = 2000;
        int endRESA = 2240;

        int stopwayLeftDistance = 50;
        int clearwayLeftDistance = 100;
        int stopwayRightDistance = 40;
        int clearwayRightDistance = 100;

        String LandingDirection = "LEFT";

        int TORA = 3902;
        int TODA = 3902;
        int ASDA = 3902;
        int LDA = 3595;


        int RUNWAY_LENGTH = 3902;
        int runwayWidth = 450;
        int blastAllowance = 300;

        // CALCULATIONS FOR SCALE
        float scale = (float) runwayWidth / RUNWAY_LENGTH;
        System.out.println(scale);
        float obstacleScaledHeight = scale * obstacleHeight * 10;
        float obstacleScaledLength = scale * obstacleLength * 10;
        float obstacleScaledDistance = scale * obstaclePosition;
        float scaledTORA = scale * TORA;
        float scaledTODA = scale * TODA;
        float scaledASDA = scale * ASDA;
        float scaledLDA = scale * LDA;


        // THRESHOLD DESIGNATOR
        HBox hBox = new HBox();
        hBox.prefHeight(27);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().add(new Label("09L"));
        Insets insets = new Insets(5, 10, 5, 10);
        hBox.setPadding(insets);
        sideViewPane.setBottom(hBox);

        Group group = new Group();

        // RUNWAY + STOPWAY + CLEARWAY
        HBox wholeRunway = new HBox();
        Rectangle runway = new Rectangle();
        runway.setFill(DARKGRAY);
        runway.setArcHeight(5);
        runway.setArcWidth(5);
        runway.setHeight(15);
        runway.setStroke(BLACK);
        runway.setStrokeType(StrokeType.INSIDE);

        float leftStopwayScaled = scale * stopwayLeftDistance;
        Rectangle leftStopway = new Rectangle(leftStopwayScaled,15);
        leftStopway.setFill(DARKBLUE);

        float leftClearwayScaled = (scale * clearwayLeftDistance) - leftStopwayScaled;
        Rectangle leftClearway = new Rectangle(leftClearwayScaled, 15);
        leftClearway.setFill(LIGHTBLUE);

        float rightStopwayScaled = scale * stopwayRightDistance;
        Rectangle rightStopway = new Rectangle(rightStopwayScaled, 15);
        rightStopway.setFill(DARKBLUE);

        float rightClearwayScaled = (scale * clearwayRightDistance) - rightStopwayScaled;
        Rectangle rightClearway = new Rectangle(rightClearwayScaled, 15);
        rightClearway.setFill(LIGHTBLUE);

        float widthChanged = runwayWidth - leftStopwayScaled - leftClearwayScaled -
                rightStopwayScaled - rightClearwayScaled;
        runway.setWidth(widthChanged);

        wholeRunway.getChildren().addAll(leftClearway, leftStopway, runway, rightStopway, rightClearway);

        // DASHED LINE
        Line dashed = new Line(0, 0, widthChanged, 0);
        dashed.getStrokeDashArray().addAll(25d, 10d);
        dashed.setStroke(WHITE);

        // OBSTACLE
//        Rectangle object = new Rectangle();
//        object.setFill(LIGHTBLUE);
//        object.setStroke(BLACK);
//        object.setStrokeType(StrokeType.INSIDE);
//        object.setWidth(obstacleScaledLength);
//        object.setHeight(obstacleScaledHeight);
        FileInputStream input = new FileInputStream("2Dplane.png");
        Image image = new Image(input);
        ImageView plane = new ImageView(image);
        plane.setFitHeight(obstacleScaledHeight);
        plane.setFitWidth(obstacleScaledLength);


        // LINE FOR RUNWAY DISTANCE
        Line runwayDistance = new Line(0, 0, runway.getWidth(), 0);

        // LINE FOR OBJECT DISTANCE
        Line objectDistance = new Line(0, 0, obstacleScaledDistance, 0);

        // LINE FOR TORA
        Line toraDistance = new Line(0, 0, scaledTORA, 0);

        // LINE FOR TODA
        Line todaDistance = new Line(0, 0, scaledTODA, 0);

        // LINE FOR ASDA
        Line asdaDistance = new Line(0, 0, scaledASDA, 0);

        // LINE FOR LDA
        Line ldaDistance = new Line(0, 0, scaledLDA, 0);

        // LABEL FOR RUNWAY DISTANCE
        VBox runwayVBox = new VBox();
        runwayVBox.setAlignment(Pos.CENTER_RIGHT);
        Label runwayDistanceLabel = new Label("(Runway Length = " + RUNWAY_LENGTH + "m)");
        runwayDistanceLabel.setFont(new Font("Arial", 10));
        runwayVBox.getChildren().addAll(runwayDistance, runwayDistanceLabel);

        // LABEL FOR OBJECT DISTANCE
        VBox objectVBox = new VBox();
        objectVBox.setAlignment(Pos.CENTER_RIGHT);
        Label objectDistanceLabel = new Label("(Object Distance = " + obstaclePosition + "m)");
        objectDistanceLabel.setFont(new Font("Arial", 10));
        objectVBox.getChildren().addAll(objectDistanceLabel, objectDistance);

        // LABEL FOR TORA
        VBox toraVBox = new VBox();
        toraVBox.setAlignment(Pos.CENTER_RIGHT);
        Label toraLabel = new Label("(TORA = " + TORA + "m)");
        toraLabel.setFont(new Font("Arial", 10));
        toraVBox.getChildren().addAll(toraLabel, toraDistance);

        // LABEL FOR TODA
        VBox todaVBox = new VBox();
        todaVBox.setAlignment(Pos.CENTER_RIGHT);
        Label todaLabel = new Label("(TODA = " + TODA + "m)");
        todaLabel.setFont(new Font("Arial", 10));
        todaVBox.getChildren().addAll(todaLabel, todaDistance);

        // LABEL FOR ASDA
        VBox asdaVBox = new VBox();
        asdaVBox.setAlignment(Pos.CENTER_RIGHT);
        Label asdaLabel = new Label("(ASDA = " + ASDA + "m)");
        asdaLabel.setFont(new Font("Arial", 10));
        asdaVBox.getChildren().addAll(asdaLabel, asdaDistance);

        // LABEL FOR LDA
        VBox ldaVBox = new VBox();
        ldaVBox.setAlignment(Pos.CENTER_RIGHT);
        Label ldaLabel = new Label("(LDA = " + LDA + "m)");
        ldaLabel.setFont(new Font("Arial", 10));
        ldaVBox.getChildren().addAll(ldaLabel, ldaDistance);

        group.getChildren().addAll(wholeRunway, dashed, plane, runwayVBox,
                objectVBox, toraVBox, todaVBox, asdaVBox, ldaVBox);

        todaVBox.setLayoutY(-100);
        asdaVBox.setLayoutY(-80);
        toraVBox.setLayoutY(-60);
        ldaVBox.setLayoutY(-40);

        objectVBox.setLayoutY(-20);
        wholeRunway.setLayoutY(obstacleScaledHeight - 10);
        dashed.setLayoutY(obstacleScaledHeight - 2.5);
        dashed.setLayoutX(leftClearwayScaled + leftStopwayScaled);
        plane.setLayoutX(obstacleScaledDistance);
        runwayVBox.setLayoutY(obstacleScaledHeight + 15);

        sideViewPane.setCenter(group);
    }
}
