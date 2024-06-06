package Clases;

public class Usuario {
    /**
     * rol del usuario.
     */
    private String rol;
    /**
     * username del usuario o administrador.
     */
    private String username;
    /**
     * id del usuario o administrador.
     */
    private int id;
    /**
     * contraseña del usuario o administrador.
     */
    private String password;
    /**
     * id exclusivo del admnistrador.
     */
    private Integer administratorId;

    /**
     * Constructor de la clase Usuario para administrador con sus respectivos atributos.
     * @param rol
     * @param username
     * @param id
     * @param password
     * @param administratorId
     */
    // Constructor para usuarios administradores
    public Usuario(String rol, String username, int id, String password, Integer administratorId) {
        this.rol = rol;
        this.username = username;
        this.id = id;
        this.password = password;
        this.administratorId = administratorId;
    }

    /**
     * Constructor de la clase Usuario para usuarios con sus respectivos atrivutos.
     * @param rol
     * @param username
     * @param id
     * @param password
     */
    // Constructor para usuarios normales
    public Usuario(String rol, String username, int id, String password) {
        this(rol, username, id, password, -1); // Usamos -1 o cualquier valor por defecto para administratorId
    }

    /**
     * Gets y Sets de los atributos de la clase.
     * @return
     */
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(Integer administratorId) {
        this.administratorId = administratorId;
    }
}


