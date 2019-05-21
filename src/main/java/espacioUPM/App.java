package espacioUPM;

import espacioUPM.Database.DB_Main;
import espacioUPM.UI.MainController;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;

public class App extends Application {

    public static HostServices hostServices;
    private static App instance;
    private MainController controller;
    public App() {
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        DB_Main.getInstance(); // Conectamos con la BD
        hostServices = getHostServices();

        controller = MainController.getInstance();
        controller.setStage(primaryStage);
        controller.replaceScene("/LandingPage.fxml");
        controller.setTitle("EspacioUPM");

        primaryStage.show();
    }
}
