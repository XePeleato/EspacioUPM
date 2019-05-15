package espacioUPM.UI;

import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_Publicacion;
import espacioUPM.Publicaciones.PublicacionFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TweetController implements Initializable {
    @FXML
    public Button btnRetweet, btnVerComentarios, btnLike, btnDislike, btnDelete;

    @FXML
    public Label txtUsername, txtDate, txtRetweet;

    @FXML
    BorderPane borderPaneTweet;

    int pubId;

    static final IDB_Publicacion DB_Pub = DB_Main.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onClickUsername(MouseEvent mouseEvent){
        String autor = txtUsername.getText();
        // TODO: Ir al perfil del autor
    }
    public void onClickRetweet(ActionEvent actionEvent){
        DB_Pub.setPublicacion(PublicacionFactory.createPublicacion(MainController.thisUser.getAlias(), "/ref" + pubId));
    }
    public void onClickVerComentarios(ActionEvent actionEvent){
        //no esta en fxml todavia
    }
    public void onClickLike(ActionEvent actionEvent){

    }
    public void onClickDislike(ActionEvent actionEvent){

    }
    public void onClickBorrar(ActionEvent actionEvent){
        //DB.borrarPublicacion(publi);
    }
}
