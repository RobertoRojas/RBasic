package interprete.ejecutor.variables;

import utileria.calculadoraBooleana.Calculadora;
import utileria.Utileria;

public class VBooleano implements Variable{
    private final String nombre;
    private boolean asignado=false;
    private boolean valor = true;
    public VBooleano(String nombre) {
        this.nombre=nombre;
    }
    @Override
    public void setValor(String valor){
        this.valor = Calculadora.resultado(valor);
        asignar();
    }
    @Override
    public String getValor(){
        return Utileria.booleanoToString(valor);
    }
    @Override
    public String getNombre() {
        return nombre;
    }
    @Override
    public void asignar() {
        asignado=true;
    }
    @Override
    public boolean isAsignado() {
        return asignado;
    }
}