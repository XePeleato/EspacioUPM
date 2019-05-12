package espacioUPM.UI;

import espacioUPM.App;
import espacioUPM.Database.DB_Main;
import espacioUPM.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    private Stage mStage;
    @FXML TextField txtAlias;
    @FXML TextField txtPass;

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

    public void Alert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }

    @FXML
    public void onBtnLoginClick(ActionEvent actionEvent) {
        Usuario usuario = DB_Main.getInstance().getUsuario(txtAlias.getText());

        if (txtAlias.getText().isEmpty() || txtPass.getText().isEmpty()) {
            Alert("Por favor rellene ambos campos.");
        }
        else if (usuario == null) {
            Alert("El usuario especificado no existe.");
        }
        else if (!DB_Main.getInstance().comprobarPasswd(txtAlias.getText(), txtPass.getText())) {
            Alert("Contraseña incorrecta.");
        } else {
            try {
                App.thisUser = usuario;
                replaceScene("/TimelinePage.fxml");
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
}
