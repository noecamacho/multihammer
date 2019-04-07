package Model;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Dialogs {
    public void displayMessage(Stage stage, String header, String body, String btn1) {
        JFXAlert alert = new JFXAlert(stage);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);
        JFXDialogLayout layout = new JFXDialogLayout();
        Label Header = new Label(header);
        layout.setHeading(Header);
        Header.setStyle("-fx-text-fill:black");
        Label Body = new Label(body);
        Body.setStyle("-fx-text-fill:black");
        layout.setBody(Body);
        JFXButton closeButton = new JFXButton(btn1);
        closeButton.setOnAction(event -> alert.hideWithAnimation());
        layout.setActions(closeButton);
        alert.setContent(layout);
        alert.show();
    }
    
    public boolean displayMessage(Stage stage, String header, String body, String btn1, String btn2) {
        final boolean[] value = {false};
        JFXAlert alert = new JFXAlert(stage);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);
        JFXDialogLayout layout = new JFXDialogLayout();
        Label Header = new Label(header);
        layout.setHeading(Header);
        Header.setStyle("-fx-text-fill:black");
        Label Body = new Label(body);
        Body.setStyle("-fx-text-fill:black");
        layout.setBody(Body);
        JFXButton firstButton = new JFXButton(btn1);
        firstButton.setOnAction((ActionEvent event) -> {
            alert.hideWithAnimation();
            value[0] = true;
        });
        JFXButton secondButton = new JFXButton(btn2);
        secondButton.setOnAction((ActionEvent event) -> {
            alert.hideWithAnimation();
        });
        layout.setActions(firstButton, secondButton);
        alert.setContent(layout);
        alert.showAndWait();
        return value[0];
    }
}
