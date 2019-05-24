package espacioUPM.UI;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;


public class ListaUsuariosController {

    @FXML ScrollPane scrollPane;

    private String[] usuarios;

    public void setUsuarios(String[] usuarios) { this.usuarios = usuarios; }

    public ScrollPane getScrollPane() { return scrollPane; }
}
