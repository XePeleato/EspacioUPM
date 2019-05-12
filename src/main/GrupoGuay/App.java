package GrupoGuay;

import GrupoGuay.Controladores.EspacioUPMController;
import GrupoGuay.Controladores.TimelineController;
import GrupoGuay.Modelos.Usuario;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Hello world!
 *
 */
public class App extends Application
{
   @FXML public Button btnLogin;
   @FXML public TextField txtAlias;
   @FXML public PasswordField txtPass;

    @FXML public TextField txtMail;
    @FXML public PasswordField txtRegPass;
    @FXML public Button btnSendReg;

    public static EspacioUPMController mController;
    private static App instance;

    public static Usuario mThisUser;

    public App() {
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }

    public static void main( String[] args )
    {
        DBHandler.getDefault(); // Cargar previamente al uso.
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        App.getInstance().mController = new EspacioUPMController();
        App.getInstance().mController.setStage(primaryStage);
        App.getInstance().mController.replaceScene("/LandingPage.fxml");
        //primaryStage.setResizable(false);
        //primaryStage.setTitle("| EspacioUPM |");
        primaryStage.show();
    }

    @FXML
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
            try {
                mThisUser = usuario;
                mController.replaceScene("/TimelinePage.fxml");
            } catch (IOException e) { e.printStackTrace(); }
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
