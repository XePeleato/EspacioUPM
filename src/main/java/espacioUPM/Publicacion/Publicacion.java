package espacioUPM.Publicacion;

import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_Publicacion;
import espacioUPM.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Publicacion implements IPublicacion {
	private String IDPublicacion;
	private String autor;
	private LocalDateTime fecha;
	private ArrayList<Comentario> comentarios;
	private int numLikes;
	private int numDislikes;

	private static final IDB_Publicacion DB = DB_Main.getInstance();

	public Publicacion(String autor) {
	    IDPublicacion = DB.getNewID();
        this.autor = autor;
        this.fecha = LocalDateTime.now();
        this.comentarios = new ArrayList<>();
        this.numLikes = 0;
        this.numDislikes = 0;
    }

	public Publicacion(String idPublicacion, String autor, LocalDateTime fecha, ArrayList<Comentario> comentarios,
                       int numLikes, int numDislikes)
	{
		IDPublicacion = idPublicacion;
		this.autor = autor;
		this.fecha = fecha;
		this.comentarios = comentarios;
		this.numLikes = numLikes;
		this.numDislikes = numDislikes;
	}

	public void verComentarios() {

	}

	public void comentar(Usuario autor, String contenido) {

	}

	public void like(Usuario usuario) {
	    int puntuacionAnterior = DB.getPuntuacion(usuario, this);
	    if (puntuacionAnterior == 0 || puntuacionAnterior == -1)
            DB.puntuar(usuario, this, 1);
	    else DB.puntuar(usuario, this, 0); // Si el usuario da like cuando ya había like este se quita
	}

	public void dislike(Usuario usuario) {
        int puntuacionAnterior = DB.getPuntuacion(usuario, this);
        if (puntuacionAnterior == 0 || puntuacionAnterior == 1)
            DB.puntuar(usuario, this, -1);
        else DB.puntuar(usuario, this, 0); // Si el usuario da dislike cuando ya había dislike este se quita
	}

	public void mostrarPublicacion() {

	}

	public void referenciar(Usuario usuario) {
	
	}

	public String getIDPublicacion() {
		return IDPublicacion;
	}

	public String getAutor() {
		return autor;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public ArrayList<Comentario> getComentarios() {
		return comentarios;
	}

	public int getNumLikes() {
		return numLikes;
	}

	public int getNumDislikes() {
		return numDislikes;
	}
}
