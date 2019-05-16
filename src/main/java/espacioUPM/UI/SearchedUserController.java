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

    private Usuario us;

    private final static IDB_Usuario DB = DB_Main.getInstance();
    private final static MainController controller = MainController.getInstance();
    private final static String thisUser = controller.getThisUser().getAlias();
    private boolean siguiendo;


    public void setUsuario(Usuario us) {
        this.us = us;
        txtUsername.setText(us.getAlias());

        if (us.getAlias().equals(MainController.thisUser.getAlias()))
            btnFollow.setDisable(true); // Nada de seguirnos a nosotros mismos
    }

    public void onFollowClick(ActionEvent actionEvent) {
        if(siguiendo)
            DB.seguir(thisUser, us.getAlias());
        else
            DB.dejarDeSeguir(thisUser, us.getAlias());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        siguiendo = DB.estaSiguiendo(thisUser, us.getAlias());
        if(siguiendo)
            btnFollow.setText("Seguir");
        else
            btnFollow.setText("Dejar de seguir");
    }
}
