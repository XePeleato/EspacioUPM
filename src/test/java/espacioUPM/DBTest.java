package espacioUPM;

import espacioUPM.Comunidades.Comunidad;
import espacioUPM.Comunidades.IComunidad;
import espacioUPM.Database.*;
import espacioUPM.Publicaciones.*;
import espacioUPM.Usuarios.IUsuario;
import espacioUPM.Usuarios.Usuario;
import org.junit.*;

import java.sql.*;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class DBTest {

    DB_Main DB;
    IUsuario us;
    IPublicacion p;
    IComunidad comunidad;
    Connection connection;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}
    private static final byte[] testValues = new byte[] {(byte) 0xFF};

    @AfterClass
    public static void tearDownAfterClass() throws Exception {}

    @Before
    public void setUp() throws Exception {
        connection = DriverManager.getConnection("jdbc:mysql://37.187.200.26:8080/twitter2?user=serv&password=Habichuelas73");
        DB = DB_Main.getInstance();
        us = new Usuario("test"); // Su contraseña es "test"
        p = new PublicacionTexto("test","hola");
        comunidad = new Comunidad("GrupoGuay");
    }

    @After
    public void tearDown() throws Exception {
        try {
            PreparedStatement pStmt = connection.prepareStatement("DELETE FROM usuarios WHERE alias = ?");
            pStmt.setString(1, us.getAlias());
            pStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void TestSetUsuario() {
        DB.setUsuario(us.getAlias(), "test@test.com", "test");

        try (PreparedStatement pStmt = connection.prepareStatement("SELECT alias, correo FROM `Usuarios` WHERE `alias` = ?"))
        {
            pStmt.setString(1, us.getAlias());

            ResultSet rs = pStmt.executeQuery();

            assertTrue(rs.next());
            assertEquals(rs.getString("alias"), us.getAlias());
            assertEquals(rs.getString("correo"), "test@test.com");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void TestGetUsuario(){
        IUsuario user = DB.getUsuario("test");
        assertNotNull(user);
        assertEquals(user.getAlias(), "test");
    }

    @Test
    public void TestBuscarUsuario(){
        IUsuario[] users = DB.buscarUsuario("test");
        boolean encontrado = false;
        for(IUsuario user : users) {
            if(!encontrado)
                encontrado = user.getAlias().equals("test");
        }
        assertTrue(encontrado);
    }

    @Test
    public void TestSetPublicacion() {
        IPublicacion p1 = new PublicacionTexto("test", "hola2");
        DB.setPublicacion(p1);
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM publicaciones WHERE autor = ? AND cuerpo = ?");
            statement.setString(1, "test");
            statement.setString(2, "hola2");
            ResultSet rs = statement.executeQuery();
            assertTrue(rs.next());
            assertEquals(rs.getString("autor"), "test");
            assertEquals(rs.getString("cuerpo"), "hola2");
        } catch(SQLException e) { e.printStackTrace(); }
    }

    @Test
    public void TestGetPublicacion() {
    }

    @Test
    public void TestGetPublicaciones() {
        IPublicacion[] pubs = DB.getPublicaciones(us);
        boolean correcto = true;
        for(IPublicacion pub : pubs) {
            if(!pub.getAutor().equals("test"))
                correcto = false;
        }
        assertTrue(correcto);
    }

    @Test
    public void TestGetComentarios() {
        try {
            PreparedStatement pStmt = connection.prepareStatement("INSERT INTO comentarios VALUES (NULL, ?, ?, ?)");
            pStmt.setString(1, us.getAlias());
            pStmt.setString(2, "adios");
            pStmt.setInt(3, p.getIDPublicacion());
            pStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals("adios",DB.getComentarios(p.getIDPublicacion()).get(0).getContenido());
    }

    @Test
    public void TestGetLikes() {
        DB.puntuar(us, p.getIDPublicacion(), Puntuacion.LIKE);
        assertEquals(1, DB.getLikes(p.getIDPublicacion()));
        DB.puntuar(us, p.getIDPublicacion(), Puntuacion.NEUTRO);
    }

    @Test
    public void TestGetDislikes() {
        DB.puntuar(us, p.getIDPublicacion(), Puntuacion.DISLIKE);
        assertEquals(1, DB.getDislikes(p.getIDPublicacion()));
        DB.puntuar(us, p.getIDPublicacion(), Puntuacion.NEUTRO);
    }

    @Test
    public void TestBorrarPublicacion() {
        IPublicacion p1 = new PublicacionTexto("test", "jeje");
        DB.borrarPublicacion(p1.getIDPublicacion());
        assertNull(DB.getPublicacion(p1.getIDPublicacion()));
    }

    @Test
    public void TestGetSeguidos() {
        IUsuario usSeguido;
        DB.setUsuario("usSeguido","usSeguido@test.com",testValues,testValues);
        usSeguido = DB.getUsuario("usSeguido");
        DB.seguir("test","usSeguido");
        assertEquals("usSeguido", DB.getSeguidos(us)[0]);
        DB.borrarUsuario(usSeguido);
    }

    @Test
    public void TestgetSeguidores() {
        IUsuario usSeguidor;
        DB.setUsuario("usSeguidor","usSeguidor@test.com",testValues,testValues);
        usSeguidor = DB.getUsuario("usSeguidor");
        DB.seguir("usSeguidor","test");
        assertEquals("usSeguidor", DB.getSeguidores(us)[0]);
        DB.borrarUsuario(usSeguidor);
    }

    @Test
    public void TestCambiarAlias() {
        DB.cambiarAlias(us, "aliasNuevo");
        assertEquals("aliasNuevo",DB.getUsuario("aliasNuevo").getAlias());
    }

    @Test
    public void TestBorrarMiembroComunidad() {
        IUsuario miembro;
        DB.setUsuario("miembro","miembro@test.com",testValues,testValues);
        miembro = DB.getUsuario("miembro");

        DB.insertarMiembroComunidad("GrupoGuay","miembro");
        DB.borrarMiembroComunidad("GrupoGuay","miembro");
        assertNull(DB.getMiembros(comunidad)[1]);
        DB.borrarMiembroComunidad("GrupoGuay", miembro.getAlias());
        DB.borrarUsuario(miembro);

    }

    @Test
    public void TestInsertarMiembroComunidad() {
        IUsuario miembro;
        DB.setUsuario("miembro","miembro@test.com",testValues,testValues);
        miembro = DB.getUsuario("miembro");

        DB.insertarMiembroComunidad("GrupoGuay","miembro");
        assertEquals(miembro.getAlias(),DB.getMiembros(comunidad)[1].getAlias());
        DB.borrarUsuario(miembro);
    }

    @Test
    public void TestSeguir() {
        IUsuario us2;
        DB.setUsuario("us2","us2@test.com",testValues,testValues);
        us2 = DB.getUsuario("us2");

        DB.seguir("us2","test");
        assertEquals("us2",DB.getSeguidores(us)[0]);
        DB.borrarUsuario(us2);
    }

    @Test
    public void TestDejarDeSeguir() {
        IUsuario us2;
        DB.setUsuario("us2","us2@test.com",testValues,testValues);
        us2 = DB.getUsuario("us2");

        DB.seguir("us2","test");
        DB.dejarDeSeguir("us2","test");
        assertEquals(DB.getSeguidores(us).length, 0);
        DB.borrarUsuario(us2);
    }

    @Test
    public void TestEstaSiguiendo() {
        IUsuario us2;
        DB.setUsuario("us2","us2@test.com",testValues,testValues);
        us2 = DB.getUsuario("usSeguidor");

        DB.seguir("us2","test");
        assertTrue(DB.estaSiguiendo("us2","test"));
        DB.borrarUsuario(us2);
    }

    @Test
    public void TestPuntuar() {
        DB.puntuar(us, p.getIDPublicacion(), Puntuacion.LIKE);
        assertEquals(1, DB.getLikes(p.getIDPublicacion()));
        DB.puntuar(us, p.getIDPublicacion(), Puntuacion.NEUTRO);
    }

    @Test
    public void TestGetPuntuacion() {
        DB.puntuar(us, p.getIDPublicacion(), Puntuacion.LIKE);
        assertEquals(Puntuacion.LIKE, DB.getPuntuacion(us,p.getIDPublicacion()));
        DB.puntuar(us, p.getIDPublicacion(), Puntuacion.NEUTRO);

    }

    @Test
    public void TestGetMiembros() {
        assertEquals(us.getAlias(), DB.getMiembros(comunidad)[0].getAlias());
    }

    @Test
    public void TestGetTimeline() {
        assertEquals(p.getIDPublicacion(), DB.getTimeline(comunidad)[0].getIDPublicacion());
    }

    @Test
    public void TestBuscarComunidad() {
        assertEquals("GrupoGuay", DB.buscarComunidad("GrupoGuay")[0].getNombre());
    }

    @Test
    public void TestComentar() {
        DB.comentar(p,us,"adios");
        assertEquals("adios",p.getComentarios().get(0).getContenido());
    }

    }
