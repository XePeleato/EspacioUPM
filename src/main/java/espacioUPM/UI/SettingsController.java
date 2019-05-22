package espacioUPM.UI;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SettingsController {

    @FXML Button btnCambiarAlias, btnLogout, btnBorrarDatos, btnBaja;
    @FXML TextField txtAliasNuevo;
    private static IMainControllerUtils controller = MainController.getInstance();
    private static IMainControllerScene controllerScene = MainController.getInstance();

    public void onClickCambiarAlias(ActionEvent actionEvent) {
        if(txtAliasNuevo.getText().isEmpty())
            controller.alert("Introduce un alias");
        else if(!controller.getThisUser().cambiarAlias(txtAliasNuevo.getText()))
            controller.alert("El alias elegido ya está en uso");
    }

    public void onClickLogout(ActionEvent actionEvent) {
        controller.setThisUser(null);
        try {
            controllerScene.replaceScene("/LandingPage.fxml");
        }
        catch(IOException e) { e.printStackTrace(); }
    }

    public void onClickBorrarDatos(ActionEvent actionEvent) {
        controller.getThisUser().borrarDatos();
    }

    public void onClickDarseDeBaja(ActionEvent actionEvent) {
        controller.getThisUser().borrarDatos();
        controller.getThisUser().borrar();
        controller.setThisUser(null);
        try {
            controllerScene.replaceScene("/LandingPage.fxml");
        }
        catch(IOException e) { e.printStackTrace(); }
    }

}
