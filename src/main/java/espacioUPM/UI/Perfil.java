package espacioUPM.UI;

import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_Publicacion;
import espacioUPM.Database.IDB_Usuario;
import espacioUPM.IUsuario;
import espacioUPM.Publicaciones.IPublicacion;
import espacioUPM.Publicaciones.Publicacion;
import espacioUPM.Usuario;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Perfil extends GridPane {
    private PerfilController controller;
    private Node view;


    private static final IMainControllerUtils maincontroller = MainController.getInstance();


    public Perfil() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ProfilePage.fxml"));
        fxmlLoader.setControllerFactory(param -> controller = new PerfilController());
        try {
            view = fxmlLoader.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        getChildren().add(view);
    }


    public void setPerfil(IUsuario us) {
        controller.setUsuario(us);
        controller.setEstaSiguiendo(maincontroller.getThisUser().sigueA(us.getAlias()));

        controller.getBtnFollow().setText(controller.getEstaSiguiendo() ? "Dejar de seguir" : "Seguir");
        controller.getTxtUsername().setText("@" + us.getAlias());

        if (us.getAlias().equals(maincontroller.getThisUser().getAlias()))
            controller.getBtnFollow().setDisable(true);

        VBox root = new VBox();
        controller.getScrollPanePublis().setContent(root);

        IPublicacion[] publis = us.obtenerPerfil();

        for (IPublicacion p : publis) {
            Tweet t = new Tweet();
            t.setTweet(p);
            root.getChildren().add(t);
        }
    }


}
