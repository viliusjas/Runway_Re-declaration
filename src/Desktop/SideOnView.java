package Desktop;

import Model.Objects.Runway;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;

import static javafx.scene.paint.Color.*;

public class SideOnView {
    
    //dummydata
    private int obstacleLength = 60;
    private int obstacleHeight = 25;
    private int obstaclePosition = 2500;
    private int displacedThreshold = 300;
    private int threshold = 0;
    private int startRESA = 2000;
    private int endRESA = 2240;

    private int stopwayLeftDistance = 50;
    private int clearwayLeftDistance = 100;
    private int stopwayRightDistance = 40;
    private int clearwayRightDistance = 100;

    private String LandingDirection = "LEFT";

    private int TORA;
    private int TODA;
    private int ASDA;
    private int LDA;


    private int RUNWAY_LENGTH = 3902;
    private int runwayWidth = 600;
    private int blastAllowance = 300;
    private float scale;

    public BorderPane setUpSideOnView(Runway runwayObject) throws Exception {

        // SETUP VALUES

        this.TORA = runwayObject.getTakeOffRunAvail();
        this.TODA = runwayObject.getTakeOffDistAvail();
        this.ASDA = runwayObject.getAccStopDistAvail();
        this.LDA = runwayObject.getLandDistAvail();


        // CALCULATIONS FOR SCALE
        scale = (float) runwayWidth / RUNWAY_LENGTH;
        System.out.println(scale);
        float obstacleScaledHeight = scale * obstacleHeight * 10;
        float obstacleScaledLength = scale * obstacleLength * 10;
        float obstacleScaledDistance = scale * obstaclePosition;
        float scaledTORA = scale * TORA;
        float scaledTODA = scale * TODA;
        float scaledASDA = scale * ASDA;
        float scaledLDA = scale * LDA;

        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 600, 400);

        // THRESHOLD DESIGNATOR
        HBox hBox = new HBox();
        hBox.prefHeight(27);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().add(new Label(runwayObject.getRunwayName()));
        Insets insets = new Insets(5, 10, 5, 10);
        hBox.setPadding(insets);
        borderPane.setBottom(hBox);

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

        borderPane.setLayoutX(152.0);
        borderPane.setLayoutY(88.0);

        borderPane.setPrefHeight(408.0);
        borderPane.setPrefWidth(629.0);

        borderPane.setCenter(group);

        return borderPane;
    }
}
