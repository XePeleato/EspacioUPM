package espacioUPM.UI;

import espacioUPM.Comunidades.Comunidad;
import espacioUPM.Comunidades.IComunidad;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CommunityPageController implements Initializable {
    @FXML Button btnCreateCommunity;
    @FXML TextField txtCommunityName;
    @FXML ScrollPane scrollPaneCommunities;

    private static final IMainControllerUtils controller = MainController.getInstance();
    private static final IMainControllerScene controllerScene = MainController.getInstance();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VBox root = new VBox();
        scrollPaneCommunities.setContent(root);

        IComunidad[] comunidades = Comunidad.getComunidades(controller.getThisUser().getAlias());

        for (IComunidad c : comunidades) {
            CommunityCard card = new CommunityCard();
            card.setCommunity(c);
            root.getChildren().add(card);
        }
    }

    public void onClickCreateCommunity(ActionEvent actionEvent)
    {
        new Comunidad(txtCommunityName.getText()).crearComunidad(controller.getThisUser().getAlias());
        controllerScene.replaceComponent("/CommunityPage.fxml");
    }
}
