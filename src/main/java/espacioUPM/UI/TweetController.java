package espacioUPM.UI;

import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;


public class TweetController extends VBox implements Initializable {
    public Button btnAliasAutor, btnRetweetear, btnVerComentarios, btnLike, btnDislike, btnBorrar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void onClickAliasAutor(ActionEvent actionEvent){

    }
    public void onClickRetweetear(ActionEvent actionEvent){

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

