public class Girasol extends Plantas{
    /*Extiende a la clase Plantas. Su constructor asigna los valores del enunciado a los atributos, con un rango de 1. 
    Posee un atributo adicional "turnos" que acumula la cantidad de turnos que van pasando. Se informa de su creación por pantalla. */
    private int turnos = 0;

    Girasol(){
        setCostoTrebol(3);
        setVida(30);
        setAtaque(2);
        setDefensa(5);
        setRango(1);
        System.out.print("Nueva planta girasol ");
    }

    public int getTurnos() {
        return this.turnos;
    }

    public void setTurnos(int turnos) {
        this.turnos = turnos;
    }

    @Override
    public String tipoPlanta(){ //Polimorfismo de la función tipoPlanta para devolver un string que indica que esta planta es de tipo Girasol para mostrar por consola.
        return "Girasol";
    }
    
    public boolean generarTreboles(){
        /*Función que calcula si se acumularon 4 turnos, devuelve un valor booleano que indica el resultado de esa verificación. 
        Si es true, el juego se encarga de aumenta la cantidad de tréboles, y si es false, solo se informa por pantalla el incremento. */
        int turnos = getTurnos();
        if (turnos == 4){
            setTurnos(0);
            return true;
        }
        else{
            setTurnos(turnos + 1);
            System.out.println("Aumenta en +1 la cantidad de turnos del Girasol \"" + getId() + "\" (" + getTurnos() + " turno(s))");
            return false;
        }
    }

}
