
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

import java.io.FileInputStream;

import static javafx.scene.paint.Color.*;

public class TopDownView {
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
    private int LDA ;

    private int RUNWAY_LENGTH = 3902;
    private int runwayWidth = 450;
    private int blastAllowance = 300;
    private float scale;


    public BorderPane setUpSideOnView(Runway runwayObject) throws Exception {

        // SETUP VALUES

        this.TORA = runwayObject.getTakeOffRunAvail();
        this.TODA = runwayObject.getTakeOffDistAvail();
        this.ASDA = runwayObject.getAccStopDistAvail();
        this.LDA = runwayObject.getLandDistAvail();

        //Scaling
        scale = (float) runwayWidth / RUNWAY_LENGTH;
        System.out.println(scale);
        float obstacleScaledHeight = scale * obstacleHeight * 10;
        float obstacleScaledLength = scale * obstacleLength * 8;
        float obstacleScaledDistance = scale * obstaclePosition;
        float scaledTORA = scale * TORA;
        float scaledTODA = scale * TODA;
        float scaledASDA = scale * ASDA;
        float scaledLDA = scale * LDA;

        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 800, 600);

        //Designator
        HBox hBox = new HBox();
        hBox.prefHeight(27);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().add(new Label(runwayObject.getRunwayName()));
        Insets insets = new Insets(5, 10, 5, 10);
        hBox.setPadding(insets);
        borderPane.setBottom(hBox);

        Group group = new Group();

        //runway, clearway, stopway
        VBox wholeRunway = new VBox();
        Rectangle runway = new Rectangle();
        runway.setFill(DARKGRAY);
        runway.setArcHeight(5);
        runway.setArcWidth(5);
        runway.setHeight(400);
        runway.setWidth(40);
        runway.setStroke(BLACK);
        runway.setStrokeType(StrokeType.INSIDE);

        float leftStopwayScaled = scale * stopwayLeftDistance;
        Rectangle leftStopway = new Rectangle(leftStopwayScaled,15);
        leftStopway.setWidth(40);
        leftStopway.setFill(DARKBLUE);

        float leftClearwayScaled = (scale * clearwayLeftDistance) - leftStopwayScaled;
        Rectangle leftClearway = new Rectangle(leftClearwayScaled, 15);
        leftClearway.setWidth(40);
        leftClearway.setFill(LIGHTBLUE);

        float rightStopwayScaled = scale * stopwayRightDistance;
        Rectangle rightStopway = new Rectangle(rightStopwayScaled, 15);
        rightStopway.setWidth(40);
        rightStopway.setFill(DARKBLUE);

        float rightClearwayScaled = (scale * clearwayRightDistance) - rightStopwayScaled;
        Rectangle rightClearway = new Rectangle(rightClearwayScaled, 15);
        rightClearway.setWidth(40);
        rightClearway.setFill(LIGHTBLUE);

        wholeRunway.getChildren().addAll(leftClearway, leftStopway, runway, rightStopway, rightClearway);

        //white strip
        Line dashed = new Line(0, 0, 0, runway.getHeight());
        dashed.getStrokeDashArray().addAll(25d, 10d);
        dashed.setStroke(WHITE);

        //obstacle
        FileInputStream input = new FileInputStream("2Dplane.png");
        Image image = new Image(input);
        ImageView plane = new ImageView(image);
        plane.setFitHeight(obstacleScaledHeight);
        plane.setFitWidth(obstacleScaledLength);

        //information lines
        Line runwayDistance = new Line(10, 0, 10, runway.getHeight());

        Line objectDistance = new Line(0, 0, 0, obstacleScaledDistance);

        Line toraDistance = new Line(0, 0, 0, scaledTORA);

        Line todaDistance = new Line(0, 0, 0, scaledTODA);

        Line asdaDistance = new Line(0, 0, 0, scaledASDA);

        Line ldaDistance = new Line(0, 0, 0, scaledLDA);

        //lines + labels
        VBox runwayVBox = new VBox();
        runwayVBox.setAlignment(Pos.CENTER_RIGHT);
        Label runwayDistanceLabel = new Label("(Runway Length = " + RUNWAY_LENGTH + "m)");
        runwayDistanceLabel.setFont(new Font("Arial", 10));
        runwayVBox.getChildren().addAll(runwayDistance, runwayDistanceLabel);

        VBox objectVBox = new VBox();
        objectVBox.setAlignment(Pos.CENTER_RIGHT);
        Label objectDistanceLabel = new Label("(Object Distance = " + obstaclePosition + "m)");
        objectDistanceLabel.setFont(new Font("Arial", 10));
        objectVBox.getChildren().addAll(objectDistanceLabel, objectDistance);

        VBox toraVBox = new VBox();
        toraVBox.setAlignment(Pos.CENTER_RIGHT);
        Label toraLabel = new Label("(TORA = " + TORA + "m)");
        toraLabel.setFont(new Font("Arial", 10));
        toraVBox.getChildren().addAll(toraDistance, toraLabel);

        VBox todaVBox = new VBox();
        todaVBox.setAlignment(Pos.CENTER_RIGHT);
        Label todaLabel = new Label("(TODA = " + TODA + "m)");
        todaLabel.setFont(new Font("Arial", 10));
        todaVBox.getChildren().addAll(todaDistance, todaLabel);

        VBox asdaVBox = new VBox();
        asdaVBox.setAlignment(Pos.CENTER_RIGHT);
        Label asdaLabel = new Label("(ASDA = " + ASDA + "m)");
        asdaLabel.setFont(new Font("Arial", 10));
        asdaVBox.getChildren().addAll(asdaLabel, asdaDistance);

        VBox ldaVBox = new VBox();
        ldaVBox.setAlignment(Pos.CENTER_RIGHT);
        Label ldaLabel = new Label("(LDA = " + LDA + "m)");
        ldaLabel.setFont(new Font("Arial", 10));
        ldaVBox.getChildren().addAll(ldaLabel, ldaDistance);

        group.getChildren().addAll(wholeRunway, dashed, plane, runwayVBox,
                objectVBox, toraVBox, todaVBox, asdaVBox, ldaVBox);

        todaVBox.setLayoutY(obstacleScaledHeight - 17);
        todaVBox.setLayoutX(225);
        asdaVBox.setLayoutY(obstacleScaledHeight - 28);
        asdaVBox.setLayoutX(175);
        toraVBox.setLayoutY(obstacleScaledHeight - 17);
        toraVBox.setLayoutX(135);
        ldaVBox.setLayoutY(obstacleScaledHeight - 28);
        ldaVBox.setLayoutX(95);

        objectVBox.setLayoutY(obstacleScaledHeight - 28);
        objectVBox.setLayoutX(3 - obstacleScaledHeight);
        wholeRunway.setLayoutY(obstacleScaledHeight - 17);
        wholeRunway.setLayoutX(100);
        dashed.setLayoutY(obstacleScaledHeight - 2.5);
        dashed.setLayoutX(120);
        plane.setLayoutY(obstacleScaledDistance);
        plane.setLayoutX(92);
        runwayVBox.setLayoutY(obstacleScaledHeight + 11);
        runwayVBox.setLayoutX(-obstacleScaledHeight - 5);

        borderPane.setCenter(group);

        borderPane.setLayoutX(152.0);
        borderPane.setLayoutY(88.0);

        borderPane.setPrefHeight(408.0);
        borderPane.setPrefWidth(629.0);

        return borderPane;
    }
}