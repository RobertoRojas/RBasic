package interprete.ejecutor.subproceso;

import interprete.ejecutor.variables.Variable;
import java.util.List;

public class SAccion extends SubSegmento{
    public SAccion(String nombre, List<Variable> argumentos) {
        super(nombre, argumentos);
    }
    @Override
    public void procesar() {
        acciones.stream().forEach((accione) -> {
            accione.procesar();
        });
    }
}