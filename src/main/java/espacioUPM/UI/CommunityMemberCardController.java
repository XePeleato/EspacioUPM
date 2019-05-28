package espacioUPM.UI;

import espacioUPM.Comunidades.IComunidad;
import espacioUPM.Usuarios.IUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CommunityMemberCardController implements Initializable {

    private boolean isAccepted;
    private boolean isAdmin;

    @FXML
    Label txtUsername;
    @FXML
    Button btnProfile;
    @FXML Button btnKickOrReject;
    @FXML Button btnMakeAdminOrAccept;
    @FXML Label txtAccepted;

    private IUsuario us;
    private IComunidad com;

    private final static IMainControllerScene controller = MainController.getInstance();
    private final static IMainControllerUtils controllerUtils = MainController.getInstance();

    public CommunityMemberCardController() {
    }


    public void onTxtUsernameAction(MouseEvent mouseEvent) {
        Perfil p = new Perfil();
        p.setPerfil(us);

        if (us == null) {
            System.out.println("BOOO");
            return;
        }
        controller.replaceComponent(p);
    }

    public void setData(IUsuario us, IComunidad com) {
        this.us = us;
        this.com = com;
        isAccepted = com.esMiembro(us);
        isAdmin = com.esAdmin(us.getAlias());
        if(controllerUtils.getThisUser().getAlias().equals(us.getAlias())) {
            btnMakeAdminOrAccept.setDisable(true);
            btnKickOrReject.setDisable(true);
        }
        if(isAccepted) {
            btnKickOrReject.setText("Expulsar");
            btnMakeAdminOrAccept.setText("Hacer admin");
            if(isAdmin) {
                txtAccepted.setText("Admin");
            }
            else {
                btnMakeAdminOrAccept.setDisable(true);
                txtAccepted.setText("Miembro");
            }
        }
        else {
            txtAccepted.setText("No aceptado");
        }
    }

    public void onBtnGoodClick(ActionEvent actionEvent) {
        if(isAccepted) {
            com.hacerAdmin(us.getAlias());
            controller.refresh();
        }
        else {
            com.aceptarMiembroNuevo(us.getAlias());
        }
    }

    public void onBtnBadClick(ActionEvent actionEvent) {
        if(isAccepted) {
            com.salir(us.getAlias());
        }
        else {
            com.rechazarMiembro(us.getAlias());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
