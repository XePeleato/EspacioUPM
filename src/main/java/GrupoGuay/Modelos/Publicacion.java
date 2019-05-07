package GrupoGuay.Modelos;

import java.time.LocalDateTime;

public class Publicacion {
    private int mId;
    private Usuario mAutor;
    private String mCuerpo;
    private LocalDateTime mFecha;
    private Publicacion mReferencia;

    public Publicacion(Usuario autor, String cuerpo, Publicacion referencia)
    {
        mAutor = autor;
        if (cuerpo != null)
            mCuerpo = cuerpo;
        else
            mReferencia = referencia;

        mFecha = LocalDateTime.now();
    }

    public int getId() {
        return mId;
    }

    public Usuario getAutor() {
        return mAutor;
    }

    public String getCuerpo() {
        return mCuerpo;
    }

    public LocalDateTime getFecha() {
        return mFecha;
    }

    public Publicacion getReferencia() {
        return mReferencia;
    }
}
