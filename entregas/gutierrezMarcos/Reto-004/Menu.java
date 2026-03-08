import java.util.Scanner;

public class Menu {

    private Scanner scanner = new Scanner(System.in);
    private int eleccion;
    private Cancion cancionActual;
    private List canciones;
    private List historial;
    private List favoritos;
    private String entradaTexto;
    private List listasReproduccion = new List();

    public Menu(Cancion cancionActual, List canciones) {
        this.canciones = canciones;
        this.historial = new List();
        this.favoritos = new List();
        this.cancionActual = cancionActual;
        limpiarPantalla();
        menuPrincipal();
    }

    private void pedirEleccion() {
        eleccion = scanner.nextInt();
        scanner.nextLine();
        pausar(2);
        limpiarPantalla();
    }

    public void menuPrincipal() {
        System.out.println("=== MENÚ PRINCIPAL ===\n" +
                "1. Reproducción\n" +
                "2. Biblioteca\n" +
                "3. Salir");

        System.out.print("Seleccione una opción: ");
        pedirEleccion();

        switch (eleccion) {
            case 1 -> menuReproduccion();
            case 2 -> menuBiblioteca();
            case 3 -> System.exit(0);
            default -> System.out.println("Opción no válida. Intente de nuevo.");
        }
    }

    public void menuReproduccion() {
        System.out.println(
                "=== MENÚ REPRODUCCIÓN ===\n" +
                        "1. Ver canción actual\n" +
                        "2. Reproducir siguiente\n" +
                        "3. Reproducir anterior\n" +
                        "4. Ver cola de reproducción\n" +
                        "5. Ver historial\n" +
                        "6. Volver al menú principal");

        System.out.print("Seleccione una opción: ");
        pedirEleccion();

        switch (eleccion) {
            case 1 -> verCancionActual();
            case 2 -> reproducirSiguiente();
            case 3 -> reproducirAnterior();
            case 4 -> mostrarCola();
            case 5 -> verHistorial();
            case 6 -> menuPrincipal();
            default -> menuReproduccion();
        }
    }

    private void verCancionActual() {
        if (cancionActual == null) {
            System.out.println("No hay canciones en reproducción\n");
            System.out.print("¿Desea comenzar a reproducir? (S/N): ");
            entradaTexto = scanner.nextLine();
            System.out.println();
        } else {
            System.out.println("Estas reproduciendo " + cancionActual.toString());
        }

        switch (entradaTexto != null ? entradaTexto.toUpperCase() : "") {
            case "S" -> reproducirCancion();
            default -> menuPrincipal();
        }
    }

    private void reproducirCancion() {
        System.out.println(canciones.mostrar());
        pedirEleccion();

        cancionActual = canciones.getCancion(eleccion);

        System.out.println("Estas reproduciendo " + cancionActual.toString());
        historial.insertEnd(cancionActual);
        menuReproduccion();
    }

    private void reproducirSiguiente() {
        if (cancionActual != null) {
            cancionActual = canciones.next(cancionActual);
            System.out.println("Estas reproduciendo " + cancionActual.toString());
            historial.insertEnd(cancionActual);
        } else {
            System.out.println("No hay más canciones en la lista.");
        }
        retornarAMenuReproduccion();
    }

    private void reproducirAnterior() {
        if (cancionActual != null) {
            cancionActual = canciones.previous(cancionActual);
            System.out.println("Estas reproduciendo " + cancionActual.toString());
        } else {
            System.out.println("No hay más canciones en la lista.");
        }
        retornarAMenuReproduccion();
    }

    private void mostrarCola() {
        if (cancionActual != null) {
            System.out.println(canciones.mostrarDesde(cancionActual.toString()));
            scanner.nextLine();
        } else {
            System.out.println("No tienes ninguna canción ni ninguna lista en reproducción");
            pausar(2);
        }
        limpiarPantalla();
        menuReproduccion();
    }

    private void verHistorial() {
        if (historial != null) {
            System.out.println("=== Historial de Canciones Reproducidas ===");
            System.out.println(historial.mostrar());
            scanner.nextLine();
            limpiarPantalla();
            menuReproduccion();
        } else {
            System.out.println("No tienes historial de reproduccion");
        }
    }

    private void retornarAMenuReproduccion() {
        pausar(2);
        limpiarPantalla();
        menuReproduccion();
    }

    public void menuBiblioteca() {
        System.out.println("=== MENÚ BIBLIOTECA ===\n" +
                "1. Añadir canción a favoritos\n" +
                "2. Eliminar canción de favoritos\n" +
                "3. Ver canciones favoritas\n" +
                "4. Crear nueva lista de reproducción\n" +
                "5. Añadir canción a lista de reproducción\n" +
                "6. Eliminar canción de lista de reproducción\n" +
                "7. Ver listas de reproducción\n" +
                "8. Ver canciones de una lista\n" +
                "9. Volver al menú principal");

        System.out.print("Seleccione una opción: ");
        pedirEleccion();

        switch (eleccion) {
            case 1 -> añadirCancionAFavoritos();
            case 2 -> eliminarCancionDeFavoritos();
            case 3 -> verCancionesFavoritas();
            case 4 -> crearListaReproduccion();
            case 5 -> agregarCancionALista();
            case 6 -> eliminarCancionDeLista();
            case 7 -> verListasReproduccion();
            case 8 -> verCancionesEnLista();
            case 9 -> menuPrincipal();
            default -> menuBiblioteca();
        }
    }

    private void añadirCancionAFavoritos() {
        System.out.println("Seleccione una canción para añadir a favoritos:");
        System.out.println(canciones.mostrar());

        pedirEleccion();

        Cancion seleccionada = canciones.getCancion(eleccion);
        if (seleccionada != null && !seleccionada.isFavorita()) {
            seleccionada.setFavorita(true);
            System.out.println("Canción añadida a favoritos: " + seleccionada);
        } else {
            System.out.println("La canción ya está en favoritos o no es válida.");
        }
        menuBiblioteca();
    }

    private void verCancionesFavoritas() {
        System.out.println("=== Canciones Favoritas ===");
        System.out.println(canciones.mostrarFavoritas());

        scanner.nextLine();
        limpiarPantalla();
        menuBiblioteca();
    }

    static void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pausar(int segundos) {
        try {
            Thread.sleep(1000L * segundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Pausa interrumpida.");
        }
    }

    private List pedirYBuscarLista() {
        System.out.print("Ingrese el nombre de la lista de reproducción: ");
        String nombreLista = scanner.nextLine();
        return encontrarLista(nombreLista);
    }

    private void crearListaReproduccion() {
        System.out.print("Ingrese el nombre de la nueva lista de reproducción: ");
        String nombreLista = scanner.nextLine();
        listasReproduccion.crearPlaylist(new List(nombreLista));
        System.out.println("Lista de reproducción creada.");
    }

    private void agregarCancionALista() {
        List lista = pedirYBuscarLista();
        
        if (lista != null) {
            System.out.print("Ingrese el título de la canción: ");
            pedirEleccion();
            Cancion cancion = canciones.getCancion(eleccion);
            if (cancion != null) {
                lista.add(cancion);
                System.out.println("Canción añadida a la lista.");
            } else {
                System.out.println("Canción no encontrada.");
            }
        } else {
            System.out.println("Lista no encontrada.");
        }
    }

    private void eliminarCancionDeLista() {
        List lista = pedirYBuscarLista();
        
        if (lista != null) {
            System.out.print("Ingrese el título de la canción a eliminar: ");
            pedirEleccion();
            lista.remove(eleccion);
            System.out.println("Canción eliminada de la lista.");
        } else {
            System.out.println("Lista no encontrada.");
        }
        menuBiblioteca();
    }

    private void verListasReproduccion() {
        System.out.println("=== Listas de Reproducción ===");
        Node nodoActual = listasReproduccion.getFirst();
        int indice = 1;
        while (nodoActual != null) {
            System.out.println(indice + ". " + nodoActual.getPlaylist().getName()); // getPlaylist() de la clase Node
            nodoActual = nodoActual.getNext();
            indice++;
        }
        scanner.nextLine();
        menuBiblioteca();
    }

    private void verCancionesEnLista() {
        List lista = pedirYBuscarLista();
        
        if (lista != null) {
            System.out.println(lista.mostrarCanciones());
        } else {
            System.out.println("Lista no encontrada.");
            menuPrincipal();
        }
    }

    private List encontrarLista(String nombre) {
        Node nodoActual = listasReproduccion.getFirst();
        while (nodoActual != null) {
            if (nodoActual.getPlaylist().getName().equalsIgnoreCase(nombre)) {
                return nodoActual.getPlaylist();
            }
            nodoActual = nodoActual.getNext();
        }
        return null;
    }

    private void eliminarCancionDeFavoritos() {
        System.out.println("=== Canciones Favoritas ===");
        String cancionesFavoritas = canciones.mostrarFavoritas();

        if (cancionesFavoritas.isEmpty()) {
            System.out.println("No hay canciones favoritas para eliminar.");
            return;
        }

        System.out.print("Seleccione el índice de la canción a eliminar de favoritos: ");
        int indice = scanner.nextInt();
        scanner.nextLine();
        Cancion cancion = canciones.getCancion(indice);

        if (cancion != null) {
            cancion.setFavorita(false);
            System.out.println("Canción eliminada de favoritos.");
        } else {
            System.out.println("Índice no válido.");
        }
    }
}