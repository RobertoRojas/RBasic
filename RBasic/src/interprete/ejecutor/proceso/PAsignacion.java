package interprete.ejecutor.proceso;

import interprete.ejecutor.bloque.Bloque;
import interprete.ejecutor.variables.VTexto;
import interprete.ejecutor.variables.Variable;
import utileria.Utileria;

public class PAsignacion extends Proceso{
    private final Variable variable;
    public PAsignacion(String sentencia, Variable variable, Bloque ejecutor) {
        super(sentencia, ejecutor);
        this.variable=variable;
        this.variable.asignar();
    }
    @Override
    public void procesar(){
        String aux = "";
        if(variable instanceof VTexto){
            String[] tokens = super.sentencia.split("[+]");
            for (String token : tokens) {
                String interno;
                if (!Utileria.isComitas(token)) {
                    interno = token.replaceAll("[\\s]+", "");
                    interno = super.ejecutor.cambiarVariables(interno);
                } else interno = token;
                interno = interno.replaceFirst("^([\\s]*[\"])", "");
                aux = aux.concat(interno.replaceAll("([\"][\\s]*)$", ""));
            }
        }else aux = super.ejecutor.cambiarVariables(super.sentencia);
        variable.setValor(aux);
    }
}