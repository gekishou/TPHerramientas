public class Main {

        static int turno = 0;
        static Juego juego = new Juego();

        static void pasarTurno(){ //Aumenta en 1 la cantidad de turnos y llama a los métodos del juego que se ejecutan cada turno.
            /*Cada turno:
            1) Se habilita el uso de agua.
            2) Se muestra un cuadro por pantalla con el turno actual mediante el método printCuadro de la clase Visuales.
            3) Se aumentan los contadores internos de las plantas carnívoras, los girasoles y los titanes.
            4) Se genera un trébol por turno ya que si no se generan mediante la creación de Girasoles, el jugador no puede continuar generando plantas.
            5) Se cambia el clima.
            6) Se mueven los monstruos 1 casillero a la derecha, si está libre.
            7) Atacan todas las plantas.
            8) Atacan todos los monstruos.
            9) Se crea un nuevo monstruo de los restantes, y si el clima está nublado, se repite esta llamada.
            10) Se muestra la grilla y el menú al usuario. */
            turno++;
            juego.puedeUsarAgua = true;
            Visuales.printCuadro("TURNO: " + turno);
            juego.turnosCarnivoras();
            juego.cargasTitanes();
            juego.trebolesDeGirasoles();
            Juego.ganarTrebol();
            juego.cambiarClima();
            juego.moverTodosLosMonstruos();
            juego.atacanPlantas();
            juego.atacanMonstruos();
            juego.crearMonstruo();
            if(juego.climaActual.equals("NUBLADO")){
                juego.crearMonstruo();
            }
            System.out.println();
            juego.consultaUsuario();
        }





        public static void main(String[] args)
        {
            Visuales.printCuadro("PLANTAS VS. MONSTRUOS"); // Se muestra un cuadro con el nombre del programa (mediante el método printCuadro de la clase Visuales)
            juego.elegirDificultad(); //se consulta al usuario por la dificultad de la partida

            boolean LOSE = false; //se establece una condición de victoria y una de derrota
            boolean WIN = false;

            do{
                pasarTurno(); //se pasan turnos hasta que se cumpla alguna de esas condiciones
                LOSE = (juego.durabilidadBase <= 0); //Como condición de derrota, la durabilidad de la base debe ser igual o menor a 0 
                WIN = (juego.monstruosRestantes == 0 && juego.dicMonstruos.keySet().size() == 0); //como condición de victoria, no deben quedar monstruos por generar 
                                                                                                //Y no deben haber monstruos restantes en el HashMap de monstruos que estén en el tablero

            }while(!LOSE && !WIN);



            //fin de juego:
            Visuales.printCuadro("FIN DEL JUEGO"); //Una vez que se cumple alguna de estas condiciones, se informa por pantalla y se cierran los scanner

            if(WIN){
                System.out.println("Venciste a todos los monstruos, ganaste!");
            }
            if(LOSE){
                System.out.println("Los monstruos destruyeron la base, perdiste!");
            }
            Juego.scText.close();
            Juego.scNum.close();




       
        }

}
