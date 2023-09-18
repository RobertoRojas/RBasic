package utileria.calculadora;

public abstract class Segmento {
    protected double valor = 0.0;
    protected Segmento(){}
    protected final double getValor(){
        return valor;
    }
    @Override
    public abstract String toString();
    public abstract void flushSegmento();
}