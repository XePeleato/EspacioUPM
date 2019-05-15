package espacioUPM.Publicaciones;

import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_Publicacion;
import espacioUPM.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Publicacion implements IPublicacion, Comparable {
	private int IDPublicacion;
	private String autor;
	private LocalDateTime fecha;
	private ArrayList<Comentario> comentarios;
	private int numLikes;
	private int numDislikes;

	private static final IDB_Publicacion DB = DB_Main.getInstance();

	public Publicacion(String autor) {
	    IDPublicacion =  0; //DB.getNewID(); //FIXME: ya hablaremos sobre esto, pero de momento explota
        this.autor = autor;
        this.fecha = LocalDateTime.now();
        this.comentarios = new ArrayList<>();
        this.numLikes = 0;
        this.numDislikes = 0;
    }

	public Publicacion(int idPublicacion, String autor, LocalDateTime fecha, ArrayList<Comentario> comentarios,
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
	    Puntuacion puntuacionAnterior = DB.getPuntuacion(usuario, this);
	    if (puntuacionAnterior == Puntuacion.NEUTRO || puntuacionAnterior == Puntuacion.DISLIKE)
            DB.puntuar(usuario, IDPublicacion, Puntuacion.LIKE);
	    else DB.puntuar(usuario, IDPublicacion, Puntuacion.NEUTRO); // Si el usuario da like cuando ya había like este se quita
	}

	public void dislike(Usuario usuario) {
        Puntuacion puntuacionAnterior = DB.getPuntuacion(usuario, this);
        if (puntuacionAnterior == Puntuacion.NEUTRO || puntuacionAnterior == Puntuacion.LIKE)
            DB.puntuar(usuario, IDPublicacion, Puntuacion.DISLIKE);
        else DB.puntuar(usuario, IDPublicacion, Puntuacion.NEUTRO); // Si el usuario da dislike cuando ya había dislike este se quita
	}

	public void mostrarPublicacion() {

	}

	public void referenciar(Usuario usuario) {
	
	}

	public int getIDPublicacion() {
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

	@Override
	public int compareTo(Object o) {
		if (!(o instanceof Publicacion)) return -1;

		return ((Publicacion) o).getFecha().compareTo(fecha);
	}
}
