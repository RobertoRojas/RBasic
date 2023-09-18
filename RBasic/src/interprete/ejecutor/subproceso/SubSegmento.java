package interprete.ejecutor.subproceso;

import diccionario.TPReservada;
import interprete.ejecutor.bloque.Bloque;
import interprete.ejecutor.variables.VBooleano;
import interprete.ejecutor.variables.VCaracter;
import interprete.ejecutor.variables.VDecimal;
import interprete.ejecutor.variables.VEntero;
import interprete.ejecutor.variables.VTexto;
import interprete.ejecutor.variables.Variable;
import java.util.ArrayList;
import java.util.List;
import utileria.Utileria;

public abstract class SubSegmento extends Bloque{
    private final String nombre;
    private List<Variable> argumentos = new ArrayList<>();
    protected String regex;
    public SubSegmento(String nombre, List<Variable> argumentos){
        this.nombre=nombre;
        this.argumentos.addAll(argumentos);
        variables.addAll(argumentos);
        argumentos.stream().forEach((argumento) -> {
            argumento.asignar();
        });
        regex = "^([\\s]*".concat(Utileria.formatRegex(nombre)).concat("[\\s]*[\\(][\\s]*").concat(getArgumentos()).concat("[\\s]*[\\)]").concat("[\\s]*)$");
    }
    public String getNombre(){
        return nombre;
    }
    protected String getArgumentos(){
        String cadena = "";
        for (int i = 0; i < argumentos.size(); i++) {
            if(i==0)cadena = cadena.concat(getSegmento(argumentos.get(i)));
            else cadena = cadena.concat(",").concat(getSegmento(argumentos.get(i)));
        }
        return cadena;
    }
    private String getSegmento(Variable variable){
        if(variable instanceof VTexto)return "[\\\"].*[\\\"]";
        if(variable instanceof VCaracter)return "['].[']";
        if(variable instanceof VEntero)return "[\\d]+";
        if(variable instanceof VDecimal)return "[\\d]+[.][\\d]+";
        if(variable instanceof VBooleano)return "(".concat(TPReservada.VERDADERO.getRegex()).concat("|").concat(TPReservada.FALSO.getRegex()).concat(")");
        return "";
    }
    public void setArgumentos(String argumento){
        argumento = argumento.replaceFirst("^([\\s]*".concat(Utileria.formatRegex(nombre)).concat("[\\s]*[\\(][\\s]*)"), "");
        argumento = argumento.replaceAll("([\\s]*[\\)]".concat("[\\s]*)$"), "");
        argumento = argumento.replaceAll("[\"]", "");
        String tokens[] = argumento.split("[,]");
        for (int i = 0; i < tokens.length; i++) {
            tokens[i]=tokens[i].replaceFirst("^([\\s]+)", "");
            tokens[i]=tokens[i].replaceAll("([\\s]+)$", "");
            argumentos.get(i).setValor(tokens[i]);
        }
    }
    public String getRegex(){
        return regex;
    }
}