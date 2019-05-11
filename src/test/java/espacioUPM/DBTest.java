package espacioUPM;

import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class DBTest {


    @Test
    public void TestCrearUsuario() {
        IDB_Usuario DB = DB_Main.getInstance();

        byte[] testValues = new byte[] {(byte) 0xFF};
        DB.setUsuario("test", "test@test.con", testValues, testValues);

        Usuario us = DB.getUsuario("test");

        assertNotNull(us);

        DB.borrarUsuario(us);
    }

    @Test
    public void TestBorrarUsuario() {
        IDB_Usuario DB = DB_Main.getInstance();

        byte[] testValues = new byte[] {(byte) 0xFF};
        DB.setUsuario("test", "test@test.con", testValues, testValues);

        Usuario us = DB.getUsuario("test");

        DB.borrarUsuario(us);

        Usuario us2 = DB.getUsuario("test");


        assertNull(us2);
    }
}