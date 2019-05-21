package espacioUPM.UI;

import espacioUPM.Comunidad;
import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_Comunidad;
import espacioUPM.Database.IDB_Usuario;
import espacioUPM.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SearchController {
    @FXML
    public TextField txtFieldInput;
    public Button btnSearch;
    @FXML
    ScrollPane scrollPaneResult;

    private static final IDB_Usuario DB_Us = DB_Main.getInstance();
    private static final IDB_Comunidad DB_Com = DB_Main.getInstance();

    public void onClickSearch(ActionEvent actionEvent) {
        VBox root = new VBox();
        scrollPaneResult.setContent(root);
        String contenido = txtFieldInput.getText();
        Usuario[] us = Usuario.buscar(contenido);
        Comunidad[] com = Comunidad.buscar(contenido);

        root.getChildren().add(new Label("Usuarios: "));

        if (us != null)
            for (Usuario u : us) {
                SearchedUser sUs = new SearchedUser();
                sUs.setUsuario(u);

                root.getChildren().add(sUs);
            }

        root.getChildren().add(new Label("Comunidades: "));

        if (com != null)
            for (Comunidad c : com)
                root.getChildren().add(new Label(c.getNombre()));


    }
}
