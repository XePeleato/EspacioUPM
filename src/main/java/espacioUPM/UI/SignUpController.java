package espacioUPM.UI;

import com.sun.tools.javac.Main;
import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Random;

public class SignUpController {

    private static IDB_Usuario DB = DB_Main.getInstance();
    private static MainController controller = MainController.getInstance();

    /* Registro */
    @FXML
    TextField txtAlias;
    @FXML
    TextField txtMail;
    @FXML
    PasswordField txtRegPass;
    @FXML
    Button btnSendReg;


    public void onBtnRegSendClick(ActionEvent actionEvent) {
        /*Random r = new Random();
        byte[] salt = new byte[16];
        r.nextBytes(salt);
        if (DB.setUsuario(txtMail.getText().split("@")[0], txtMail.getText(), txtRegPass.getText().getBytes(), salt)) {
            mStage.setTitle("| Login |");
            try {
                replaceScene("/LandingPage.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            alert("Fallo al registrarse"); FIXME*/
    }

}
