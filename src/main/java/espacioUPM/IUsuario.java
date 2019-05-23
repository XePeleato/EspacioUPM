package espacioUPM;

import espacioUPM.Publicaciones.IPublicacion;

public interface IUsuario {
    boolean comprobarPasswd(String pass);

    boolean sigueA(String aliasSeguido);

    void seguir(String aliasSeguido);

    void dejarDeSeguir(String aliasSeguido);

    String[] getSeguidos();

    String[] getSeguidores();

    IPublicacion[] obtenerPerfil();

    String getAlias();

    boolean cambiarAlias(String aliasNuevo);

    void borrarDatos();

    void borrar();
}
