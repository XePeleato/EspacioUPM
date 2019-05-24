package espacioUPM.UI;

import espacioUPM.Usuarios.Usuario;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ListaUsuarios extends ScrollPane {
    private ScrollPane view;
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

        controller.setUsuarios(usuarios);
        VBox root = new VBox();

        controller.getScrollPane().setContent(root);

        for (String u : usuarios) {
            SearchedUser sU = new SearchedUser();
            sU.setUsuario(Usuario.getUsuario(u));
            root.getChildren().add(sU);
        }
    }


}
