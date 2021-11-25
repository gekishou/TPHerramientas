public class MonstruoFactory {
    /*Implemeneta el patrón de diseño de Factory. */
    public static Monstruos crearMonstruos(int tipoMonstruo){
        /*Recibe un valor int ingresado por el usuario desde el Juego y llama al constructor de una clase hija de Monstruos según la opción elegida, 
        ocultando estos constructores del resto del código. */
        Monstruos nuevoMonstruo = null;
        if(tipoMonstruo == 0){
            return new MonstruoNormal();
        }
        else if(tipoMonstruo == 1){
            return new Venenoso();
        }
        else if(tipoMonstruo == 2){
            return new Fantasma();
        }
        else if(tipoMonstruo == 3){
            return new Titan();
        }
        else return nuevoMonstruo;  
    }
}

