package espacioUPM.Comunidades;//


import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_Comunidad;
import espacioUPM.Publicaciones.IPublicacion;

import java.util.ArrayList;

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
		return DB.borrarMiembroComunidad(nombre, alias);
	}
	
	public IPublicacion[] obtenerTimelineCompartido(int pagina) {
		ArrayList<IPublicacion> ret = new ArrayList<>();
		IPublicacion[] publicaciones = DB.getTimeline(this);
		if(publicaciones == null) return null;
		for (int i = pagina*50, j = 0; i < publicaciones.length && j < 50; i++, j++)
			ret.add(publicaciones[i]);
		return ret.toArray(IPublicacion[]::new);
	}
	
	public String getNombre() {
		return nombre;
	}
}