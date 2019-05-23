package espacioUPM.UI;

import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_Publicacion;
import espacioUPM.Publicaciones.Publicacion;
import espacioUPM.Publicaciones.PublicacionFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;


public class NewPublicacionController implements Initializable {

    @FXML TextArea txtAreaTweet;
    @FXML Button btnSendTweet;

    public NewPublicacionController() {}

    private static IMainControllerUtils controller = MainController.getInstance();
    private static IMainControllerScene controllerScene = MainController.getInstance();
    public void initialize() {

    }

    public void onSendTweetClick(ActionEvent actionEvent)
    {

        String tweet = txtAreaTweet.getText();
        if(tweet.length() > 140)
            controller.alert("El mensaje no puede tener más de 140 caracteres");
        else {
            PublicacionFactory.createPublicacion(controller.getThisUser().getAlias(), tweet);
            Perfil p = new Perfil();
            p.setPerfil(controller.getThisUser());
            controllerScene.replaceComponent(p);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}