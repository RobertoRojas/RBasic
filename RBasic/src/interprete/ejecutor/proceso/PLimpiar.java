package interprete.ejecutor.proceso;

import interprete.Interprete;

public class PLimpiar extends Proceso{
    private final Interprete interprete;
    public PLimpiar(Interprete interprete) {
        super("", null);
        this.interprete=interprete;
    }
    @Override
    public void procesar() {
        interprete.clearConsola();
    } 
}