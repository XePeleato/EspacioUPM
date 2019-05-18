package espacioUPM;

import espacioUPM.Database.DB_Main;
import espacioUPM.Database.IDB_Usuario;
import org.junit.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
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
}
