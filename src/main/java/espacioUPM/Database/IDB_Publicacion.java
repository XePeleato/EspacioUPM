package espacioUPM.Database;

import espacioUPM.Publicaciones.IPublicacion;
import espacioUPM.Publicaciones.Publicacion;
import espacioUPM.Publicaciones.Puntuacion;
import espacioUPM.Usuarios.IUsuario;

public interface IDB_Publicacion {

	Publicacion getPublicacion(int id);

	boolean setPublicacion(IPublicacion publi);

	IPublicacion[] getPublicaciones(IUsuario usuario);
	
	void borrarPublicacion(int publi);
	
	void puntuar(IUsuario usuario, int publi, Puntuacion puntuacion);
	
	void comentar(IPublicacion publi, IUsuario usuario, String contenido);

	Puntuacion getPuntuacion(IUsuario usuario, int publi);

	int getNewID();

}
