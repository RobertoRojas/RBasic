package utileria.calculadora;

public final class Procesador{
    private String expresion  = "";
    private double resultado = 0;
    private final Evaluador evaluador = new Evaluador();
    private final Calculadora calculadora = new Calculadora();
    public Procesador setExpresion(String expresion){
        evaluador.setExpresion(expresion);
        this.expresion=evaluador.getExpresionProcesada();
        return this;
    }
    public String getCadena(){
        return expresion;
    }
    public double getResultado(){
        return resultado;
    }
    public String getCalculoRobertiano(){
        return calculadora.getFormula();
    }
    public Procesador calcular(){
        resultado = calculadora.setExpresion(expresion).calcular().getResultado();
        return this;
    }
}
