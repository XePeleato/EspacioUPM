package espacioUPM.UI;

import espacioUPM.Usuarios.IUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import java.net.URL;
import java.util.ResourceBundle;

public class PerfilController implements Initializable {

    @FXML Button btnFollow;
    @FXML Label txtUsername;
    @FXML Label txtSeguidores;
    @FXML Label txtSeguidos;
    @FXML ScrollPane scrollPanePublis;


    private static final IMainControllerUtils maincontroller = MainController.getInstance();
    private static final IMainControllerScene maincontrollerScene = MainController.getInstance();

    private IUsuario usuario;
    private static boolean estaSiguiendo = false;

    public Label getTxtUsername() {
        return txtUsername;
    }

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

    public void onClickFollow(ActionEvent actionEvent) {
        if (estaSiguiendo) {
            maincontroller.getThisUser().dejarDeSeguir(usuario.getAlias());
            btnFollow.setText("Seguir");
        }
        else {
            maincontroller.getThisUser().seguir(usuario.getAlias());
            btnFollow.setText("Dejar de seguir");
        }
        estaSiguiendo = !estaSiguiendo;
        maincontrollerScene.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtUsername.requestFocus();
    }

    public void setUsuario(IUsuario usuario) {
        this.usuario = usuario;
        estaSiguiendo = maincontroller.getThisUser().sigueA(usuario.getAlias());
        if(estaSiguiendo)
            btnFollow.setText("Dejar de seguir");
        else
            btnFollow.setText("Seguir");
        txtSeguidores.setText("Seguidores: " + usuario.getSeguidores().length);
        txtSeguidos.setText("Seguidos: " + usuario.getSeguidos().length);
    }
}
