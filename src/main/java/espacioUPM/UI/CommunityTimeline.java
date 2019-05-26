package espacioUPM.UI;

import espacioUPM.Comunidades.IComunidad;
import espacioUPM.Publicaciones.IPublicacion;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
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

        final ProgressBar progressBar = new ProgressBar();
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(progressBar);
        root.prefWidthProperty().bind(controller.getScrollPanePublis().widthProperty().multiply(0.8));
        root.prefHeightProperty().bind(controller.getScrollPanePublis().heightProperty().multiply(0.8));


        Task getTimelineTask = new Task<IPublicacion[]>() {

            @Override
            protected IPublicacion[] call() {
                return  c.obtenerTimelineCompartido(0, progressBar.progressProperty());
            }
        };

        new Thread(getTimelineTask).start();

        getTimelineTask.setOnSucceeded(taskFinishEv -> {
            IPublicacion[] publis = (IPublicacion[]) getTimelineTask.getValue();
            root.getChildren().clear();

            if(publis != null) {
                for (IPublicacion p : publis) {
                    Tweet t = new Tweet();
                    t.setTweet(p);
                    root.getChildren().add(t);
                }
            }
        });


    }

}
