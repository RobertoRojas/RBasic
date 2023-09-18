package interprete.ejecutor.subproceso;

import interprete.ejecutor.variables.Variable;
import java.util.List;

public class SFuncion extends SubSegmento implements Variable{
    private final Variable funcion;
    public SFuncion(String nombre, List<Variable> argumentos, Variable funcion) {
        super(nombre, argumentos);
        this.funcion=funcion;
        this.funcion.asignar();
    }
    @Override
    public void procesar() {
        acciones.stream().forEach((accione) -> accione.procesar());
    }
    public Variable getFuncion(){
        return funcion;
    }
    @Override
    public String getValor(){
        return funcion.getValor();
    }    
    @Override
    public void asignar() {}

    @Override
    public boolean isAsignado() {
        return true;
    }
    @Override
    public void setValor(String valor) {
        setArgumentos(valor);
    }
}
