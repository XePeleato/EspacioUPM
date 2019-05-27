package espacioUPM.UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class SettingsController {

    @FXML Button btnCambiarAlias, btnLogout, btnBorrarDatos, btnBaja;
    @FXML HBox actionBox, respMessageBox;

    private static IMainControllerUtils controller = MainController.getInstance();
    private static IMainControllerScene controllerScene = MainController.getInstance();

    public void onClickCambiarAlias(ActionEvent actionEvent) {
        actionBox.getChildren().clear();
        respMessageBox.getChildren().clear();

        TextField alias = new TextField();
        alias.setPrefWidth(200);
        alias.setPromptText("Nuevo alias");
        alias.getStylesheets().add("/fextile.css");
        alias.getStyleClass().add("text-field");

        Button btnAccept = new Button("Aceptar");
        btnAccept.getStylesheets().add("/fextile.css");
        btnAccept.getStyleClass().add("btn-success");
        btnAccept.setOnAction(this::onCambiarAlias);

        actionBox.setAlignment(Pos.CENTER);
        actionBox.getChildren().add(alias);
        actionBox.getChildren().add(btnAccept);
    }


    public void onCambiarAlias(ActionEvent actionEvent) {
        respMessageBox.getChildren().clear();

        TextField alias = (TextField) actionBox.getChildren().get(0);

        if (alias.getText().equals("")) {
            Label responseLabel = new Label("Por favor introduce un alias");
            responseLabel.getStylesheets().add("/fextile.css");
            responseLabel.getStyleClass().add("alert-warning");

            HBox txtBox = new HBox();
            txtBox.getChildren().add(responseLabel);
            respMessageBox.getChildren().add(txtBox);
            return;
        }

        boolean ret = controller.getThisUser().cambiarAlias(alias.getText());

        Label responseLabel = new Label("Por favor, elige otro alias");
        if(!ret) {

            responseLabel.getStylesheets().add("/fextile.css");
            responseLabel.getStyleClass().add("alert-danger");

            HBox txtBox = new HBox();
            txtBox.getChildren().add(responseLabel);
            respMessageBox.getChildren().add(txtBox);


        }
        else {
            controller.setThisUser(null);
            try {
                controllerScene.replaceScene("/LandingPage.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onClickLogout(ActionEvent actionEvent) {
        controller.setThisUser(null);
        try {
            controllerScene.replaceScene("/LandingPage.fxml");
        }
        catch(IOException e) { e.printStackTrace(); }
    }

    public void onClickBorrarDatos(ActionEvent actionEvent) {
        actionBox.getChildren().clear();
        respMessageBox.getChildren().clear();

        controller.getThisUser().borrarDatos();

        Label responseLabel = new Label("Datos borrados");
        responseLabel.getStylesheets().add("/fextile.css");
        responseLabel.getStyleClass().add("alert-success");
        respMessageBox.getChildren().add(responseLabel);
    }

    public void onClickDarseDeBaja(ActionEvent actionEvent) {
        controller.getThisUser().borrarDatos();
        controller.getThisUser().borrar();
        controller.setThisUser(null);
        try {
            controllerScene.replaceScene("/LandingPage.fxml");
        }
        catch(IOException e) { e.printStackTrace(); }
    }

}
