package espacioUPM.UI;

import espacioUPM.Comunidades.IComunidad;
import espacioUPM.Publicaciones.IPublicacion;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class CommunityTimeline extends GridPane {

    private CommunityTimelineController controller;
    private Node view;


    private static final IMainControllerUtils maincontroller = MainController.getInstance();


    public CommunityTimeline() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CommunityTimelinePage.fxml"));
        fxmlLoader.setControllerFactory(param -> controller = new CommunityTimelineController());
        try {
            view = fxmlLoader.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        getChildren().add(view);
    }


    public void setComunidad(IComunidad c) {
        controller.setComunidad(c);
        controller.getTxtNombre().setText(c.getNombre());

        VBox root = new VBox();
        controller.getScrollPanePublis().setContent(root);

        IPublicacion[] publis = c.obtenerTimelineCompartido(0);
        if(publis != null) {
            for (IPublicacion p : publis) {
                Tweet t = new Tweet();
                t.setTweet(p);
                root.getChildren().add(t);
            }
        }
    }

}
