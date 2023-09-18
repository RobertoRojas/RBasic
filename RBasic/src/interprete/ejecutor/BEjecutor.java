package interprete.ejecutor;

import interprete.ejecutor.bloque.Bloque;

public class BEjecutor extends Bloque{
    @Override
    public void procesar() {
        acciones.stream().forEach((proceso) -> {proceso.procesar();});
    }
}