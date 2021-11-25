import java.util.HashSet;

// Clase auxiliar para funciones relacionadas a sistemas de coordenadas de la forma (x,y).
// Sus atributos son dos int que representan las coordenadas. Para comparaciones, dos objetos de tipo Pos solo son iguales si ambas coordenadas son iguales.

public class Pos {
    private int x;
    private int y;

    Pos(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public static HashSet<Pos> Vecinos(String[][] mapa, Pos posActual, int rango){ //Recibe una matriz (array de array) de String, una Pos y un int rango. 
                                                                                //Devuelve un Hashset de elementos tipo Pos que contiene todos los Pos 
                                                                                //que estén a "rango" de distancia de la posición actual. 
                                                                                //La matriz se utiliza para verificar que los Pos del conjunto estén dentro del rango.
        HashSet<Pos> vecinos = new HashSet<Pos>();
        int cantFilas = mapa.length;
        int cantColumnas = mapa[0].length;
        int filaActual = posActual.x;
        int columnaActual = posActual.y;

        
        for (int distanciaX = 0; distanciaX <= rango; distanciaX++ ){ //casilleros vecinos hacia el Sur
            for (int distanciaY = 0; distanciaY <= rango; distanciaY++ ){ //casilleros vecinos hacia el Sudeste
                if (filaActual + distanciaX < cantFilas  //si no se pasa del rango hacia el Sur
                    && columnaActual + distanciaY < cantColumnas ){ //y no se pasa del rango hacia el Este
                        Pos nuevaPos = new Pos(filaActual + distanciaX, columnaActual + distanciaY);
                        if (!(nuevaPos.equals(posActual)) ){
                            vecinos.add(nuevaPos);
                        }//agregar a los vecinos
                    }

            }
            for (int distanciaY = 0; distanciaY * (-1) <= rango; distanciaY-- ){ //casilleros vecinos hacia el Sudoeste
                if (filaActual + distanciaX < cantFilas //si no se pasa del rango hacia el Sur

                    && columnaActual + distanciaY >= 0){ //y no se pasa del rango hacia el Oeste
                        Pos nuevaPos = new Pos(filaActual + distanciaX, columnaActual + distanciaY);
                        if (!(nuevaPos.equals(posActual)) ){
                            vecinos.add(nuevaPos);
                        } //agregar a los vecinos
                    }

            }

        }
        for (int distanciaX = 0; distanciaX * (-1) <= rango; distanciaX-- ){ //casilleros vecinos hacia el Norte
            for (int distanciaY = 0; distanciaY < rango; distanciaY++ ){ //casilleros vecinos hacia el Noreste
                if (filaActual + distanciaX >= 0 //si no se pasa del rango hacia el Norte
                    && columnaActual + distanciaY < cantColumnas){ //y no se pasa del rango hacia el Este
                        Pos nuevaPos = new Pos(filaActual + distanciaX, columnaActual + distanciaY);
                        if (!(nuevaPos.equals(posActual)) ){
                            vecinos.add(nuevaPos);
                        }//agregar a los vecinos
                    }

            }
            for (int distanciaY = 0; distanciaY * (-1)<= rango; distanciaY-- ){ //casilleros vecinos hacia el Noroeste
                if (filaActual + distanciaX >= 0 //si no se pasa del rango hacia el Norte
                    && columnaActual + distanciaY >= 0){ //y no se pasa del rango hacia el Oeste
                        Pos nuevaPos = new Pos(filaActual + distanciaX, columnaActual + distanciaY);
                        if (!(nuevaPos.equals(posActual)) ){
                            vecinos.add(nuevaPos);
                        }//agregar a los vecinos
                    }

            }

        }
        return vecinos;
    }

    public static Boolean posOcupada (String[][] mapa, Pos posConsulta){
        return (!(mapa[posConsulta.x][posConsulta.y]).equals(""));
    }

    public static Boolean posValida(Pos validar){

        return (validar.x >=0
                && validar.x <=9
                && validar.y >=0
                && validar.y <= 9    
               );
    }

    public static void agregarAMapa(String[][] mapa, Pos posicion, String dato){ //Escribe un dato String en la coordenada Pos de la matriz de String. 
                                                                                //Se utiliza para mostrar monstruos y plantas en el mapa. Anuncia la modificación.
        mapa[posicion.x][posicion.y] = dato;
        System.out.println("\"" + dato + "\" se agregó a la posición: " + posicion );
    }
    public static void quitarDeMapa(String[][] mapa, Pos posicion, String dato){ //Función opuesta a agregarAMapa, deja vacío el elemento de la matriz y anuncia su eliminación.
        mapa[posicion.x][posicion.y] = "";
        System.out.println("Se eliminó a \"" + dato + "\" en la posición (" + posicion.x + "," + posicion.y + ")!" );
    }

    @Override
    public String toString() {
        
        return "(X: " + this.x 
                + ", Y: " + this.y + ")";
    }

    @Override
    public boolean equals(Object otro){
        if (otro == this){
            return true;
        }
        if (otro == null){
            return false;
        }
        if (!(otro instanceof Pos)){
            return false;

        }
        Pos p = (Pos) otro;
        return (this.x == p.x)
        && (this.y == p.y);


    }
    @Override
    public int hashCode(){
        int hash = 7;
        hash = 31 * hash + x + y;
        hash = 31 * hash + y;
        return hash;
    }
    
}
