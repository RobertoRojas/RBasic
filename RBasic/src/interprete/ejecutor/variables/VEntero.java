package interprete.ejecutor.variables;

import utileria.calculadora.Procesador;

public class VEntero implements Variable{
    private final String nombre;
    private boolean asignado=false;
    private int valor = 0;
    public VEntero(String nombre) {
        this.nombre=nombre;
    }
    @Override
    public void setValor(String valor){
        double resultado = new Procesador().setExpresion(valor).calcular().getResultado();
        this.valor = (int)resultado;
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