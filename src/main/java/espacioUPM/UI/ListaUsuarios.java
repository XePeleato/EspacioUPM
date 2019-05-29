package espacioUPM.UI;

import espacioUPM.Comunidades.IComunidad;
import espacioUPM.Usuarios.IUsuario;
import espacioUPM.Usuarios.Usuario;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ListaUsuarios extends VBox{

    private ScrollPane s;
    private static final IMainControllerScene controller = MainController.getInstance();

    public ListaUsuarios() {
        s = new ScrollPane();
        this.getChildren().add(s);
    }



    public void setData(IComunidad com) {

        IUsuario[] usuarios = com.getMiembros();
        VBox root = new VBox();

        s.setContent(root);

        for (IUsuario u : usuarios) {
            CommunityMemberCard member = new CommunityMemberCard();
            member.setData(u, com);
            root.getChildren().add(member);
            root.getChildren().add(new Separator());
        }

        Button btnAtras = new Button();
        btnAtras.setText("Atrás");
        btnAtras.getStylesheets().add("/fextile.css");
        btnAtras.getStyleClass().add("btn-default");
        btnAtras.setOnAction((actionEvent) -> {
            CommunityTimeline ct = new CommunityTimeline();
            ct.setComunidad(com);
            controller.replaceComponent(ct);
        });

        this.getChildren().add(btnAtras);
    }

    public void setData(IUsuario[] users, IUsuario origen) {
        VBox root = new VBox();

        s.setContent(root);

        for (IUsuario u : users) {
            SearchedUser su = new SearchedUser();
            su.setUsuario(u);
            root.getChildren().add(su);
            root.getChildren().add(new Separator());
        }

        Button btnAtras = new Button();
        btnAtras.setText("Atrás");
        btnAtras.getStylesheets().add("/fextile.css");
        btnAtras.getStyleClass().add("btn-default");
        btnAtras.setOnAction((actionEvent) -> {
            Perfil p = new Perfil();
            p.setPerfil(origen);
            controller.replaceComponent(p);
        });

        this.getChildren().add(btnAtras);
    }
}
