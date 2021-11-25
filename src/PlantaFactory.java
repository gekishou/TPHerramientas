public class PlantaFactory{
    //Implemeneta el patrón de diseño de Factory.

    public static Plantas crearPlanta(int tipoPlanta){
        /*Recibe un valor int ingresado por el usuario desde el Juego 
        y llama al constructor de una clase hija de Plantas según la opción elegida, 
        ocultando estos constructores del resto del código. */
        Plantas nuevaPlanta = null;

        if(tipoPlanta == 0){
            return new PlantaNormal();
        }
        else if(tipoPlanta == 1){
            return new Girasol();
        }
        else if(tipoPlanta == 2){
            return new Carnivora();
        }
        else if(tipoPlanta == 3){
            return new Guisante();
        }
        else return nuevaPlanta;
    }

    
}
