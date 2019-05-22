package espacioUPM.UI;

import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_Publicacion;
import espacioUPM.Database.IDB_Usuario;
import espacioUPM.Publicaciones.IPublicacion;
import espacioUPM.Publicaciones.Publicacion;
import espacioUPM.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.TreeSet;

public class TimelineController implements Initializable {

    @FXML ScrollPane timelinePane;
    @FXML Button btnPaginaSig;
    @FXML Button btnPaginaAnt;
    @FXML Label txtPagina;

    private static final IMainControllerUtils controller = MainController.getInstance();
    private static final IMainControllerScene controllerScene = MainController.getInstance();

    private static int numPagina = 0;

    public static void setNumPagina(int num) { numPagina = num; }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VBox root = new VBox();
        timelinePane.setContent(root);

        String[] seguidos = controller.getThisUser().getSeguidos();

        ArrayList<Publicacion> total = new ArrayList<>();

            for (String seguido : seguidos) {
                IPublicacion[] pubs = new Usuario(seguido).obtenerPerfil();
                total.addAll(Arrays.asList((Publicacion[])pubs));
            }

        total.sort(Publicacion::compareTo);

        for (int i = 50*numPagina; i < 50*(numPagina + 1) && i < total.size(); i++) {
            Tweet tweet = new Tweet();
            tweet.setTweet(total.get(i));
            root.getChildren().add(tweet);
        }

        txtPagina.setText("Página " + (numPagina + 1));

        if(numPagina == 0)
            btnPaginaAnt.setDisable(true);
        if(total.size() < 50*(numPagina + 1))
            btnPaginaSig.setDisable(true);
    }

    public void onBtnPaginaAntClick(ActionEvent actionEvent) {
        numPagina--;
        controllerScene.refresh();
    }

    public void onBtnPaginaSigClick(ActionEvent actionEvent) {
        numPagina++;
        controllerScene.refresh();
    }
}
