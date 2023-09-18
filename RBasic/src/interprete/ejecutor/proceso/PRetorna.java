package interprete.ejecutor.proceso;

import interprete.ejecutor.bloque.Bloque;
import interprete.ejecutor.subproceso.SFuncion;

public class PRetorna extends Proceso{
    private final SFuncion funcion;
    public PRetorna(String sentencia, SFuncion funcion, Bloque ejecutor) {
        super(sentencia, ejecutor);
        this.funcion=funcion;
    }
    @Override
    public void procesar() {
        funcion.getFuncion().setValor(super.ejecutor.cambiarVariables(sentencia));
    }    
}