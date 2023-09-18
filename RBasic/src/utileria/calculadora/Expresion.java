package utileria.calculadora;

import java.util.ArrayList;
import java.util.List;

public final class Expresion extends Segmento{
    private String cadena;
    private List<Segmento> segmentos = new ArrayList<>();
    private List<Operacion> operaciones = new ArrayList<>();
    protected Expresion(String cadena){
        if(cadena.charAt(0)=='+'||cadena.charAt(0)=='-')cadena = "0".concat(cadena);
        this.cadena=cadena;
        super.valor=segmentacion();
    }
    private double segmentacion(){
        int i = 0;
        while(i<cadena.length()){
            int[] aux;
            if(isParentecis(cadena.charAt(i))){
                aux = separarParentecis(i);
                segmentos.add(new Expresion(cadena.substring(aux[0]+1,aux[1]-1)));
                i=aux[1];
            }
            if(i<cadena.length()&&isOperador(cadena.charAt(i))){
                i=separarOperacion(i);
            }
            if(i<cadena.length()&&isNumero(cadena.charAt(i))){
                aux = separarNumero(i);
                segmentos.add(new Numero(Double.parseDouble(cadena.substring(aux[0],aux[1]))));
                i=aux[1];
            }
        }
        return operar();
    }
    private double operar(){
        double aux = segmentos.get(0).valor;
        for (int i = 0; i < operaciones.size(); i++) {
            aux = operaciones.get(i).getResultado(aux, segmentos.get(i+1).valor);
        }
        aux = Operacion.SUMA.getResultado(aux, 0);
        return aux;
    }
    private int separarOperacion(int i){
        Operacion op = getOperador(cadena.charAt(i));
        i++;
        Segmento segmento;
        int[] aux;
        if(isNumero(cadena.charAt(i))){
            aux = separarNumero(i);
            segmento = new Numero(Double.parseDouble(cadena.substring(aux[0],aux[1])));
        }else{
            aux = separarParentecis(i);
            segmento = new Expresion(cadena.substring(aux[0], aux[1]));
        }
        if(op==Operacion.DIVISION||op==Operacion.MULTIPLICACION){
            segmentos.add(new SubSegmento(segmentos.get(segmentos.size()-1), segmento, op));
            segmentos.remove(segmentos.size()-2);
        }else{
            segmentos.add(segmento);
            operaciones.add(op);
        }
        return aux[1];
    }
    private Operacion getOperador(char c){
        switch(c){
            case '+': return Operacion.SUMA;
            case '-': return Operacion.RESTA;
            case '*': return Operacion.MULTIPLICACION;
            default:  return Operacion.DIVISION;
        }
    }
    private int[] separarNumero(int i){
        int j= i;
        while(j<cadena.length()&&isNumero(cadena.charAt(j)))j++;
        return new int[]{i,j};
    }
    private int[] separarParentecis(int i){
        int j = i+1,a = 1,b=0;
        while(a!=b){
            if(cadena.charAt(j)=='(')a++;
            if(cadena.charAt(j)==')')b++;
            j++;
        }
        return new int[]{i,j};
    }
    private boolean isNumero(char c){
        return !(isOperador(c)||isParentecis(c));
    }
    private boolean isParentecis(char c){
        return c=='('||c==')';
    }
    private boolean isOperador(char c){
        return c=='+'||c=='-'||c=='*'||c=='/';
    }
    @Override
    public String toString(){
        String representacion = "";
        representacion = segmentos.stream().map((s) -> s instanceof Numero?"[" + s.toString() + "]":"[("+s.toString()+")]").reduce(representacion, String::concat);
        representacion = operaciones.stream().map((o) -> o.toString()+"").reduce(representacion, String::concat);
        return segmentos.size()>1?"[("+representacion+")][0]+":representacion + "[0]+";
    }

    @Override
    public void flushSegmento() {
        cadena="";
        for (int i = 0; i < segmentos.size(); i++) {
            segmentos.get(i).flushSegmento();
        }
        segmentos = null;
        operaciones = null;
    }
}