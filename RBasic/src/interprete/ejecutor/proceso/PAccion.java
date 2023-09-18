package interprete.ejecutor.proceso;

import interprete.ejecutor.bloque.Bloque;
import interprete.ejecutor.subproceso.SAccion;

public class PAccion extends Proceso{
    private final SAccion sAccion;
    public PAccion(String sentencia, Bloque ejecutor, SAccion sAccion) {
        super(sentencia, ejecutor);
        this.sAccion=sAccion;
    }
    @Override
    public void procesar() {
        sAccion.setArgumentos(sentencia);
        sAccion.procesar();
    }
}