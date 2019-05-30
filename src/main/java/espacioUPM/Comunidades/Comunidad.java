package espacioUPM.Comunidades;//


import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_Comunidad;
import espacioUPM.Publicaciones.IPublicacion;
import espacioUPM.Usuarios.IUsuario;
import javafx.beans.property.DoubleProperty;

import java.util.ArrayList;
import java.util.Random;

public class Comunidad implements IComunidad {
	private String nombre;
	private String fundador;
	private static final IDB_Comunidad DB = DB_Main.getInstance();

	public Comunidad(String id) {
		nombre = id;
	}

	public static IComunidad[] getComunidades(String alias) {
		return DB.getComunidades(alias);
	}

	public boolean crearComunidad(String fundador) {
		return DB.crearComunidad(this, fundador);
	}

	public static IComunidad[] buscar(String id) {
		return DB.buscarComunidad(id);
	}

	public boolean unirse(String alias) {
		return DB.insertarMiembroComunidad(nombre, alias);
	}
	
	public boolean salir(String alias) {
		IUsuario[] miembros = getMiembros();
		if(miembros.length != 1) {
			int numAdmins = 0;
			for (IUsuario usuario : miembros) {
				if (esAdmin(usuario.getAlias()))
					numAdmins++;
			}
			if (numAdmins == 1 && esAdmin(alias)) { //Si es el ultimo admin, ponemos un admin al azar
				Random r = new Random();
				int i = r.nextInt();
				while (miembros[i].getAlias().equals(alias)) {
					i = r.nextInt();
				}
				hacerAdmin(miembros[i].getAlias());
			}
		}
		return DB.borrarMiembroComunidad(nombre, alias);
	}
	
	public IPublicacion[] obtenerTimelineCompartido(int pagina, DoubleProperty progressProp) {
		ArrayList<IPublicacion> ret = new ArrayList<>();
		IPublicacion[] publicaciones = DB.getTimeline(this, progressProp);
		if(publicaciones == null) return null;
		for (int i = pagina*50, j = 0; i < publicaciones.length && j < 50; i++, j++)
			ret.add(publicaciones[i]);
		return ret.toArray(IPublicacion[]::new);
	}
	
	public String getNombre() {
		return nombre;
	}

	public boolean esMiembro(IUsuario user) {
		return DB.esMiembroComunidad(nombre, user.getAlias()) >= 0;
	}

	public boolean esMiembroAceptado(IUsuario user) {
		return  DB.esMiembroComunidad(nombre, user.getAlias()) > 0;
	}

	@Override
	public boolean aceptarMiembroNuevo(String alias) {
		return DB.aceptarMiembroComunidad(nombre, alias);
	}

	public void rechazarMiembro(String alias) {
		DB.rechazarMiembro(this.nombre, alias);
	}

	public void hacerAdmin(String alias) {
		DB.hacerAdmin(this.nombre, alias);
	}

	public boolean esAdmin(String alias) {
		return DB.esAdmin(this.nombre, alias);
	}

	public IUsuario[] getMiembros() {
		return DB.getMiembros(this);
	}
}
