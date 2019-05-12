package GrupoGuay.Controladores;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EspacioUPMController {
    public TimelineController timelineController;
    public NewTweetController newTweetController;

    private Stage mStage;
    public void initialize() {
    }

    public void setStage(Stage stage) {
        this.mStage = stage;
    }

    public Parent replaceScene(String fxml) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource(fxml), null, new JavaFXBuilderFactory());
        if (mStage == null) {
            System.out.println("[-] Ouch");
            return null;
        }
        Scene scene = mStage.getScene();
        if (scene == null) {
            scene = new Scene(root, 600, 400);
            mStage.setScene(scene);
        }
        else
            mStage.getScene().setRoot(root);

        mStage.sizeToScene();
        return root;
    }
}
