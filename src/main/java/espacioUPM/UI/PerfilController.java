package espacioUPM.UI;

import com.sun.tools.javac.Main;
import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_Publicacion;
import espacioUPM.Database.IDB_Usuario;
import espacioUPM.Publicaciones.Publicacion;
import espacioUPM.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class PerfilController implements Initializable {

    @FXML
    public Button btnFollow;

    public Label getTxtUsername() {
        return txtUsername;
    }

    @FXML
    Label txtUsername;

    @FXML public ScrollPane scrollPanePublis;
    private static final IDB_Usuario DB = DB_Main.getInstance();
    private static final MainController maincontroller = MainController.getInstance();

    private Usuario usuario;
    private static boolean estaSiguiendo = false;


    public Button getBtnFollow() {
        return btnFollow;
    }

    public void setEstaSiguiendo(boolean estaSiguiendo) {
        this.estaSiguiendo = estaSiguiendo;
    }

    public boolean getEstaSiguiendo() {
        return estaSiguiendo;
    }

    public ScrollPane getScrollPanePublis() {
        return scrollPanePublis;
    }

    public void onClickSeguir(ActionEvent actionEvent) {
        if (estaSiguiendo)
            DB.dejarDeSeguir(maincontroller.getThisUser().getAlias(), usuario.getAlias());
        else
            DB.seguir(maincontroller.getThisUser().getAlias(), usuario.getAlias());
        maincontroller.refresh();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        estaSiguiendo = DB.estaSiguiendo(maincontroller.getThisUser().getAlias(), usuario.getAlias());
        if(estaSiguiendo)
            btnFollow.setText("Dejar de seguir");
        else
            btnFollow.setText("Seguir");
    }
}
