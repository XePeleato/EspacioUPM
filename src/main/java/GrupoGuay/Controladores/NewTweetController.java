package GrupoGuay.Controladores;

import GrupoGuay.App;
import GrupoGuay.DBHandler;
import GrupoGuay.Modelos.Publicacion;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class NewTweetController implements Initializable {

    public TextArea txtAreaTweet;
    public Button btnSendTweet;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onBtnSendTweetClick(ActionEvent actionEvent)
    {
        String tweet = txtAreaTweet.getText();
        Publicacion pub = new Publicacion(App.mThisUser, tweet, null);
        DBHandler.getDefault().setPublicacion(pub);
    }


}
