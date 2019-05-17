package espacioUPM.UI;

import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_Usuario;
import espacioUPM.Usuario;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SearchedUser extends VBox {
    private SearchedUserController controller;
    private Node view;

    private final static IDB_Usuario DB = DB_Main.getInstance();

    public SearchedUser() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SearchedUser.fxml"));
        fxmlLoader.setControllerFactory(param -> controller = new SearchedUserController());
        try {
            view = fxmlLoader.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        getChildren().add(view);
    }

    public void setUsuario(Usuario us) {
        controller.us = us;

        if (us.getAlias().equals(MainController.thisUser.getAlias()))
            controller.btnFollow.setDisable(true); // Nada de seguirnos a nosotros mismos

        controller.txtUsername.setText(us.getAlias());
        controller.setSiguiendo(DB.estaSiguiendo(MainController.thisUser.getAlias(), us.getAlias()));
        if(controller.getSiguiendo())
            controller.btnFollow.setText("Dejar de seguir");
        else
            controller.btnFollow.setText("Seguir");
    }
}
