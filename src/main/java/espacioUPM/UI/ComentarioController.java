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

    private Publicacion pub;

    private static final IMainControllerUtils controller = MainController.getInstance();
    private static final IMainControllerScene controllerScene = MainController.getInstance();

    public void initialize() {
    }

    public void onClickSendComment(ActionEvent actionEvent) {
        String comentario = txtAreaComment.getText();
        pub.comentar(controller.getThisUser(), comentario);
        ComentarioController comentarioController = controllerScene.refresh();
        comentarioController.setPub(pub);
    }

    public void setPub(Publicacion p) {
        pub = p;

        VBox root = new VBox();
        scrollPaneComments.setContent(root);

        ArrayList<Comentario> comentarios = pub.getComentarios();

        for(Comentario c : comentarios)
            root.getChildren().add(new Label(c.getAutor() + ":\n" + c.getContenido()));
    }

}