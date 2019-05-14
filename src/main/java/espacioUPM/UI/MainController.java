package espacioUPM.UI;

import espacioUPM.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    private Stage mStage;
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
        //TODO
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
}
