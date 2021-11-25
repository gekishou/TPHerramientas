public class PlantaNormal extends Plantas{
    /*Extiende a la clase Plantas. Su constructor asigna los valores del enunciado a los atributos, con un rango de 1. Se informa de su creación por pantalla. */

    public PlantaNormal(){
        setCostoTrebol(3);
        setVida(30);
        setAtaque(10);
        setDefensa(5);
        setRango(1);
        System.out.print("Nueva planta normal ");
    }

    @Override
    public String tipoPlanta(){ //Polimorfismo de la función tipoPlanta para devolver un string que indica que esta planta es de tipo Normal para mostrar por consola.
        return "Normal";
    }


}
