//Clase auxiliar para funciones relacionadas a mostrar gráficos y colores por la consola.
public class Visuales {
    public static final String ANSI_RESET = "\u001B[0m"; //vuelve el texto a valores default
    public static final String ANSI_BLACK = "\u001B[30m";//constantes para manipular color/fondo de texto en consola
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String BLACK_BACKGROUND = "\033[40m";
    public static final String RED_BACKGROUND = "\033[41m";
    public static final String GREEN_BACKGROUND = "\033[42m";
    public static final String YELLOW_BACKGROUND = "\033[43m";
    public static final String BLUE_BACKGROUND = "\033[44m";
    public static final String PURPLE_BACKGROUND = "\033[45m";
    public static final String CYAN_BACKGROUND = "\033[46m";
    public static final String WHITE_BACKGROUND = "\033[47m";

    public static void printDeshabilitado(String texto){ //Toma un String y lo imprime por pantalla en color rojo. 
                                                        //Se utiliza para mostrar que una de las opciones que puede tomar el jugador no está disponible debido a falta de recursos.
        System.out.print(ANSI_RED
        + texto
        + ANSI_RESET + "\n");
    }

    public static void printPeligro(String mensaje){
        System.out.print(RED_BACKGROUND 
                        +mensaje
                        +ANSI_RESET
                        );

}

    public static void printSegunClima(String texto, String clima){ //Similar a las funciones anteriores, imprime texto con un fondo según el clima: 
                                                                    //Fondo verde para clima normal, blanco para nublado, amarillo para soleado y azul para lluvia. 
                                                                    //Se utiliza para crear el tablero e informar sobre el clima.
        if(clima == "SOLEADO"){
            System.out.print(YELLOW_BACKGROUND
                            + ANSI_BLACK
                            + texto
                            + ANSI_RESET);

        } 
        else if (clima == "NUBLADO"){
            System.out.print(WHITE_BACKGROUND
                            + ANSI_BLACK
                            + texto
                            + ANSI_RESET);

        }
        else if (clima == "LLUVIA"){
            System.out.print(CYAN_BACKGROUND
                            + ANSI_BLACK
                            + texto
                            + ANSI_RESET);

        }
        else{
            System.out.print(GREEN_BACKGROUND
                            + ANSI_BLACK
                            + texto
                            + ANSI_RESET);
        }


    }

    public static void printStringGrid(Juego juego){//Imprime el tablero que representa el estado del juego. 
                                                    //Toma el juego como parámetro para determinar ciertos valores. 
                                                    //Si un monstruo llega a la columna 0, el número de orden de esa columna se ilumina en rojo para indicar peligro.
        String[][] array = Juego.mapa;
        String clima = juego.climaActual;
        System.out.print("BASE|"); //imprimir encabezados de grilla
        for (int i = 0; i < array[0].length; i++){
            System.out.print("    ");;
            int encabezado = 0 + i;
            System.out.print(encabezado);
            if (encabezado > 9){ //si el encabezado son 2 digitos, emparejar el ancho usando un espacio menos
                System.out.print("   |");
            }
            else{
                System.out.print("    |");
            }

        }
        System.out.println();
        for (int i = 0; i < array.length; i++){ //por cada fila
            System.out.print("----+"); //imprimir el margen superior de la primer celda
            for (int j = 0; j < array[0].length; j++){
                System.out.print("---------+"); //margenes superiores de las siguientes celdas de la fila
            }
            System.out.println();
            if (Pos.posOcupada(array, new Pos(i,0))){
                if(array[i][0].startsWith("M")){
                    printPeligro("  " + (i) + " "); //si la base está bajo ataque en esta fila, colorear en rojo
                    System.out.print("|"); //numero de fila
                }
                else{
                    System.out.print("  " + (i) + " |"); //numero de fila
                }

            }
            else{
                System.out.print("  " + (i) + " |"); //numero de fila
            }

            for (int j = 0; j < array[0].length; j++){ //por cada columna
                if (array[i][j].length() < 10){ //acomodar espacios alrededor del contenido, si fuera mayor a 10 caracteres imprimir directo
                    int espacios = (9 - array[i][j].length()) / 2;
                    for (int k = 0; k < espacios; k++){
                        printSegunClima(" ", clima);//espacio a la izq del contenido
                    }
                    printSegunClima(array[i][j], clima); //contenido
                    for (int k = 0; k < (9 - array[i][j].length()) - espacios; k++){
                        printSegunClima(" ", clima);//espacio a la der del contenido
                    }
                }
                else{ //si el contenido es mayor a 10 caracteres, imprimir directo
                    System.out.print(array[i][j]);
                }
                System.out.print("|"); // fin de celda
            }
            System.out.println(); //nueva fila
        }
        System.out.print("----+"); //margen inferior de grilla
        for (int j = 0; j < array[0].length; j++){
            System.out.print("---------+");
        }
        System.out.println(); //nueva fila
    }

    public static void printCuadro(String mensaje){ //imprime el mensaje que se envía por parámetro en un cuadro de 100 char de ancho
        System.out.println();
        for (int i = 0; i < 100; i++){
            System.out.print("*");
        }
        System.out.println();
        int espacios = (100 - 2 - mensaje.length()) / 2; //se resta 2 por los márgenes
        System.out.print("*");
        for (int i = 0; i < espacios; i++){
            System.out.print(" ");
        }
        System.out.print(mensaje);
        for (int i = 0; i < 98 - mensaje.length() - espacios; i++){
            System.out.print(" ");
        }
        System.out.print("*");
        System.out.println();

        for (int i = 0; i < 100; i++){
            System.out.print("*");
        }
        System.out.println();

    }

    public static void printEstado(Juego j){ //Función que imprime el estado del juego con información para el jugador (clima, recursos, monstruos restantes, durabilidad de la base).
        System.out.print("Clima Actual: " );
        printSegunClima(j.climaActual, j.climaActual);
        System.out.println();
        System.out.println("TRÉBOLES: " + Juego.treboles + "🍀");
        System.out.println("AGUA: " + Juego.agua + "💧");
        System.out.println("MONSTRUOS RESTANTES POR APARECER: " + j.monstruosRestantes + "👽");
        System.out.println("DURABILIDAD BASE: " + j.durabilidadBase + "❤️");


    }

  

}
