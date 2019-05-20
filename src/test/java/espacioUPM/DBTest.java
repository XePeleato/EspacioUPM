package espacioUPM;

import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_Usuario;
import espacioUPM.Publicaciones.Comentario;
import espacioUPM.Publicaciones.Publicacion;
import espacioUPM.Publicaciones.Puntuacion;
import org.junit.*;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertNull;

public class DBTest {

    IDB_Usuario DB;
    Usuario us;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception {}

    @Before
    public void setUp() throws Exception {
        DB = DB_Main.getInstance();

        byte[] testValues = new byte[] {(byte) 0xFF};
        DB.setUsuario("test", "test@test.con", testValues, testValues);

        us = DB.getUsuario("test");
    }

    @After
    public void tearDown() throws Exception {}

    @Test
    public void TestCrearUsuario() {

        assertNotNull(us);
        assertEquals(us.getAlias(), "test");

        DB.borrarUsuario(us);
    }

    @Test
    public void TestBorrarUsuario() {

        DB.borrarUsuario(us);

        Usuario us2 = DB.getUsuario("test");

        assertNull(us2);
    }

    @Test
    public void TestgetUsuario(String alias){
        fail("No esta implementado todavia");
    }

    @Test
    public void TestBuscarUsuario(String alias){
        fail("No esta implementado todavia");
    }

    @Test
    public void TestSetUsuario(String alias, String correo, String pass) {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestGetNewID() {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestSetPublicacion(Publicacion publi) {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestGetPublicacion(int id) {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestGetPublicaciones(Usuario usuario) {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestGetComentarios(int publication_id) {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestGetLikes(int publication_id) {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestGetDislikes(int publication_id) {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestBorrarPublicacion(int publi) {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestGetSeguidos(Usuario usuario) {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestgetSeguidores(Usuario usuario) {
        fail("No esta implementado todavia");
    }
    @Test
    public void TestCambiarAlias(Usuario usuario, String aliasNuevo) {
        fail("No esta implementado todavia");
    }
    @Test
    public void TestBorrarUsuario(Usuario usuario) {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestBorrarMiembroComunidad(String id, String alias) {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestInsertarMiembroComunidad(String id, String alias) {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestAceptarMiembroComunidad(String id, String alias) {
        fail("No esta implementado todavia");
    }
    @Test
    public void TestSeguir(String seguidor, String seguido) {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestDejarDeSeguir(String seguidor, String seguido) {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestEstaSiguiendo(String aliasSeguidor, String aliasSeguido) {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestPuntuar(Usuario usuario, int publi, Puntuacion puntuacion) {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestGetPuntuacion(Usuario usuario, int publi) {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestHacerAdminComunidad(String id, String alias) {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestGetMiembros(Comunidad comunidad) {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestGetTimeline(Comunidad comunidad) {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestBuscarComunidad(String id) {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestComentar(Publicacion publi, Usuario usuario, String contenido) {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestComprobarPasswd(String alias, String passwd) {
        fail("No esta implementado todavia");
    }




    }
