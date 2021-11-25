public class Carnivora extends Plantas{
    /*Extiende a la clase Plantas. Su constructor asigna los valores del enunciado a los atributos, con un rango de 1. 
    Posee un atributo adicional "turnos" que acumula la cantidad de turnos que van pasando. Se informa de su creación por pantalla.
    No posee un valor de ataque ya que destruye monstruos automáticamente cuando pasan suficientes turnos. */
    private int turnos = 0;

    Carnivora(){
        setCostoTrebol(6);
        setVida(50);
        setDefensa(9);
        setRango(1);
        System.out.print("Nueva planta carnívora ");
    }

    public int getTurnos() {
        return this.turnos;
    }

    public void setTurnos(int turnos) {
        this.turnos = turnos;
    }
    public void pasarTurno() {//Acumula turnos intérnamente e informa el avance por pantalla. Si pasan 4 turnos se informa que está lista para atacar.
        if(this.turnos < 4){
            this.turnos += 1;
            System.out.println("La planta carnívora \"" + getId() + "\" avanzó su contador en 1 turno. (" + this.turnos + " turno(s))");
            if(this.turnos == 4){
                System.out.println("Ya está lista para destruir monstruos automáticamente!");
            }

        }

    }
    @Override
    public String tipoPlanta(){
        /*Polimorfismo de la función tipoPlanta para devolver un string que indica que esta planta es de tipo Carnívora para mostrar por consola. */
        return "Carnivora";
    }

    @Override
    public Boolean atacarMonstruo(Monstruos m, Boolean estaLloviendo){
        /*Polimorfismo de la función de ataque. En lugar de calcular el daño, devuelve true si pasaron suficientes turnos (e informa por pantalla) o false en caso contrario. */
        int turnos = getTurnos();
        if(turnos == 4){
            System.out.println("La planta carnívora" + " \""+ getId()
                            + "\" destruyó al monstruo \"" + m.getId() + "\" automáticamente" );
            setTurnos(0);
            return true;
        }
        else{
            return false;
        }

    }

}
