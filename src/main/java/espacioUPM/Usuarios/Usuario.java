package espacioUPM.Usuarios;//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Usuario.java
//  @ Date : 5/9/2019
//  @ Author : 
//
//


import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_PasswordHandler;
import espacioUPM.Database.IDB_Publicacion;
import espacioUPM.Database.IDB_Usuario;
import espacioUPM.Publicaciones.IPublicacion;
import javafx.beans.property.DoubleProperty;

public class Usuario implements IUsuario {

    private String alias;
    private static final IDB_Publicacion DB = DB_Main.getInstance();
    private static final IDB_Usuario DB_user = DB_Main.getInstance();
    private static final IDB_PasswordHandler DB_pass = DB_Main.getInstance();

    public Usuario(String alias) {
        this.alias = alias;
    }

    public static IUsuario getUsuario(String alias) {
        return DB_user.getUsuario(alias);
    }

    public static void setUsuario(String alias, String correo, String passwd) {
        DB_user.setUsuario(alias, correo, passwd);
    }

    public static IUsuario[] buscar(String alias) {
        return DB_user.buscarUsuario(alias);
    }

    public boolean comprobarPasswd(String pass) {
        return DB_pass.comprobarPasswd(alias, pass);
    }

    public boolean sigueA(String aliasSeguido) {
        return DB_user.estaSiguiendo(this.alias, aliasSeguido);
    }

    public void seguir(String aliasSeguido) {
        DB_user.seguir(alias, aliasSeguido);
    }

    public void dejarDeSeguir(String aliasSeguido) {
        DB_user.dejarDeSeguir(alias, aliasSeguido);
    }

    public String[] getSeguidos() {
        return DB_user.getSeguidos(this);
    }

    public String[] getSeguidores() {
        return DB_user.getSeguidores(this);
    }

    public IPublicacion[] obtenerPerfil(DoubleProperty progressProp) {
        return DB.getPublicaciones(this, progressProp);
    }

    public String getAlias() {
        return alias;
    }

    public boolean cambiarAlias(String aliasNuevo) {
        return DB_user.cambiarAlias(this, aliasNuevo);
    }

    public void borrarDatos() {
        for(IPublicacion publi : DB.getPublicaciones(this, null)) {
            DB.borrarPublicacion(publi.getIDPublicacion());
        }
        DB_user.borrarComentarios(this);
    }

    public void borrar() {
        DB_user.borrarUsuario(this);
    }
}
