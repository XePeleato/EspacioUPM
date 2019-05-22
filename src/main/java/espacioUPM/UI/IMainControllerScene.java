package espacioUPM.UI;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public interface IMainControllerScene {

    void setTitle(String txt);

    Parent replaceScene(String fxml) throws IOException;

    void setStage(Stage stage);

    <T> T replaceComponent(String fxml);

    void replaceComponent(Node node);

    <T> T refresh();
}
