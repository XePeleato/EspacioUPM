package espacioUPM.UI;

import espacioUPM.Publicaciones.IPublicacion;
import espacioUPM.Publicaciones.Puntuacion;
import espacioUPM.Usuarios.Usuario;
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
    @FXML Label txtUsername, txtDate, txtRetweet, txtLike;
    @FXML BorderPane borderPaneTweet;

    private IPublicacion pub;

    private static final IMainControllerUtils controller = MainController.getInstance();
    private static final IMainControllerScene controllerScene = MainController.getInstance();

    private Tweet view;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setPub(IPublicacion p) {
        pub = p;
        if(!pub.getAutor().equals(controller.getThisUser().getAlias())) {
            btnDelete.setDisable(true);
        }
        Puntuacion punt = p.getPuntuacion(controller.getThisUser());
        if(punt == Puntuacion.LIKE)
            txtLike.setText("Te gusta esta publicación");
        else if(punt == Puntuacion.DISLIKE)
            txtLike.setText("No te gusta esta publicación");
        else if(punt == Puntuacion.NEUTRO)
            txtLike.setText("");
    }

    public void onClickUsername(MouseEvent mouseEvent){
        String autor = txtUsername.getText().split("@")[1];
        Perfil p = new Perfil();
        p.setPerfil(Usuario.getUsuario(autor));
        controllerScene.replaceComponent(p);

    }

    public void onClickRetweet(ActionEvent actionEvent){
        pub.referenciar(controller.getThisUser());
        Perfil p = new Perfil();
        p.setPerfil(controller.getThisUser());
        controllerScene.replaceComponent(p);
    }

    public void onClickComment(ActionEvent actionEvent){
        ComentarioController comentarioController = controllerScene.replaceComponent("/CommentsPage.fxml");
        comentarioController.setPub(pub);
    }

    public void onClickLike(ActionEvent actionEvent){
        pub.like(controller.getThisUser());
        view.setTweet(pub);
    }
    public void onClickDislike(ActionEvent actionEvent){
        pub.dislike(controller.getThisUser());
        view.setTweet(pub);
    }
    public void onClickDelete(ActionEvent actionEvent){
        pub.borrar();
        Perfil p = new Perfil();
        p.setPerfil(controller.getThisUser());
        controllerScene.replaceComponent(p);
    }

    public void setView(Tweet node) { view = node; }
}
