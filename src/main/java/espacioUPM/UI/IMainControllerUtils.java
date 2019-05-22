package espacioUPM.UI;

import espacioUPM.Usuario;

public interface IMainControllerUtils {

    void alert(String message);

    Usuario getThisUser();

    void setThisUser(Usuario value);
}
