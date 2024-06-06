
import Sistema.SistemaImpl;


/**
 * Main del programa. Está el sistemaImpl con singleton para inicializar el programa.
 */
public class Main {
    public static void main(String[] args) {
        SistemaImpl sistema = SistemaImpl.getInstance();// Inicialización del sistema
        sistema.mostrarMenu();
    }
}