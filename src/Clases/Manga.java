package Clases;

public class Manga {
    /**
     * isbn del manga.
     */
    private String isbn;
    /**
     * titulo del manga.
     */
    private String titulo;
    /**
     * stock del manga
     */
    private int stock;
    /**
     * descripcion del manga.
     */
    private String descripcion;
    /**
     * precio del manga.
     */
    private int precio;

    /**
     * Constructor de la clase Manga con sus respectivos atributos.
     * @param isbn
     * @param titulo
     * @param stock
     * @param descripcion
     * @param precio
     */
    public Manga(String isbn, String titulo, int stock, String descripcion, int precio) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.stock = stock;
        this.descripcion = descripcion;
        this.precio = precio;
    }
    @Override
    public String toString() {
        return "Título: " + titulo + ", ISBN: " + isbn + ", Stock: " + stock; //",Descripción: " + descripcion + ", Precio: " + precio;
    }

    /**
     * Gets y Sets de los atributos de la clase.
     * @return
     */
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setNombre(String nombre) {
        this.titulo = titulo;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}
