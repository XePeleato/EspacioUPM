package espacioUPM.UI;

import espacioUPM.Comunidad;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class CommunityCard extends GridPane {
    private CommunityCardController controller;
    private Node view;

    public CommunityCard() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Tweet.fxml"));
        fxmlLoader.setControllerFactory(param -> controller = new CommunityCardController());
        try {
            view = fxmlLoader.load();

        } catch (IOException ignored) {
        }
        getChildren().add(view);
    }

    public void setCommunity(Comunidad comunidad)
    {
        controller.community = comunidad;
        controller.txtCommunity.setText(comunidad.getNombre());
    }
}
