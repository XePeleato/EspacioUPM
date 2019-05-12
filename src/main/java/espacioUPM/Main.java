package espacioUPM;

public class EntryPoint {

    // Clase estática - no se instancia
    private EntryPoint() {}

    public static void main(String[] args) {
        App app = new App();
        app.start();
    }
}
