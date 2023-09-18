package utileria.calculadora;

public final class Calculadora {
    private String cadena;
    private double resultado;
    private Expresion expresion=null;
    protected Calculadora(){
    }
    protected Calculadora setExpresion(String cadena){
        this.cadena=cadena;
        return this;
    }
    protected Calculadora calcular(){
        expresion = new Expresion(cadena);
        resultado = expresion.getValor();
        return this;
    }
    protected String getFormula(){
        return expresion.toString();
    }
    protected double getResultado(){
        return resultado;
    }
    protected String getCadena(){
        return cadena;
    }
}