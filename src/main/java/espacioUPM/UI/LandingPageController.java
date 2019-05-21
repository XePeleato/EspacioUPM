package espacioUPM.UI;

import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_PasswordHandler;
import espacioUPM.Database.IDB_Usuario;
import espacioUPM.Usuario;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LandingPageController implements Initializable {

    @FXML Button btnRegister;
    @FXML Button btnLogin;
    @FXML
    TextField txtAlias;
    @FXML
    PasswordField txtPass;

    private static IDB_PasswordHandler DB_pass = DB_Main.getInstance();
    private static IDB_Usuario DB_user = DB_Main.getInstance();
    private static MainController controller = MainController.getInstance();

    @FXML
    public void onBtnLoginClick(ActionEvent actionEvent) {
        Usuario usuario = DB_user.getUsuario(txtAlias.getText());

        if (txtAlias.getText().isEmpty() || txtPass.getText().isEmpty()) {
            controller.alert("Por favor rellene ambos campos.");
        }
        else if (usuario == null) {
            controller.alert("El usuario especificado no existe.");
        }
        else if (!DB_pass.comprobarPasswd(txtAlias.getText(), txtPass.getText())) {
            System.out.println("Contraseña incorrecta.");
            controller.alert("Contraseña incorrecta.");
        } else {
            try {
                controller.setThisUser(usuario);
                controller.replaceScene("/LoggedInPage.fxml");
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    public void onBtnRegisterClick(ActionEvent actionEvent) {
        try {
            controller.setTitle("Registro");
            controller.replaceScene("/RegisterPage.fxml");
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
        txtAlias.requestFocus();//FIXME: esta verga no hase na
    }
}
