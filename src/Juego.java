import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;



public class Juego {
    public static String[] climas = {"NORMAL", "SOLEADO", "NUBLADO", "LLUVIA"}; //array que define constantes de tipo String para expresar los climas de manera legible
    String climaActual = "NORMAL"; //se utiliza para llevar registro del clima actual, que irá cambiando aleatóriamente cada turno.
    
    HashMap<String, Monstruos> dicMonstruos = new HashMap<String, Monstruos>(); /*El juego utiliza dos HashMap, uno para Monstruos y otro para Plantas para llevar registro de los Monstruos y Plantas en juego. 
                                                                                Se utiliza el id de tipo String de cada una de estas clases como clave para los Hashmap.*/
    HashMap<String, Plantas> dicPlantas = new HashMap<String, Plantas>();
    int proximoIdMonstruo = 0;//se utilizan para construir un id incremental al construir esas clases.
    int proximoIdPlanta = 0;

    int durabilidadBase = 1000; //representa el daño que puede tomar la base hasta ser destruida y que el jugador pierda la partida.
    int monstruosRestantes = 0; //cantidad de monstruos que falta generar durante la partida, que varía su inicialización según la dificultad elegida por el jugador al iniciar el juego.
    static int treboles = 10;
    static int agua = 2;
    Boolean puedeUsarAgua = true; //se utiliza para restringir el uso de agua a una vez por turno.
    static String[][] mapa; //se utiliza para ubicar las plantas y monstruos en la grilla que se muestra por pantalla.
    static Scanner scText = new Scanner(System.in); //se utilizan dos scanner para operaciones de entrada por parte del jugador.
    static Scanner scNum = new Scanner(System.in);


    Juego(){ //Solo se encarga de inicializar todos los casilleros del mapa como vacíos. Los demás atributos se inicializan con valores default.
        Juego.mapa = new String[10][10];
        for (int i = 0; i < mapa.length ; i++ ){
            for(int j = 0 ;  j < mapa[i].length ; j++){
                mapa[i][j] = "";
            }
        }
        
    }

    public void elegirDificultad(){//Método que interactúa con el usuario para decidir la dificultad del juego. La cantidad de monstruos a generar varía según la dificultad elegida.
        int opcionDificultad = -1;
        do{
            System.out.println("\nELIJA LA DIFICULTAD: ");
            System.out.println("0 - FACIL ");
            System.out.println("1 - NORMAL");
            System.out.println("2 - DIFICIL");
            try {
                opcionDificultad =  Integer.parseInt(scNum.nextLine());
                if (opcionDificultad<0 || opcionDificultad > 2)
                {
                    throw new IllegalArgumentException("Opción inválida.");
                }
                
            } catch (Exception e) {
                System.out.println("Error en el input: " + e.getMessage());
            }

        }while(opcionDificultad < 0  || opcionDificultad > 2);
        
        switch(opcionDificultad){
            case 0: {
                System.out.println("Eligio dificultad FACIL");
                System.out.println("Peleará contra 5 monstruos");
                monstruosRestantes = 5;
                break;
            }
            case 1: {
                System.out.println("Eligio dificultad NORMAL");
                System.out.println("Peleará contra 5 monstruos");
                monstruosRestantes = 15;
                break;
            }
            case 2: {
                System.out.println("Eligio dificultad DIFICIL");
                System.out.println("Peleará contra 30 monstruos");
                monstruosRestantes = 30;
                break;
            }
        }
    }

    //funciones por turno
    static void ganarTrebol(){ //Método que agrega 1 trébol cada turno a los recursos y lo informa por pantalla.
        treboles +=1;
        System.out.println("Nuevo turno, aumenta en +1 la cantidad de tréboles!");
    }

    void habilitarAgua(){ //Método que habilita el uso de agua cada turno.
        puedeUsarAgua = true;
    }

    static String climaRandom(){ //Función que devuelve uno de los climas al azar.
        Random r = new Random();
        return climas[r.nextInt(climas.length)];
    }

    void cambiarClima(){ //Método que cambia el clima. Basado en el clima aleatorio que devuelve el método climaRandom, informa por pantalla el cambio. 
        climaActual = climaRandom();

        if (climaActual.equals("LLUVIA")){ //Si el clima es lluvioso, aumenta la vida de todas las plantas en +2.
            for(String pID:dicPlantas.keySet()){
                Plantas plantaActual = dicPlantas.get(pID);
                int vidaActual = plantaActual.getVida();
                plantaActual.setVida(vidaActual +2);
            }
            agua +=2;
            System.out.println("Clima lluvioso. Todas las plantas ganan +2 de vida!"
                            +" Aumenta cantidad de agua en +2!");
        }
        if (climaActual.equals("NUBLADO")){
            System.out.println("Clima nublado." + (monstruosRestantes >=2?"Este turno aparecieron dos monstruos en lugar de uno!":"") );

        }
        if(climaActual.equals("SOLEADO")){
            System.out.println("Clima soleado. Este turno las plantas pierden -2 defensa!");
        }

    }

    void trebolesDeGirasoles(){ //Método para aumentar la cuenta de turnos de los girasoles.
        for(String id: dicPlantas.keySet()){ //Recorre el HashMap de Plantas verificando si son instanceof Girasol, y en caso de que lo sean, se invoca su método generarTreboles()
            Plantas plantaActual = dicPlantas.get(id);
            if(plantaActual instanceof Girasol){
                Girasol girasolActual = (Girasol) plantaActual;
                boolean seGeneraronTreboles = girasolActual.generarTreboles();
                if(seGeneraronTreboles){ //Si la cuenta de turnos llegó al valor requerido, se aumentan los tréboles del jugador y se informa por pantalla
                    treboles += 1;
                    System.out.println("+1 trébol por el efecto del Girasol \"" + girasolActual.getId() + "\"");
                }
            }
        }
    }

    void consultaUsuario(){ //Método que presenta al usuario las opciones que tiene disponibles durante el turno. 
        if( durabilidadBase >0){ /*se verifica si la base fue destruida por las acciones automáticas, 
            ya que en ese caso el usuario ya perdió la partida y sus opciones no tendrán impacto. */
            int opcionConsulta = -1;
            Boolean terminarTurno = false;
            do{
                Visuales.printStringGrid(this); //Se muestran las opciones y la grilla por pantalla cada vez que el usuario toma alguna decisión durante el turno.
                Visuales.printEstado(this);
                Boolean opcion0Habilitada = treboles >= 3;
                Boolean hayPlantas = dicPlantas.size() >0;
                Boolean opcion1Habilitada = agua > 0 && puedeUsarAgua && hayPlantas;
                System.out.println("Qué desea hacer este turno?");
                /*Si el usuario no puede crear plantas o utliizar agua, se marca la opción como deshabilitada. 
                Las opciones de pasar el turno y destruir la base siempre están disponibles. Si el usuario elige una opción deshabilitada, 
                su elección devuelve una excepción y debe elegir una opción diferente. */
                if(opcion0Habilitada){
                    System.out.println("0 - Crear una planta");
                }
                else{
                    Visuales.printDeshabilitado("0 - Crear una planta");
                }
                if(opcion1Habilitada){
                    System.out.println("1 - Usar agua en una planta (una vez por turno)");
                }
                else{
                    Visuales.printDeshabilitado("1 - Usar agua en una planta (una vez por turno)");
                }
                System.out.println("2 - Pasar turno");
                System.out.println("3 - Destruir base");
                try {
                    opcionConsulta =  Integer.parseInt(scNum.nextLine());
                    if (opcionConsulta<0 || opcionConsulta > 3){
                        throw new IllegalArgumentException("Opción inválida.");
                    }
                    else{
                        switch(opcionConsulta){
                            case(0):{
                                if(opcionConsulta == 0 && !opcion0Habilitada){

                                    throw new IllegalArgumentException("No hay suficientes tréboles.");
                                }
                                else{
                                    consultaPlanta(); //Si el usuario elige crear una planta, se llama al método consultaPlanta.
                                }
                                break;
                            }
                            case(1):{
                                if(opcionConsulta == 1 &&  !opcion1Habilitada){
                                    throw new IllegalArgumentException("Ya utilizó agua este turno, no hay agua disponible, o no hay plantas para regar.");
                                }
                                else{
                                    consultaAgua(); //Si elige utlizar agua, se llama al método consultaAgua.
                                    
                                }
                                break;
                            }
                            case(2):{
                                terminarTurno =true;
                                System.out.println("Se pasa al turno siguiente.");
                                break;                                 
                            }
                            case(3):{
                                durabilidadBase = 0;
                                System.out.println("Eligió destruir la base para terminar el juego.");
                                terminarTurno = true;
                            }
                        }             
                    }
                } 
                catch (Exception e) {
                    System.out.println("Error en el input: " + e.getMessage());
                }

            }while(!terminarTurno);

        }
        
    }



    void consultaAgua(){ 
        /*Método para verificar si el usuario eligió la opción que quería, 
        sirve para tener una salida al loop en caso de que el usuario encuentre un problema al utilizar agua. */
        String opcionConsulta = "";
        do{
            System.out.println("\nDesea utilizar agua este turno?");
            System.out.println("-SI");
            System.out.println("-NO");
            try {
                opcionConsulta =  scText.nextLine().strip().toUpperCase();
                if (!opcionConsulta.equals("SI") && !opcionConsulta.equals("NO") )
                {
                    throw new IllegalArgumentException("Opción inválida.");
                }
            }                
            catch (Exception e) {
                System.out.println("Error en el input: " + e.getMessage());
            }

        }while(!opcionConsulta.equals("SI") && !opcionConsulta.equals("NO") );

        if(opcionConsulta.equals("SI")){
            usarAgua();
        }
        else{
            System.out.println("Se canceló el uso de agua.");
        }

    }

    void usarAgua(){ //Método para utlizar agua. Aumenta la vida de una planta al azar en +30. 
        Set<String> conjPlantas = dicPlantas.keySet();
        Iterator<String> it = conjPlantas.iterator();

        Random r = new Random();
        int indiceRandom = r.nextInt(conjPlantas.size());
        for(int i = 0; i< indiceRandom; i++){ //itera el HashMap de plantas un número random de veces y aumenta la vida de esa planta
            it.next();

        }
        String idPlantaARegar = it.next();

        Plantas plantaARegar = dicPlantas.get(idPlantaARegar);
        plantaARegar.setVida(plantaARegar.getVida() + 30);

        agua -= 1; //Reduce el agua disponible y deshabilita la opción de usar agua este turno.
        puedeUsarAgua = false;

        System.out.println("La planta \"" + idPlantaARegar + "\" ganó +30 vida!");  //lo informa por pantalla. 
        

    }

    void consultaPlanta(){ 
        /*Método para verificar si el usuario eligió la opción que quería, 
        sirve para tener una salida al loop en caso de que el usuario encuentre un problema al crear una planta. */
        String opcionConsulta = "";
        do{
            System.out.println("\nDesea crear una nueva planta este turno?");
            System.out.println("-SI");
            System.out.println("-NO");
            try {
                opcionConsulta =  scText.nextLine().strip().toUpperCase();
                if (!opcionConsulta.equals("SI") && !opcionConsulta.equals("NO") )
                {
                    throw new IllegalArgumentException("Opción inválida.");
                }
            }                
            catch (Exception e) {
                System.out.println("Error en el input: " + e.getMessage());
            }

        }while(!opcionConsulta.equals("SI") && !opcionConsulta.equals("NO") );

        if(opcionConsulta.equals("SI")){
            crearPlanta();
        }
        else{
            System.out.println("Se canceló la creación de la planta.");
        }



    }

    public static Boolean alcanzanTreboles(int treboles, int tipoPlanta){ //Función auxiliar. Devuelve si un valor de tréboles es suficiente para crear un tipo dado de planta.

        switch (tipoPlanta){
            case 0: {
                return (treboles >= 3);
            }
            case 1: {
                return (treboles >= 3);
            }
            case 2:{
                return (treboles >= 6);
            }
            case 3:{
                return (treboles >= 4);
            }


        }


        return true;
    }

    public void turnosCarnivoras(){ //Método para aumentar la cuenta de turnos de las plantas Carnívoras. 
        for(String pId:dicPlantas.keySet()){
            Plantas p = dicPlantas.get(pId);
            if(p instanceof Carnivora){//Recorre el HashMap de Plantas verificando si son instanceof Carnivora y en caso de que lo sean, se invoca su método pasarTurno().
                Carnivora c = (Carnivora) p;
                c.pasarTurno();
            }
        }
    }

    public void cargasTitanes(){ //Método para aumentar la cuenta de cargas de los titanes.
        for(String mId:dicMonstruos.keySet()){
            Monstruos m = dicMonstruos.get(mId);
            if(m instanceof Titan){//Recorre el HashMap de Monstruos verificando si son instanceof Titan, y en caso de que lo sean, se invoca su método t.cargar()
                Titan t = (Titan) m;
                t.cargar();
            }
        }
    }

    void crearPlanta(){ //Método para crear plantas. El usuario puede generar todas las plantas que pueda costear por turno

        int opcionPlanta = -1;
        Boolean cancelarPlanta = false;
        do{//informa al usuario de qué opciones tiene disponibles para las plantas utilizando el método alcanzanTreboles.
            Boolean opcion0Habilitada = alcanzanTreboles(treboles, 0); 
            Boolean opcion1Habilitada = alcanzanTreboles(treboles, 1);
            Boolean opcion2Habilitada = alcanzanTreboles(treboles, 2);
            Boolean opcion3Habilitada = alcanzanTreboles(treboles, 3); 
            System.out.println("\nELIJA EL TIPO DE PLANTA: ");
            if(opcion0Habilitada){
                System.out.println("0 - Planta Normal (Costo: 3🍀 Vida: 30 ATQ: 30 DEF: 5 ) ");
            }
            else{
                Visuales.printDeshabilitado("0 - Planta Normal (Costo: 3🍀 Vida: 30 ATQ: 30 DEF: 5 ) ");
            }
            if(opcion1Habilitada){
                System.out.println("1 - Girasol (Costo: 3🍀 Vida: 30 ATQ: 2 DEF: 5 ESPECIAL: Genera 1 🍀 cada 4 turnos)");
            }
            else{
                Visuales.printDeshabilitado("1 - Girasol (Costo: 3🍀 Vida: 30 ATQ: 2 DEF: 5 ESPECIAL: Genera 1 🍀 cada 4 turnos)");
            }
            if(opcion2Habilitada){
                System.out.println("2 - Carnivora (Costo: 6🍀 Vida: 50 DEF: 9 ESPECIAL: Cada 4 turnos puede matar al primer monstruo que se acerque )");
            }
            else{
                Visuales.printDeshabilitado("2 - Carnivora (Costo: 6🍀 Vida: 50 DEF: 9 ESPECIAL: Cada 4 turnos puede matar al primer monstruo que se acerque )");
            }
            if(opcion3Habilitada){
                System.out.println("3 - Guisante (Costo: 4🍀 Vida: 35 ATQ: 3 DEF: 8 ESPECIAL: Ataca a 3 posiciones de distancia)");
            }
            else{
                Visuales.printDeshabilitado("3 - Guisante (Costo: 4🍀 Vida: 35 ATQ: 3 DEF: 8 ESPECIAL: Ataca a 3 posiciones de distancia)");
            }
            System.out.println("4 - CANCELAR");
            try {
                opcionPlanta =  Integer.parseInt(scNum.nextLine());
                if (opcionPlanta<0 || opcionPlanta > 4)
                {
                    throw new IllegalArgumentException("Opción inválida.");
                }
                cancelarPlanta = opcionPlanta == 4;

                if (!alcanzanTreboles(treboles, opcionPlanta)){
                    opcionPlanta = -1;
                    throw new IllegalArgumentException("No tiene suficientes 🍀 para ese tipo de planta");

                }
                
            } catch (Exception e) {
                System.out.println("Error en el input: " + e.getMessage());
            }

        }while((opcionPlanta < 0  || opcionPlanta > 4) && !(cancelarPlanta));

        if (!cancelarPlanta){
            int opcionX = -1;
            int opcionY = -1;
            Pos nuevaPos = new Pos(opcionX, opcionY);
            Boolean nuevaPosValida = false;


            do{ //se elige su ubicación. Se verifica que la posición a crear sea válida (dentro del rango 0,0 y 9,9)
                do{
                    System.out.println("\nELIJA LA UBICACION DE LA PLANTA: ");
                    System.out.println("-Ingrese la fila (0-9):");
        
                    try {
                        opcionX =  Integer.parseInt(scNum.nextLine());
                        if (opcionX<0 || opcionX > 9)
                        {
                            throw new IllegalArgumentException("Opción inválida.");
                        }
                        
                    } catch (Exception e) {
                        System.out.println("Error en el input: " + e.getMessage());
                    }
        
                }while(opcionX <0 || opcionX > 9);
    
                do{
                    System.out.println("-Ingrese la columna (0-9):");
        
                    try {
                        opcionY =  Integer.parseInt(scNum.nextLine());
                        if (opcionY<0 || opcionY > 9)
                        {
                            throw new IllegalArgumentException("Opción inválida.");
                        }
                        
                    } catch (Exception e) {
                        System.out.println("Error en el input: " + e.getMessage());
                    }
        
                }while(opcionY <0 || opcionY > 9);
                nuevaPos = new Pos(opcionX, opcionY);
                if (Pos.posOcupada(mapa, nuevaPos)){ //se verifica si está ocupada por otra planta o un monstruo mediante el método de la clase Pos posOcupada
                    System.out.println("La posición ya está ocupada");
                    System.out.println("\nDesea volver a ingresar la posición?");
                    System.out.println("-SI");
                    System.out.println("-NO");
                    String opcionConsulta = "";
                    do{
                        try {
                            opcionConsulta =  scText.nextLine().strip().toUpperCase();
                            if (!opcionConsulta.equals("SI") && !opcionConsulta.equals("NO") )
                            {
                                throw new IllegalArgumentException("Opción inválida.");
                            }
                        }                
                        catch (Exception e) {
                            System.out.println("Error en el input: " + e.getMessage());
                        }

                    }while(!opcionConsulta.equals("SI") && !opcionConsulta.equals("NO") );
                    cancelarPlanta = opcionConsulta.equals("NO");
 
                }
                else{
                    nuevaPosValida = Pos.posValida(nuevaPos);
                }

            }while(!(nuevaPosValida) && !(cancelarPlanta));

            if (cancelarPlanta){
                System.out.println("Se canceló la creación de la planta.");
                consultaPlanta();
            }
            else{
            Plantas nuevaPlanta = PlantaFactory.crearPlanta(opcionPlanta); //Cuando se tiene una posición válida, se llama al al método crearPlanta de PlantaFactory
                    
            nuevaPlanta.setPosicion(new Pos(opcionX, opcionY)); //Se setea la posición de la planta a la posicíon asignada por el usuario.
            proximoIdPlanta++;    
            String numeroOrdenPlantaNueva = "P".concat(String.valueOf(proximoIdPlanta)); //Se genera un id consecutivo al de la última planta generada (o a partir de 0),  
            nuevaPlanta.setId(numeroOrdenPlantaNueva); //se le setea este valor a la planta
            dicPlantas.put(numeroOrdenPlantaNueva, nuevaPlanta); //y se agrega esta planta al HashMap de Plantas con ese id
            Pos.agregarAMapa(mapa, nuevaPlanta.getPosicion(), numeroOrdenPlantaNueva); //se agrega la planta al mapa para mostrarla por pantalla
    
            treboles -= nuevaPlanta.getCostoTrebol(); //Finálmente, se descuenta su costo de tréboles de los recursos del jugador.
    
            }

            


        }
        else{
            System.out.println("Se canceló la creación de la planta.");
            consultaPlanta();
        }
    }

    void crearMonstruo(){ //Método para crear monstruos
        if(monstruosRestantes > 0){ //Solo aplica si quedan monstruos restantes por generar en el juego
            Boolean posOcupada = true;
            int intentos = 0;
            Pos posNueva = null;
            do{ //Primero, se intenta generar una posición en la mitad derecha (Y >=5) del tablero.
                Random r = new Random();
                int posX = r.nextInt(10);
                int posY = r.nextInt(5) + 5; //un monstruo nuevo debe aparecer a 5 casilleros de distancia de la base
                posNueva = new Pos(posX,posY);
                posOcupada = Pos.posOcupada(mapa, posNueva); //se verifica si esa posición está libre con el método de la clase Pos posOcupada

                
            }while(posOcupada && intentos < 10); //se realizan 10 intentos para generar una posición libre al azar.
                                                //si no se logra generar en 10 intentos, el campo puede estar muy lleno.
                                                //si no se establece un límite, este loop se vuelve infinito cuando la mitad derecha del campo está llena.

            if (!posOcupada){ //se genera un monstruo de alguna de las clases al azar con el método crearMonstruos de la clase MonstruoFactory
                Random r = new Random();
        
                int tipoMonstruo = r.nextInt(4);
                Monstruos mNuevo = MonstruoFactory.crearMonstruos(tipoMonstruo);
                mNuevo.setPosicion(posNueva);    
                proximoIdMonstruo++;
                String numeroOrdenMonstruoNuevo = "M".concat(String.valueOf(String.valueOf(proximoIdMonstruo))) ;
                mNuevo.setId(numeroOrdenMonstruoNuevo);
                dicMonstruos.put(numeroOrdenMonstruoNuevo, mNuevo);
                Pos.agregarAMapa(mapa, mNuevo.getPosicion(), numeroOrdenMonstruoNuevo);
    
                monstruosRestantes -= 1; //Finálmente, se reduce la cantidad de monstruos restantes por generar.

            }
            else{ //Si llegaran a agotarse los 10 intentos, no se genera el Monstruo y se informa por pantalla.
                System.out.println("La mitad derecha del campo está muy llena para generar nuevos monstruos por ahora...");
            }

        }

    }

    void moverTodosLosMonstruos(){ //Método que invoca el método moverMonstruo de todos los monstruos en el HashMap de Monstruos.
        for(String mId:dicMonstruos.keySet()){
            Monstruos mActual = dicMonstruos.get(mId);
            mActual.moverMonstruo(mapa);
        }
    }



//funciones de combate:
    void destruirPlanta(String id){ //Método que dado un id de una Planta, la elimina de la grilla visible y luego la borra del HashMap.
        Plantas p = dicPlantas.get(id);
        Pos posActual = p.getPosicion();
        Pos.quitarDeMapa(mapa, posActual, id);
        dicPlantas.remove(id);

    }

    void destruirMonstruo (String id) //Método que dado un id de una Monstruo, lo elimina de la grilla visible y luego lo borra del HashMap.
    {
        Monstruos p = dicMonstruos.get(id);
        Pos posActual = p.getPosicion();
        Pos.quitarDeMapa(mapa, posActual, id);
        dicMonstruos.remove(id);

    }

    void atacanPlantas(){
        //Método para iniciar el ataque de todas las plantas. 
        Set<String> setPlantas = dicPlantas.keySet();
        for(String idPlanta:setPlantas){//Por cada planta en el HashMap de Plantas,  
            Plantas pActual = dicPlantas.get(idPlanta);
            HashSet<Pos> vecinos = Pos.Vecinos(mapa, pActual.getPosicion(), pActual.getRango()); //se obtiene un HashSet con sus casilleros vecinos según el rango de ataque mediante el método Vecinos de la clase Pos.
            HashSet<Pos> posMonstruos = new  HashSet<Pos>(); //Se agregan a un HashSet todos los monstruos que se encuentren en alguna de estas posiciones vecinas
            Set<String> setMonstruos = dicMonstruos.keySet();
            for(String idMonstruo:setMonstruos){
                posMonstruos.add(dicMonstruos.get(idMonstruo).getPosicion());
            }
            for (Pos posVecina:vecinos){
                for (Pos posM:posMonstruos){
                    if (posVecina.equals(posM)){
                        String idMonstruoDefensa = mapa[posM.getX()][posM.getY()];
                        plantaAtacaMonstruo(idPlanta, idMonstruoDefensa); //se ataca a cada monstruo con el método plantaAtacaMonstruo, obteniendo el id del monstruo desde el mapa                  

                    }
                }

            }

        }
    }



    void plantaAtacaMonstruo(String idPlanta, String idMonstruo){ //Método que dados los id de una Planta y un Monstruo, procede a llamar al método atacarMonstruo de la planta. 
        Plantas p = dicPlantas.get(idPlanta);
        Monstruos m = dicMonstruos.get(idMonstruo);

        if(m instanceof Fantasma){ //Se asegura de aplicar la habilidad de los Fantasmas en caso de que corresponda
            Fantasma f = (Fantasma) m;
            f.setEsquivaAtaque();
            if (f.isEsquivaAtaque()){
                f.incorporeo(p);
            }
            else{
                Boolean monstruoDestruido = p.atacarMonstruo(m, climaActual.equals("LLUVIA")); //se informa al método atacarMonstruo si está lloviendo.

                if(monstruoDestruido){ //Si se destruye al monstruo, se llama al método destruirMonstruo
                    destruirMonstruo(idMonstruo);
                }
            }
        }
        else{
            Boolean monstruoDestruido = p.atacarMonstruo(m, climaActual.equals("LLUVIA")); //se informa al método atacarMonstruo si está lloviendo.

            if(monstruoDestruido){ //Si se destruye al monstruo, se llama al método destruirMonstruo
                destruirMonstruo(idMonstruo);
            }
        }
        
    }

    void atacanMonstruos(){ //Método para iniciar el ataque de todos los monstruos. Obtiene las plantas objetivo de la misma forma que el método atacanPlantas con los roles invertidos.
        Set<String> setMonstruos = dicMonstruos.keySet();
        for(String idMonstruo: setMonstruos){
            Monstruos mActual = dicMonstruos.get(idMonstruo);
            HashSet<Pos> vecinos = Pos.Vecinos(mapa, mActual.getPosicion(), mActual.getRango());
            HashSet<Pos> posPlantas = new  HashSet<Pos>();
            Set<String> setPlantas = dicPlantas.keySet();
            for(String idPlanta:setPlantas){
                posPlantas.add(dicPlantas.get(idPlanta).getPosicion());
            }
            for (Pos posVecina:vecinos){
                for (Pos posP:posPlantas){
                    if (posVecina.equals(posP)){
                        String idPlantaDefensa = mapa[posP.getX()][posP.getY()];
                        monstruoAtacaPlanta(idMonstruo, idPlantaDefensa); //Invoca el método monstruoAtacaPlanta de cada monstruo que corresponda.                  

                    }
                }

            }
            if(mActual.getPosicion().getY() == 0){ //Adicionálmente, si el Monstruo llegó a la base (columna Y = 0), inflinge daño a la base y se informa por pantalla.
                durabilidadBase -= mActual.atacarBase();   
                System.out.println("(" + (durabilidadBase < 0 ? 0:durabilidadBase) + "❤️ restante)");        
            }
        }
    }



    void monstruoAtacaPlanta(String idMonstruo, String idPlanta){ //Método que dados los id de un Monstruo y una Planta, procede a llamar al método atacarPlanta del monstruo
        Monstruos m = dicMonstruos.get(idMonstruo);
        Plantas p = dicPlantas.get(idPlanta);

        Boolean plantaDestruida = m.atacarPlanta(p, climaActual.equals("SOLEADO")); //Informa al método a llamar si el clima está soleado.

        if(plantaDestruida){ //Si se destruye a la planta, se llama al método destruirPlanta.
            destruirPlanta(idPlanta);
        }
        
    }
    
}
