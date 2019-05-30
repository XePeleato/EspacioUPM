package espacioUPM.Database;

public interface IDB_PasswordHandler {
    boolean comprobarPasswd(String alias, String password);

    static IDB_PasswordHandler getInstance() {
        return DB_Main.getInstance();
    }
}
