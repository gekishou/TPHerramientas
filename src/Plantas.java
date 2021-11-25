import java.util.Random;

public abstract class Plantas {
    /*Implementa el modelo base para todas las plantas y sus funciones genéricas. 
    Los atributos comunes para todas las plantas son un id String para identificarla, 
    valores int para su costo de tréboles, vida, ataque y defensa, un valor de clase Pos que indica su posición actual 
    y un valor int para su rango de ataque. */
    private String id;
    private int costoTrebol;
    private int vida;
    private int ataque;
    private int defensa;
    private Pos posicion;
    private int rango;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCostoTrebol() {
        return this.costoTrebol;
    }

    public void setCostoTrebol(int costoTrebol) {
        this.costoTrebol = costoTrebol;
    }

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

    public int getRango() {
        return this.rango;
    }

    public void setRango(int rango) {
        this.rango = rango;
    }

    public abstract String tipoPlanta(); //Función abstracta a implementar por las clases hijas. Devuelve el tipo de subclase para informar por pantalla.
    

    public Boolean atacarMonstruo(Monstruos m, Boolean estaLloviendo){ 
        /*Recibe el Monstruo que está siendo atacado como parámetro, así como un valor booleano que indica si está lloviendo o no. 
        Se devuelve como valor de salida un booleano que indica si el monstruo atacado fue destruido o no.
        Primero, se calcula el valor de ataque de la planta con un valor random entre 0 y su valor de ataque. 
        Luego, si el valor estaLloviendo es true, aumenta su valor de ataque. Según el enunciado, este aumento debería ser de 2%, 
        pero como los valores de ataque/defensa/vida no son muy grandes, la diferencia no es significativa, por lo que se decidió aumentar el valor de ataque en +2 en su lugar.
        Se obtiene la defensa del monstruo atacado y se procede a calcular cuánto daño se produce restando la defensa al ataque (con un mínimo de 0 daño). 
        Se calcula si este daño es suficiente para destruir al monstruo, y este es el valor booleano que se devuelve como resultado. 
        Cualquiera sea el resultado, se informa por pantalla antes de finalizar la ejecución del método. */
        Random r = new Random();
        Boolean monstruoDestruido = false;
        int ataquePlanta = r.nextInt(getAtaque() +1); //valor de ataque +1 porque nextInt no incluye el último valor
        if(estaLloviendo){
            ataquePlanta += 2; //NOTA: No se utiliza el incremento del enunciado, 2%, porque los valores son muy chicos para que 2% haga una diferencia. 
                            //Se aumenta el poder de ataque en 2 en su lugar.
        }

        int defensaMonstruo = r.nextInt(m.getDefensa());
        int dano = 0;
        dano += ataquePlanta; 
        dano -= defensaMonstruo;
        if(dano < 0){
            dano = 0;
        }
        int vidaMonstruo = m.getVida();
        if(vidaMonstruo <=dano){
            System.out.println("La planta " + tipoPlanta() + " \"" + getId()
                            + "\" destruyó al monstruo " + m.tipo() + " \"" + m.getId() + "\"" );
            monstruoDestruido = true;
        }
        else{
            m.setVida(vidaMonstruo - dano);
            System.out.println("La planta " + tipoPlanta() + " \""+ getId() 
                                + "\" atacó al monstruo " + m.tipo() + " \"" + m.getId() 
                                + "\" por " + dano + " daño. Le queda " + m.getVida() + " de vida.");
        }
        return monstruoDestruido;

    }

    
}
