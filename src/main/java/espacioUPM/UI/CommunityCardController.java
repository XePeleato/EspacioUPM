package espacioUPM.UI;

import espacioUPM.Comunidades.Comunidad;
import espacioUPM.Comunidades.IComunidad;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CommunityCardController implements Initializable {

    @FXML Label txtCommunity;
    @FXML Button btnGotoCommunity;

    IComunidad community;

    private static final IMainControllerScene controller = MainController.getInstance();

    public void onClickGotoCommunity(ActionEvent actionEvent)
    {
        // Ir a la comunidad
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
