package espacioUPM.Publicaciones;//


import espacioUPM.IUsuario;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface IPublicacion {
	
	void comentar(IUsuario autor, String contenido);

	void like(IUsuario usuario);
	
	void dislike(IUsuario usuario);
	
	void referenciar(IUsuario usuario);

	int getIDPublicacion();

	String getAutor();

	LocalDateTime getFecha();

	ArrayList<IComentario> getComentarios();

	void borrar();

	int getNumLikes();

	int getNumDislikes();

	Puntuacion getPuntuacion(IUsuario user);
}
