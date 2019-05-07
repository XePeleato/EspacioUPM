package GrupoGuay.Controladores;

import GrupoGuay.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TimelineController implements Initializable {
    @FXML
    public ScrollPane timelinePane;
    public Button btnNew;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VBox vBoxRoot = new VBox();
        vBoxRoot.setSpacing(10);
        vBoxRoot.setPadding(new Insets(10));
        timelinePane.setContent(vBoxRoot);
        timelinePane.setPannable(true);
    }

    public void onBtnNewClick(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/NewTweetPage.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Nueva publicacion");
            stage.setScene(new Scene(root, 408, 277));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
