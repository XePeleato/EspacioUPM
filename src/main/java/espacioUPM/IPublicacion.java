package espacioUPM;//


public interface IPublicacion {
	void verComentarios();
	
	void comentar(Usuario autor, String contenido);
	void like(Usuario usuario);
	
	void dislike(Usuario usuario);
	
	void referenciar(Usuario usuario);
}
