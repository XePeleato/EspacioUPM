package espacioUPM.UI;

import espacioUPM.IUsuario;
import espacioUPM.Usuario;

public interface IMainControllerUtils {

    void alert(String message);

    IUsuario getThisUser();

    void setThisUser(IUsuario value);
}
