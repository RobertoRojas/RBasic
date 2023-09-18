package utileria.calculadora;

enum Operacion {
    SUMA(0),RESTA(1),MULTIPLICACION(2),DIVISION(3);
    private final int tipo;
    Operacion(int tipo){
        this.tipo=tipo;
    }
    protected double getResultado(double a, double b){
        switch(tipo){
            case 0:  return a+b;
            case 1:  return a-b;
            case 2:  return a*b;
            default: return a/b;
        }
    }
    @Override
    public String toString(){
        switch(tipo){
            case 0:  return "+";
            case 1:  return "-";
            case 2:  return "*";
            default: return "/";
        }
    }
}