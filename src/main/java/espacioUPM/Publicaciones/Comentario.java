package espacioUPM.Publicaciones;//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Comentario.java
//  @ Date : 5/9/2019
//  @ Author : 
//
//



public class Comentario implements IComentario {

	private String autor;
	private String contenido;
	private String pub_id;

	public Comentario(String autor, String contenido, String pub_id) {
		this.autor = autor;
		this.contenido = contenido;
		this.pub_id = pub_id;
	}

	public String getPublicacionMadre() { return pub_id; }

	public String getAutor() { return autor; }
	
	public String getContenido() { return contenido; }
}