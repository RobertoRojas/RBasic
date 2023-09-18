package interprete.ejecutor.proceso;

import interprete.ejecutor.bloque.Bloque;
import interprete.ejecutor.subproceso.SFuncion;
import interprete.ejecutor.variables.Variable;

public class PAsignacionFuncion extends Proceso{
    private final Variable variable;
    private final SFuncion funcion;
    public PAsignacionFuncion(String sentencia, Variable variable, SFuncion funcion, Bloque ejecutor) {
        super(sentencia, ejecutor);
        this.variable=variable;
        this.variable.asignar();
        this.funcion=funcion;
    }
    @Override
    public void procesar() {
        funcion.setArgumentos(sentencia);
        funcion.procesar();
        variable.setValor(funcion.getValor());
    }
}