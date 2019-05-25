package espacioUPM.Publicaciones;

import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_Publicacion;
import espacioUPM.UI.MainController;
import espacioUPM.Usuarios.IUsuario;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Publicacion implements IPublicacion, Comparable {
    private int IDPublicacion;
    private String autor;
    private LocalDateTime fecha;
    private ArrayList<IComentario> comentarios;
    private int numLikes;
    private int numDislikes;

    private static final IDB_Publicacion DB = DB_Main.getInstance();
    private static final MainController controller = MainController.getInstance();

    public Publicacion(String autor) {
        IDPublicacion =  0;
        this.autor = autor;
        this.fecha = LocalDateTime.now();
        this.comentarios = new ArrayList<>();
        this.numLikes = 0;
        this.numDislikes = 0;
    }

    public Publicacion(int idPublicacion, String autor, LocalDateTime fecha, ArrayList<IComentario> comentarios,
                       int numLikes, int numDislikes)
    {
        IDPublicacion = idPublicacion;
        this.autor = autor;
        this.fecha = fecha;
        this.comentarios = comentarios;
        this.numLikes = numLikes;
        this.numDislikes = numDislikes;
    }

    public void comentar(IUsuario autor, String contenido) {
        Comentario comentario = new Comentario(autor.getAlias(),contenido, IDPublicacion);
        comentarios.add(comentario);
        DB.comentar(this, controller.getThisUser(), comentario.getContenido());
    }

    public void like(IUsuario usuario) {
        Puntuacion puntuacionAnterior = DB.getPuntuacion(usuario, IDPublicacion);
        if(puntuacionAnterior == Puntuacion.NEUTRO) {
            numLikes++;
            DB.puntuar(usuario, IDPublicacion, Puntuacion.LIKE);
        }
        else if(puntuacionAnterior == Puntuacion.LIKE) {
            numLikes--;
            DB.puntuar(usuario, IDPublicacion, Puntuacion.NEUTRO); // Si ya había like este se quita
        }
        else {
            numDislikes--;
            numLikes++;
            DB.puntuar(usuario, IDPublicacion, Puntuacion.LIKE);
        }
    }

    public void dislike(IUsuario usuario) {
        Puntuacion puntuacionAnterior = DB.getPuntuacion(usuario, IDPublicacion);
        if(puntuacionAnterior == Puntuacion.NEUTRO) {
            numDislikes++;
            DB.puntuar(usuario, IDPublicacion, Puntuacion.DISLIKE);
        }
        else if(puntuacionAnterior == Puntuacion.DISLIKE) {
            numDislikes--;
            DB.puntuar(usuario, IDPublicacion, Puntuacion.NEUTRO); // Si ya había like este se quita
        }
        else {
            numDislikes++;
            numLikes--;
            DB.puntuar(usuario, IDPublicacion, Puntuacion.DISLIKE);
        }
    }

    public void referenciar(IUsuario usuario) {
        if(this instanceof PublicacionReferencia)
            PublicacionFactory.createPublicacion(usuario.getAlias(), "/ref" + ((PublicacionReferencia)this).getPublicacionRef().getIDPublicacion());
        else PublicacionFactory.createPublicacion(usuario.getAlias(), "/ref" + IDPublicacion);
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

    public ArrayList<IComentario> getComentarios() {
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

    public void borrar() {
        DB.borrarPublicacion(IDPublicacion);
    }

    public Puntuacion getPuntuacion(IUsuario user) {
        return DB.getPuntuacion(user, this.getIDPublicacion());
    }
}
