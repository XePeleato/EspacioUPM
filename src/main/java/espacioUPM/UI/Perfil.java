package espacioUPM.UI;

import espacioUPM.Publicaciones.IPublicacion;
import espacioUPM.Usuarios.IUsuario;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

@SuppressWarnings("Duplicates")
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

        final ProgressBar progressBar = new ProgressBar();
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(progressBar);
        root.prefWidthProperty().bind(controller.getScrollPanePublis().widthProperty().multiply(0.8));
        root.prefHeightProperty().bind(controller.getScrollPanePublis().heightProperty().multiply(0.8));

        Task getTimelineTask = new Task<IPublicacion[]>() {

            @Override
            protected IPublicacion[] call() {
                return  us.obtenerPerfil(progressBar.progressProperty());
            }
        };

        new Thread(getTimelineTask).start();

        getTimelineTask.setOnSucceeded(taskFinishEv -> {
            IPublicacion[] publis = (IPublicacion[]) getTimelineTask.getValue();
            root.getChildren().clear();

            for (IPublicacion p : publis) {
                Tweet t = new Tweet();
                t.setTweet(p);
                root.getChildren().add(t);
            }
        });
    }
}
