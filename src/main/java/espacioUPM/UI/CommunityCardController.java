package espacioUPM.UI;

import espacioUPM.Comunidad;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CommunityCardController implements Initializable {

    @FXML Label txtCommunity;
    @FXML Button btnGotoCommunity;

    Comunidad community;

    private static final IMainControllerScene controller = MainController.getInstance();

    public void onClickGotoCommunity(ActionEvent actionEvent)
    {
        try{
            controller.replaceScene("/CommunityPage.fxml");
        }
        catch(IOException e) { e.printStackTrace(); }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
