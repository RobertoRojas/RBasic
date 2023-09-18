package interprete.ejecutor.proceso;

import interprete.ejecutor.Accion;
import interprete.ejecutor.bloque.Bloque;

public abstract class Proceso implements Accion{
    protected final String sentencia;
    protected final Bloque ejecutor;
    public Proceso(String sentencia, Bloque ejecutor){
        this.sentencia=sentencia;
        this.ejecutor=ejecutor;
    }
    @Override
    public abstract void procesar();
}