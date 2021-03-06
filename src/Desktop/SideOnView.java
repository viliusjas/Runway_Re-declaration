package Desktop;

import Model.Objects.Obstacle;
import Model.Objects.Runway;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import java.lang.Math;

import java.io.FileInputStream;

import static javafx.scene.paint.Color.*;

public class SideOnView {

    private int obstacleLength;
    private int obstacleHeight;
    private int obstacleLeftPos;
    private int obstacleRightPos;
    private int obstacleLeftDistance;
    private int obstacleRightDistance;
//    private int displacedThreshold = 300;
//    private int threshold = 0;

    private int stopwayLeftDistance;
    private int clearwayLeftDistance;
    private int stopwayRightDistance;
    private int clearwayRightDistance;

    private String landingDirection;
    private Boolean distancesFromLeft = false;

    private int TORA;
    private int TODA;
    private int ASDA;
    private int LDA;

    private String runwayName;
    private int runwayLength;
    private int runwayPixelWidth = 600;
    private int BLAST = 300;
    private int RESA;
    private int TOCS;
//    private int obstaclePosition;

    private float runwayHeight = 15;

    public static Scene SOVscene;

    //Visible/invisible values
    private VBox arrowVBox;
    private ImageView plane;
    private VBox resaVBox;
    private VBox blastVBox;
    private Line objectDistance;
    private Label objectDistanceLabel;
    private Line tocsDistance;
    private Label tocsLabel;

    public BorderPane setUpSideOnView(Runway runwayObject) throws Exception {
        this.setUpRunwayUI(runwayObject);
        return getSideOnView(runwayObject);
    }

    public BorderPane setUpSideOnView(Runway runwayObject, Obstacle obstacle) throws Exception {
        this.setUpRunwayUI(runwayObject);
        return getSideOnView(runwayObject);
    }

    private void setUpRunwayUI(Runway runwayObject) {
        this.runwayName = runwayObject.getRunwayName();

        this.TORA = runwayObject.getTakeOffRunAvail();
        this.TODA = runwayObject.getTakeOffDistAvail();
        this.ASDA = runwayObject.getAccStopDistAvail();
        this.LDA = runwayObject.getLandDistAvail();

        this.landingDirection = runwayObject.getDirection();
        System.out.println(landingDirection);

        if(runwayObject.getTakeOffRunAvail() < runwayObject.getToraOG()) {
            this.runwayLength = runwayObject.getToraOG();
        } else {
            this.runwayLength = runwayObject.getTakeOffRunAvail();
        }

        if(runwayObject.getDirection().equals("left")) {
            this.distancesFromLeft = false;
            System.out.println("left");
        }
        else if(runwayObject.getDirection().equals("right")) {
            this.distancesFromLeft = true;
            System.out.println("right");
        }

        if(runwayObject.getObstacle() != null){
            this.obstacleLength = runwayObject.getObstacle().getObstacleLength();
            this.obstacleHeight = runwayObject.getObstacle().getObstacleHeight();
            this.obstacleLeftPos = runwayObject.getObstacle().getObstacleLeftPos();
            this.obstacleRightPos = runwayObject.getObstacle().getObstacleRightPos();
            this.obstacleLeftDistance = this.obstacleLeftPos;
            this.obstacleRightDistance = runwayLength - this.obstacleLeftPos - this.obstacleLength;

            this.RESA = runwayObject.getRunwayEndSafeArea();
            this.stopwayRightDistance = runwayObject.getStopwayLength();
            this.clearwayRightDistance = runwayObject.getClearwayLength();
            this.TOCS = 50 * this.obstacleHeight;
        }

        // Need to add these:
//        this.distancesFromLeft = runwayObject.getDistanceDirection();
    }

    public void setObstacleVisibility (boolean bool) {
        this.plane.setVisible(bool);
        this.objectDistance.setVisible(bool);
        this.objectDistanceLabel.setVisible(bool);
    }

    public void arrowVisibility(boolean bool){
        this.arrowVBox.setVisible(bool);
        this.tocsDistance.setVisible(bool);
        this.tocsLabel.setVisible(bool);
        this.resaVBox.setVisible(bool);
        this.blastVBox.setVisible(bool);
    }

    public void setRunwayLength(int runwayLength) {
        this.runwayLength = runwayLength;
    }

    public BorderPane getSideOnView(Runway runwayObject) throws Exception {

//        // CALCULATIONS FOR SCALE
        this.setUpRunwayUI(runwayObject);

        float scale = (float) runwayPixelWidth / runwayLength;
        float obstacleScaledHeight = scale * obstacleHeight * 10;
        float obstacleScaledLength = scale * obstacleLength * 10;
        float obstacleScaledDistanceLeft = scale * obstacleLeftPos;
        float obstacleScaledDistanceRight = runwayPixelWidth -
                (scale * obstacleLeftPos) - obstacleScaledLength;
        float scaledTORA = scale * TORA;
        float scaledTODA = scale * TODA;
        float scaledASDA = scale * ASDA;
        float scaledLDA = scale * LDA;
        float scaledClearway = scale * clearwayRightDistance;
        float scaledStopway = scale * stopwayRightDistance;
        float scaledRESA = scale * RESA;
        float scaledBlast = scale * BLAST;
        float scaledTOCS = scale * TOCS;

        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 600, 400);

        // THRESHOLD DESIGNATOR
        HBox bottom = new HBox();
        bottom.prefHeight(27);

        HBox hBox = new HBox();
        hBox.prefHeight(27);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().add(new Label(this.runwayName));
        Insets insets = new Insets(5, 10, 5, 10);
        hBox.setPadding(insets);

        HBox keyHBox = new HBox();
        keyHBox.prefHeight(27);
        keyHBox.setAlignment(Pos.CENTER_RIGHT);
        Label key = new Label("KEY:    ");
        Rectangle stopwayKey = new Rectangle(10,10);
        stopwayKey.setFill(DARKBLUE);
        Label key1 = new Label(" - Stopway  ");
        Rectangle clearwayKey = new Rectangle(10,10);
        clearwayKey.setFill(LIGHTBLUE);
        Label key2 = new Label(" - Clearway");
        Insets insets2 = new Insets(5, 10, 5, 10);
        keyHBox.setPadding(insets2);
        keyHBox.getChildren().addAll(key, stopwayKey, key1, clearwayKey, key2);

        Region region = new Region();
        region.setMaxHeight(27);

        bottom.getChildren().addAll(hBox, region, keyHBox);
        bottom.setHgrow(region, Priority.ALWAYS);
        borderPane.setBottom(bottom);

        Group group = new Group();

        // RUNWAY + STOPWAY + CLEARWAY
        HBox wholeRunway = new HBox();
        Rectangle runway = new Rectangle();
        runway.setFill(DARKGRAY);
        runway.setArcHeight(5);
        runway.setArcWidth(5);
        runway.setHeight(runwayHeight);
        runway.setStroke(BLACK);
        runway.setStrokeType(StrokeType.INSIDE);

        float leftStopwayScaled = scale * stopwayLeftDistance;
        float leftClearwayScaled = Math.max(0, (scale * clearwayLeftDistance) - leftStopwayScaled);
        float rightStopwayScaled = scale * stopwayRightDistance;
        float rightClearwayScaled = (scale * clearwayRightDistance) - rightStopwayScaled;

        HBox left = new HBox();
        float leftWidth;
        if (clearwayLeftDistance > stopwayLeftDistance) {
            left.getChildren().addAll(new Rectangle(leftClearwayScaled, runwayHeight, LIGHTBLUE),
                    new Rectangle(leftStopwayScaled, runwayHeight, DARKBLUE));
            leftWidth = leftClearwayScaled + leftStopwayScaled;
        } else {
            left.getChildren().addAll(new Rectangle(leftStopwayScaled, runwayHeight, DARKBLUE));
            leftWidth = leftStopwayScaled;
        }

        HBox right = new HBox();
        float rightWidth;
        if (clearwayRightDistance > stopwayRightDistance) {
            right.getChildren().addAll(new Rectangle(rightStopwayScaled, runwayHeight, DARKBLUE),
                    new Rectangle(rightClearwayScaled, runwayHeight, LIGHTBLUE));
            rightWidth = rightClearwayScaled + rightStopwayScaled;
        } else {
            right.getChildren().addAll(new Rectangle(rightStopwayScaled, runwayHeight, DARKBLUE));
            rightWidth = rightStopwayScaled;
        }

        float widthChanged = runwayPixelWidth - leftWidth - rightWidth;

        runway.setWidth(widthChanged);
        wholeRunway.getChildren().addAll(left, runway, right);

        // DASHED LINE
        Line dashed = new Line(0, 0, widthChanged - 10, 0);
        dashed.getStrokeDashArray().addAll(25d, 10d);
        dashed.setStroke(WHITE);

        // OBSTACLE
//        Rectangle object = new Rectangle();
//        object.setFill(LIGHTBLUE);
//        object.setStroke(BLACK);
//        object.setStrokeType(StrokeType.INSIDE);
//        object.setWidth(obstacleScaledLength);
//        object.setHeight(obstacleScaledHeight);

        // PLANE OBSTACLE
        FileInputStream input = new FileInputStream("2Dplane.png");
        Image image = new Image(input);
        this.plane = new ImageView(image);
        this.plane.setFitHeight(obstacleScaledHeight);
        this.plane.setFitWidth(obstacleScaledLength);

        // ARROW
        FileInputStream arrowFile = new FileInputStream("arrow.png");
        Image arrowImage = new Image(arrowFile);
        ImageView arrow = new ImageView(arrowImage);
        arrow.setFitWidth(40);
        arrow.setFitHeight(30);
        this.arrowVBox = new VBox();
        this.arrowVBox.getChildren().addAll(arrow, new Label("Landing Direction"));
        this.arrowVBox.setAlignment(Pos.CENTER);
        if (landingDirection.equals("left")) {
            arrow.setRotate(180);
        }

        // LINE FOR RUNWAY DISTANCE
        Line runwayDistance = new Line(0, 0, runwayPixelWidth, 0);

        // LINE FOR OBJECT DISTANCE
        HBox objectDistanceHBox = new HBox();
        if (this.distancesFromLeft) {
            objectDistanceHBox.setAlignment(Pos.CENTER_LEFT);
            this.objectDistance = new Line(0, 0, obstacleScaledDistanceLeft, 0);
            this.objectDistanceLabel = new Label("(Object Distance = " + obstacleLeftDistance + "m)");
        } else {
            objectDistanceHBox.setAlignment(Pos.CENTER_RIGHT);
            this.objectDistance = new Line(runwayPixelWidth - obstacleScaledDistanceRight,
                    0, runwayPixelWidth, 0);
            this.objectDistanceLabel = new Label("(Object Distance = " + obstacleRightDistance + "m)");
        }
        this.objectDistanceLabel.setFont(new Font("Arial", 10));

        // LINE FOR TORA
        HBox toraDistanceHBox = new HBox();
        Line toraDistance;
        if (this.distancesFromLeft) {
            toraDistanceHBox.setAlignment(Pos.CENTER_LEFT);
            toraDistance = new Line(0, 0, scaledTORA, 0);
        } else {
            toraDistanceHBox.setAlignment(Pos.CENTER_RIGHT);
            toraDistance = new Line(runwayPixelWidth - scaledTORA, 0, runwayPixelWidth, 0);
        }

        // LINE FOR TODA
        HBox todaDistanceHBox = new HBox();
        Line todaDistance;
        if (this.distancesFromLeft) {
            todaDistanceHBox.setAlignment(Pos.CENTER_LEFT);
            todaDistance = new Line(0, 0, scaledTODA, 0);
        } else {
            todaDistanceHBox.setAlignment(Pos.CENTER_RIGHT);
            todaDistance = new Line(runwayPixelWidth - scaledTODA, 0, runwayPixelWidth, 0);
        }

        // LINE FOR ASDA
        HBox asdaDistanceHBox = new HBox();
        Line asdaDistance;
        if (this.distancesFromLeft) {
            asdaDistanceHBox.setAlignment(Pos.CENTER_LEFT);
            asdaDistance = new Line(0, 0, scaledASDA, 0);
        } else {
            asdaDistanceHBox.setAlignment(Pos.CENTER_RIGHT);
            asdaDistance = new Line(runwayPixelWidth - scaledASDA, 0, runwayPixelWidth, 0);
        }

        // LINE FOR LDA
        HBox ldaDistanceHBox = new HBox();
        Line ldaDistance;
        if (this.distancesFromLeft) {
            ldaDistanceHBox.setAlignment(Pos.CENTER_LEFT);
            ldaDistance = new Line(0, 0, scaledLDA, 0);
        } else {
            ldaDistanceHBox.setAlignment(Pos.CENTER_RIGHT);
            ldaDistance = new Line(runwayPixelWidth - scaledLDA, 0, runwayPixelWidth, 0);
        }

        // LINE FOR CLEARWAY
        Line clearwayDistance = new Line(0, 0, scaledClearway, 0);

        // LINE FOR STOPWAY
        Line stopwayDistance = new Line(0, 0, scaledStopway, 0);

        // LINE FOR RESA
        Line resaDistance = new Line(0, 0, scaledRESA, 0);

        // LINE FOR BLAST
        Line blastDistance = new Line(0, 0, scaledBlast, 0);

        // LINE FOR TOCS
        if (this.distancesFromLeft) {
            this.tocsDistance = new Line(obstacleScaledDistanceLeft + obstacleScaledLength - scaledTOCS,
                    obstacleScaledHeight, obstacleScaledDistanceLeft + obstacleScaledLength, 0);
        } else {
            this.tocsDistance = new Line(obstacleScaledDistanceLeft + obstacleScaledLength, 0,
                    obstacleScaledDistanceLeft + obstacleScaledLength + scaledTOCS, obstacleScaledHeight);
        }

        // LABEL FOR RUNWAY DISTANCE
        VBox runwayVBox = new VBox();
        runwayVBox.setAlignment(Pos.CENTER_RIGHT);
        Label runwayDistanceLabel = new Label("(Runway Length = " + runwayLength + "m)");
        runwayDistanceLabel.setFont(new Font("Arial", 10));
        runwayVBox.getChildren().addAll(runwayDistance, runwayDistanceLabel);

        // LABEL FOR OBJECT DISTANCE
        VBox objectVBox = new VBox();
        objectVBox.setAlignment(Pos.CENTER_RIGHT);
        objectVBox.getChildren().addAll(this.objectDistanceLabel, objectDistance);
        objectDistanceHBox.getChildren().add(objectVBox);
        objectDistanceHBox.setPrefWidth(runwayPixelWidth);

        // LABEL FOR TORA
        VBox toraVBox = new VBox();
        toraVBox.setAlignment(Pos.CENTER_RIGHT);
        Label toraLabel = new Label("(TORA = " + TORA + "m)");
        toraLabel.setFont(new Font("Arial", 10));
        toraVBox.getChildren().addAll(toraLabel, toraDistance);
        toraDistanceHBox.getChildren().add(toraVBox);
        toraDistanceHBox.setPrefWidth(runwayPixelWidth);

        // LABEL FOR TODA
        VBox todaVBox = new VBox();
        todaVBox.setAlignment(Pos.CENTER_RIGHT);
        Label todaLabel = new Label("(TODA = " + TODA + "m)");
        todaLabel.setFont(new Font("Arial", 10));
        todaVBox.getChildren().addAll(todaLabel, todaDistance);
        todaDistanceHBox.getChildren().add(todaVBox);
        todaDistanceHBox.setPrefWidth(runwayPixelWidth);

        // LABEL FOR ASDA
        VBox asdaVBox = new VBox();
        asdaVBox.setAlignment(Pos.CENTER_RIGHT);
        Label asdaLabel = new Label("(ASDA = " + ASDA + "m)");
        asdaLabel.setFont(new Font("Arial", 10));
        asdaVBox.getChildren().addAll(asdaLabel, asdaDistance);
        asdaDistanceHBox.getChildren().add(asdaVBox);
        asdaDistanceHBox.setPrefWidth(runwayPixelWidth);

        // LABEL FOR LDA
        VBox ldaVBox = new VBox();
        ldaVBox.setAlignment(Pos.CENTER_RIGHT);
        Label ldaLabel = new Label("(LDA = " + LDA + "m)");
        ldaLabel.setFont(new Font("Arial", 10));
        ldaVBox.getChildren().addAll(ldaLabel, ldaDistance);
        ldaDistanceHBox.getChildren().add(ldaVBox);
        ldaDistanceHBox.setPrefWidth(runwayPixelWidth);

        // LABEL FOR STOPWAY
        VBox stopwayVBox = new VBox();
        stopwayVBox.setAlignment(Pos.CENTER_RIGHT);
        Label stopwayLabel = new Label("(" + stopwayRightDistance + "m)");
        stopwayLabel.setFont(new Font("Arial", 10));
        if (stopwayRightDistance != 0) {
            stopwayVBox.getChildren().addAll(stopwayLabel, stopwayDistance);
        }

        // LABEL FOR CLEARWAY
        VBox clearwayVBox = new VBox();
        clearwayVBox.setAlignment(Pos.CENTER_RIGHT);
        Label clearwayLabel = new Label("(" + clearwayRightDistance + "m)");
        clearwayLabel.setFont(new Font("Arial", 10));
        if (clearwayRightDistance != 0) {
            clearwayVBox.getChildren().addAll(clearwayLabel, clearwayDistance);
        }

        HBox clearwayHBox = new HBox();
        clearwayHBox.getChildren().add(clearwayVBox);
        clearwayHBox.setPrefWidth(runwayPixelWidth);
        clearwayHBox.setAlignment(Pos.CENTER_RIGHT);

        HBox stopwayHBox = new HBox();
        stopwayHBox.getChildren().add(stopwayVBox);
        stopwayHBox.setPrefWidth(runwayPixelWidth);
        stopwayHBox.setAlignment(Pos.CENTER_RIGHT);

        // LABEL FOR RESA
        this.resaVBox = new VBox();
        this.resaVBox.setAlignment(Pos.CENTER_RIGHT);
        Label resaLabel = new Label("(RESA = " + RESA + "m)");
        resaLabel.setFont(new Font("Arial", 10));
        this.resaVBox.getChildren().addAll(resaDistance, resaLabel);

        HBox resaHBox = new HBox();
        resaHBox.getChildren().add(resaVBox);
        resaHBox.setPrefWidth(runwayPixelWidth);
        resaHBox.setAlignment(Pos.CENTER_RIGHT);

        // LABEL FOR BLAST
        this.blastVBox = new VBox();
        this.blastVBox.setAlignment(Pos.CENTER_RIGHT);
        Label blastLabel = new Label("(BLAST = " + BLAST + "M)");
        blastLabel.setFont(new Font("Arial", 10));
        this.blastVBox.getChildren().addAll(blastLabel, blastDistance);

        HBox blastHBox = new HBox();
        blastHBox.getChildren().add(blastVBox);
        blastHBox.setPrefWidth(runwayPixelWidth);
        blastHBox.setAlignment(Pos.CENTER_RIGHT);

        // LABEL FOR TOCS
        this.tocsLabel = new Label("(TOCS = " + TOCS + "M)");
        this.tocsLabel.setFont(new Font("Arial", 10));


        group.getChildren().addAll(wholeRunway, dashed, plane, runwayVBox,
                objectDistanceHBox, toraDistanceHBox, todaDistanceHBox,
                asdaDistanceHBox, ldaDistanceHBox, clearwayHBox, stopwayHBox,
                resaHBox, blastHBox, arrowVBox, tocsDistance, tocsLabel);

        arrowVBox.setLayoutY(-250);
        arrowVBox.setLayoutX(runwayPixelWidth/2 - 45);

        todaDistanceHBox.setLayoutY(-195);
        asdaDistanceHBox.setLayoutY(-165);
        toraDistanceHBox.setLayoutY(-135);
        ldaDistanceHBox.setLayoutY(-105);

        objectDistanceHBox.setLayoutY(-75);

        clearwayHBox.setLayoutY(obstacleScaledHeight - 57.5);
        stopwayHBox.setLayoutY(obstacleScaledHeight - 40);
        stopwayHBox.setLayoutX(-rightClearwayScaled);

        blastHBox.setLayoutY(obstacleScaledHeight - 40);
        if (distancesFromLeft) {
            blastHBox.setPrefWidth(obstacleScaledDistanceLeft - scaledRESA);
        } else {
            blastHBox.setPrefWidth(obstacleScaledDistanceLeft
                    + obstacleScaledLength + scaledRESA + scaledBlast);
        }

        tocsLabel.setLayoutX(obstacleScaledDistanceLeft + obstacleScaledLength);

        wholeRunway.setLayoutY(obstacleScaledHeight - (runwayHeight/2));
        dashed.setLayoutY(obstacleScaledHeight);
        dashed.setLayoutX(leftClearwayScaled + leftStopwayScaled + 5);
        plane.setLayoutX(obstacleScaledDistanceLeft);

        resaHBox.setLayoutY(obstacleScaledHeight + 25);
        if (distancesFromLeft) {
            resaHBox.setPrefWidth(obstacleScaledDistanceLeft);
        } else {
            resaHBox.setPrefWidth(obstacleScaledDistanceLeft
                    + obstacleScaledLength + scaledRESA);
        }

        runwayVBox.setLayoutY(obstacleScaledHeight + 82.5);

        borderPane.setLayoutX(75);
        borderPane.setLayoutY(50);

        borderPane.setPrefHeight(500);
        borderPane.setPrefWidth(630);

        borderPane.setCenter(group);
        SOVscene = scene;

        return borderPane;
    }

}