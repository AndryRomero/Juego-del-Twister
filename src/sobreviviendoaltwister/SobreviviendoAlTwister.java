package sobreviviendoaltwister;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SobreviviendoAlTwister {
    // Listas estáticas de refugios, suministros y puntos
    static List<String> refugios = List.of("Subterráneo", "Montañas", "Bosque");
    static List<String> inventario_usuario = new ArrayList<>();
    static List<String> suministros_clave = List.of(
            "Manta térmica", "Encendedor", "Comida enlatada", "Botiquín básico", 
            "Kit de primeros auxilios", "Linterna", "Radio", "Filtro de agua", 
            "Cobija", "Herramientas"
    );
    static List<Integer> puntos_suministros = List.of(10, 20, 30, 40, 50, 60, 70, 80, 90, 100);

    // Variables de control del juego
    static int puntos_totales = 0;
    static int puntos_minimos_para_ganar = 200;
    static boolean cambio_permitido = true;
    static String nombre_usuario;
    static String suministro_clave_requerido;
    static Huracan huracanActual;
    static String refugioSeleccionado;

    // Clase para definir las características del huracán
    static class Huracan {
        String nombreCategoria;
        int velocidadMinima;
        int velocidadMaxima;
        String danos;
        String medidasSeguridad;

        // Objeto Huracán
        Huracan(String nombreCategoria, int velocidadMinima, int velocidadMaxima, 
                String danos, String medidasSeguridad) {
            this.nombreCategoria = nombreCategoria;
            this.velocidadMinima = velocidadMinima;
            this.velocidadMaxima = velocidadMaxima;
            this.danos = danos;
            this.medidasSeguridad = medidasSeguridad;
        }
    }

    public static void main(String[] args) {
        // Inicializa una lista de huracanes disponibles
        Huracan[] huracanes = {
            new Huracan("Categoría 1", 119, 153, "Daños menores", "Asegurar objetos sueltos"),
            new Huracan("Categoría 2", 154, 177, "Daños moderados", "Almacenar agua potable"),
            new Huracan("Categoría 3", 178, 209, "Daños severos", "Evacuar zonas de riesgo"),
            new Huracan("Categoría 4", 210, 249, "Daños extensos", "Cortar servicios básicos"),
            new Huracan("Categoría 5", 250, 300, "Destrucción catastrófica", "Evacuar inmediatamente")
        };

        // Selecciona un huracán aleatorio
        Random random = new Random();
        huracanActual = huracanes[random.nextInt(huracanes.length)];

        // Inicializa el escáner para capturar la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        // Solicita el nombre del jugador
        System.out.println("Bienvenido al juego 'Sobreviviendo al Twister'. ¿Cuál es tu nombre?");
        nombre_usuario = scanner.nextLine();

        // Permite al jugador seleccionar un refugio
        refugioSeleccionado = seleccionarRefugio(scanner);

        // Define el suministro clave según el refugio seleccionado
        definirSuministroClave(refugioSeleccionado);

        // Permite al jugador seleccionar 5 suministros
        recolectarSuministros(scanner);

        // Muestra una cuenta regresiva antes de que el huracán llegue
        mostrarCuentaRegresiva();

        // Muestra información del huracán seleccionado
        System.out.println("\n¡El huracán se acerca! Es de categoría: " + huracanActual.nombreCategoria +
                ". Su velocidad mínima es de " + huracanActual.velocidadMinima + " Km/h. La velocidad máxima alcanzará los " +
                huracanActual.velocidadMaxima + " Km/h. " + huracanActual.danos + ". Se sugiere: " + 
                huracanActual.medidasSeguridad);

        // Verifica si el refugio es seguro
        verificarSeguridad(scanner);

        // Cierra el escáner
        scanner.close();
    }

    public static String seleccionarRefugio(Scanner scanner) {
        // Muestra las opciones de refugios disponibles
        System.out.println("Selecciona un refugio:");
        for (int i = 0; i < refugios.size(); i++) {
            System.out.println((i + 1) + ". " + refugios.get(i));
        }
        // Captura la elección del usuario
        int opcion = scanner.nextInt() - 1;
        scanner.nextLine();
        return refugios.get(opcion);
    }

    public static void definirSuministroClave(String refugio) {
        // Asigna un suministro clave según el refugio seleccionado
        switch (refugio) {
            case "Montañas" -> suministro_clave_requerido = "Cobija";
            case "Bosque" -> suministro_clave_requerido = "Filtro de agua";
            default -> suministro_clave_requerido = "Herramientas";
        }
    }

    public static void recolectarSuministros(Scanner scanner) {
        // Muestra las opciones de suministros disponibles
        System.out.println("\nSelecciona 5 suministros:");
        for (int i = 0; i < suministros_clave.size(); i++) {
            System.out.println((i + 1) + ". " + suministros_clave.get(i));
        }

        // Permite al jugador seleccionar 5 suministros diferentes
        while (inventario_usuario.size() < 5) {
            System.out.println("Elige un suministro: ");
            int opcion = scanner.nextInt() - 1;
            scanner.nextLine();
            String suministro = suministros_clave.get(opcion);

            // Verifica si el suministro ya fue seleccionado
            if (!inventario_usuario.contains(suministro)) {
                inventario_usuario.add(suministro);
                // Suma los puntos del suministro seleccionado
                puntos_totales += puntos_suministros.get(opcion);
            } else {
                System.out.println("Suministro ya seleccionado.");
            }
        }
    }

    public static void mostrarCuentaRegresiva() {
        // Muestra una cuenta regresiva para aumentar la expectativa
        System.out.println("\nPreparando datos del huracán...");
        for (int i = 3; i > 0; i--) {
            System.out.println("Revelando en... " + i);
            esperar(1000);
        }
        System.out.println("¡Prepárate!");
    }

    public static void verificarSeguridad(Scanner scanner) {
        // Verifica si el jugador tiene el suministro clave necesario
        if (!inventario_usuario.contains(suministro_clave_requerido)) {
            System.out.println("No tienes el suministro clave. ¿Deseas cambiar uno por un suministro al azar? (Sí/No)");
            if (scanner.nextLine().equalsIgnoreCase("Sí") && cambio_permitido) {
                cambiarSuministro(scanner);
            }
        } else {
            // Suma puntos adicionales por tener el suministro clave
            puntos_totales += 100;
        }

        // Verifica si se alcanzan los puntos mínimos para asegurar el refugio
        if (puntos_totales >= puntos_minimos_para_ganar) {
            System.out.println("¡Tu refugio es seguro!");
        } else {
            System.out.println("No alcanzaste los puntos necesarios para asegurar el refugio.");
        }

        // Muestra el resumen final de la partida
        mostrarResumenFinal();
    }

    public static void cambiarSuministro(Scanner scanner) {
        // Muestra los suministros actuales del jugador
        System.out.println("Suministros actuales:");
        for (int i = 0; i < inventario_usuario.size(); i++) {
            System.out.println((i + 1) + ". " + inventario_usuario.get(i));
        }

        // Permite al jugador cambiar un suministro
        System.out.print("Elige el suministro a cambiar: ");
        int opcion = scanner.nextInt() - 1;
        scanner.nextLine();
        inventario_usuario.set(opcion, suministro_clave_requerido);
        cambio_permitido = false;
        System.out.println("Suministro cambiado. Ahora tienes: " + suministro_clave_requerido);
    }

    public static void mostrarResumenFinal() {
        // Muestra un resumen con los detalles de la partida
        System.out.println("\nResumen de tu partida:");
        System.out.println("Nombre: " + nombre_usuario);
        System.out.println("Refugio elegido: " + refugioSeleccionado);
        System.out.println("Suministros seleccionados:");
        for (String suministro : inventario_usuario) {
            System.out.println("- " + suministro);
        }
        System.out.println("Huracán: " + huracanActual.nombreCategoria);
        System.out.println("Puntos totales: " + puntos_totales);

        // Muestra el resultado final según los puntos acumulados
        if (puntos_totales >= puntos_minimos_para_ganar) {
            System.out.println("¡Has sobrevivido al Twister!");
        } else {
            System.out.println("El Twister arrasó tu refugio.");
        }
    }
    public static void esperar(int milisegundos) {
        // Pausa la ejecución del programa por los milisegundos indicados
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
