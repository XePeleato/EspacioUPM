package espacioUPM.UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TweetController implements Initializable {
    @FXML
    public Button btnAliasAutor, btnRetweetear, btnVerComentarios, btnLike, btnDislike, btnBorrar;

    @FXML
    public Label txtUsername, txtDate, txtRetweet;

    @FXML
    BorderPane borderPaneTweet;

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
