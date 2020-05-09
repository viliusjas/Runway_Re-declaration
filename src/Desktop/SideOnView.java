package Desktop;

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

import java.io.FileInputStream;

import static javafx.scene.paint.Color.*;

public class SideOnView {

    //dummydata
    private int obstacleLength = 60;
    private int obstacleHeight = 20;
    private int obstaclePosition = 2500;
    private int displacedThreshold = 300;
    private int threshold = 0;
    private int RESA = 240;

    private int stopwayLeftDistance = 0;
    private int clearwayLeftDistance = 0;
    private int stopwayRightDistance = 40;
    private int clearwayRightDistance = 100;

    private String LandingDirection = "RIGHT";

    private int TORA;
    private int TODA;
    private int ASDA;
    private int LDA;

    private int RUNWAY_LENGTH = 3902;
    private int runwayWidth = 600;
    private int BLAST = 300;

    private int TOCS = 50 * obstacleHeight;

    private float runwayHeight = 15;

    public static Scene SOVscene;


    public BorderPane setUpSideOnView(Runway runwayObject) throws Exception {

        // SETUP VALUES

        this.TORA = runwayObject.getTakeOffRunAvail();
        this.TODA = runwayObject.getTakeOffDistAvail();
        this.ASDA = runwayObject.getAccStopDistAvail();
        this.LDA = runwayObject.getLandDistAvail();

        if(runwayObject.getObstacle() != null){
            this.obstacleHeight = runwayObject.getObstacle().getObstacleHeight();
            this.obstacleLength = runwayObject.getObstacle().getObstacleLength();
            this.obstaclePosition = runwayObject.getObstacle().getObstacleLeftPos() + runwayObject.getObstacle().getObstacleLength()/2;

            this.RESA = runwayObject.getRunwayEndSafeArea();
            this.stopwayRightDistance = runwayObject.getStopwayLength();
            this.clearwayRightDistance = runwayObject.getClearwayLength();
        }

//        // CALCULATIONS FOR SCALE
        float scale = (float) runwayWidth / RUNWAY_LENGTH;
        System.out.println(scale);
        float obstacleScaledHeight = scale * obstacleHeight * 10;
        float obstacleScaledLength = scale * obstacleLength * 10;
        float obstacleScaledDistance = scale * obstaclePosition;
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
        hBox.getChildren().add(new Label(runwayObject.getRunwayName()));
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

        float widthChanged = runwayWidth - leftWidth - rightWidth;

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
        ImageView plane = new ImageView(image);
        plane.setFitHeight(obstacleScaledHeight);
        plane.setFitWidth(obstacleScaledLength);

        // ARROW
        FileInputStream arrowFile = new FileInputStream("arrow.png");
        Image arrowImage = new Image(arrowFile);
        ImageView arrow = new ImageView(arrowImage);
        arrow.setFitWidth(50);
        arrow.setFitHeight(15);
        VBox arrowVBox = new VBox();
        arrowVBox.getChildren().addAll(arrow, new Label("Landing Direction"));
        arrowVBox.setAlignment(Pos.CENTER);
        if (LandingDirection.equals("LEFT")) {
            arrow.setRotate(180);
        }

        // LINE FOR RUNWAY DISTANCE
        Line runwayDistance = new Line(0, 0, runwayWidth, 0);

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

        // LINE FOR CLEARWAY
        Line clearwayDistance = new Line(0, 0, scaledClearway, 0);

        // LINE FOR STOPWAY
        Line stopwayDistance = new Line(0, 0, scaledStopway, 0);

        // LINE FOR RESA
        Line resaDistance = new Line(0, 0, scaledRESA, 0);

        // LINE FOR BLAST
        Line blastDistance = new Line(0, 0, scaledBlast, 0);

        // LINE FOR TOCS
        Line tocsDistance = new Line(obstacleScaledDistance + obstacleScaledLength - scaledTOCS,
                obstacleScaledHeight, obstacleScaledDistance + obstacleScaledLength, 0);

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

        // LABEL FOR STOPWAY
        VBox stopwayVBox = new VBox();
        stopwayVBox.setAlignment(Pos.CENTER_RIGHT);
        Label stopwayLabel = new Label("(" + stopwayRightDistance + "m)");
        stopwayLabel.setFont(new Font("Arial", 10));
        stopwayVBox.getChildren().addAll(stopwayLabel, stopwayDistance);

        // LABEL FOR CLEARWAY
        VBox clearwayVBox = new VBox();
        clearwayVBox.setAlignment(Pos.CENTER_RIGHT);
        Label clearwayLabel = new Label("(" + clearwayRightDistance + "m)");
        clearwayLabel.setFont(new Font("Arial", 10));
        clearwayVBox.getChildren().addAll(clearwayLabel, clearwayDistance);

        HBox clearwayHBox = new HBox();
        clearwayHBox.getChildren().add(clearwayVBox);
        clearwayHBox.setPrefWidth(runwayWidth);
        clearwayHBox.setAlignment(Pos.CENTER_RIGHT);

        HBox stopwayHBox = new HBox();
        stopwayHBox.getChildren().add(stopwayVBox);
        stopwayHBox.setPrefWidth(runwayWidth);
        stopwayHBox.setAlignment(Pos.CENTER_RIGHT);

        // LABEL FOR RESA
        VBox resaVBox = new VBox();
        resaVBox.setAlignment(Pos.CENTER_RIGHT);
        Label resaLabel = new Label("(RESA = " + RESA + "m)");
        resaLabel.setFont(new Font("Arial", 10));
        resaVBox.getChildren().addAll(resaDistance, resaLabel);

        HBox resaHBox = new HBox();
        resaHBox.getChildren().add(resaVBox);
        resaHBox.setPrefWidth(runwayWidth);
        resaHBox.setAlignment(Pos.CENTER_RIGHT);

        // LABEL FOR BLAST
        VBox blastVBox = new VBox();
        blastVBox.setAlignment(Pos.CENTER_RIGHT);
        Label blastLabel = new Label("(BLAST = " + BLAST + "M)");
        blastLabel.setFont(new Font("Arial", 10));
        blastVBox.getChildren().addAll(blastLabel, blastDistance);

        HBox blastHBox = new HBox();
        blastHBox.getChildren().add(blastVBox);
        blastHBox.setPrefWidth(runwayWidth);
        blastHBox.setAlignment(Pos.CENTER_RIGHT);

        // LABEL FOR TOCS
        Label tocsLabel = new Label("(TOCS = " + TOCS + "M)");
        tocsLabel.setFont(new Font("Arial", 10));


        group.getChildren().addAll(wholeRunway, dashed, plane, runwayVBox,
                objectVBox, toraVBox, todaVBox, asdaVBox, ldaVBox,
                stopwayHBox, clearwayHBox, resaHBox, blastHBox, arrowVBox,
                tocsDistance, tocsLabel);
        arrowVBox.setLayoutY(-210);
        arrowVBox.setLayoutX(0.2 * runwayWidth);

        todaVBox.setLayoutY(-170);
        asdaVBox.setLayoutY(-140);
        toraVBox.setLayoutY(-110);
        ldaVBox.setLayoutY(-80);

        objectVBox.setLayoutY(-50);

        clearwayHBox.setLayoutY(obstacleScaledHeight - 57.5);
        stopwayHBox.setLayoutY(obstacleScaledHeight - 40);
        stopwayHBox.setLayoutX(-rightClearwayScaled);

        blastHBox.setLayoutY(obstacleScaledHeight - 40);
        blastHBox.setPrefWidth(obstacleScaledDistance - scaledRESA);

        tocsLabel.setLayoutX(obstacleScaledDistance + obstacleScaledLength);

        wholeRunway.setLayoutY(obstacleScaledHeight - (runwayHeight/2));
        dashed.setLayoutY(obstacleScaledHeight);
        dashed.setLayoutX(leftClearwayScaled + leftStopwayScaled + 5);
        plane.setLayoutX(obstacleScaledDistance);

        resaHBox.setLayoutY(obstacleScaledHeight + 25);
        resaHBox.setPrefWidth(obstacleScaledDistance);

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
