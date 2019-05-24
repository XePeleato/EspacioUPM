package espacioUPM.Comunidades;

import espacioUPM.Publicaciones.IPublicacion;

public interface IComunidad {

    boolean unirse(String alias);

    boolean salir(String alias);

    IPublicacion[] obtenerTimelineCompartido(int pagina);

    String getNombre();

}
