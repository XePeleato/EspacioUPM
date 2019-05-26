package espacioUPM;

import espacioUPM.Comunidades.Comunidad;
import espacioUPM.Comunidades.IComunidad;
import espacioUPM.Database.*;
import espacioUPM.Publicaciones.*;
import espacioUPM.Usuarios.IUsuario;
import espacioUPM.Usuarios.Usuario;
import org.junit.*;

import java.sql.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class DBTest {

    private DB_Main DB;
    private Connection connection;
    private IUsuario us;
    private IPublicacion p;
    private IComunidad comunidad;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}
    private static final byte[] testValues = new byte[] {(byte) 0xFF};

    @AfterClass
    public static void tearDownAfterClass() throws Exception {}

    @Before
    public void setUp() throws Exception {
        connection = DriverManager.getConnection("jdbc:mysql://37.187.200.26:8080/twitter2?user=serv&password=Habichuelas73");
        DB = DB_Main.getInstance();
        us = new Usuario("usuarioTest"); // contraseña "test"
        p = new PublicacionTexto("usuarioTest", "publicacionTest");
        ((PublicacionTexto) p).setIDPublicacion(89); // valor de esa publicacion
        comunidad = new Comunidad("comunidadTest");
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void TestSetUsuario() {
        DB.setUsuario("test", "test@test.com", "test");

        try (PreparedStatement pStmt = connection.prepareStatement("SELECT alias, correo FROM `Usuarios` WHERE `alias` = ?"))
        {
            pStmt.setString(1, "test");

            ResultSet rs = pStmt.executeQuery();

            assertTrue(rs.next());
            assertEquals(rs.getString("alias"), "test");
            assertEquals(rs.getString("correo"), "test@test.com");


        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement pStmt = connection.prepareStatement("DELETE FROM usuarios WHERE alias = ?");
            pStmt.setString(1, "test");
            pStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void TestGetUsuario(){
        IUsuario user = DB.getUsuario("usuarioTest");
        assertNotNull(user);
        assertEquals(user.getAlias(), "usuarioTest");
    }

    @Test
    public void TestBuscarUsuario(){
        IUsuario[] users = DB.buscarUsuario("usuarioTest");
        boolean encontrado = false;
        for(IUsuario user : users) {
            if(!encontrado)
                encontrado = user.getAlias().equals("usuarioTest");
        }
        assertTrue(encontrado);
    }

    @Test
    public void TestSetPublicacion() {
        PublicacionTexto p1 = new PublicacionTexto(us.getAlias(), "hola");
        DB.setPublicacion(p1);
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM publicaciones WHERE autor = ? AND cuerpo = ?");
            statement.setString(1, us.getAlias());
            statement.setString(2, "hola");
            ResultSet rs = statement.executeQuery();
            assertTrue(rs.next());
            assertEquals(rs.getInt("id"), p1.getIDPublicacion());
            assertEquals(rs.getString("autor"), p1.getAutor());
            assertEquals(rs.getString("cuerpo"), p1.getContenido());
        } catch(SQLException e) { e.printStackTrace(); }
        try
        {
            PreparedStatement pStmt = connection.prepareStatement("DELETE FROM publicaciones WHERE id = ?");
            pStmt.setInt(1, p1.getIDPublicacion());
            pStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestGetPublicacion() {
        IPublicacion p1 = DB.getPublicacion(p.getIDPublicacion());
        assertNotNull(p1);
        assertEquals(p1.getIDPublicacion(), p.getIDPublicacion());
        assertEquals(p1.getAutor(), p.getAutor());
    }

    @Test
    public void TestGetPublicaciones() {
        IPublicacion[] pubs = DB.getPublicaciones(us);
        try {
            assertEquals(p.getIDPublicacion(), pubs[0].getIDPublicacion());
        } catch(ArrayIndexOutOfBoundsException e) {
            fail("No existe la publicación");
        }
    }

    @Test
    public void TestGetComentarios() {
        IComentario c = DB.getComentarios(p.getIDPublicacion()).get(0);
        assertNotNull(c);
        assertEquals(c.getContenido(), "comentarioTest");
        assertEquals(c.getAutor(), us.getAlias());
        assertEquals(c.getPublicacionMadre(), p.getIDPublicacion());
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
        IPublicacion p1 = new PublicacionTexto("usuarioTest", "jeje");
        DB.setPublicacion(p1);
        DB.borrarPublicacion(p1.getIDPublicacion());
        assertNull(DB.getPublicacion(p1.getIDPublicacion()));
    }

    @Test
    public void TestGetSeguidos() {
        DB.setUsuario("usSeguido","usSeguido@test.com",testValues,testValues);
        DB.seguir("usuarioTest","usSeguido");
        assertEquals("usSeguido", DB.getSeguidos(us)[0]);
        DB.borrarUsuario(new Usuario("usSeguido"));
    }

    @Test
    public void TestgetSeguidores() {
        DB.setUsuario("usSeguidor","usSeguidor@test.com",testValues,testValues);
        DB.seguir("usSeguidor","usuarioTest");
        assertEquals("usSeguidor", DB.getSeguidores(us)[0]);
        DB.borrarUsuario(new Usuario("usSeguidor"));
    }

    @Test
    public void TestCambiarAlias() {
        DB.cambiarAlias(us, "aliasNuevo");
        assertEquals("aliasNuevo",DB.getUsuario("aliasNuevo").getAlias());
        DB.cambiarAlias(new Usuario("aliasNuevo"), "usuarioTest");
    }

    @Test
    public void TestBorrarMiembroComunidad() {
        DB.setUsuario("miembro","miembro@test.com",testValues,testValues);

        DB.insertarMiembroComunidad("comunidadTest","miembro");
        DB.borrarMiembroComunidad("comunidadTest","miembro");
        assertEquals(DB.getMiembros(comunidad).length, 1);
        DB.borrarUsuario(new Usuario("miembro"));
    }

    @Test
    public void TestInsertarMiembroComunidad() {
        DB.setUsuario("miembro","miembro@test.com",testValues,testValues);

        DB.insertarMiembroComunidad("comunidadTest","miembro");
        assertEquals("miembro",DB.getMiembros(comunidad)[1].getAlias());
        DB.borrarMiembroComunidad("comunidadTest", "miembro");
        DB.borrarUsuario(new Usuario("miembro"));
    }

    @Test
    public void TestSeguir() {
        DB.setUsuario("us2","us2@test.com",testValues,testValues);
        DB.seguir("us2","usuarioTest");
        assertEquals("us2",DB.getSeguidores(us)[0]);
        DB.borrarUsuario(new Usuario("us2"));
    }

    @Test
    public void TestDejarDeSeguir() {
        DB.setUsuario("us2","us2@test.com",testValues,testValues);
        DB.seguir("us2","usuarioTest");
        DB.dejarDeSeguir("us2","usuarioTest");
        assertEquals(DB.getSeguidores(us).length, 0);
        DB.borrarUsuario(new Usuario("us2"));
    }

    @Test
    public void TestEstaSiguiendo() {
        DB.setUsuario("us2","us2@test.com",testValues,testValues);
        DB.seguir("us2","usuarioTest");
        assertTrue(DB.estaSiguiendo("us2","usuarioTest"));
        DB.borrarUsuario(new Usuario("us2"));
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
        IUsuario[] miembros = DB.getMiembros(comunidad);
        assertEquals(1, miembros.length);
        assertEquals(us.getAlias(), miembros[0].getAlias());
    }

    @Test
    public void TestGetTimelineComunidad() {
        IPublicacion[] timeline = DB.getTimeline(comunidad);
        assertEquals(1, timeline.length);
        assertEquals(p.getIDPublicacion(), timeline[0].getIDPublicacion());
    }

    @Test
    public void TestBuscarComunidad() {
        IComunidad[] coms = DB.buscarComunidad("comunidadTest");
        assertEquals(1, coms.length);
        assertEquals("comunidadTest", coms[0].getNombre());
    }

    @Test
    public void TestComentar() {
        DB.comentar(p,us,"adios");
        assertEquals("adios",DB.getComentarios(p.getIDPublicacion()).get(1).getContenido());
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM comentarios WHERE id_publicacion = ? and texto = ?");
            statement.setInt(1, p.getIDPublicacion());
            statement.setString(2, "adios");
            statement.execute();
        }
        catch(SQLException e) { e.printStackTrace(); }
    }

}
