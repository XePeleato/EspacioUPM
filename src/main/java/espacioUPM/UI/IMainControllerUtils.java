package espacioUPM.UI;

import espacioUPM.Usuarios.IUsuario;

public interface IMainControllerUtils {

    void alert(String message);

    IUsuario getThisUser();

    void setThisUser(IUsuario value);
}
