package espacioUPM.UI;

import espacioUPM.App.IMainControllerScene;
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

    private static final IMainControllerScene controller = IMainControllerScene.getInstance();

    public void onClickGotoCommunity(ActionEvent actionEvent)
    {
        CommunityTimeline c = new CommunityTimeline();
        c.setComunidad(community);
        controller.replaceComponent(c);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
