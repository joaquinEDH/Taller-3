package Sistema;
import java.util.List;
import Clases.*;


public interface Sistema {
    boolean iniciarSesionAdministrador(int idAdministrador, String username, String password);
    boolean iniciarSesionUsuario(String username, String password);
    void registrarManga(Manga mangas);
    List<Compra> verUltimasCompras();
    void actualizarEstadoCompra(int usernameId, String nuevoEstado);
    List<Manga> buscarManga(String titulo);
    void verComprasUsuario(int usernameId);
    void valorarManga(String isbn, String comentario, double valoracion);
    List<String> visualizarComentarios(String isbn);
    void estadisticas();
    void comprarManga(int usernameId, String isbn, int cantidad);
    void guardarDatos();
    void mostrarMenu();
}