package interprete.ejecutor.bloque;

import utileria.calculadoraBooleana.Calculadora;

public class BMientras extends Bloque{
    private final String condicion;
    public BMientras(String condicion){
        this.condicion=condicion;
    }
    @Override
    public void procesar() {
        while(Calculadora.resultado(cambiarVariables(condicion))){
            acciones.stream().forEach((accion) -> accion.procesar());
        }        
    }
}