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

    public Usuario us;

    private final static IDB_Usuario DB = DB_Main.getInstance();
    private final static MainController controller = MainController.getInstance();
    private final static String thisUser = controller.getThisUser().getAlias();

    public SearchedUserController() {
    }


    public void onBtnProfileClick(ActionEvent actionEvent) {
        Perfil p = new Perfil();
        p.setPerfil(us);

        if (us == null) {
            System.out.println("BOOO");
            return;
        }
        MainController.getInstance().replaceComponent(p);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
