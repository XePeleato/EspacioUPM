package espacioUPM;

import espacioUPM.Database.DB_Main;
import espacioUPM.UI.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

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
        controller = MainController.getInstance();
        controller.setStage(primaryStage);
        controller.replaceScene("/LandingPage.fxml");
        primaryStage.setTitle("| EspacioUPM |");
        primaryStage.show();
    }
}
