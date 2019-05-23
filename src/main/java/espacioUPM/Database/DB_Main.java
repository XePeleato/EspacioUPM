package espacioUPM.Database;

import espacioUPM.Comunidad;
import espacioUPM.Publicaciones.*;
import espacioUPM.Usuario;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class DB_Main implements IDB_Usuario, IDB_Comunidad, IDB_Publicacion, IDB_PasswordHandler {
    private Connection connection;
    private static DB_Main instancia;
    private  DB_Main() {

        try {
            connection = DriverManager.getConnection("jdbc:mysql://37.187.200.26:8080/twitter2?user=serv&password=Habichuelas73");
            System.out.println("[+] DB Conectada.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static DB_Main getInstance() {

        if (instancia == null) {
            instancia = new DB_Main();
        }
        return instancia;
    }

    public Usuario getUsuario(String alias) {

        try (PreparedStatement pStmt = connection.prepareStatement("SELECT alias FROM `Usuarios` WHERE `alias` = ?"))
        {
            pStmt.setString(1, alias);


            ResultSet ret = pStmt.executeQuery();

            return ret.next() ? new Usuario(ret.getString("alias")) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Usuario[] buscarUsuario(String alias) {
        try (PreparedStatement pStmt = connection.prepareStatement("SELECT alias FROM `Usuarios` WHERE `alias` LIKE(?)"))
        {
            pStmt.setString(1, '%' + alias + '%');


            ResultSet rs = pStmt.executeQuery();

            LinkedList<Usuario> ret = new LinkedList<>();
            while (rs.next())
                ret.add(new Usuario(rs.getString("alias")));

            return ret.toArray(Usuario[]::new); //no cambiar esto, que peta
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setUsuario(String alias, String correo, byte[] password, byte[] salt) {
        try (PreparedStatement pStmt = connection.prepareStatement("INSERT INTO `Usuarios` VALUES (?, ?, ?, ?)")) {
            pStmt.setString(1, alias);
            pStmt.setString(2, correo);
            pStmt.setBytes(3, password);
            pStmt.setBytes(4, salt);

            return pStmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setUsuario(String alias, String correo, String pass) {
        Random r = new SecureRandom();
        byte[] salt = new byte[16];

        r.nextBytes(salt);
        byte[] password = hash(pass, salt);

       return setUsuario(alias, correo, password, salt);
    }

    public String getNewID() {
        try (PreparedStatement pStmt = connection.prepareStatement("NEXT VALUE FOR numeroPublicacionID AS id")) {
            ResultSet rs = pStmt.executeQuery();
            rs.next();

            return "/pub"+ rs.getInt("id");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setPublicacion(Publicacion publi) {


        try (PreparedStatement pStmt = connection.prepareStatement("INSERT INTO `publicaciones` VALUES (NULL, ?, ?, ?, ?)")) {
            pStmt.setString(1, publi.getAutor());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            pStmt.setString(2, publi.getFecha().format(formatter));
            if (publi instanceof PublicacionTexto) {
                pStmt.setString(3, ((PublicacionTexto) publi).getContenido());
                pStmt.setNull(4, Types.INTEGER);
            } else if (publi instanceof PublicacionReferencia) {
                    pStmt.setInt(4, ((PublicacionReferencia)publi).getPublicacionRef().getIDPublicacion());
                    pStmt.setNull(3, Types.VARCHAR);
            }
            else {
                pStmt.setString(3, ((PublicacionEnlace) publi).getUrl());
                pStmt.setNull(4, Types.INTEGER);
            }

            return pStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public Publicacion getPublicacion(int id) {
        Publicacion ret = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM publicaciones WHERE id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                String autor = rs.getString("autor");

                String cuerpo = rs.getString("id_ref") != null ?
                        "/ref" + rs.getString("id_ref") :
                        rs.getString("cuerpo");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime date = LocalDateTime.parse(rs.getString("fecha"), formatter);
                ArrayList<IComentario> comentarios = getComentarios(id);
                int numLikes = getLikes(id);
                int numDislikes = getDislikes(id);
                ret = PublicacionFactory.createPublicacion(id, cuerpo, autor, date, comentarios, numLikes, numDislikes);
            }
        }
        catch(SQLException e) { e.printStackTrace(); }

        return ret;
    }

    public Publicacion[] getPublicaciones(Usuario usuario) {

        try (PreparedStatement pStmt = connection.prepareStatement("SELECT id FROM publicaciones WHERE autor = ? ORDER BY fecha DESC"))
        {
            pStmt.setString(1, usuario.getAlias());
            ResultSet rs = pStmt.executeQuery();
            ArrayList<Publicacion> ret = new ArrayList<>();
            while(rs.next()) {
                ret.add(getPublicacion(rs.getInt("id")));
            }
            return ret.toArray(Publicacion[]::new); // dejad esto así, que hacer un cast peta
        }
        catch (SQLException e) { e.printStackTrace(); }

        return null;
    }

    public ArrayList<IComentario> getComentarios(int publication_id) {
        ArrayList<IComentario> ret = new ArrayList<>();
        try {
            PreparedStatement comentarios = connection.prepareStatement("SELECT * FROM comentarios WHERE id_publicacion = ?");
            comentarios.setInt(1, publication_id);
            ResultSet rs = comentarios.executeQuery();
            while(rs.next()) {
                String autor = rs.getString("alias");
                String contenido = rs.getString("texto");
                ret.add(new Comentario(autor,contenido,publication_id));
            }
        }
        catch(SQLException e) { e.printStackTrace(); }

        return ret;
    }

    public int getLikes(int publication_id) {
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS numero FROM likes WHERE id_publicacion = ? AND valor = 1");
            statement.setInt(1, publication_id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            System.out.println("se piden likes de " + publication_id + ": " + rs.getInt("numero"));
            return rs.getInt("numero");
        }
        catch(SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public int getDislikes(int publication_id) {
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS numero FROM likes WHERE id_publicacion = ? AND valor = -1");
            statement.setInt(1, publication_id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("numero");
        }
        catch(SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public void borrarPublicacion(int publi) {
        try
        {
            PreparedStatement pStmt = connection.prepareStatement("DELETE FROM publicaciones WHERE id = ?");
            pStmt.setInt(1, publi);
            pStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String[] getSeguidos(Usuario usuario) {
        ArrayList<String> ret = new ArrayList<>();
        try (PreparedStatement pStmt = connection.prepareStatement("SELECT seguido FROM seguimiento WHERE seguidor = ?")) {
            pStmt.setString(1, usuario.getAlias());

            ResultSet rs = pStmt.executeQuery();

            while (rs.next())
                ret.add(rs.getString("seguido"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret.toArray(String[]::new); // dejad esto así, que hacer un cast peta
    }

    public String[] getSeguidores(Usuario usuario) {
        ArrayList<String> ret = new ArrayList<>();
        try (PreparedStatement pStmt = connection.prepareStatement("SELECT seguidor FROM seguimiento WHERE seguido = ?")) {
            pStmt.setString(1, usuario.getAlias());

            ResultSet rs = pStmt.executeQuery();

            while (rs.next())
                ret.add(rs.getString("seguidor"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret.toArray(String[]::new); // dejad esto así, que hacer un cast peta
    }

    public boolean cambiarAlias(Usuario usuario, String aliasNuevo) {
        try (PreparedStatement pStmt = connection.prepareStatement("UPDATE usuarios SET alias = ? WHERE alias = ?")) {
            pStmt.setString(1, aliasNuevo);
            pStmt.setString(2, usuario.getAlias());
            return pStmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean borrarUsuario(Usuario usuario) {
        try {
            PreparedStatement pStmt = connection.prepareStatement("DELETE FROM usuarios WHERE alias = ?");
            pStmt.setString(1, usuario.getAlias());
            pStmt.execute();
            return pStmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean borrarMiembroComunidad(String id, String alias) {
        try {
            PreparedStatement pStmt = connection.prepareStatement("DELETE FROM miembros_comunidad WHERE id_usuario = ? AND id_comunidad = ?");
            pStmt.setString(1, alias);
            pStmt.setString(2, id);
            return pStmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertarMiembroComunidad(String id, String alias) {
        try {
            PreparedStatement pStmt = connection.prepareStatement("INSERT INTO miembros_comunidad VALUES (?, ?, ?)");
            pStmt.setString(1, alias);
            pStmt.setString(2, id);
            pStmt.setBoolean(3, false);
            return pStmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean aceptarMiembroComunidad(String id, String alias) {
        try (PreparedStatement pStmt = connection.prepareStatement("UPDATE miembros_comunidad SET aceptado = 1 WHERE id_usuario = ? AND id_comunidad = ?")) {
            pStmt.setString(1, alias);
            pStmt.setString(2, id);
            return pStmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean seguir(String seguidor, String seguido) {
        try (PreparedStatement pStmt = connection.prepareStatement("INSERT INTO seguimiento VALUES (?, ?)")){
            pStmt.setString(1, seguidor);
            pStmt.setString(2, seguido);
            return pStmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean dejarDeSeguir(String seguidor, String seguido) {
        try (PreparedStatement pStmt = connection.prepareStatement("DELETE FROM seguimiento WHERE seguidor = ? AND seguido = ?")) {
            pStmt.setString(1, seguidor);
            pStmt.setString(2, seguido);
            return pStmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean estaSiguiendo(String aliasSeguidor, String aliasSeguido) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS num FROM seguimiento WHERE seguidor = ? AND seguido = ?");
            statement.setString(1, aliasSeguidor);
            statement.setString(2, aliasSeguido);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                return rs.getInt("num") >= 1; // El mayor no debería ocurrir, pero y si sí?
            }
            return false;
        }
        catch(SQLException e) { e.printStackTrace(); }
        return false;
    }

    public void puntuar(Usuario usuario, int publi, Puntuacion puntuacion) {
        try {
            PreparedStatement delete = connection.prepareStatement("DELETE FROM likes WHERE id_usuario = ? AND id_publicacion = ?");
            delete.setString(1, usuario.getAlias());
            delete.setInt(2, publi);
            delete.execute();

            PreparedStatement insert = connection.prepareStatement("INSERT INTO likes VALUES (?, ?, ?)");
            insert.setString(1, usuario.getAlias());
            int puntuacionInt = 0;
            switch (puntuacion) {
                case LIKE:
                    puntuacionInt = 1;
                    break;
                case DISLIKE:
                    puntuacionInt = -1;
                    break;
                default:
                    break;
            }
            insert.setInt(2, publi);
            insert.setInt(3, puntuacionInt);
            insert.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Puntuacion getPuntuacion(Usuario usuario, int publi) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT valor FROM likes WHERE id_usuario = ? AND id_publicacion = ?");
            statement.setString(1, usuario.getAlias());
            statement.setInt(2, publi);

            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                switch (rs.getInt("valor")) {
                    case 1:
                        return Puntuacion.LIKE;
                    case -1:
                        return Puntuacion.DISLIKE;
                    default:
                        return Puntuacion.NEUTRO;
                }
            }
            return Puntuacion.NEUTRO;
        }
        catch(SQLException e) { e.printStackTrace(); }
        return Puntuacion.NEUTRO;
    }

    public boolean hacerAdminComunidad(String id, String alias) {
        try {
            PreparedStatement pStmt = connection.prepareStatement("UPDATE miembros_comunidad SET admin = 1 WHERE id_comunidad = ? AND id_usuario = ?");
            pStmt.setString(1, alias);
            pStmt.setString(2, id);
            return pStmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario[] getMiembros(Comunidad comunidad) {
        ArrayList<Usuario> ret = new ArrayList<>();
        try {
            PreparedStatement pStmt = connection.prepareStatement("SELECT id_usuario FROM miembros_comunidad WHERE id_comunidad = ?");
            pStmt.setString(1, comunidad.getNombre());

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                ret.add(getUsuario(rs.getString("id_usuario")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (Usuario[])ret.toArray();
    }

    public Publicacion[] getTimeline(Comunidad comunidad) {
        ArrayList<Publicacion> ret = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT p.id AS pubid, p.fecha AS fec " +
                                                                          "FROM publicaciones AS p, miembros_comunidad AS mc" +
                                                                          "WHERE p.autor = mc.id_usuario AND ? = mc.id_comunidad" +
                                                                          "ORDER BY fec DESC");
            statement.setString(1, comunidad.getNombre());
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                ret.add(getPublicacion(rs.getInt("pubid")));
            }
            return ret.toArray(Publicacion[]::new); // dejad esto así, que hacer un cast peta
        }
        catch(SQLException e) { e.printStackTrace(); }
        return null;
    }

    public Comunidad[] buscarComunidad(String id) {
        LinkedList<Comunidad> ret = new LinkedList<>();
        try (PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM comunidades WHERE id LIKE(?)"))
        {
            pStmt.setString(1, '%' + id + '%');
            ResultSet rs = pStmt.executeQuery();

            while (rs.next())
                ret.add(new Comunidad(rs.getString("id"), new Usuario(rs.getString("fundador"))));

            return ret.toArray(Comunidad[]::new); //no cambiar esto, que peta
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void comentar(Publicacion publi, Usuario usuario, String contenido) {
        try {
            PreparedStatement pStmt = connection.prepareStatement("INSERT INTO comentarios VALUES (NULL, ?, ?, ?)");
            pStmt.setString(1, usuario.getAlias());
            pStmt.setString(2, contenido);
            pStmt.setInt(3, publi.getIDPublicacion());
            pStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean comprobarPasswd(String alias, String passwd) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT contrasenya, salt FROM Usuarios WHERE alias = ?");
            statement.setString(1, alias);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                return Arrays.equals(hash(passwd, rs.getBytes("salt")), rs.getBytes("contrasenya"));
            }
        }
        catch(SQLException e) { e.printStackTrace(); }
        return false;
    }

    private byte[] hash(String txt, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(txt.toCharArray(), salt, 10000, 256);

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }
}
