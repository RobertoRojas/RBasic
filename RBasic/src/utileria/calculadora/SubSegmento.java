package utileria.calculadora;

public final class SubSegmento extends Segmento{
    private Segmento a,b;
    private Operacion operacion;
    protected SubSegmento(Segmento a, Segmento b, Operacion operacion){
        super.valor=operacion.getResultado(a.valor, b.valor);
        this.a=a;this.b=b;this.operacion=operacion;
    }
    @Override
    public String toString() {
        return "["+a.toString()+"]["+b.toString()+"]"+operacion.toString();
    }
    @Override
    public void flushSegmento() {
        a.flushSegmento();
        b.flushSegmento();
        a=null;
        b=null;
        operacion = null;
    }
}