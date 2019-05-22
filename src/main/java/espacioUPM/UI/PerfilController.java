package espacioUPM.UI;

import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_Usuario;
import espacioUPM.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

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
    private static final IMainControllerUtils maincontroller = MainController.getInstance();
    private static final IMainControllerScene maincontrollerScene = MainController.getInstance();

    private Usuario usuario;
    private static boolean estaSiguiendo = false;


    public Button getBtnFollow() {
        return btnFollow;
    }

    public void setEstaSiguiendo(boolean estaSiguiendo) {
        PerfilController.estaSiguiendo = estaSiguiendo;
    }

    public boolean getEstaSiguiendo() {
        return estaSiguiendo;
    }

    public ScrollPane getScrollPanePublis() {
        return scrollPanePublis;
    }

    public void onClickSeguir(ActionEvent actionEvent) {
        if (estaSiguiendo)
            maincontroller.getThisUser().dejarDeSeguir(usuario.getAlias());
        else
            maincontroller.getThisUser().seguir(usuario.getAlias());
        maincontrollerScene.refresh();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        estaSiguiendo = maincontroller.getThisUser().sigueA(usuario.getAlias());
        if(estaSiguiendo)
            btnFollow.setText("Dejar de seguir");
        else
            btnFollow.setText("Seguir");
    }
}
