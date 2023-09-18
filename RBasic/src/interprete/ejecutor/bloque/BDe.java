package interprete.ejecutor.bloque;

import diccionario.TPReservada;
import interprete.ejecutor.variables.VEntero;
import utileria.calculadoraBooleana.Calculadora;
import utileria.calculadora.Procesador;

public class BDe extends Bloque{
    private final VEntero variable;
    private String inicial, condicion, paso;
    public BDe(VEntero variable, String inicial){
        this.variable=variable;
        this.inicial=inicial;
        variable.asignar();
        variables.add(this.variable);
    }
    public void setCondicion(String condicion){
        this.condicion=condicion;
    }
    private String setTipo(String condicion){
        return Integer.parseInt(variable.getValor())<= new Procesador().setExpresion(cambiarVariables(condicion)).calcular().getResultado()?TPReservada.MENORQUE.toString():TPReservada.MAYORQUE.toString();
    }
    public void setPaso(String paso){
        this.paso=paso;
    }
    @Override
    public void procesar() {
        variable.setValor(cambiarVariables(inicial));
        condicion=variable.getNombre().concat(" ").concat(setTipo(condicion)).concat(" ").concat(condicion);
        while(Calculadora.resultado(cambiarVariables(condicion))){
            acciones.stream().forEach((accion) -> accion.procesar());
            variable.setValor(cambiarVariables(paso));
        }    
    }
}