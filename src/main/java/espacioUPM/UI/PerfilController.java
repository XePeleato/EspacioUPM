package espacioUPM.UI;

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

    @FXML Button btnGotoProfile;
    @FXML Label txtUsername;
    @FXML ScrollPane scrollPanePublis;


    private static final IMainControllerUtils maincontroller = MainController.getInstance();
    private static final IMainControllerScene maincontrollerScene = MainController.getInstance();

    private Usuario usuario;
    private static boolean estaSiguiendo = false;

    public Label getTxtUsername() {
        return txtUsername;
    }

    public Button getBtnGotoProfile() {
        return btnGotoProfile;
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

    public void onClickGotoProfile(ActionEvent actionEvent) {
        if (estaSiguiendo) {
            maincontroller.getThisUser().dejarDeSeguir(usuario.getAlias());
            btnGotoProfile.setText("Seguir");
        }
        else {
            maincontroller.getThisUser().seguir(usuario.getAlias());
            btnGotoProfile.setText("Dejar de seguir");
        }
        maincontrollerScene.refresh();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        estaSiguiendo = maincontroller.getThisUser().sigueA(usuario.getAlias());
        if(estaSiguiendo)
            btnGotoProfile.setText("Dejar de seguir");
        else
            btnGotoProfile.setText("Seguir");
    }
}
