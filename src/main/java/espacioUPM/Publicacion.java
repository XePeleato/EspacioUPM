package espacioUPM;//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Publicacion.java
//  @ Date : 5/9/2019
//  @ Author : 
//
//


import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Publicacion extends IPublicacion {
	private String IDPublicacion;
	private String autor;
	private LocalDateTime fecha;
	private ArrayList<Comentario> comentarios;
	private int numLikes;
	private int numDislikes;

	private static final IDB_Publicacion DB = DB_Main.getInstance();

	public Publicacion(String autor) {
	    IDPublicacion = "0";//DB.getNewID(); TODO: implementar getNewID
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

	}

	public void dislike(Usuario usuario) {

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
