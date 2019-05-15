package espacioUPM.Database;

import espacioUPM.Publicaciones.Publicacion;
import espacioUPM.Publicaciones.Puntuacion;
import espacioUPM.Usuario;

public interface IDB_Publicacion {

	String getNewID();

	Publicacion getPublicacion(int id);

	//Publicacion getPublicacion(String cuerpo);


	boolean setPublicacion(Publicacion publi);

	Publicacion[] getPublicaciones(Usuario usuario);
	
	void borrarPublicacion(Publicacion publi);
	
	void puntuar(Usuario usuario, Publicacion publi, Puntuacion puntuacion);
	
	void comentar(Publicacion publi, Usuario usuario, String contenido);

	Puntuacion getPuntuacion(Usuario usuario, Publicacion publi);

}
