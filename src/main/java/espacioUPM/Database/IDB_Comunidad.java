package espacioUPM.Database;//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : IDB_Comunidad.java
//  @ Date : 5/9/2019
//  @ Author : 
//
//


import espacioUPM.Comunidades.IComunidad;
import espacioUPM.Publicaciones.Publicacion;
import espacioUPM.Usuarios.IUsuario;

public interface IDB_Comunidad {
	boolean insertarMiembroComunidad(String id, String alias);
	
	boolean borrarMiembroComunidad(String id, String alias);

	boolean crearComunidad(IComunidad comunidad, String fundador);

	IUsuario[] getMiembros(IComunidad comunidad);

	Publicacion[] getTimeline(IComunidad comunidad);

	IComunidad[] buscarComunidad(String id);

	IComunidad[] getComunidades(String alias);
}
