package espacioUPM.UI;

import espacioUPM.Usuarios.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ListaUsuarios extends ScrollPane {
    private Node view;
    private ListaUsuariosController controller;

    public ListaUsuarios() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SeguidoresPage.fxml"));
        fxmlLoader.setControllerFactory(param -> controller = new ListaUsuariosController());
        try {
            view = fxmlLoader.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        getChildren().add(view);
    }


    public void setUsuarios(String[] usuarios) {

        VBox root = new VBox();

        controller.scrollPane.setContent(root);
        for (String u : usuarios) {
            SearchedUser sU = new SearchedUser();
            sU.setUsuario(Usuario.getUsuario(u));
            root.getChildren().add(sU);
        }
    }


}
