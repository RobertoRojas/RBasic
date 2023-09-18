package utileria.calculadora;

import java.util.regex.Pattern;

public final class Evaluador {
    public final String REGEXP_PARENTECIS = "([\\(][\\)]|[\\+\\-\\*/][\\)]|[\\(][\\+\\-\\*/])";   
    private String expresion;
    private String expresionProcesada;
    protected Evaluador(){}
    protected String getExpresionProcesada(){
        return expresionProcesada;
    }
    protected void setExpresion(String expresion){
        expresion = Pattern.compile(" ").matcher(expresion).replaceAll("");
        expresion = Pattern.compile("[\\)][\\(]").matcher(expresion).replaceAll(")*(");
        this.expresion=expresion;
        this.expresion = modCadena();
        expresionProcesada=this.expresion;
    }
    private String modCadena(){
        String aux = Pattern.compile("[\\d][\\(]").matcher(expresion).replaceAll("#*(");
        aux = Pattern.compile("[\\)][\\d#]").matcher(aux).replaceAll(")*#");
        int i = 0,j = 0;
        while(i<expresion.length()){
            if(expresion.charAt(i)!=aux.charAt(j)){
                if(aux.charAt(j)!='#')j++;
                aux = aux.substring(0, j) + expresion.charAt(i) + aux.substring(j+1);
            }
            i++;j++;
        }
        return aux;
    }
}