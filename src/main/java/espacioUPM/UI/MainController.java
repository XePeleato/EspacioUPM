package espacioUPM.UI;

import espacioUPM.App;
import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_PasswordHandler;
import espacioUPM.Database.IDB_Usuario;
import espacioUPM.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

public class MainController {
    private Stage mStage;
    @FXML TextField txtAlias;
    @FXML PasswordField txtPass;

    /* Registro */
    @FXML TextField txtMail;
    @FXML PasswordField txtRegPass;

    static final IDB_Usuario DB_user = DB_Main.getInstance();
    static final IDB_PasswordHandler DB_pass = DB_Main.getInstance();

    public void initialize() {
    }

    public void setStage(Stage stage) {
        this.mStage = stage;
    }

    public Parent replaceScene(String fxml) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource(fxml), null, new JavaFXBuilderFactory());
        if (mStage == null) {
            System.out.println("[-] Ouch");
            return null;
        }
        Scene scene = mStage.getScene();
        if (scene == null) {
            scene = new Scene(root, 600, 400);
            mStage.setScene(scene);
        }
        else
            mStage.getScene().setRoot(root);

        mStage.sizeToScene();
        return root;
    }

    public void alert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.show();
    }

    @FXML
    public void onBtnLoginClick(ActionEvent actionEvent) {
        Usuario usuario = DB_user.getUsuario(txtAlias.getText());

        if (txtAlias.getText().isEmpty() || txtPass.getText().isEmpty()) {
            alert("Por favor rellene ambos campos.");
        }
        else if (usuario == null) {
            alert("El usuario especificado no existe.");
        }
        else if (!DB_pass.comprobarPasswd(txtAlias.getText(), txtPass.getText())) {
            alert("Contraseña incorrecta.");
        } else {
            try {
                App.thisUser = usuario;
                replaceScene("/TimelinePage.fxml");
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    public void onBtnRegisterClick(ActionEvent actionEvent) {
        try {
            mStage.setTitle("| Registro |");
            replaceScene("/RegisterPage.fxml");
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void onBtnRegSendClick(ActionEvent actionEvent) {
        if (DB_user.setUsuario(txtMail.getText().split("@")[0], txtMail.getText(), txtRegPass.getText())) {
            mStage.setTitle("| Login |");
            try {
                replaceScene("/LandingPage.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            alert("Fallo al registrarse");
    }
}
