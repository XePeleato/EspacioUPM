package espacioUPM.UI;

import espacioUPM.Usuarios.Usuario;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ListaUsuarios extends GridPane {
    private Node view;


    private static final IMainControllerUtils maincontroller = MainController.getInstance();


    public ListaUsuarios() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SeguidoresPage.fxml"));
        try {
            view = fxmlLoader.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        getChildren().add(view);
    }


    public void setUsuarios(String[] usuarios) {

        VBox root = new VBox();

        for (String u : usuarios) {
            SearchedUser sU = new SearchedUser();
            sU.setUsuario(Usuario.getUsuario(u));
            root.getChildren().add(sU);
        }
    }


}
