package utileria.calculadora;

public final class Numero extends Segmento{
    protected Numero(double valor){
        super.valor=valor;
    }
    @Override
    public String toString() {
        return valor+"";
    }
    @Override
    public void flushSegmento() {
        
    }
}