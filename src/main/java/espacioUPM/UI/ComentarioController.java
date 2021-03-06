package espacioUPM.UI;

import espacioUPM.App.IMainControllerScene;
import espacioUPM.App.IMainControllerUtils;
import espacioUPM.Publicaciones.IComentario;
import espacioUPM.Publicaciones.IPublicacion;
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

    private IPublicacion pub;

    private static final IMainControllerUtils controller = IMainControllerUtils.getInstance();
    private static final IMainControllerScene controllerScene = IMainControllerScene.getInstance();

    public void initialize() {
    }

    public void onClickSendComment(ActionEvent actionEvent) {
        String comentario = txtAreaComment.getText();
        pub.comentar(controller.getThisUser(), comentario);
        ComentarioController comentarioController = controllerScene.refresh();
        comentarioController.setPub(pub);
    }

    public void setPub(IPublicacion p) {
        pub = p;

        VBox root = new VBox();
        scrollPaneComments.setContent(root);

        ArrayList<IComentario> comentarios = pub.getComentarios();

        for(IComentario c : comentarios)
            root.getChildren().add(new Label(c.getAutor() + ":\n" + c.getContenido()));
    }

}