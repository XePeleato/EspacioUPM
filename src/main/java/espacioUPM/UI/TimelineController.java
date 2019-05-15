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
import java.util.LinkedList;
import java.util.ResourceBundle;

public class TimelineController implements Initializable {
    @FXML ScrollPane timelinePane;

    private static IDB_Usuario DB_user = DB_Main.getInstance();
    private static IDB_Publicacion DB_publi = DB_Main.getInstance();
    private MainController controller = MainController.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VBox root = new VBox();
        LinkedList<Publicacion> publicaciones = new LinkedList<>();
        timelinePane.setContent(root);


            String[] seguidos = DB_user.getSeguidos(controller.getThisUser());

            for (String seguido : seguidos) {
                Publicacion[] pubs = DB_publi.getPublicaciones(new Usuario(seguido));
                publicaciones.addAll(Arrays.asList(pubs));
            }

        for (Publicacion pub : publicaciones) {
            Tweet tweet = new Tweet();
            tweet.setTweet(pub);
            root.getChildren().add(tweet);
        }

    }
}
