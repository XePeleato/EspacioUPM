package espacioUPM.Database;

import espacioUPM.Publicaciones.IPublicacion;
import espacioUPM.Publicaciones.Publicacion;
import espacioUPM.Publicaciones.Puntuacion;
import espacioUPM.Usuarios.IUsuario;
import javafx.beans.property.DoubleProperty;

public interface IDB_Publicacion {

	Publicacion getPublicacion(int id);

	boolean setPublicacion(IPublicacion publi);

	IPublicacion[] getPublicaciones(IUsuario usuario, DoubleProperty progressProp);
	
	void borrarPublicacion(int publi);
	
	void puntuar(IUsuario usuario, int publi, Puntuacion puntuacion);
	
	void comentar(IPublicacion publi, IUsuario usuario, String contenido);

	Puntuacion getPuntuacion(IUsuario usuario, int publi);

	int getNewID();

	static IDB_Publicacion getInstance() {
		return DB_Main.getInstance();
	}

}
