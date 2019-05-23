package espacioUPM.UI;

import espacioUPM.Comunidad;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VBox root = new VBox();
        scrollPaneCommunities.setContent(root);

        Comunidad[] comunidades = Comunidad.getComunidades(MainController.getInstance().getThisUser().getAlias());

        for (Comunidad c : comunidades) {
            CommunityCard card = new CommunityCard();
            card.setCommunity(c);
            root.getChildren().add(card);
        }
    }

    public void onClickCreateCommunity(ActionEvent actionEvent)
    {
        new Comunidad(txtCommunityName.getText()).crearComunidad(MainController.getInstance().getThisUser().getAlias());
        MainController.getInstance().replaceComponent("/CommunityPage.fxml");
    }
}
