package Desktop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.FileInputStream;
import java.time.LocalDateTime;

public class NotificationPanel {

    public void notify(ListView<BorderPane> notificationPanel, String message) throws Exception {
        BorderPane bp = new BorderPane();

        // Image
        FileInputStream symbolFile = new FileInputStream("planeSymbol.png");
        Image image = new Image(symbolFile);
        ImageView planeSymbol = new ImageView(image);
        planeSymbol.setFitWidth(40);
        planeSymbol.setFitHeight(40);

        // Message
        Label notification = new Label(message);
        notification.setPrefWidth(200);
        notification.setWrapText(true);

        // Time
        int h = LocalDateTime.now().getHour();
        int m = LocalDateTime.now().getMinute();
        int s = LocalDateTime.now().getSecond();
        String hours = String.format("%02d", h);
        String minutes = String.format("%02d", m);
        String seconds = String.format("%02d", s);
        Label time = new Label(hours + ":" + minutes + ":" + seconds);

        bp.setLeft(planeSymbol);
        bp.setCenter(notification);
        bp.setRight(time);

        notificationPanel.getItems().add(0, bp);

        bp.setAlignment(time, Pos.CENTER);
        bp.setAlignment(planeSymbol, Pos.CENTER);

        bp.setMargin(planeSymbol, new Insets(0,10,0,0));
        bp.setMargin(time, new Insets(0,0,0,10));
    }
}
