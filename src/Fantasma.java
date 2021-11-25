import java.util.Random;

public class Fantasma extends Monstruos{
    /*Extiende a la clase Monstruos. Su constructor asigna valores a los atributos (vida, ataque, defensa), con un rango de 1. 
    Se informa de su creación por pantalla. El fantasma es el tipo más débil de monstruo pero tiene una interacción especial cuando es atacado: Con una probabilidad de 50%, 
    se vuelve incorpóreo y evita recibir daño. Para esto, implementa un valor booleano esquivaAtaque que es seteado al azar cada vez que es atacado. */

    Boolean esquivaAtaque = false;

    public Fantasma(){
        setVida(20);
        setAtaque(10);
        setDefensa(1);
        setRango(2);
        System.out.print("Nuevo monstruo fantasma");      

    }

    public Boolean isEsquivaAtaque() {
        return this.esquivaAtaque;
    }

    public void setEsquivaAtaque() {
        /*Método que setea el valor booleano esquivaAtaque al azar. */
        Random r = new Random();
        
        this.esquivaAtaque = r.nextBoolean();
    }

    public void incorporeo(Plantas p){
        /*Método para informar por pantalla que el fantasma se volvió incorpóreo y esquivó el ataque de la planta enviada como parámetro. */
        System.out.println("El fantasma \"" + getId() + "\" se volvió incorpóreo y esquivó el ataque de la planta \"" + p.getId() + "\"!");

    }

    @Override
    public String tipo(){
        /*Polimorfismo de la función tipo para devolver un string que indica que este monstruo es de tipo Normal para mostrar por consola. */
        return "Fantasma";
    }


    
}
