package espacioUPM.UI;

import espacioUPM.Usuarios.IUsuario;
import espacioUPM.Usuarios.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LandingPageController implements Initializable {

    @FXML Button btnRegister;
    @FXML Button btnLogin;
    @FXML TextField txtAlias;
    @FXML PasswordField txtPass;

    private static IMainControllerUtils controller = MainController.getInstance();
    private static IMainControllerScene controllerScene = MainController.getInstance();

    @FXML
    public void onBtnLoginClick(ActionEvent actionEvent) {
        IUsuario usuario = Usuario.getUsuario(txtAlias.getText());

        if (txtAlias.getText().isEmpty() || txtPass.getText().isEmpty()) {
            controller.alert("Por favor rellene ambos campos.");
        }
        else if (usuario == null) {
            controller.alert("El usuario especificado no existe.");
        }
        else if (!usuario.comprobarPasswd(txtPass.getText())) {
            System.out.println("Contraseña incorrecta.");
            controller.alert("Contraseña incorrecta.");
        } else {
            try {
                controller.setThisUser(usuario);
                controllerScene.replaceScene("/MainPage.fxml");
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    public void onBtnRegisterClick(ActionEvent actionEvent) {
        try {
            controllerScene.setTitle("Registro");
            controllerScene.replaceScene("/SignUpPage.fxml");
        }catch(NullPointerException | IOException e) {
            System.out.println("oops");
        }
    }

    public void onActionAlias(KeyEvent event) {
        if(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.ENTER) {
            txtPass.requestFocus();
        }
    }

    public void onActionPass(KeyEvent event) {
        if(event.getCode() == KeyCode.UP)
            txtAlias.requestFocus();
        else if(event.getCode() == KeyCode.ENTER)
            onBtnLoginClick(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtAlias.requestFocus();
    }
}
