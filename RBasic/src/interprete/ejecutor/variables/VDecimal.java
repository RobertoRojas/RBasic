package interprete.ejecutor.variables;

import utileria.calculadora.Procesador;

public class VDecimal implements Variable{
    private final String nombre;
    private boolean asignado=false;
    private double valor = 0.0;
    public VDecimal(String nombre) {
        this.nombre=nombre;
    }
    @Override
    public void setValor(String valor){
        double resultado = new Procesador().setExpresion(valor).calcular().getResultado();
        this.valor = resultado;
        asignar();
    }
    @Override
    public String getValor(){
        return String.valueOf(valor);
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