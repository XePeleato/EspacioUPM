package espacioUPM.UI;

import espacioUPM.Publicaciones.PublicacionFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ResourceBundle;


public class NewPublicacionController implements Initializable {

    @FXML TextArea txtAreaTweet;
    @FXML Button btnSendTweet;
    @FXML Label txtCharLimit;

    public NewPublicacionController() {}

    private static IMainControllerUtils controller = MainController.getInstance();
    private static IMainControllerScene controllerScene = MainController.getInstance();
    public void initialize() {

    }

    public void onSendTweetClick(ActionEvent actionEvent)
    {

        String tweet = txtAreaTweet.getText();
        if(tweet.length() > 140)
            controller.alert("El mensaje no puede tener más de 140 caracteres");
        else {
            PublicacionFactory.createPublicacion(controller.getThisUser().getAlias(), tweet);
            Perfil p = new Perfil();
            p.setPerfil(controller.getThisUser());
            controllerScene.replaceComponent(p);
        }
    }

    public void updateCharLimit(KeyEvent event) {
        int txtLen = txtAreaTweet.getText().length();
        txtCharLimit.setText(txtLen + "/140");
        txtCharLimit.getStyleClass().clear();

        if (txtLen >= 125)
            txtCharLimit.getStyleClass().add("text-warning");
        if (txtLen >= 140)
            txtCharLimit.getStyleClass().add("text-danger");
        else
            txtCharLimit.getStyleClass().add("text-primary");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}