import java.util.Random;

public class Titan extends Monstruos{
    /*Extiende a la clase Monstruos. Su constructor asigna valores a los atributos (vida, ataque, defensa), con un rango de 3. Se informa de su creación por pantalla. 
    El titán posee atributos muy elevados y ataca en un rango de 3 casilleros pero debe cargar sus ataques por 4 turnos (puede acumular varios). 
    Como tiene mucha defensa, no recibe daño de plantas Girasol o Guisante, debe ser atacado por plantas Normales, que tienen pocas chances de hacerle daño, 
    o colocando una planta Carnívora estratégicamente.  */
    int cargaAtaque = 0;

    public Titan(){
        setVida(100);
        setAtaque(80);
        setDefensa(8);
        setRango(3);
        System.out.print("Nuevo monstruo titán ");      

    }

    public int getCargaAtaque() {
        return this.cargaAtaque;
    }

    public void cargar() {
        /*Este método provee 1 carga para los ataques del titán. 
        Se informa por pantalla del progreso, y si tiene por lo menos 4 cargas se informa que está listo para atacar porque inflinge mucho daño. */
        this.cargaAtaque += 1;
        System.out.println("El titán \"" + getId() + "\" ganó 1 carga. (" + cargaAtaque + " cargas)");
        if(this.getCargaAtaque() >= 4){
            System.out.println("Ya está listo para atacar!!!");
        }
    }

    @Override
    public String tipo(){
        /*Polimorfismo de la función tipo para devolver un string que indica que este monstruo es de tipo Titán para mostrar por consola. */
        return "Titán";
    }

    @Override
    public Boolean atacarPlanta(Plantas p, Boolean estaSoleado){
        /*Sobreescribe la función para atacar agregando la lógica de cargas de ataque. Si no tiene por lo menos 4 cargas, 
        se informa que el titán no puede atacar a los objetivos visibles. Luego de atacar, se consumen 4 cargas. */
        if(cargaAtaque <=3){
            System.out.println("El titán \"" + getId() + "\" todavía no está cargado para atacar a " + p.getId() +  ". (faltan " + (4 - cargaAtaque) + " carga(s))");
            return false;

        }
        else{
            Random r = new Random();
            Boolean plantaDestruida = false;
            int ataqueMonstruo = r.nextInt(getAtaque() +1);
    
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
            cargaAtaque -= 4;
            return plantaDestruida;

        }


    }

}
