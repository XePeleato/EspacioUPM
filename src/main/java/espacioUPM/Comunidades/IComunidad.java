package espacioUPM.Comunidades;

import espacioUPM.Publicaciones.IPublicacion;
import espacioUPM.Usuarios.IUsuario;
import javafx.beans.property.DoubleProperty;

public interface IComunidad {

    boolean unirse(String alias);

    boolean salir(String alias);

    IPublicacion[] obtenerTimelineCompartido(int pagina, DoubleProperty progressProp);

    String getNombre();

    boolean esMiembro(IUsuario user);

}
