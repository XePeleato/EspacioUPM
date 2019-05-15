package espacioUPM.UI;

import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_PasswordHandler;
import espacioUPM.Database.IDB_Usuario;
import espacioUPM.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private static Stage mStage;
    public static Usuario thisUser;
    private static MainController instance;

    /* Principal */
    @FXML BorderPane borderPaneMain;
    @FXML Button btnTimeline;
    @FXML Button btnProfile;
    @FXML Button btnSearch;
    @FXML Button btnSettings;
    @FXML Button btnNew;

    static final IDB_Usuario DB_user = DB_Main.getInstance();
    static final IDB_PasswordHandler DB_pass = DB_Main.getInstance();

    public MainController() {}

    public static MainController getInstance() {
        if(instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    public void setStage(Stage stage) {
        this.mStage = stage;
    }

    public Usuario getThisUser() { return thisUser; }

    public void setThisUser(Usuario value) { thisUser = value; }

    public void setTitle(String txt) {
        mStage.setTitle(txt);
    }

    public Parent replaceScene(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fxml));
        Parent root = loader.load();
        if (mStage == null) {
            System.out.println("[-] Ouch");
            return null;
        }
        Scene scene = new Scene(root);
        mStage.setScene(scene);
        mStage.sizeToScene();
        System.out.println("[+] Nueva escena: " + fxml);
        return root;
    }

    public void alert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.show();
    }

    public void replaceComponent(String fxml) {
        try {
            borderPaneMain.setCenter(FXMLLoader.load(getClass().getResource(fxml)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[+] Nueva subescena: " + fxml);
    }

    public void onBtnTimelineClick(ActionEvent actionEvent) {
        replaceComponent("/TimelinePage.fxml");
    }

    public void onBtnProfileClick(ActionEvent actionEvent) {
        // TODO: aún no existe ProfilePage.fxml
    }

    public void onBtnSearchClick(ActionEvent actionEvent) {
        replaceComponent("/BuscadorPage.fxml");
    }

    public void onBtnSettingsClick(ActionEvent actionEvent) {
        // TODO: aún no existe SettingsPage.fxml
    }

    public void onBtnNewClick(ActionEvent actionEvent) {
        replaceComponent("/NewTweetPage.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
