public class Explosivo extends Monstruos{

    public Explosivo(){
        setVida(10);
        setAtaque(50);
        setDefensa(3);
        setRango(1);
        System.out.print("Nuevo monstruo explosivo ");     

    }

    public void anunciarMovimiento(){
        System.out.println("El monstruo explosivo \"" + getId() + "\" avanzó al casillero " + getPosicion() + "." );
    }

    public String tipo(){
        return "Explosivo";
    }
    
}
