package espacioUPM.UI;

import espacioUPM.Usuarios.IUsuario;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class CommunityMemberCard extends VBox {
    private SearchedUserController controller;
    private Node view;

    public CommunityMemberCard() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SearchedUser.fxml"));
        fxmlLoader.setControllerFactory(param -> controller = new SearchedUserController());
        try {
            view = fxmlLoader.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        getChildren().add(view);
    }

    public void setUsuario(IUsuario us) {
        controller.setUser(us);
        controller.txtUsername.setText(us.getAlias());
    }
}
