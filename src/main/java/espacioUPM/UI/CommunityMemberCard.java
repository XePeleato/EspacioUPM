package espacioUPM.UI;

import espacioUPM.Comunidades.IComunidad;
import espacioUPM.Usuarios.IUsuario;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class CommunityMemberCard extends VBox {
    private CommunityMemberCardController controller;
    private Node view;

    public CommunityMemberCard() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CommunityMemberCard.fxml"));
        fxmlLoader.setControllerFactory(param -> controller = new CommunityMemberCardController());
        try {
            view = fxmlLoader.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        getChildren().add(view);
    }

    public void setData(IUsuario us, IComunidad com) {
        controller.setData(us, com);
        controller.txtUsername.setText("@" + us.getAlias());
    }
}
