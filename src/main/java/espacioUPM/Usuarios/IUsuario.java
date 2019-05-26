package espacioUPM.Usuarios;

import espacioUPM.Publicaciones.IPublicacion;
import javafx.beans.property.DoubleProperty;

public interface IUsuario {
    boolean comprobarPasswd(String pass);

    boolean sigueA(String aliasSeguido);

    void seguir(String aliasSeguido);

    void dejarDeSeguir(String aliasSeguido);

    String[] getSeguidos();

    String[] getSeguidores();

    IPublicacion[] obtenerPerfil(DoubleProperty progressProp);

    String getAlias();

    boolean cambiarAlias(String aliasNuevo);

    void borrarDatos();

    void borrar();
}
