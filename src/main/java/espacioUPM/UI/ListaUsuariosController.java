package espacioUPM.UI;

import espacioUPM.Comunidades.IComunidad;
import espacioUPM.Usuarios.IUsuario;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;


public class ListaUsuariosController {

    @FXML ScrollPane scrollPane;

    private IUsuario[] usuarios;
    private IComunidad comunidad;

    public void setData(IUsuario[] usuarios, IComunidad com) { this.usuarios = usuarios;
    this.comunidad = com;}

    public ScrollPane getScrollPane() { return scrollPane; }
}
