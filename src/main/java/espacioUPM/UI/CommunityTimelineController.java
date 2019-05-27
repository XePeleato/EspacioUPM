package espacioUPM.UI;

import espacioUPM.Comunidades.IComunidad;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CommunityTimelineController implements Initializable {

    private IComunidad comunidad;
    @FXML
    Button btnSalir;
    @FXML
    Button btnUnirse;
    @FXML
    Label txtNombre;
    @FXML
    ScrollPane scrollPanePublis;

    private static final IMainControllerUtils maincontroller = MainController.getInstance();
    private static final IMainControllerScene maincontrollerScene = MainController.getInstance();

    public void setComunidad(IComunidad value) {
        comunidad = value;
        if(comunidad.esMiembro(maincontroller.getThisUser()))
            btnUnirse.setDisable(true);
    }

    public Label getTxtNombre() {
        return txtNombre;
    }

    public ScrollPane getScrollPanePublis() {
        return scrollPanePublis;
    }

    public void onClickSalir(ActionEvent actionEvent) {
        comunidad.salir(maincontroller.getThisUser().getAlias());
        try {
            maincontrollerScene.replaceComponent("/CommunityPage.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickUnirse(ActionEvent actionEvent) {
        comunidad.unirse(maincontroller.getThisUser().getAlias());
        try {
            maincontrollerScene.replaceComponent("/CommunityPage.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
