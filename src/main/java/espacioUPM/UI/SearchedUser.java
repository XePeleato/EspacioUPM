package espacioUPM.UI;

import espacioUPM.Usuario;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SearchedUser extends VBox {
    private SearchedUserController controller;
    private Node view;

    public SearchedUser(Usuario us) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SearchedUser.fxml"));
        fxmlLoader.setControllerFactory(param -> controller = new SearchedUserController());
        controller.setUsuario(us);
        try {
            view = fxmlLoader.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if(view == null) {
            System.out.println("fuck");
        }
        getChildren().add(view);
    }
}
