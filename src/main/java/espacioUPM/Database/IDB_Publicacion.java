package espacioUPM.Database;

import espacioUPM.Publicaciones.IPublicacion;
import espacioUPM.Publicaciones.Publicacion;
import espacioUPM.Publicaciones.Puntuacion;
import espacioUPM.Usuario;

public interface IDB_Publicacion {

	Publicacion getPublicacion(int id);

	boolean setPublicacion(Publicacion publi);

	IPublicacion[] getPublicaciones(Usuario usuario);
	
	void borrarPublicacion(int publi);
	
	void puntuar(Usuario usuario, int publi, Puntuacion puntuacion);
	
	void comentar(Publicacion publi, Usuario usuario, String contenido);

	Puntuacion getPuntuacion(Usuario usuario, int publi);

}
