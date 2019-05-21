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

    private static IDB_Publicacion DB = DB_Main.getInstance();
    private static MainController controller = MainController.getInstance();
    public void initialize() {

    }

    public void onSendTweetClick(ActionEvent actionEvent)
    {
        String tweet = txtAreaTweet.getText();
        Publicacion pub = PublicacionFactory.createPublicacion(controller.getThisUser().getAlias(), tweet);
        DB.setPublicacion(pub);

        controller.replaceComponent("/TimelinePage.fxml");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}