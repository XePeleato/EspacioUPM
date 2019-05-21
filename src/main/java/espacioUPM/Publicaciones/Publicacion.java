package espacioUPM.Publicaciones;

import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_Publicacion;
import espacioUPM.UI.MainController;
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
    private static final MainController controller = MainController.getInstance();

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
        Comentario comentario = new Comentario(autor.getAlias(),contenido,getIDPublicacion());
        comentarios.add(comentario);
        DB.comentar(this, controller.getThisUser(), comentario.getContenido());
    }

    public void like(Usuario usuario) {
        Puntuacion puntuacionAnterior = DB.getPuntuacion(usuario, this.getIDPublicacion());
        if(puntuacionAnterior == Puntuacion.NEUTRO) {
            numLikes++;
            DB.puntuar(usuario, this.getIDPublicacion(), Puntuacion.LIKE);
        }
        else if(puntuacionAnterior == Puntuacion.LIKE) {
            numLikes--;
            DB.puntuar(usuario, this.getIDPublicacion(), Puntuacion.NEUTRO); // Si ya había like este se quita
        }
        else {
            numDislikes--;
            numLikes++;
            DB.puntuar(usuario, this.getIDPublicacion(), Puntuacion.LIKE);
        }
    }

    public void dislike(Usuario usuario) {
        Puntuacion puntuacionAnterior = DB.getPuntuacion(usuario, this.getIDPublicacion());
        if(puntuacionAnterior == Puntuacion.NEUTRO) {
            numDislikes++;
            DB.puntuar(usuario, this.getIDPublicacion(), Puntuacion.DISLIKE);
        }
        else if(puntuacionAnterior == Puntuacion.DISLIKE) {
            numDislikes--;
            DB.puntuar(usuario, this.getIDPublicacion(), Puntuacion.NEUTRO); // Si ya había like este se quita
        }
        else {
            numDislikes++;
            numLikes--;
            DB.puntuar(usuario, this.getIDPublicacion(), Puntuacion.DISLIKE);
        }
    }

    public void mostrarPublicacion() {

    }

    public void referenciar(Usuario usuario) {
        PublicacionFactory.createPublicacion(usuario.getAlias(), "/ref" + IDPublicacion);
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

    public void borrar() {
        DB.borrarPublicacion(this.getIDPublicacion());
    }
}
