package espacioUPM.UI;

import espacioUPM.Usuario;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SearchedUser extends VBox {
    private SearchedUserController controller;
    private Node view;

    public SearchedUser() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SearchedUser.fxml"));
        fxmlLoader.setControllerFactory(param -> controller = new SearchedUserController());
        try {
            view = (Node) fxmlLoader.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        getChildren().add(view);
    }

    public void setUser(Usuario us) {
        controller.setUsuario(us);
    }
}
