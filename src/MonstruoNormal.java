public class MonstruoNormal extends Monstruos{
    /*Extiende a la clase Monstruos. Su constructor asigna valores a los atributos (vida, ataque, defensa), con un rango de 1. 
    Se eligen los mismos valores que una plantaNormal ya que el jugador cuenta con la ventaja de que sus plantas atacan primero 
    y que puede colocarlas donde desee en el mapa. Se informa de su creación por pantalla. */

    public MonstruoNormal(){
        setVida(30);
        setAtaque(10);
        setDefensa(5);
        setRango(1);
        System.out.print("Nuevo monstruo normal ");


    }

    @Override
    public String tipo(){
    /*Polimorfismo de la función tipo para devolver un string que indica que este monstruo es de tipo Normal para mostrar por consola. */
        return "Normal";
    }
    
}
