package espacioUPM.UI;

import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_Publicacion;
import espacioUPM.Database.IDB_Usuario;
import espacioUPM.Publicaciones.Publicacion;
import espacioUPM.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.TreeSet;

public class TimelineController implements Initializable {

    @FXML ScrollPane timelinePane;

    private static final IMainControllerUtils controller = MainController.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VBox root = new VBox();
        timelinePane.setContent(root);

        String[] seguidos = controller.getThisUser().getSeguidos();

        Publicacion[] nuestras = controller.getThisUser().obtenerPerfil();

        TreeSet<Publicacion> total = new TreeSet<>(Arrays.asList(nuestras));

            for (String seguido : seguidos) {
                Publicacion[] pubs = new Usuario(seguido).obtenerPerfil();
                total.addAll(Arrays.asList(pubs));
            }

        for (Publicacion pub : total) {
            Tweet tweet = new Tweet();
            tweet.setTweet(pub);
            root.getChildren().add(tweet);
        }

    }
}
