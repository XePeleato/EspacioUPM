package espacioUPM.UI;

import espacioUPM.App;
import espacioUPM.Database.DB_Main;
import espacioUPM.Publicaciones.Publicacion;
import espacioUPM.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class TimelineController implements Initializable {
    @FXML ScrollPane timelinePane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DB_Main DB = DB_Main.getInstance();

        VBox root = new VBox();
        LinkedList<Publicacion> publicaciones = new LinkedList<>();
        timelinePane.setContent(root);


            String[] seguidos = DB.getSeguidos(App.thisUser);

            for (String seguido : seguidos) {
                Publicacion[] pubs = DB.getPublicaciones(new Usuario(seguido));
                publicaciones.addAll(Arrays.asList(pubs));
            }

        for (Publicacion pub : publicaciones) {
            Tweet tweet = new Tweet();
            tweet.setTweet(pub);
            root.getChildren().add(tweet);
        }

    }

    public void onBtnNewClick(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/NewTweetPage.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            ((NewPublicacionController)loader.getController()).setStage(stage);

            stage.setTitle("Nueva publicacion");
            stage.setScene(new Scene(root, 408, 277));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
