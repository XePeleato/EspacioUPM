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
    }

    @After
    public void tearDown() throws Exception {
        DB.borrarUsuario(us);
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
    public void TestBuscarUsuario(String alias){
        fail("No esta implementado todavia");
    }

    @Test
    public void TestSetUsuario(String alias, String correo, String pass) {
        DB.setUsuario("test", "test@test.com", pass);
        assertEquals(us.getAlias(), "test");

    }

    @Test
    public void TestGetNewID() {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestSetPublicacion() {
        Publicacion p = new PublicacionTexto("test","hola");
        assertTrue(DB.setPublicacion(p));
        DB.borrarPublicacion(p.getIDPublicacion());
    }

    @Test
    public void TestGetPublicacion() {
        Publicacion p = new PublicacionTexto("test","hola");
        assertEquals("hola",((PublicacionTexto) p).getContenido());
        DB.borrarPublicacion(p.getIDPublicacion());
    }

    @Test
    public void TestGetPublicaciones() {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestGetComentarios() {
        Publicacion p = new PublicacionTexto("test","hola");
        p.comentar(us, "adios");
        assertEquals("adios",p.getComentarios().get(0).getContenido());
        DB.borrarPublicacion(p.getIDPublicacion());
    }

    @Test
    public void TestGetLikes() {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestGetDislikes() {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestBorrarPublicacion() {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestGetSeguidos() {
        fail("No esta implementado todavia");
    }

    @Test
    public void TestgetSeguidores() {
        fail("No esta implementado todavia");
    }
    @Test
    public void TestCambiarAlias() {
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
