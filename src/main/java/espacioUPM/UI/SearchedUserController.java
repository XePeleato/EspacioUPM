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
    Button btnFollow;

    public Usuario us;

    private final static IDB_Usuario DB = DB_Main.getInstance();
    private final static MainController controller = MainController.getInstance();
    private final static String thisUser = controller.getThisUser().getAlias();
    private boolean siguiendo;

    public SearchedUserController() {
    }

    public void setSiguiendo(boolean value) { siguiendo = value; }
    public boolean getSiguiendo() { return siguiendo; }


    public void onFollowClick(ActionEvent actionEvent) {
        if(siguiendo)
            DB.dejarDeSeguir(thisUser, us.getAlias());
        else
            DB.seguir(thisUser, us.getAlias());
        controller.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
