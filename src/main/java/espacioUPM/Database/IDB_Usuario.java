package espacioUPM.Database;//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : IDB_Usuario.java
//  @ Date : 5/9/2019
//  @ Author : 
//
//


import espacioUPM.Usuarios.IUsuario;

public interface IDB_Usuario {
	 IUsuario getUsuario(String alias);

	 IUsuario[] buscarUsuario(String alias);

	 boolean setUsuario(String alias, String correo, byte[] password, byte[] salt);

	 boolean setUsuario(String alias, String correo, String pass);

	 String[] getSeguidos(IUsuario usuario);
	
	 String[] getSeguidores(IUsuario usuario);
	
	 boolean cambiarAlias(IUsuario usuario, String aliasNuevo);
	
	 boolean borrarUsuario(IUsuario usuario);
	
	 boolean seguir(String seguidor, String seguido);
	
	 boolean dejarDeSeguir(String seguidor, String seguido);

	 boolean estaSiguiendo(String aliasSeguidor, String aliasSeguido);

	 void borrarComentarios(IUsuario user);

	 void borrarComunidades(IUsuario user);
}
