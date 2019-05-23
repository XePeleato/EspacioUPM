package espacioUPM.Database;

import espacioUPM.IUsuario;
import espacioUPM.Publicaciones.IPublicacion;
import espacioUPM.Publicaciones.Publicacion;
import espacioUPM.Publicaciones.Puntuacion;
import espacioUPM.Usuario;

public interface IDB_Publicacion {

	Publicacion getPublicacion(int id);

	boolean setPublicacion(Publicacion publi);

	IPublicacion[] getPublicaciones(IUsuario usuario);
	
	void borrarPublicacion(int publi);
	
	void puntuar(IUsuario usuario, int publi, Puntuacion puntuacion);
	
	void comentar(Publicacion publi, IUsuario usuario, String contenido);

	Puntuacion getPuntuacion(IUsuario usuario, int publi);

}
