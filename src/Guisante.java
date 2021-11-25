public class Guisante extends Plantas{
/*Extiende a la clase Plantas. Su constructor asigna los valores del enunciado a los atributos, 
con un rango de 3, lo que permite atacar a distancia. Se informa de su creación por pantalla. */

    Guisante(){
        setCostoTrebol(4);
        setVida(35);
        setAtaque(3);
        setDefensa(8);
        setRango(3);
        System.out.print("Nueva planta guisante ");
    }

    public String tipoPlanta(){
        /*Polimorfismo de la función tipoPlanta para devolver un string que indica que esta planta es de tipo Guisante para mostrar por consola. */
        return "Guisante";
    }

    
}
