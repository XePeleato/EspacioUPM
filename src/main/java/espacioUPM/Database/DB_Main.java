package espacioUPM.Database;

import espacioUPM.Comunidades.Comunidad;
import espacioUPM.Comunidades.IComunidad;
import espacioUPM.Publicaciones.*;
import espacioUPM.Usuarios.IUsuario;
import espacioUPM.Usuarios.Usuario;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;

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

    public IUsuario getUsuario(String alias) {

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

    public IUsuario[] buscarUsuario(String alias) {
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

    public boolean setPublicacion(IPublicacion publi) {


        try (PreparedStatement pStmt = connection.prepareStatement("INSERT INTO publicaciones VALUES (NULL, ?, ?, ?, ?)")) {
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

    public Publicacion[] getPublicaciones(IUsuario usuario, DoubleProperty progressProp) {

        try (PreparedStatement pStmt = connection.prepareStatement("SELECT id FROM publicaciones WHERE autor = ? ORDER BY fecha DESC"))
        {
            pStmt.setString(1, usuario.getAlias());
            ResultSet rs = pStmt.executeQuery();
            ArrayList<Publicacion> ret = new ArrayList<>();

            rs.last();
            int rows = rs.getRow();
            rs.beforeFirst();

            while(rs.next()) {
                if (progressProp != null) {
                    Platform.runLater(() -> {
                        try {
                            progressProp.set((double) rs.getRow() / (double) rows);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch(RuntimeException ignored) {}

                    });
                }
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

    public int getNewID() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT AUTO_INCREMENT \n" +
                    "FROM  INFORMATION_SCHEMA.TABLES\n" +
                    "WHERE TABLE_SCHEMA = 'twitter2'\n" +
                    "AND   TABLE_NAME   = 'publicaciones';");
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("auto_increment");

        } catch(SQLException e) { e.printStackTrace(); }
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

    public String[] getSeguidos(IUsuario usuario) {
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

    public String[] getSeguidores(IUsuario usuario) {
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

    public boolean cambiarAlias(IUsuario usuario, String aliasNuevo) {
        try (PreparedStatement pStmt = connection.prepareStatement("UPDATE usuarios SET alias = ? WHERE alias = ?")) {
            pStmt.setString(1, aliasNuevo);
            pStmt.setString(2, usuario.getAlias());
            return pStmt.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean borrarUsuario(IUsuario usuario) {
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
            if (pStmt.executeUpdate() == 1) {
                PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS numero FROM miembros_comunidad WHERE id_comunidad = ?");
                statement.setString(1, id);
                ResultSet rs = statement.executeQuery();
                if (rs.next() && rs.getInt("numero") == 0) {
                    PreparedStatement statement1 = connection.prepareStatement("DELETE FROM comunidades WHERE id = ?");
                    statement1.setString(1, id);
                    statement.execute();
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean crearComunidad(IComunidad comunidad, String fundador) {
        try (PreparedStatement pStmt = connection.prepareStatement("INSERT INTO comunidades VALUES (?, ?)"))
        {
            pStmt.setString(1, comunidad.getNombre());
            pStmt.setString(2, fundador);

            try (PreparedStatement pStmtMember = connection.prepareStatement("INSERT INTO miembros_comunidad VALUES (?, ?, 2)")) {
                pStmtMember.setString(1, fundador);
                pStmtMember.setString(2, comunidad.getNombre());

                if (pStmt.executeUpdate() == 1)
                    return pStmtMember.executeUpdate() == 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean insertarMiembroComunidad(String id, String alias) {
        try {
            PreparedStatement pStmt = connection.prepareStatement("INSERT INTO miembros_comunidad VALUES (?, ?, ?)");
            pStmt.setString(1, alias);
            pStmt.setString(2, id);
            pStmt.setInt(3, 0);
            return pStmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public IComunidad[] getComunidades(String alias)
    {
        ArrayList<IComunidad> ret = new ArrayList<>();
        try (PreparedStatement pStmt = connection.prepareStatement("SELECT id_comunidad FROM miembros_comunidad WHERE id_usuario = ? AND aceptado <> 0")) {
            pStmt.setString(1, alias);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next())
                ret.add(new Comunidad(rs.getString("id_comunidad")));

            return ret.toArray(Comunidad[]::new);
            } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean aceptarMiembroComunidad(String id, String alias) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE miembros_comunidad SET aceptado = 1 WHERE id_comunidad = ? AND id_usuario = ?");
            statement.setString(1, id);
            statement.setString(2, alias);
            return statement.executeUpdate() == 1;
        } catch(SQLException e) { e.printStackTrace(); }
        return false;
    }

    public int esMiembroComunidad(String id, String alias) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT aceptado FROM miembros_comunidad WHERE id_comunidad = ? AND id_usuario = ?");
            statement.setString(1, id);
            statement.setString(2, alias);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                return rs.getInt("aceptado");
            }
            else return -1;
        } catch(SQLException e) { e.printStackTrace(); }
        return -1;
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

    public void puntuar(IUsuario usuario, int publi, Puntuacion puntuacion) {
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
            if(puntuacion != Puntuacion.NEUTRO)
                insert.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Puntuacion getPuntuacion(IUsuario usuario, int publi) {
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

    public IUsuario[] getMiembros(IComunidad comunidad) {
        ArrayList<IUsuario> ret = new ArrayList<>();
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
        return ret.toArray(IUsuario[]::new);
    }

    public Publicacion[] getTimeline(IComunidad comunidad, DoubleProperty progressProp)
    {
        ArrayList<Publicacion> ret = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT p.id AS pubid, p.fecha AS fec " +
                                                                          "FROM publicaciones AS p, miembros_comunidad AS mc " +
                                                                          "WHERE p.autor = mc.id_usuario AND ? = mc.id_comunidad " +
                                                                          "AND mc.aceptado <> 0 " +
                                                                          "ORDER BY fec DESC");
            statement.setString(1, comunidad.getNombre());
            ResultSet rs = statement.executeQuery();

            /* Este bloque de aquí cuenta las publicaciones para la progressbar */
            rs.last();
            int rows = rs.getRow();
            rs.beforeFirst();

            while(rs.next()) {
                if (progressProp != null) {
                    Platform.runLater(() -> {
                        try {
                            progressProp.set((double) rs.getRow() / (double) rows);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
                }
                ret.add(getPublicacion(rs.getInt("pubid")));
            }
            return ret.toArray(Publicacion[]::new); // dejad esto así, que hacer un cast peta
        }
        catch(SQLException e) { e.printStackTrace(); }
        return null;
    }

    public IComunidad[] buscarComunidad(String id) {
        LinkedList<IComunidad> ret = new LinkedList<>();
        try (PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM comunidades WHERE id LIKE(?)"))
        {
            pStmt.setString(1, '%' + id + '%');
            ResultSet rs = pStmt.executeQuery();

            while (rs.next())
                ret.add(new Comunidad(rs.getString("id")));

            return ret.toArray(Comunidad[]::new); //no cambiar esto, que peta
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void borrarComentarios(IUsuario user) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM comentarios WHERE alias = ?");
            statement.setString(1, user.getAlias());
            statement.execute();
        }
        catch(SQLException e) { e.printStackTrace(); }
    }

    public void comentar(IPublicacion publi, IUsuario usuario, String contenido) {
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

    @Override
    public void rechazarMiembro(String id, String alias) {
        try {
            PreparedStatement pStmt = connection.prepareStatement("DELETE FROM miembros_comunidad WHERE id_comunidad = ? AND id_usuario = ?");
            pStmt.setString(1, id);
            pStmt.setString(2, alias);
            pStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hacerAdmin(String id, String alias) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE miembros_comunidad SET aceptado = 2 WHERE id_comunidad = ? AND id_usuario = ?");
            statement.setString(1, id);
            statement.setString(2, alias);
            statement.execute();
        } catch(SQLException e) { e.printStackTrace(); }
    }

    @Override
    public boolean esAdmin(String id, String alias) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT aceptado FROM miembros_comunidad WHERE id_comunidad = ? AND id_usuario = ?");
            statement.setString(1, id);
            statement.setString(2, alias);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
                return rs.getInt("aceptado") == 2;
            return false;
        } catch(SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public void borrarComunidades(IUsuario user) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM miembros_comunidad WHERE id_usuario = ?");
            statement.setString(1, user.getAlias());
            statement.executeUpdate();
        } catch(SQLException e) { e.printStackTrace(); }
    }
}
