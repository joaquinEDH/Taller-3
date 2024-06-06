package Sistema;

import java.util.*;
import Clases.*;
import edu.princeton.cs.stdlib.In;
import edu.princeton.cs.stdlib.Out;


public class SistemaImpl implements Sistema {
    private static SistemaImpl instance;

    private List<Usuario> usuarios;
    /**
     * lista de usuarios.
     */
    private List<Manga> mangas;
    /**
     * lista de mangas.
     */
    private List<Comentario> comentarios;
    /**
     * lista de comentarios.
     */
    private List<Compra> compras;
    /**
     * lista de compras.
     */
    private Usuario usuario;
    private int contadorCompras;

    private Scanner scanner;

    /**
     * constructor de la clase SistemaImpl
     */

    public SistemaImpl() {
        this.usuarios = new ArrayList<>();
        this.mangas = new ArrayList<>();
        this.comentarios = new ArrayList<>();
        this.compras = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.contadorCompras = 0;
        cargarDatos();
    }
    /**
     * metodo con el proposito de realizar singleton.
     * @return
     */
    public static SistemaImpl getInstance(){
        if(instance == null){
            instance = new SistemaImpl();
        }
        return instance;
    }
    /**
     * Metodo para cargar todos los archivos de tipo ".csv".
     */

    private void cargarDatos() {

        // Cargar datos desde los archivos CSV
        cargarUsuarios("users.csv");
        cargarMangas("mangas.csv");
        cargarComentarios("comments.csv");
        cargarCompras("compras.csv");
    }
    /**
     * Metodo para cargar el archivo "users.csv".
     * @param filename
     */

    private void cargarUsuarios(String filename) {
        In archivoEntrada = new In(filename);
        String line = archivoEntrada.readLine();
        line = archivoEntrada.readLine();
        while (line!= null){
            String[] elementos = line.split(",");
            String rol = elementos[0];
            String username = elementos[1];
            int id = Integer.parseInt(elementos[2]);
            String password = elementos[3];
            int administrador_id = elementos.length > 4 ? Integer.parseInt(elementos[4]) : 0;
            Usuario usuario = new Usuario(rol, username, id, password, administrador_id);
            usuarios.add(usuario);

            line = archivoEntrada.readLine();
        }
    }
    /**
     * Metodo para cargar el archivo "mangas.csv".
     * @param filename
     */

    private void cargarMangas(String filename) {
        In archivoEntrada = new In(filename);
        archivoEntrada.readLine(); // Saltamos la primera linea del archivo.
        String line = archivoEntrada.readLine();
        while (line!= null) {
            String[] elementos = line.split(";");
            String isbn = elementos[0];
            String nombre = elementos[1];
            /*
             *En el archivo mangas.csv los datos se separan  por ";" y en la parte de stock, solo "Manga Dragon Ball Super - Tomo 5" tiene stock de 10 unidades, los demas estan vacios (mencionar que los archivos csv tienen bastantes errores ortográficos que son graves para la implementación).
             * Al momento de querer cargar los archivos de manga.csv y luego ejecutar el programa, aparecía inmediatamente el error "Exception in thread "main" java.lang.NumberFormatException: For input string: "One Piece relata las aventuras de Monkey D """
             * at java.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:67) at java.base/java.lang.Integer.parseInt(Integer.java:588) at
             * java.base/java.lang.Integer.parseInt(Integer.java:685) at Sistema.SistemaImpl.cargarMangas(SistemaImpl.java:68) at Sistema.SistemaImpl.cargarDatos(SistemaImpl.java:36) at Sistema.SistemaImpl.<init>(SistemaImpl.java:23) at
             * Sistema.SistemaImpl.getInstance(SistemaImpl.java:27) at Main.main(Main.java:8).
             * Por lo tanto, se decidió investigar por cuenta propia el problema a resolver ya que no habían otros medios para poder arreglarlo.
             * Finalmente se llegó al código que se muestra.
             * Si el valor de stock o precio está vacío o contiene comillas dobles, se asignará un valor de 0 por defecto.
             * Si el valor de stock o precio no está vacío y no contiene comillas dobles, se convertirá a un entero (int) como antes.
             * Fue la única solución que pude encontrar por mi cuenta.
             *
             */
            int stock = 0; // Inicializa stock en 0 por defecto
            if (!elementos[2].isEmpty() &&!elementos[2].equals("\"\"")) { // Verifica si el valor de stock no está vacío y no contiene comillas dobles
                stock = Integer.parseInt(elementos[2]);
            }
            String descripcion = elementos[3];
            int precio = 0; // Inicializa precio en 0 por defecto
            if (!elementos[4].isEmpty() &&!elementos[4].equals("\"\"")) { // Verifica si el valor de precio no está vacío y no contiene comillas dobles
                precio = Integer.parseInt(elementos[4]);
            }
            Manga manga = new Manga(isbn, nombre, stock, descripcion, precio);
            mangas.add(manga);
            line = archivoEntrada.readLine();
        }
    }

    /**
     * Metodo para cargar el archivo "comments.csv".
     * @param filename
     */
    private void cargarComentarios(String filename) {
        try {
            In archivoEntrada = new In(filename);
            String line = archivoEntrada.readLine(); // Leer la primera línea (cabecera)
            line = archivoEntrada.readLine(); // Leer la primera línea de datos

            while (line != null) {
                String[] elementos = line.split(":");

                if (elementos.length < 3) {
                    line = archivoEntrada.readLine();
                    continue;
                }
                String isbn = elementos[0];
                int cantidadComentarios = Integer.parseInt(elementos[1]);
                String comentariosStr = elementos[2].substring(1, elementos[2].length() - 1); // Quitar los corchetes

                List<String> listaComentarios = new ArrayList<>();
                if (!comentariosStr.isEmpty()) {
                    String[] comentariosArray = comentariosStr.split("#");
                    listaComentarios.addAll(Arrays.asList(comentariosArray));
                }

                Comentario comentario = new Comentario(isbn, cantidadComentarios, listaComentarios);
                comentarios.add(comentario);

                line = archivoEntrada.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Para ver detalles del error
        }
    }

    /**
     * Metodo para cargar el archivo "compras.csv".
     * @param filename
     */

    private void cargarCompras(String filename) {
        try {
            In archivoEntrada = new In(filename);
            String line = archivoEntrada.readLine(); // Leer la primera línea (cabecera)
            line = archivoEntrada.readLine(); // Leer la primera línea de datos

            while (line != null) {
                String[] elementos = line.split(",");

                if (elementos.length < 6) {
                    line = archivoEntrada.readLine();
                    continue;
                }

                int idCompra = Integer.parseInt(elementos[0]);
                String isbn = elementos[1];
                int idUsuario = Integer.parseInt(elementos[2]);
                String estado = elementos[3];
                String fecha = elementos[4];
                int cantidad = Integer.parseInt(elementos[5]);

                Compra compra = new Compra(idCompra, isbn, idUsuario, estado, fecha, cantidad);
                compras.add(compra);

                line = archivoEntrada.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Para ver detalles del error
        }
    }

    /**
     * Metodo en el cual se muestra el menu inicial.
     */
    public void mostrarMenu() {
        int opcionPrincipal = 0;

        while (opcionPrincipal != 3) {
            System.out.println("Bienvenido a Fuente Manga");
            System.out.println("1. Iniciar sesión como administrador");
            System.out.println("2. Iniciar sesión como usuario");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opcionPrincipal = scanner.nextInt();
            scanner.nextLine();

            switch (opcionPrincipal) {
                case 1:
                    if(iniciarSesionAdministrador()){
                        mostrarMenuAdministrador();
                    }
                    break;
                case 2:
                    iniciarSesionUsuario();
                    break;
                case 3:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }
    }
    /**
     *
     * Metodo con sobrecarga en el cual se validan los datos del administrador para ingresar al sistema.
     * @return
     */
    public boolean iniciarSesionAdministrador(){ //REVISARRRRRRRRRRRR!!!!!!!!!!!!!!!!!
        System.out.println("<-------- { Iniciar sesión } -------->");
        System.out.println("Inicio de sesión como administrador:");
        System.out.println("Id-Administrador:");
        String admin_id = scanner.nextLine();
        System.out.println("Nombre de usuario:");
        String name = scanner.nextLine();
        System.out.println("Contraseña:");
        String pass = scanner.nextLine();

        for (Usuario usuario : usuarios) {
            if (usuario.getRol().equals("ADMINISTRADOR") &&
                    usuario.getAdministratorId() != null &&
                    usuario.getAdministratorId().toString().equals(admin_id) &&
                    usuario.getUsername().equals(name) &&
                    usuario.getPassword().equals(pass)) {

                System.out.println("(!) ¡Inicio de sesión exitoso!");
                System.out.println("Bienvenido@ " + name);
                return true;
            }
        }
        System.out.println("Error, ingrese datos validos.");
        return false;
    }

    /**
     * Metodo con sobrecarga en el cual se validan los datos del usuario para ingresar al sistema.
     */
    private void iniciarSesionUsuario() {
        System.out.println("<-------- { Iniciar sesión } -------->");
        System.out.print("Nombre de usuario: ");
        String username = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        if (iniciarSesionUsuario(username, password)) {
            System.out.println("(!) ¡Inicio de sesión exitoso!");
            System.out.println("Bienvenid@ " + username);
            mostrarMenuUsuario();
        } else {
            System.out.println("Datos incorrectos. Intente de nuevo.");
        }

    }

    /**
     * Metodo en el cual se muestran las opciones para el administrador luego de iniciar
     */
    private void mostrarMenuAdministrador() {
        int opcion = 0;
        while (opcion != 5) {
            System.out.println(" ");
            System.out.println("Menú Administrador");
            System.out.println("1. Registrar Manga");
            System.out.println("2. Ver últimas compras");
            System.out.println("3. Actualizar estado de compra");
            System.out.println("4. Estadísticas");
            System.out.println("5. Salir y guardar");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    registrarManga();
                    break;
                case 2:
                    mostrarUltimasCompras();
                    break;
                case 3:
                    actualizarEstadoCompra();
                    break;
                case 4:
                    estadisticas();
                    break;
                case 5:
                    System.out.println("Cerrando y guardando sesión...");
                    guardarDatos();
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }
    }

    private void mostrarMenuUsuario() {
        int opcion = 0;
        while (opcion != 6) {
            System.out.println(" ");
            System.out.println("Menú Usuario");
            System.out.println("1. Buscar Manga");
            System.out.println("2. Ver compras");
            System.out.println("3. Valorar Manga");
            System.out.println("4. Visualizar comentarios");
            System.out.println("5. Comprar Manga");
            System.out.println("6. Salir y guardar");
            System.out.print("Seleccione una opción: ");


            try{
                opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion) {
                    case 1:
                        System.out.println("Ingrese el nombre del manga que desea buscar:");
                        String tituloManga= scanner.nextLine();
                        // Validar que el título de búsqueda no esté vacío
                        if (tituloManga.trim().isEmpty()) {
                            System.out.println("Por favor, ingrese un título válido.");
                        } else {
                            buscarManga(tituloManga);
                        }
                        break;
                    case 2:
                        verComprasUsuario(this.usuario.getId());
                        break;
                    case 3:
                        System.out.println("Ingrese el ISBN del manga a valorar:");
                        String isbnValorar = scanner.nextLine();
                        System.out.println("Ingrese su comentario:");
                        String comentario = scanner.nextLine();
                        System.out.println("Ingrese su valoración (1-5):");
                        double valoracion = scanner.nextDouble();
                        scanner.nextLine();
                        valorarManga(isbnValorar, comentario, valoracion);
                        break;
                    case 4:
                        System.out.println("Ingrese el ISBN del manga a visualizar:");
                        String isbnVisualizar = scanner.nextLine();
                        visualizarComentarios(isbnVisualizar);
                        break;
                    case 5:
                        comprarManga();
                        break;
                    case 6:
                        System.out.println("Cerrando sesión...Guardando...");
                        guardarDatos();
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                        break;
                }
            }catch (InputMismatchException e) {
                System.out.println("Error: Ingrese un número válido.");
                scanner.next(); // Limpiar la entrada incorrecta del scanner
            }
        }
    }


    @Override
    public boolean iniciarSesionAdministrador(int idAdministrador, String username, String password) {

        return false;
    }

    @Override
    public boolean iniciarSesionUsuario(String username, String password) {
        for (Usuario usuario : usuarios) {
            if (usuario.getRol().equals("USUARIO") && usuario.getUsername().equals(username)
                    && usuario.getPassword().equals(password)) {
                this.usuario=usuario; // Asigna el usuario autenticado a la variable usuario para que no quede nulo
                return true;
            }
        }
        return false;
    }

    /**
     * Métodos para el menú de administrador, luego se harán los métodos del menú usuario.
     */

    public void registrarManga(){
        Scanner s = new Scanner(System.in);

        System.out.println("Ingrese el código/isbn de su manga:");
        String isbn = obtenerDatoValido(s);
        System.out.println("Ingrese el título de su manga:");
        String titulo = obtenerDatoValido(s);
        System.out.println("Ingrese el stock de su manga:");
        int stock = s.nextInt();
        System.out.println("Ingrese la descripción de su manga:");
        s.nextLine();
        String descripcion = obtenerDatoValido(s);
        System.out.println("Ingrese el precio de su manga:");
        int precio = s.nextInt();

        Manga mangas = new Manga(isbn, titulo, stock, descripcion, precio);
        registrarManga(mangas);
        System.out.println("Manga agregado!!");
    }

    private String obtenerDatoValido(Scanner scanner) {
        String dato = scanner.nextLine();
        while (dato.isEmpty()) {
            System.out.println("Error: Por favor, ingrese un valor válido.");
            dato = scanner.nextLine();
        }
        return dato;
    }



    @Override
    public void registrarManga(Manga mangas) {
        this.mangas.add(mangas);
        imprimirMangas();
    }

    public void imprimirMangas(){
        for (int i = 0; i < mangas.size(); i++) {
            System.out.println((i + 1) + ".-");
            System.out.println(mangas.get(i).toString());
            System.out.println("---------------------------------");
        }
    }

    public void mostrarUltimasCompras() {
        System.out.println("Últimas compras realizadas:");
        for (int i = Math.max(compras.size() - 5, 0); i < compras.size(); i++) {
            Compra compra = compras.get(i);
            System.out.println("ID Compra: " + compra.getId());
            System.out.println("ISBN: " + compra.getIsbn());
            System.out.println("ID Usuario: " + compra.getUsernameId());
            System.out.println("Estado: " + compra.getEstado());
            System.out.println("Fecha: " + compra.getFecha());
            System.out.println("Cantidad: " + compra.getCantidad());
            System.out.println("-----------------------------------");
        }
    }

    @Override
    public List<Compra> verUltimasCompras() {
        mostrarUltimasCompras();
        return compras;
    }

    public void actualizarEstadoCompra(){
        System.out.println("Ingrese el id del cliente:");
        int usernameId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Ingrese el nuevo estado de la compra:");
        String nuevoEstado = scanner.nextLine();

        boolean compraEncontrada = false;

        for (Compra compra : compras) {
            if (compra.getUsernameId() == usernameId && !nuevoEstado.isEmpty()) {
                compra.setEstado(nuevoEstado);
                compraEncontrada = true;
                System.out.println("Estado modificado con éxito!.");
                break;
            }
        }
        if (!compraEncontrada) {
            System.out.println("Datos inválidos, intente nuevamente.");
        }
    }


    @Override
    public void actualizarEstadoCompra(int usernameId, String nuevoEstado) {
    }

    public double calcularPorcentajeUsuariosFrecuentes() {
        int totalUsuarios = usuarios.size();
        int usuariosFrecuentes = 0;

        for (Usuario usuario : usuarios) {
            if (calcularTotalComprasUsuario(usuario.getId()) > 5) {
                usuariosFrecuentes++;
            }
        }

        return (double) usuariosFrecuentes / totalUsuarios * 100.0;
    }

    private int calcularTotalComprasUsuario(int usernameId) {
        int totalCompras = 0;

        for (Compra compra : compras) {
            if (compra.getUsernameId() == usernameId) {
                totalCompras++;
            }
        }

        return totalCompras;
    }
    public Manga encontrarMangaMejorValorado() {
        Manga mejorManga = null;
        double mejorValoracion = 0.0;

        for (Manga manga : mangas) {
            double valoracionPromedio = calcularValoracionPromedioManga(manga.getIsbn());
            if (valoracionPromedio > mejorValoracion) {
                mejorManga = manga;
                mejorValoracion = valoracionPromedio;
            }
        }

        return mejorManga;
    }

    private double calcularValoracionPromedioManga(String isbn) {
        double sumaValoraciones = 0.0;
        int totalValoraciones = 0;

        for (Comentario comentario : comentarios) {
            if (comentario.getIsbn().equals(isbn)) {
                for (String valoracion : comentario.getComentarios()) {
                    String[] partes = valoracion.split(";");
                    double valoracionNum = Double.parseDouble(partes[1]);
                    sumaValoraciones += valoracionNum;
                    totalValoraciones++;
                }
            }
        }

        return totalValoraciones > 0 ? sumaValoraciones / totalValoraciones : 0.0;
    }
    public double calcularPorcentajeProductosNoEntregados() {
        int totalCompras = compras.size();
        int productosNoEntregados = 0;

        for (Compra compra : compras) {
            if (!compra.getEstado().equals("RECIBIDO")) {
                productosNoEntregados++;
            }
        }

        return (double) productosNoEntregados / totalCompras * 100.0;
    }


    @Override
    public void estadisticas() {
        // Porcentaje de usuarios frecuentes
        double porcentajeUsuariosFrecuentes = calcularPorcentajeUsuariosFrecuentes();
        System.out.println("Porcentaje de usuarios frecuentes: " + porcentajeUsuariosFrecuentes + "%");

        // Manga con mejor valoración
        Manga mangaMejorValorado = encontrarMangaMejorValorado();
        if (mangaMejorValorado != null) {
            System.out.println("Manga con mejor valoración: " + mangaMejorValorado.getTitulo());
        } else {
            System.out.println("No hay mangas registrados para calcular la mejor valoración.");
        }

        // Porcentaje de productos no entregados
        double porcentajeProductosNoEntregados = calcularPorcentajeProductosNoEntregados();
        System.out.println("Porcentaje de productos no entregados: " + porcentajeProductosNoEntregados + "%");

    }

    /**
     * De aquí en adelante se hacen los métodos para el menu del usuario.
     * La mayor parte del código arriba es enfocado en el menu del administrador.
     * @param titulo
     * @return
     */


    @Override
    public List<Manga> buscarManga(String titulo) {
        List<Manga> resultados = new ArrayList<>();
        for (Manga manga : mangas) {
            if (manga.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                resultados.add(manga);
            }
        }

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron mangas que coincidan con la búsqueda.");
        } else {
            System.out.println("Resultados de la búsqueda:");
            for (Manga manga : resultados) {
                int cantidadComentarios = obtenerCantidadComentarios(manga.getIsbn());
                double valoracion = calcularValoracionPromedioManga(manga.getIsbn());

                System.out.println("Título: " + manga.getTitulo());
                System.out.println("ISBN: " + manga.getIsbn());
                System.out.println("Cantidad de comentarios: " + cantidadComentarios);
                System.out.println("Valoración: " + valoracion);
                System.out.println("Stock: " + manga.getStock());
                System.out.println("-----------------------------------");
            }
        }

        return resultados;
    }

    private int obtenerCantidadComentarios(String isbn) {
        for (Comentario comentario : comentarios) {
            if (comentario.getIsbn().equals(isbn)) {
                return comentario.getCantidadComentarios();
            }
        }
        return 0;
    }


    @Override
    public void verComprasUsuario(int usernameId) {
        List<Compra> comprasUsuario = new ArrayList<>();
        for (Compra compra : compras) {
            if (compra.getUsernameId() == usernameId) {
                comprasUsuario.add(compra);
            }
        }

        if (comprasUsuario.isEmpty()) {
            System.out.println("No se encontraron compras para el usuario con ID: " + usernameId);
            return;
        }

        int index = 0;
        while (true) {
            Compra compraActual = comprasUsuario.get(index);
            String isbn = compraActual.getIsbn();
            Manga mangaComprado = null;

            // Busca el manga relacionado por ISBN
            for (Manga manga : mangas) {
                if (manga.getIsbn().equalsIgnoreCase(isbn)) {
                    mangaComprado = manga;
                    break;
                }
            }

            if (mangaComprado != null) {
                System.out.println("ID Compra: " + compraActual.getId());
                System.out.println("Título del Manga: " + mangaComprado.getTitulo());
                System.out.println("Estado del Producto: " + compraActual.getEstado());
                System.out.println("Costo: " + mangaComprado.getPrecio());
                System.out.println("-----------------------------------");
            } else {
                System.out.println("Manga no encontrado para la compra con ID: " + compraActual.getId());
            }

            System.out.println("Seleccione una opción:");
            System.out.println("1. Visualizar el producto anterior");
            System.out.println("2. Visualizar el producto siguiente");
            System.out.println("3. Salir");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            if (opcion == 1) {
                if (index > 0) {
                    index--;
                } else {
                    System.out.println("Este es el primer producto adquirido, no hay productos anteriores.");
                }
            } else if (opcion == 2) {
                if (index < comprasUsuario.size() - 1) {
                    index++;
                } else {
                    System.out.println("Este es el último producto adquirido, no hay productos siguientes.");
                }
            } else if (opcion == 3) {
                break;
            } else {
                System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    @Override
    public void valorarManga(String isbn, String comentario, double valoracion) {
        if (comentario.length() > 255) {
            System.out.println("El comentario no puede tener más de 255 caracteres.");
            return;
        }

        boolean haComprado = false;
        for (Compra compra : compras) {
            if (compra.getUsernameId() == usuario.getId() && compra.getIsbn().equals(isbn)) {
                haComprado = true;
                System.out.println("Ha adquirido el producto! Comente y valore su compra.");
                break;
            }
        }

        if (!haComprado) {
            System.out.println("No puedes valorar un manga que no has comprado.");
            return;
        }

        for (Comentario c : comentarios) {
            if (c.getIsbn().equals(isbn)) {
                c.getComentarios().add(comentario + ";" + valoracion);
                c.setCantidadComentarios(c.getCantidadComentarios() + 1);
                System.out.println("Valoración añadida con éxito.");
                return;
            }
        }

        // Si no existe el comentario, lo agregamos
        List<String> nuevosComentarios = new ArrayList<>();
        nuevosComentarios.add(comentario + ";" + valoracion);
        comentarios.add(new Comentario(isbn, 1, nuevosComentarios));
        System.out.println("Valoración añadida con éxito.");
    }




    @Override
    public List<String> visualizarComentarios(String isbn) {
        for (Comentario c : comentarios) {
            if (c.getIsbn().equals(isbn)) {
                for (String comentario:c.getComentarios()){
                    System.out.println("Comentario y Valoración:");
                    System.out.println(comentario);
                }
            }
            return null;
        }
        return new ArrayList<>();
    }


    public void comprarManga(){
        System.out.println("<-------- { Comprar producto } -------->");
        System.out.println("Ingresar el isbn del producto a comprar:");
        String isbn = scanner.nextLine();
        System.out.println("Ingresar la cantidad de productos:");
        int cantidad = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Ingrese el método de pago:");
        System.out.println("(Débito / Credito / Efectivo)");
        String pago = scanner.nextLine();

        boolean isbnValido = false;
        int n= 2;
        for (Manga manga : mangas) {
            if (manga.getIsbn().equals(isbn)) {
                isbnValido = true;
                if (manga.getStock() >= cantidad) {
                    manga.setStock(manga.getStock() - cantidad);
                    contadorCompras++;
                    Compra compra = new Compra(contadorCompras+3, isbn, usuario.getId(), "ENVIADO", n+"/06/24", cantidad);
                    compras.add(compra);
                    System.out.println("Compra realizada con éxito.");
                } else {
                    System.out.println("No hay suficiente stock para realizar la compra.");
                }
                break;
            }
            n++;
        }
        if (!isbnValido) {
            System.out.println("Error, Ingrese un ISBN válido");
            return;
        }if (cantidad <= 0) {
            System.out.println("Error, Ingrese una cantidad válida");
            return;
        }if (!pago.equalsIgnoreCase("debito") && !pago.equalsIgnoreCase("credito") && !pago.equalsIgnoreCase("efectivo")) {
            System.out.println("Error, Ingrese un método de pago válido");
            return;
        }

        System.out.println("[Producto comprado]: [ID] -> " + contadorCompras);
    }



    @Override
    public void comprarManga(int usernameId, String isbn, int cantidad) {

    }



    @Override
    public void guardarDatos() {

        // Guardar mangas
        Out outMangas = new Out("mangas.csv");
        outMangas.print("isbn;nombre;stock;descripcion;precio\n");
        for (Manga manga : mangas) {
            outMangas.print(manga.getIsbn() + ";" +
                    manga.getTitulo() + ";" +
                    manga.getStock() + ";" +
                    manga.getDescripcion() + ";" +
                    manga.getPrecio() + "\n");
        }
        // Guardar comentarios
        Out outComentarios = new Out("comments.csv");
        outComentarios.print("isbn:cantidadComentarios:comentarios\n");
        for (Comentario comentario : comentarios) {
            String comentariosStr = String.join("#", comentario.getComentarios());
            outComentarios.print(comentario.getIsbn() + ":" +
                    comentario.getCantidadComentarios() + ":" +
                    "[" + comentariosStr + "]\n");
        }
        // Guardar compras
        Out outCompras = new Out("compras.csv");
        outCompras.print("idCompra,isbn,idUsuario,estado,fecha,cantidad\n");
        for (Compra compra : compras) {
            outCompras.print(compra.getId() + "," +
                    compra.getIsbn() + "," +
                    compra.getUsernameId() + "," +
                    compra.getEstado() + "," +
                    compra.getFecha() + "," +
                    compra.getCantidad() + "\n");
        }
    }

}


