package espacioUPM.Database;

import espacioUPM.Publicaciones.Publicacion;
import espacioUPM.Usuario;

public interface IDB_Publicacion {

	String getNewID();

	Publicacion getPublicacion(String id);

	boolean setPublicacion(Publicacion publi);

	Publicacion[] getPublicaciones(Usuario usuario);
	
	void borrarPublicacion(Publicacion publi);
	
	void puntuar(Usuario usuario, Publicacion publi, int puntuacion);
	
	void comentar(Publicacion publi, Usuario usuario, String contenido);

	int getPuntuacion(Usuario usuario, Publicacion publi);

}
