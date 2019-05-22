package espacioUPM.UI;

import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_Usuario;
import espacioUPM.Usuario;
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

    private Usuario us;

    private final static IMainControllerScene controller = MainController.getInstance();

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

    public void setUser(Usuario us) { this.us = us; }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
