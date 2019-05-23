package espacioUPM.Publicaciones;//


import espacioUPM.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface IPublicacion {
	
	void comentar(Usuario autor, String contenido);

	void like(Usuario usuario);
	
	void dislike(Usuario usuario);
	
	void referenciar(Usuario usuario);

	int getIDPublicacion();

	String getAutor();

	LocalDateTime getFecha();

	ArrayList<IComentario> getComentarios();

	void borrar();

	int getNumLikes();

	int getNumDislikes();

	Puntuacion getPuntuacion(Usuario user);
}
