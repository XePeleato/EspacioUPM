package espacioUPM;

import espacioUPM.Database.*;
import espacioUPM.Publicaciones.*;
import org.junit.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class DBTest {

    DB_Main DB;
    Usuario us;
    Publicacion p;
    Comunidad comunidad;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception {}

    @Before
    public void setUp() throws Exception {
        DB = DB_Main.getInstance();

        byte[] testValues = new byte[] {(byte) 0xFF};
        DB.setUsuario("test", "test@test.com", testValues, testValues);
        us = DB.getUsuario("test");
        p = new PublicacionTexto("test","hola");
        comunidad = new Comunidad("GrupoGuay",us);
    }

    @After
    public void tearDown() throws Exception {
        DB.borrarUsuario(us);
        DB.borrarPublicacion(p.getIDPublicacion());
        DB.borrarMiembroComunidad("GrupoGuay","test");
    }

    @Test
    public void TestCrearUsuario() {
        assertNotNull(us);
        assertEquals(us.getAlias(), "test");
    }

    @Test
    public void TestBorrarUsuario() {
        DB.borrarUsuario(us);
        Usuario us2 = DB.getUsuario("test");
        assertNull(us2);
    }

    @Test
    public void TestGetUsuario(){
        assertEquals(us.getAlias(), "test");
    }

    @Test
    public void TestBuscarUsuario(){
        fail("No esta implementado todavia");
    }

    @Test
    public void TestSetUsuario() {
        byte[] testValues2 = new byte[] {(byte) 0xFF};
        DB.setUsuario("test", "test@test.com",testValues2, testValues2 );
        assertEquals(us.getAlias(), "test");
    }

    @Test
    public void TestGetNewID() {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestSetPublicacion() {
        assertTrue(DB.setPublicacion(p));
    }

    @Test
    public void TestGetPublicacion() {
        assertEquals("hola",((PublicacionTexto) p).getContenido());
    }

    @Test
    public void TestGetPublicaciones() {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestGetComentarios() {
        DB.comentar(p,us,"adios");
        assertEquals("adios",p.getComentarios().get(0).getContenido());
    }

    @Test
    public void TestGetLikes() {
        DB.puntuar(us, p.getIDPublicacion(), Puntuacion.LIKE);
        assertEquals(1, DB.getLikes(p.getIDPublicacion()));
        DB.puntuar(us, p.getIDPublicacion(), Puntuacion.LIKE);
    }

    @Test
    public void TestGetDislikes() {
        DB.puntuar(us, p.getIDPublicacion(), Puntuacion.DISLIKE);
        assertEquals(-1, DB.getDislikes(p.getIDPublicacion()));
        DB.puntuar(us, p.getIDPublicacion(), Puntuacion.DISLIKE);
    }

    @Test
    public void TestBorrarPublicacion() {
        DB.borrarPublicacion(p.getIDPublicacion());
        assertNull(p);
    }

    @Test
    public void TestGetSeguidos() {
        byte[] testValues2 = new byte[] {(byte) 0xFF};
        Usuario usSeguido;
        DB.setUsuario("usSeguido","usSeguido@test.com",testValues2,testValues2);
        usSeguido = DB.getUsuario("usSeguido");
        DB.seguir("test","usSeguido");
        assertEquals("usSeguido", DB.getSeguidos(us)[0]);
        DB.borrarUsuario(usSeguido);
    }

    @Test
    public void TestgetSeguidores() {
        byte[] testValues2 = new byte[] {(byte) 0xFF};
        Usuario usSeguidor;
        DB.setUsuario("usSeguidor","usSeguidor@test.com",testValues2,testValues2);
        usSeguidor = DB.getUsuario("usSeguidor");
        DB.seguir("usSeguidor","test");
        assertEquals("usSeguidor", DB.getSeguidores(us)[0]);
        DB.borrarUsuario(usSeguidor);
    }
    @Test
    public void TestCambiarAlias() {
        DB.cambiarAlias(us, "aliasNuevo");
        assertEquals(us,DB.getUsuario("aliasNuevo"));
    }

    @Test
    public void TestBorrarMiembroComunidad() {
        byte[] testValues2 = new byte[] {(byte) 0xFF};
        Usuario miembro;
        DB.setUsuario("miembro","miembro@test.com",testValues2,testValues2);
        miembro = DB.getUsuario("miembro");

        DB.insertarMiembroComunidad("GrupoGuay","miembro");
        DB.aceptarMiembroComunidad("GrupoGuay","miembro");
        DB.borrarMiembroComunidad("GrupoGuay","miembro");
        assertNull(DB.getMiembros(comunidad)[1]);
        DB.borrarUsuario(miembro);

    }

    @Test
    public void TestInsertarMiembroComunidad() {
        byte[] testValues2 = new byte[] {(byte) 0xFF};
        Usuario miembro;
        DB.setUsuario("miembro","miembro@test.com",testValues2,testValues2);
        miembro = DB.getUsuario("miembro");

        DB.insertarMiembroComunidad("GrupoGuay","miembro");
        DB.aceptarMiembroComunidad("GrupoGuay","miembro");
        assertEquals(miembro,DB.getMiembros(comunidad)[1]);
        DB.borrarUsuario(miembro);
    }

    @Test
    public void TestAceptarMiembroComunidad() {
        byte[] testValues2 = new byte[] {(byte) 0xFF};
        Usuario miembro;
        DB.setUsuario("miembro","miembro@test.com",testValues2,testValues2);
        miembro = DB.getUsuario("miembro");

        DB.insertarMiembroComunidad("GrupoGuay","miembro");
        DB.aceptarMiembroComunidad("GrupoGuay","miembro");
        assertEquals(miembro,DB.getMiembros(comunidad)[1]);
        DB.borrarUsuario(miembro);
    }
    @Test
    public void TestSeguir() {
        byte[] testValues2 = new byte[] {(byte) 0xFF};
        Usuario us2;
        DB.setUsuario("us2","us2@test.com",testValues2,testValues2);
        us2 = DB.getUsuario("usSeguidor");
        DB.seguir("us2","test");
        assertEquals("us2",DB.getSeguidores(us)[0]);
        DB.borrarUsuario(us2);
    }

    @Test
    public void TestDejarDeSeguir() {
        byte[] testValues2 = new byte[] {(byte) 0xFF};
        Usuario us2;
        DB.setUsuario("us2","us2@test.com",testValues2,testValues2);
        us2 = DB.getUsuario("usSeguidor");

        DB.seguir("us2","test");
        assertEquals("us2",DB.getSeguidores(us)[0]);
        DB.borrarUsuario(us2);
    }

    @Test
    public void TestEstaSiguiendo() {
        byte[] testValues2 = new byte[] {(byte) 0xFF};
        Usuario us2;
        DB.setUsuario("us2","us2@test.com",testValues2,testValues2);
        us2 = DB.getUsuario("usSeguidor");

        DB.seguir("us2","test");
        assertTrue(DB.estaSiguiendo("us2","us"));
        DB.borrarUsuario(us2);
    }

    @Test
    public void TestPuntuar() {
        DB.puntuar(us, p.getIDPublicacion(), Puntuacion.LIKE);
        assertEquals(1, DB.getLikes(p.getIDPublicacion()));
        DB.puntuar(us, p.getIDPublicacion(), Puntuacion.LIKE);
    }

    @Test
    public void TestGetPuntuacion() {
        DB.puntuar(us, p.getIDPublicacion(), Puntuacion.LIKE);
        assertEquals(Puntuacion.LIKE, DB.getPuntuacion(us,p.getIDPublicacion()));
        DB.puntuar(us, p.getIDPublicacion(), Puntuacion.LIKE);

    }

    @Test
    public void TestHacerAdminComunidad() {
        byte[] testValues2 = new byte[] {(byte) 0xFF};
        Usuario miembro;
        DB.setUsuario("miembro","miembro@test.com",testValues2,testValues2);
        miembro = DB.getUsuario("miembro");

        DB.insertarMiembroComunidad("GrupoGuay","miembro");
        DB.aceptarMiembroComunidad("GrupoGuay","miembro");
        DB.hacerAdminComunidad("GrupoGuay","miembro");
        assertEquals(miembro,DB.getMiembros(comunidad)[0]);
        DB.borrarUsuario(miembro);
        //NO ESTA BIEN HECHO
    }

    @Test
    public void TestGetMiembros() {
        assertEquals(us,DB.getMiembros(comunidad)[0]);
    }

    @Test
    public void TestGetTimeline() {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestBuscarComunidad() {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestComentar() {
        DB.comentar(p,us,"adios");
        assertEquals("adios",p.getComentarios().get(0).getContenido());
    }

    @Test
    public void TestComprobarPasswd() {
        fail("No esta implementado todavia");
    }

    }
