import java.util.Random;

public abstract class Monstruos {
    private String id;
    private int vida;
    private int ataque;
    private int defensa;
    private Pos posicion;
    private int rango;

    public int getVida() {
        return this.vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getAtaque() {
        return this.ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return this.defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public Pos getPosicion() {
        return this.posicion;
    }

    public void setPosicion(Pos posicion) {
        this.posicion = posicion;
    }
    
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public int getRango() {
        return this.rango;
    }

    public void setRango(int rango) {
        this.rango = rango;
    }

    public void moverMonstruo(String[][] mapa){
        /*El método recibe una matriz de String para determinar cómo puede moverse el monstruo. Si el monstruo llegó a la columna 0, 
        el monstruo ha llegado a la base y no hace falta que continúe moviéndose. En caso contrario, 
        se calcula la posición siguiente restando 1 al valor de la columna actual para mover al monstruo de derecha a izquierda. 
        Se utiliza el método de la clase Pos posOcupada para determinar si ya hay otro Monstruo o una Planta en el casillero siguiente, 
        y en ese caso no se avanza al monstruo. Esto genera un comportamiento que algunos monstruos pueden llegar a bloquear a otros según el orden de ejecución. 
         */
        Pos posActual = getPosicion();
        if(posActual.getY() != 0){ //Si el monstruo llegó a la base, no hace falta que se mueva.
            Pos posSiguiente = new Pos(posActual.getX(), posActual.getY() - 1);
            Boolean siguienteOcupado = Pos.posOcupada(mapa, posSiguiente);
            if (!siguienteOcupado){ //si la siguiente posición está libre, avanzar.
                setPosicion(posSiguiente);
                mapa[posActual.getX()][posActual.getY()] = "";
                mapa[posSiguiente.getX()][posSiguiente.getY()] = getId();
                anunciarMovimiento();
            }
        }     
    }

    public void anunciarMovimiento(){
        /*Se informa por pantalla la nueva ubicación del monstruo, y si llegó a la columna 0, se envía un mensaje de alerta al jugador. */
            Pos posAnterior = new Pos (getPosicion().getX(), getPosicion().getY());
            System.out.println("El monstruo "+ tipo() + " \"" + getId() + "\" avanzó al casillero " + getPosicion() + " desde " + posAnterior + "." );
            if(getPosicion().getY() == 0){
                Visuales.printPeligro("PELIGRO: El monstruo "+ getId() + " llegó a la base!!!");
                System.out.println();
            }
        }

    public abstract String tipo();
    /*Función abstracta implementada por cada clase de monstruo, devuelve un String con su tipo para mostrar por pantalla. */

    public Boolean atacarPlanta(Plantas p, Boolean estaSoleado){
        /*Recibe la planta que está siendo atacada como parámetro, así como un valor booleano que indica si está soleado o no. 
        Se devuelve como valor de salida un booleano que indica si la planta atacada fue destruida o no.
        Primero, se calcula el valor de ataque del monstruo con un valor random entre 0 y su valor de ataque.que. 
        Se obtiene la defensa de la planta atacada. Luego, si el valor estaSoleado es true, se reduce su defensa en -2 como indica el enunciado. 
        Se procede a calcular cuánto daño se produce restando la defensa al ataque (con un mínimo de 0 daño). 
        Se calcula si este daño es suficiente para destruir a la planta, y este es el valor booleano que se devuelve como resultado. 
        Cualquiera sea el resultado, se informa por pantalla antes de finalizar la ejecución del método. */
        Random r = new Random();
        Boolean plantaDestruida = false;
        int ataqueMonstruo = r.nextInt(getAtaque() +1); //valor de ataque +1 porque nextInt no incluye el último valor

        int defensaPlanta = r.nextInt(p.getDefensa());
        if(estaSoleado){
            defensaPlanta -= 2;
            if(defensaPlanta <= 0){
                defensaPlanta = 0;
            } 
        }
        int dano = 0;
        dano += ataqueMonstruo; 
        dano -= defensaPlanta;
        if(dano < 0){
            dano = 0;
        }
        int vidaMonstruo = p.getVida();
        if(vidaMonstruo <=dano){
            System.out.println("El monstruo " + tipo() + " \"" + getId()
                            + "\" destruyó a la planta " + p.tipoPlanta() + " \"" + p.getId() + "\"" );
            plantaDestruida = true;
        }
        else{
            p.setVida(vidaMonstruo - dano);
            System.out.println("El monstruo " + tipo() + " \""+ getId() 
                                + "\" atacó a la planta " + p.tipoPlanta() + " \"" + p.getId() 
                                + "\" por " + dano + " daño. Le queda " + p.getVida() + " de vida.");
        }
        return plantaDestruida;

    }
    
    int atacarBase(){
    /*Función para atacar la base. Devuelve un int que es el daño a restar de la durabilidad de la base. 
    Como la base no tiene capacidad defensiva, diréctamente se inflinge daño igual al ataque del monstruo en un rango entre 0 y su valor de ataque. 
    Si bien las plantas y monstruos atacan a su alrededor, en el caso de la base el ataque no se replica a lo largo de 3 casilleros 
    (arriba izq, izq y abajo izq) de la posición del monstruo. */
        Random r = new Random();
        int ataqueMonstruo = r.nextInt(getAtaque() +1);
        System.out.println("El monstruo " + tipo() + " \""+ getId() 
        + "\" atacó a la base " 
        + " por " + ataqueMonstruo + " daño!");
        return ataqueMonstruo;

    }
    
}
