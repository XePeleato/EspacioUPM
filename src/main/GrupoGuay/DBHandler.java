package GrupoGuay;

import GrupoGuay.Modelos.Publicacion;
import GrupoGuay.Modelos.Usuario;

import java.sql.*;

public class DBHandler {
    private Connection mConnection;
    private static DBHandler mInstancia;

    private DBHandler() {
        try {
            mConnection = DriverManager.getConnection("jdbc:mysql://37.187.200.26:3307/twitter2?user=serv&password=Habichuelas73");
            System.out.println("[+] DB Conectada.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBHandler getDefault() {
        if (mInstancia == null) {
            mInstancia = new DBHandler();
        }
        return mInstancia;
    }

    public Usuario getUsuario(String alias) {
        try (PreparedStatement pStmt = mConnection.prepareStatement("SELECT * FROM `Usuarios` WHERE `alias` = ?"))
        {
            pStmt.setString(1, alias);


            ResultSet ret = pStmt.executeQuery();

            return ret.next() ? new Usuario(ret.getString("alias"), ret.getString("correoupm"), ret.getBytes("contrasenya"), ret.getBytes("salt")) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setUsuario(Usuario usuario) {
        try (PreparedStatement pStmt = mConnection.prepareStatement("INSERT INTO `Usuarios` VALUES (NULL, ?, ?, ?, ?)")) {
            pStmt.setString(1, usuario.getAlias());
            pStmt.setString(2, usuario.getCorreo());
            pStmt.setBytes(3, usuario.getPassword());
            pStmt.setBytes(4, usuario.getSalt());

            return pStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setPublicacion(Publicacion publicacion) {
        try (PreparedStatement pStmt = mConnection.prepareStatement("INSERT INTO `publicaciones` VALUES (NULL, ?, ?, ?, ?)")) {
            pStmt.setString(1, publicacion.getAutor());
            pStmt.setString(2, publicacion.getFecha().toString());
            pStmt.setString(3, publicacion.getCuerpo() != null ? publicacion.getCuerpo() : "NULL");
            if (publicacion.getReferencia() != null)
                pStmt.setInt(4, publicacion.getReferencia().getId());
            else
                pStmt.setNull(4, Types.INTEGER);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
