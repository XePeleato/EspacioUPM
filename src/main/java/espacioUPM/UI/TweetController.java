package espacioUPM.UI;

import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_Publicacion;
import espacioUPM.Publicaciones.Publicacion;
import espacioUPM.Publicaciones.PublicacionFactory;
import espacioUPM.Usuario;
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

    @FXML Button btnRetweet, btnComment, btnLike, btnDislike, btnDelete;
    @FXML Label txtUsername, txtDate, txtRetweet;
    @FXML BorderPane borderPaneTweet;

    private static Publicacion pub;

    private static final IDB_Publicacion DB_Pub = DB_Main.getInstance();
    private static final IMainControllerUtils controller = MainController.getInstance();
    private static final IMainControllerScene controllerScene = MainController.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setPub(Publicacion p) {
        pub = p;
        if(!pub.getAutor().equals(controller.getThisUser().getAlias())) {
            btnDelete.setDisable(true);
        }
    }

    public static Publicacion getCurrentPub() {
        return pub;
    }

    public void onClickUsername(MouseEvent mouseEvent){
        String autor = txtUsername.getText();
        Perfil p = new Perfil();
        p.setPerfil(Usuario.getUsuario(autor));
        controllerScene.replaceComponent(p);

    }
    public void onClickRetweet(ActionEvent actionEvent){
        pub.referenciar(controller.getThisUser());
    }
    public void onClickComment(ActionEvent actionEvent){
        controllerScene.replaceComponent("/CommentsPage.fxml");
    }

    public void onClickLike(ActionEvent actionEvent){
        pub.like(controller.getThisUser());
        controllerScene.refresh();
    }
    public void onClickDislike(ActionEvent actionEvent){
        pub.dislike(controller.getThisUser());
        controllerScene.refresh();
    }
    public void onClickDelete(ActionEvent actionEvent){
        pub.borrar();
        controllerScene.refresh();
    }
}
