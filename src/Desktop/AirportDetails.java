package Desktop;
import Model.Objects.Airport;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AirportDetails {

    @FXML
    private Label airportNameLabel;
    @FXML
    private Label runwaysLabel;

    void initData(String name, int runways) {
        this.airportNameLabel.setText(name);
        this.runwaysLabel.setText(String.valueOf(runways));
    }
}
