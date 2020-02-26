package Desktop;

import Model.Objects.Runway;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class RunwayDetails {

    @FXML
    private Label todaLabel;
    @FXML
    private Label toraLabel;
    @FXML
    private Label asdaLabel;
    @FXML
    private Label sldLabel;
    @FXML
    private Label posLabel;
    @FXML
    private Label numLabel;
    @FXML
    private Label clearwayLabel;
    @FXML
    private Label stopwayLabel;
    @FXML
    private Label runwayendLabel;
    @FXML
    private Label stripendLabel;
    @FXML
    private Label alsLabel;
    @FXML
    private Label tcsLabel;

    void initData(Runway runway) {
        this.numLabel.setText(String.valueOf(runway.getRunwayNumber()));
        this.clearwayLabel.setText(String.valueOf(runway.getClearwayLength()));
        this.toraLabel.setText(String.valueOf(runway.getTakeOffRunAvail()));
        this.asdaLabel.setText(String.valueOf(runway.getAccStopDistAvail()));
        this.sldLabel.setText(String.valueOf(runway.getLandDistAvail()));
        this.tcsLabel.setText(String.valueOf(runway.getTakeoffClimbSurf()));
        this.stopwayLabel.setText(String.valueOf(runway.getStopwayLength()));
        this.stripendLabel.setText(String.valueOf(runway.getStripEnd()));
        this.todaLabel.setText(String.valueOf(runway.getTakeOffDistAvail()));
        this.runwayendLabel.setText(String.valueOf(runway.getRunwayEndSafeArea()));
        this.alsLabel.setText(String.valueOf(runway.getApprochLandSurf()));
        this.posLabel.setText(String.valueOf(runway.getRunwayPos()));
    }
}
