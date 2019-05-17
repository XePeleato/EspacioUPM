package espacioUPM.UI;

import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_Publicacion;
import espacioUPM.Publicaciones.Publicacion;
import espacioUPM.Publicaciones.PublicacionFactory;
import espacioUPM.Publicaciones.Puntuacion;
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
    public Button btnRetweet, btnComment, btnLike, btnDislike, btnDelete;

    @FXML
    public Label txtUsername, txtDate, txtRetweet;

    @FXML
    BorderPane borderPaneTweet;

    private static Publicacion pub;

    private static final IDB_Publicacion DB_Pub = DB_Main.getInstance();
    private static final MainController controller = MainController.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public static void setPub(Publicacion p) {
        pub = p;
    }

    public static Publicacion getCurrentPub() {
        return pub;
    }

    public void onClickUsername(MouseEvent mouseEvent){
        String autor = txtUsername.getText();
        // TODO: Ir al perfil del autor
    }
    public void onClickRetweet(ActionEvent actionEvent){
        DB_Pub.setPublicacion(PublicacionFactory.createPublicacion(MainController.thisUser.getAlias(), "/ref" + pub.getIDPublicacion()));
    }
    public void onClickComment(ActionEvent actionEvent){
        controller.replaceComponent("/CommentsPage.fxml");
    }
    public void onClickLike(ActionEvent actionEvent){
        pub.like(MainController.thisUser);
        controller.refresh();
    }
    public void onClickDislike(ActionEvent actionEvent){
        pub.dislike(MainController.thisUser);
        controller.refresh();
    }
    public void onClickDelete(ActionEvent actionEvent){
        DB_Pub.borrarPublicacion(pub.getIDPublicacion());
        controller.refresh();
    }
}
