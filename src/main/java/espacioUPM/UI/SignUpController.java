package espacioUPM.UI;

import espacioUPM.App.IMainControllerScene;
import espacioUPM.App.IMainControllerUtils;
import espacioUPM.Usuarios.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignUpController {

    private static IMainControllerUtils controller = IMainControllerUtils.getInstance();
    private static IMainControllerScene controllerScene = IMainControllerScene.getInstance();

    /* Registro */
    @FXML
    public TextField txtAlias;
    @FXML
    public TextField txtMail;
    @FXML
    public PasswordField txtRegPass;
    @FXML
    public PasswordField txtRegPassRepeat;
    @FXML
    public Button btnSendReg;
    @FXML
    public Button btnBack;

    public void onBtnRegSendClick(ActionEvent actionEvent) {
        String alias = txtAlias.getText();
        String correo = txtMail.getText();
        String password = txtRegPass.getText();
        String password2 = txtRegPassRepeat.getText();
        if(alias.isEmpty() || correo.isEmpty() ||
        password.isEmpty() || password2.isEmpty()) {
            controller.alert("Por favor, rellene todos los campos");
        }
        else if(Usuario.getUsuario(alias) != null) {
            controller.alert("El alias pedido ya existe");
        }
        else if(!password.equals(password2)) {
            controller.alert("Las contraseñas no coinciden");
        }
        else {
            Usuario.setUsuario(alias, correo, password);
            try {
                controllerScene.setTitle("EspacioUPM");
                controllerScene.replaceScene("/LandingPage.fxml");
            }
            catch(IOException e) { e.printStackTrace(); }
        }
    }

    public void onBtnBack(ActionEvent actionEvent) {
        try{
            controllerScene.setTitle("EspacioUPM");
            controllerScene.replaceScene("/LandingPage.fxml");
        }
        catch(IOException e) { e.printStackTrace(); }
    }

}
