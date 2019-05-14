package espacioUPM.UI;
import espacioUPM.Database.DB_Main;
import espacioUPM.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class BuscadorController {
    @FXML
    public TextField txtFieldInput;
    public Button btnSearch, btnAlias, btnUnirseComunidad;
    @FXML
    ScrollPane scrollPaneResult;


    public void initialize() {
    }

    public void onClickSearch(ActionEvent actionEvent) {
       String contenido = txtFieldInput.getText();
        Usuario us = DB_Main.getInstance().getUsuario(contenido);

    }
    public void onClickAlias(ActionEvent actionEvent) {

    }
    public void onClickUnirseComunidad(ActionEvent actionEvent) {
       /* String comentario = txtArea.getText();
        Comen tario comment = new Comentario(Main.mThisUser.getAlias(), comentario, publicacion.getIDPublicacion());
        DBHandler.getDefault().comentar(publicacion,usuario,comment.getContenido());
        */
    }
}
