package espacioUPM.UI;

import espacioUPM.App.IMainControllerScene;
import espacioUPM.App.IMainControllerUtils;
import espacioUPM.Publicaciones.IPublicacion;
import espacioUPM.Publicaciones.Publicacion;
import espacioUPM.Usuarios.Usuario;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class TimelineController implements Initializable {

    @FXML ScrollPane timelinePane;
    @FXML Button btnPaginaSig;
    @FXML Button btnPaginaAnt;
    @FXML Label txtPagina;

    private static final IMainControllerUtils controller = IMainControllerUtils.getInstance();
    private static final IMainControllerScene controllerScene = IMainControllerScene.getInstance();

    private static int numPagina = 0;

    public static void setNumPagina(int num) { numPagina = num; }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        VBox root = new VBox();
        final ProgressBar progressBar = new ProgressBar();
        btnPaginaAnt.setDisable(true);
        btnPaginaSig.setDisable(true);
        //root.setPrefSize(timelinePane.getWidth(), timelinePane.getHeight());
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(progressBar);

        timelinePane.setContent(root);
        root.prefWidthProperty().bind(timelinePane.widthProperty().multiply(0.8));
        root.prefHeightProperty().bind(timelinePane.heightProperty().multiply(0.8));


        Task initTask = new Task<ArrayList<Publicacion>>() {
            @Override
            protected ArrayList<Publicacion> call() {

                String[] seguidos = controller.getThisUser().getSeguidos();

                ArrayList<Publicacion> total = new ArrayList<>();

                for (String seguido : seguidos) {
                    IPublicacion[] pubs = new Usuario(seguido).obtenerPerfil(progressBar.progressProperty());
                    total.addAll(Arrays.asList((Publicacion[]) pubs));
                }

                total.sort(Publicacion::compareTo);
                return total;
            }
        };

        progressBar.progressProperty().bind(initTask.progressProperty());

        initTask.setOnSucceeded(taskFinishEv -> {
            ArrayList<Publicacion> total = (ArrayList<Publicacion>) initTask.getValue();

            root.getChildren().clear();

               for (int i = 50 * numPagina; i < 50 * (numPagina + 1) && i < total.size(); i++) {
                   Tweet tweet = new Tweet();
                   tweet.setTweet(total.get(i));

                   root.getChildren().add(tweet);
                   root.getChildren().add(new Separator());
               }

               txtPagina.setText("Página: " + (numPagina + 1));

               if (numPagina == 0)
                   btnPaginaAnt.setDisable(true);
               else btnPaginaAnt.setDisable(false);
               if (total.size() < 50 * (numPagina + 1))
                   btnPaginaSig.setDisable(true);
               else btnPaginaSig.setDisable(false);
        });
        new Thread(initTask).start();

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
