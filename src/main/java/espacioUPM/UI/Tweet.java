package espacioUPM.UI;

import espacioUPM.Publicaciones.Publicacion;
import espacioUPM.Publicaciones.PublicacionTexto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.util.Callback;


public class Tweet extends VBox{
    private TweetController controller;
    private Node view;

    public Tweet() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Tweet.fxml"));
        fxmlLoader.setControllerFactory(param -> controller = new TweetController());
        try {
            view = (Node) fxmlLoader.load();

        } catch (IOException ex) {
        }
        getChildren().add(view);
    }

    public void setTweet(Publicacion p) {
        controller.txtUsername.setText(p.getAutor());
        controller.txtRetweet.setText("");
        controller.txtDate.setText(p.getFecha().toString());
        if (p instanceof PublicacionTexto)
            controller.borderPaneTweet.setCenter(new Label(((PublicacionTexto) p).getContenido()));
    }


}

