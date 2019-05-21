package espacioUPM.UI;

import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_Publicacion;
import espacioUPM.Publicaciones.Comentario;
import espacioUPM.Publicaciones.Publicacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class ComentarioController {

    @FXML
    ScrollPane scrollPaneComments;
    @FXML
    public TextArea txtAreaComment;
    @FXML
    public Button btnSendComment;

    private final IDB_Publicacion DB_Pub = DB_Main.getInstance();
    private static final MainController controller = MainController.getInstance();

    public void initialize() {
        VBox root = new VBox();
        scrollPaneComments.setContent(root);
        Publicacion pub = TweetController.getCurrentPub();

        ArrayList<Comentario> comentarios = pub.getComentarios();

        for(Comentario c : comentarios)
            root.getChildren().add(new Label(c.getAutor() + ":\n" + c.getContenido()));
    }

    public void onClickSendComment(ActionEvent actionEvent) {
        String comentario = txtAreaComment.getText();
        DB_Pub.comentar(TweetController.getCurrentPub(), controller.getThisUser() , comentario);

        controller.refresh(); // FIXME: esto no furula
    }

}