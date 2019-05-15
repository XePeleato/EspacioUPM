package espacioUPM.UI;

import espacioUPM.Database.DB_Main;
import espacioUPM.Publicaciones.Publicacion;
import espacioUPM.Publicaciones.PublicacionReferencia;
import espacioUPM.Publicaciones.PublicacionTexto;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;


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
        controller.pub = p;
        controller.txtUsername.setText(p.getAutor());
        controller.txtRetweet.setText("");
        controller.txtDate.setText(p.getFecha().toString());
        controller.btnLike.setText("Me gusta (" + p.getNumLikes() + ")");
        controller.btnDislike.setText("No me gusta (" + p.getNumDislikes() + ")");
        if (p instanceof PublicacionTexto)
            controller.borderPaneTweet.setCenter(new Label(((PublicacionTexto) p).getContenido()));

        if (p instanceof PublicacionReferencia) {
            controller.txtRetweet.setText("Retwitteado por " + p.getAutor());
            Publicacion pubReferenciada = ((PublicacionReferencia)p).getPublicacionRef();
            controller.txtUsername.setText(pubReferenciada.getAutor());
            controller.txtDate.setText(pubReferenciada.getFecha().toString());
            if (pubReferenciada instanceof PublicacionTexto)
                controller.borderPaneTweet.setCenter(new Label(((PublicacionTexto) pubReferenciada).getContenido()));
        }
    }


}

