package espacioUPM.UI;

import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_Publicacion;
import espacioUPM.Publicaciones.Publicacion;
import espacioUPM.Publicaciones.PublicacionFactory;
import espacioUPM.Usuario;
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

    public NewPublicacionController() {}

    private static Stage thisStage;
    private static IDB_Publicacion DB = DB_Main.getInstance();

    public void setStage(Stage stage) { thisStage = stage; }

    public void initialize() {

    }

    public void onBtnSendTweetClick(ActionEvent actionEvent)
    {
        String tweet = txtAreaTweet.getText();
        Publicacion pub = PublicacionFactory.createPublicacion(MainController.thisUser.getAlias(), tweet);
        DB.setPublicacion(pub);

        MainController.getInstance().replaceComponent("/TimelinePage.fxml");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}