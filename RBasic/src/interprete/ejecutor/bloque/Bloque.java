package interprete.ejecutor.bloque;

import interprete.ejecutor.Accion;
import interprete.ejecutor.subproceso.SubSegmento;
import interprete.ejecutor.variables.Variable;
import java.util.ArrayList;
import java.util.List;
import utileria.Utileria;

public abstract class Bloque implements Accion{
    protected List<Variable> variables = new ArrayList<>();
    protected List<Accion> acciones = new ArrayList<>();
    protected List<SubSegmento> metodos = new ArrayList<>();
    public Bloque() {
        super();
    }
    public SubSegmento getMetodo(String sentencia){
        for (SubSegmento metodo : metodos)if (sentencia.matches(metodo.getRegex()))return metodo;    
        return null;
    }
    public void setMetodo(SubSegmento metodo){
        metodos.add(metodo);
    }
    public boolean isMetodo(String sentencia){
        return metodos.stream().anyMatch((metodo) -> (sentencia.matches(metodo.getRegex())));
    }
    public Variable getVariable(String nombre){
        for (Variable variable : variables)if (variable.getNombre().equals(nombre))return variable;
        return null;
    }
    public String cambiarVariables(String cadena){
        for (Variable variable : variables){
            if (variable.isAsignado()){
                cadena = cadena.replaceAll("^([\\s]*".concat(Utileria.formatRegex(variable.getNombre())).concat("[\\s]*)$"), variable.getValor());
                cadena = cadena.replaceAll("^([\\s]*".concat(Utileria.formatRegex(variable.getNombre())).concat("[\\s]+)"), "".concat(variable.getValor()).concat(" "));
                cadena = cadena.replaceAll("[\\s]+".concat(Utileria.formatRegex(variable.getNombre())).concat("[\\s]+"), " ".concat(variable.getValor()).concat(" "));
                cadena = cadena.replaceAll("[\\s]+(".concat(Utileria.formatRegex(variable.getNombre())).concat("[\\s]*)$"), " ".concat(variable.getValor()).concat(""));
            }
        }   
        return cadena;
    }
    public boolean isNombreAsignado(String nombre){
        return variables.stream().anyMatch((variable) -> (variable.getNombre().equals(nombre)));
    }
    public void setVariables(List<Variable> variables){
        variables.stream().forEach((variable) -> {this.variables.add(variable);});
    }
    public List<Variable> getVariables(){
        return variables;
    }
    public void setVariable(Variable variable){
        variables.add(variable);
    }
    public void setAcciones(Accion accion){
        acciones.add(accion);
    }
    @Override
    public abstract void procesar();
}