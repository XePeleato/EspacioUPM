package espacioUPM.UI;

import espacioUPM.App.IMainControllerScene;
import espacioUPM.Usuarios.IUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchedUserController implements Initializable {
    @FXML
    Label txtUsername;
    @FXML
    Button btnProfile;

    private IUsuario us;

    private final static IMainControllerScene controller = IMainControllerScene.getInstance();

    public SearchedUserController() {
    }


    public void onBtnProfileClick(ActionEvent actionEvent) {
        Perfil p = new Perfil();
        p.setPerfil(us);

        if (us == null) {
            System.out.println("BOOO");
            return;
        }
        controller.replaceComponent(p);
    }

    public void setUser(IUsuario us) { this.us = us; }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
