package espacioUPM;

import espacioUPM.UI.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    private static App instance;
    private static Usuario thisUser;
    private MainController controller;
    public App() {
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new MainController();
        controller.setStage(primaryStage);
        controller.replaceScene("/LandingPage.fxml");
        primaryStage.setTitle("| EspacioUPM |");
        primaryStage.show();
    }
}
