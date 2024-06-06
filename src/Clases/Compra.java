package Clases;


public class Compra {
    /**
     * id de la compra
     */
    private int id;
    /**
     * isbn de la compra.
     */
    private String isbn;
    /**
     * id del usuario.
     */
    private int usernameId;
    /**
     * estado de la compra.
     */
    private String estado;
    /**
     * fecha de emision de la compra.
     */
    private String fecha;
    /**
     * cantidad de managas u objetos comprados.
     */
    private int cantidad;

    /**
     * Constructor de la clase Compra con sus respectivos atrivutos.
     * @param id
     * @param isbn
     * @param usernameId
     * @param estado
     * @param fecha
     * @param cantidad
     */
    public Compra(int id,String  isbn, int usernameId, String estado, String fecha, int cantidad) {
        this.id = id;
        this.isbn = isbn;
        this.usernameId = usernameId;
        this.estado = estado;
        this.fecha = fecha;
        this.cantidad = cantidad;
    }

    /**
     * Gets y Sets de los atributos de la clase.
     * @return
     */
    public int getId() {
        return id;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public int getUsernameId() {
        return usernameId;
    }
    public void setUsernameId(int usernameId) {
        this.usernameId = usernameId;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
