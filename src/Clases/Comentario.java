package Clases;

import java.util.List;

public class Comentario {
    /**
     * isbn del comentario.
     */
    private String isbn;
    /**
     * cantidad de comentarios.
     */
    private int cantidadComentarios;
    /**
     * lista de comentarios.
     */
    private List<String> comentarios;

    /**
     * Constructor de la clase Comentarios con sus respectivos atributos.
     * @param isbn
     * @param cantidadComentarios
     * @param comentarios
     */
    public Comentario(String isbn, int cantidadComentarios, List<String> comentarios) {
        this.isbn = isbn;
        this.cantidadComentarios = cantidadComentarios;
        this.comentarios = comentarios;
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

    public int getCantidadComentarios() {
        return cantidadComentarios;
    }

    public void setCantidadComentarios(int cantidadComentarios) {
        this.cantidadComentarios = cantidadComentarios;
    }

    public List<String> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<String> comentarios) {
        this.comentarios = comentarios;
    }
}
