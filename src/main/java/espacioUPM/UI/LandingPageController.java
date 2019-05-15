package espacioUPM.UI;

import com.sun.tools.javac.Main;
import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_PasswordHandler;
import espacioUPM.Database.IDB_Usuario;
import espacioUPM.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LandingPageController implements Initializable {

    @FXML
    public TextField txtAlias;
    @FXML
    public PasswordField txtPass;

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
            System.out.println("contraseña incorrecta");
            controller.alert("Contraseña incorrecta.");
        } else {
            try {
                controller.setThisUser(usuario);
                controller.replaceScene("/LoggedInPage.fxml");
                controller.replaceComponent("/TimelinePage.fxml");
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    public void onBtnRegisterClick(ActionEvent actionEvent) {
        try {
            controller.setTitle("| Registro |");
            controller.replaceScene("/RegisterPage.fxml");
        }catch(NullPointerException | IOException e) {
            System.out.println("oops");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
