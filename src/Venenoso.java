public class Venenoso extends Monstruos{
    /*Extiende a la clase Monstruos. Su constructor asigna valores a los atributos (vida, ataque, defensa), con un rango de 5. 
    El monstruo venenoso es ligéramente más débil que un monstruo normal pero tiene un gran rango de ataque pensado como una nube venenosa. 
    Se informa de su creación por pantalla. */

    public Venenoso(){
        setVida(20);
        setAtaque(8);
        setDefensa(3);
        setRango(5);
        System.out.print("Nuevo monstruo venenoso ");      

    }

    @Override
    public String tipo(){
        return "Venenoso";
    }    
}
