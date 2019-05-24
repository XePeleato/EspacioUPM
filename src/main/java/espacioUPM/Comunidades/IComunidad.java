package espacioUPM.Comunidades;

import espacioUPM.Publicaciones.IPublicacion;

import java.util.ArrayList;

public interface IComunidad {

    boolean unirse(String alias);

    boolean salir(String alias);

    IPublicacion[] obtenerTimelineCompartido(int pagina);

    String getNombre();

}
