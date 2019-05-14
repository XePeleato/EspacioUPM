package espacioUPM.UI;

import espacioUPM.App;
import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_Publicacion;
import espacioUPM.Publicaciones.Publicacion;
import espacioUPM.Publicaciones.PublicacionTexto;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class NewPublicacionController implements Initializable {

    public TextArea txtAreaTweet;
    public Button btnSendTweet;
    private static Stage thisStage;

    public void setStage(Stage stage) { thisStage = stage; }

    public void initialize() {

    }

    public void onBtnSendTweetClick(ActionEvent actionEvent)
    {

        String tweet = txtAreaTweet.getText();
        Publicacion pub = new PublicacionTexto(App.thisUser.getAlias(), tweet);
        DB_Main.getInstance().setPublicacion(pub);
        thisStage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}