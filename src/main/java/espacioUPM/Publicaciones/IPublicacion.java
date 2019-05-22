package espacioUPM.Publicaciones;//


import espacioUPM.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface IPublicacion {
	
	void comentar(Usuario autor, String contenido);

	void like(Usuario usuario);
	
	void dislike(Usuario usuario);
	
	void referenciar(Usuario usuario);

	public int getIDPublicacion();

	public String getAutor();

	public LocalDateTime getFecha();

	public ArrayList<IComentario> getComentarios();

	public int getNumLikes();

	public int getNumDislikes();
}
