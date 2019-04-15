package GrupoGuay;

import GrupoGuay.Modelos.Usuario;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Hello world!
 *
 */
public class App extends Application
{
    public Button btnLogin;
    public TextField txtAlias;
    public PasswordField txtPass;

    public TextField txtMail;
    public PasswordField txtRegPass;
    public Button btnSendReg;

    public static void main( String[] args )
    {
        DBHandler.getDefault(); // Cargar previamente al uso.
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LandingPage.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("| EspacioUPM |");
        primaryStage.setScene(new Scene(root, 408, 277));
        primaryStage.show();
    }

    public void onBtnLoginClick(ActionEvent actionEvent) {
        Usuario usuario = DBHandler.getDefault().getUsuario(txtAlias.getText());

        if (txtAlias.getText().isEmpty() || txtPass.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor rellene ambos campos.", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.show();
        }
        else if (usuario == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "El usuario especificado no existe.", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.show();
        }
        else if (!usuario.checkPassword(txtPass.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Contraseña incorrecta.", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Contraseña correcta.", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.show();
        }
    }

    public void onBtnRegisterClick(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/RegisterPage.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Registro");
            stage.setScene(new Scene(root, 408, 277));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onBtnRegSendClick(ActionEvent actionEvent) {
        if (txtMail.getText().isEmpty() || txtRegPass.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor rellene ambos campos.", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.show();
            return;
        }

        if (!txtMail.getText().contains("@")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor introduzca un correo válido.", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.show();
            return;
        }

        Usuario nuevo = new Usuario(txtMail.getText().split("@")[0], txtMail.getText(), null, null);
        nuevo.setPassword(txtRegPass.getText());
        DBHandler.getDefault().setUsuario(nuevo);
    }
}
